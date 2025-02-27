package org.cyclops.integrateddynamics.core.network;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.Direction;
import org.cyclops.cyclopscore.datastructure.CompositeMap;
import org.cyclops.cyclopscore.datastructure.DimPos;
import org.cyclops.cyclopscore.helper.BlockEntityHelpers;
import org.cyclops.integrateddynamics.Capabilities;
import org.cyclops.integrateddynamics.IntegratedDynamics;
import org.cyclops.integrateddynamics.api.block.IVariableContainer;
import org.cyclops.integrateddynamics.api.evaluate.variable.IValue;
import org.cyclops.integrateddynamics.api.evaluate.variable.IVariable;
import org.cyclops.integrateddynamics.api.item.IVariableFacade;
import org.cyclops.integrateddynamics.api.network.FullNetworkListenerAdapter;
import org.cyclops.integrateddynamics.api.network.INetwork;
import org.cyclops.integrateddynamics.api.network.INetworkElement;
import org.cyclops.integrateddynamics.api.network.IPartNetwork;
import org.cyclops.integrateddynamics.api.part.IPartContainer;
import org.cyclops.integrateddynamics.api.part.IPartState;
import org.cyclops.integrateddynamics.api.part.IPartType;
import org.cyclops.integrateddynamics.api.part.PartPos;
import org.cyclops.integrateddynamics.api.part.PartTarget;
import org.cyclops.integrateddynamics.api.part.aspect.IAspectRead;
import org.cyclops.integrateddynamics.api.part.read.IPartStateReader;
import org.cyclops.integrateddynamics.api.part.read.IPartTypeReader;
import org.cyclops.integrateddynamics.api.path.IPathElement;
import org.cyclops.integrateddynamics.core.helper.PartHelpers;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A network that can hold parts.
 * Note that this network only contains references to the relevant data, it does not contain the actual information.
 * @author rubensworks
 */
public class PartNetwork extends FullNetworkListenerAdapter implements IPartNetwork {

    @Getter
    @Setter
    private INetwork network;
    private Int2ObjectMap<PartPos> partPositions = new Int2ObjectOpenHashMap<>();
    private List<DimPos> variableContainerPositions = Lists.newArrayList();
    private Map<Integer, IVariableFacade> compositeVariableCache = null;
    private Int2ObjectMap<IValue> lazyExpressionValueCache = new Int2ObjectOpenHashMap<>();
    private Int2ObjectMap<DimPos> proxyPositions = new Int2ObjectOpenHashMap<>();

    private volatile boolean partsChanged = false;

    @Override
    public boolean addPart(int partId, PartPos partPos) {
        if(partPositions.containsKey(partId)) {
            return false;
        }
        compositeVariableCache = null;
        partPositions.put(partId, partPos);
        return true;
    }

    @Override
    public IPartState getPartState(int partId) {
        PartPos partPos = partPositions.get(partId);
        return PartHelpers.getPartContainerChecked(partPos.getPos(), partPos.getSide()).getPartState(partPos.getSide());
    }

    @Override
    public IPartType getPartType(int partId) {
        PartPos partPos = partPositions.get(partId);
        return PartHelpers.getPartContainerChecked(partPos.getPos(), partPos.getSide()).getPart(partPos.getSide());
    }

    @Override
    public void removePart(int partId) {
        compositeVariableCache = null;
        partPositions.remove(partId);
    }

    @Override
    public boolean hasPart(int partId) {
        if(!partPositions.containsKey(partId)) {
            return false;
        }
        PartPos partPos = partPositions.get(partId);
        return PartHelpers.getPartContainer(partPos.getPos(), partPos.getSide())
                .map(partContainer -> partContainer.hasPart(partPos.getSide()))
                .orElse(false);
    }

