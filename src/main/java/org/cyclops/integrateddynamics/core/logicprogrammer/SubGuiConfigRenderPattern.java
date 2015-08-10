package org.cyclops.integrateddynamics.core.logicprogrammer;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import org.apache.commons.lang3.tuple.Pair;
import org.cyclops.cyclopscore.helper.RenderHelpers;
import org.cyclops.integrateddynamics.client.gui.GuiLogicProgrammer;
import org.cyclops.integrateddynamics.core.client.gui.subgui.SubGuiBox;
import org.cyclops.integrateddynamics.core.evaluate.operator.IConfigRenderPattern;
import org.cyclops.integrateddynamics.inventory.container.ContainerLogicProgrammer;

/**
 * Sub gui for logic programmer elements.
 * @author rubensworks
 */
public class SubGuiConfigRenderPattern<E extends ILogicProgrammerElement> extends SubGuiBox {

    protected final E element;
    private final int x, y;
    protected final GuiLogicProgrammer gui;
    protected final ContainerLogicProgrammer container;

    public SubGuiConfigRenderPattern(E element, int baseX, int baseY, int maxWidth, int maxHeight,
                                     GuiLogicProgrammer gui, ContainerLogicProgrammer container) {
        super(SubGuiBox.Box.LIGHT);
        this.element = element;
        IConfigRenderPattern configRenderPattern = element.getRenderPattern();
        this.x = baseX + (maxWidth  - configRenderPattern.getWidth()) / 2;
        this.y = baseY + (maxHeight - configRenderPattern.getHeight()) / 2;
        this.gui = gui;
        this.container = container;
    }

    protected void drawSlot(int x, int y) {
        this.drawTexturedModalRect(x, y, 3, 0, 18, 18);
    }

    @Override
    public void initGui(int guiLeft, int guiTop) {

    }

    @Override
    public void drawGuiContainerBackgroundLayer(int guiLeft, int guiTop, TextureManager textureManager, FontRenderer fontRenderer, float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(guiLeft, guiTop, textureManager, fontRenderer, partialTicks, mouseX, mouseY);
        IConfigRenderPattern configRenderPattern = element.getRenderPattern();

        int baseX = getX() + guiLeft;
        int baseY = getY() + guiTop;

        for(Pair<Integer, Integer> slot : configRenderPattern.getSlotPositions()) {
            drawSlot(baseX + slot.getLeft(), baseY + slot.getRight());
        }

        if(configRenderPattern.getSymbolPosition() != null) {
            int width = fontRenderer.getStringWidth(element.getSymbol());
            RenderHelpers.drawScaledCenteredString(fontRenderer, element.getSymbol(),
                    baseX + configRenderPattern.getSymbolPosition().getLeft(),
                    baseY + configRenderPattern.getSymbolPosition().getRight() + 8,
                    width, 1, 0);
        }
        GlStateManager.color(1, 1, 1);
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    protected int getWidth() {
        return element.getRenderPattern().getWidth();
    }

    @Override
    protected int getHeight() {
        return element.getRenderPattern().getHeight();
    }

}