    @Override
    public <V extends IValue> boolean hasPartVariable(int partId, IAspectRead<V, ?> aspect) {
        if(!hasPart(partId)) {
            return false;
        }
        IPartState partState = getPartState(partId);
        if(!(partState instanceof IPartStateReader)) {
            return false;
        }
        IPartType partType = getPartType(partId);
        if(!(partType instanceof IPartTypeReader)) {
            return false;
        }
        try {
            return ((IPartTypeReader) partType).getVariable(partType.getTarget(partPositions.get(partId), partState),
                    (IPartStateReader) partState, aspect) != null;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public <V extends IValue> IVariable<V> getPartVariable(int partId, IAspectRead<V, ?> aspect) {
        return ((IPartStateReader) getPartState(partId)).getVariable(aspect);
    }

    protected Map<Integer, IVariableFacade> getVariableCache() {
        if(compositeVariableCache == null) {
            // Create a new composite map view on the existing variable containers in this network.
            CompositeMap<Integer, IVariableFacade> compositeMap = new CompositeMap<>();
            for(Iterator<DimPos> it = variableContainerPositions.iterator(); it.hasNext();) {
                DimPos dimPos = it.next();
                if (dimPos.isLoaded()) {
                    IVariableContainer variableContainer = BlockEntityHelpers.getCapability(dimPos, null, Capabilities.VariableContainer.BLOCK).orElse(null);
                    if (variableContainer != null) {
                        compositeMap.addElement(variableContainer.getVariableCache());
                    } else {
                        IntegratedDynamics.clog(org.apache.logging.log4j.Level.ERROR, "The variable container at " + dimPos + " was invalid, skipping.");
                        it.remove();
                    }
                }
            }
            // Also check parts
            for(PartPos partPos : partPositions.values()) {
                if (partPos.getPos().isLoaded()) {
                    IPartContainer partContainer = PartHelpers.getPartContainerChecked(partPos.getPos(), partPos.getSide());
                    partContainer.getCapability(Capabilities.VariableContainer.PART, network, this, PartTarget.fromCenter(partPos))
                            .ifPresent(variableContainer -> compositeMap.addElement(variableContainer.getVariableCache()));
                }
            }
            compositeVariableCache = compositeMap;
        }
        return compositeVariableCache;
    }

    @Override
    public boolean hasVariableFacade(int variableId) {
        return getVariableCache().containsKey(variableId);
    }

    @Override
    public IVariableFacade getVariableFacade(int variableId) {
        return getVariableCache().get(variableId);
    }

    @Override
    public void setValue(int id, IValue value) {
        lazyExpressionValueCache.put(id, value);
    }

    @Override
    public boolean hasValue(int id) {
        return lazyExpressionValueCache.containsKey(id);
    }

    @Override
    public IValue getValue(int id) {
        return lazyExpressionValueCache.get(id);
    }

    @Override
    public void removeValue(int id) {
        lazyExpressionValueCache.remove(id);
    }

    @Override
    public boolean addVariableContainer(DimPos dimPos) {
        compositeVariableCache = null;
        return variableContainerPositions.add(dimPos);
    }

    @Override
    public void removeVariableContainer(DimPos dimPos) {
        compositeVariableCache = null;
        variableContainerPositions.remove(dimPos);
    }

    @Override
    public boolean addProxy(int proxyId, DimPos dimPos) {
        if(proxyPositions.containsKey(proxyId)) {
            return false;
        }
        proxyPositions.put(proxyId, dimPos);
        return true;
    }

    @Override
    public void removeProxy(int proxyId) {
        proxyPositions.remove(proxyId);
    }

    @Override
    public DimPos getProxy(int proxyId) {
        return proxyPositions.get(proxyId);
    }

    @Override
    public void notifyPartsChanged() {
        this.partsChanged = true;
    }

    private void onPartsChanged() {

    }

    @Override
    public void update() {
        // Signal parts of any changes
        if (partsChanged) {
            this.partsChanged = false;
            onPartsChanged();
        }
    }

    @Override
    public boolean removePathElement(IPathElement pathElement, Direction side) {
        notifyPartsChanged();
        return true;
    }

    @Override
    public void invalidateElement(INetworkElement element) {
        compositeVariableCache = null;
        super.invalidateElement(element);
    }

    @Override
    public void revalidateElement(INetworkElement element) {
        compositeVariableCache = null;
        super.revalidateElement(element);
    }
}
