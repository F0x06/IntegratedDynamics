package org.cyclops.integrateddynamics;

import org.cyclops.cyclopscore.config.ConfigHandler;
import org.cyclops.integrateddynamics.advancement.criterion.NetworkInitializedTriggerConfig;
import org.cyclops.integrateddynamics.advancement.criterion.PartReaderAspectTriggerConfig;
import org.cyclops.integrateddynamics.advancement.criterion.PartVariableDrivenAspectTriggerConfig;
import org.cyclops.integrateddynamics.advancement.criterion.PartWriterAspectTriggerConfig;
import org.cyclops.integrateddynamics.advancement.criterion.VariableCreatedTriggerConfig;
import org.cyclops.integrateddynamics.block.*;
import org.cyclops.integrateddynamics.blockentity.*;
import org.cyclops.integrateddynamics.component.*;
import org.cyclops.integrateddynamics.core.blockentity.BlockEntityMultipartTickingConfig;
import org.cyclops.integrateddynamics.core.inventory.container.ContainerAspectSettingsConfig;
import org.cyclops.integrateddynamics.core.inventory.container.ContainerPartOffsetConfig;
import org.cyclops.integrateddynamics.core.inventory.container.ContainerPartSettingsConfig;
import org.cyclops.integrateddynamics.core.recipe.type.*;
import org.cyclops.integrateddynamics.entity.item.EntityItemTargettedConfig;
import org.cyclops.integrateddynamics.fluid.FluidLiquidChorusConfig;
import org.cyclops.integrateddynamics.fluid.FluidMenrilResinConfig;
import org.cyclops.integrateddynamics.inventory.container.*;
import org.cyclops.integrateddynamics.item.*;
import org.cyclops.integrateddynamics.loot.conditions.LootConditionMatchWrenchConfig;
import org.cyclops.integrateddynamics.loot.functions.LootFunctionCopyEnergyBatteryDataConfig;
import org.cyclops.integrateddynamics.loot.functions.LootFunctionCopyMechanicalDryingBasinTanksConfig;
import org.cyclops.integrateddynamics.loot.functions.LootFunctionCopyMechanicalMachineEnergyConfig;
import org.cyclops.integrateddynamics.loot.functions.LootFunctionCopyMechanicalSqueezerTankConfig;
import org.cyclops.integrateddynamics.loot.functions.LootFunctionCopyProxyIdConfig;
import org.cyclops.integrateddynamics.recipe.ItemFacadeRecipeConfig;
import org.cyclops.integrateddynamics.recipe.ItemVariableCopyRecipeConfig;
import org.cyclops.integrateddynamics.recipe.RecipeSerializerCraftingSpecialShapedOmniDirectional3Config;
import org.cyclops.integrateddynamics.recipe.RecipeSerializerCraftingSpecialShapedOmniDirectionalConfig;
import org.cyclops.integrateddynamics.recipe.RecipeSerializerCraftingSpecialShapelessOmniDirectionalConfig;
import org.cyclops.integrateddynamics.sound.SoundEventEffectPageFlipMultipleConfig;
import org.cyclops.integrateddynamics.sound.SoundEventEffectPageFlipSingleConfig;
import org.cyclops.integrateddynamics.world.gen.foliageplacer.FoliagePlacerMenrilConfig;
import org.cyclops.integrateddynamics.world.gen.trunkplacer.TrunkPlacerMenrilConfig;

/**
 * This class holds a set of all the configs that need to be registered.
 * @author rubensworks
 */
public class Configs {

    public static void registerBlocks(ConfigHandler configHandler) {

        // Fluids
        configHandler.addConfigurable(new FluidMenrilResinConfig());
        configHandler.addConfigurable(new FluidLiquidChorusConfig());

        // Tile entities
        configHandler.addConfigurable(new BlockEntityCoalGeneratorConfig());
        configHandler.addConfigurable(new BlockEntityDelayConfig());
        configHandler.addConfigurable(new BlockEntityDryingBasinConfig());
        configHandler.addConfigurable(new BlockEntityEnergyBatteryConfig());
        configHandler.addConfigurable(new BlockEntityMaterializerConfig());
        configHandler.addConfigurable(new BlockEntityMechanicalDryingBasinConfig());
        configHandler.addConfigurable(new BlockEntityMechanicalSqueezerConfig());
        configHandler.addConfigurable(new BlockEntityMultipartTickingConfig());
        configHandler.addConfigurable(new BlockEntityProxyConfig());
        configHandler.addConfigurable(new BlockEntitySqueezerConfig());
        configHandler.addConfigurable(new BlockEntityVariableStoreConfig());

        // Blocks
        configHandler.addConfigurable(new BlockCableConfig());
        configHandler.addConfigurable(new BlockVariablestoreConfig());
        configHandler.addConfigurable(new BlockLogicProgrammerConfig());
        configHandler.addConfigurable(new BlockInvisibleLightConfig());
        configHandler.addConfigurable(new BlockEnergyBatteryConfig());
        configHandler.addConfigurable(new BlockCreativeEnergyBatteryConfig());
        configHandler.addConfigurable(new BlockCoalGeneratorConfig());
        configHandler.addConfigurable(new BlockProxyConfig());
        configHandler.addConfigurable(new BlockMaterializerConfig());
        configHandler.addConfigurable(new BlockMenrilLogConfig());
        configHandler.addConfigurable(new BlockMenrilLogStrippedConfig());
        configHandler.addConfigurable(new BlockMenrilWoodConfig());
        configHandler.addConfigurable(new BlockMenrilWoodStrippedConfig());
        configHandler.addConfigurable(new BlockMenrilLogFilledConfig());
        configHandler.addConfigurable(new BlockMenrilLeavesConfig());
        configHandler.addConfigurable(new BlockMenrilSaplingConfig());
        configHandler.addConfigurable(new BlockMenrilPlanksConfig());
        configHandler.addConfigurable(new BlockMenrilSlabConfig());
        configHandler.addConfigurable(new BlockMenrilFenceConfig());
        configHandler.addConfigurable(new BlockMenrilFenceGateConfig());
        configHandler.addConfigurable(new BlockCrystalizedMenrilBlockConfig());
        configHandler.addConfigurable(new BlockCrystalizedMenrilBrickConfig());
        configHandler.addConfigurable(new BlockFluidMenrilResinConfig());
        configHandler.addConfigurable(new BlockDryingBasinConfig());
        configHandler.addConfigurable(new BlockSqueezerConfig());
        configHandler.addConfigurable(new BlockMenrilDoorConfig());
        configHandler.addConfigurable(new BlockMenrilTorchWallConfig());
        configHandler.addConfigurable(new BlockMenrilTorchConfig());
        configHandler.addConfigurable(new BlockMenrilTorchStoneWallConfig());
        configHandler.addConfigurable(new BlockMenrilTorchStoneConfig());
        configHandler.addConfigurable(new BlockMenrilPlanksStairsConfig());
        configHandler.addConfigurable(new BlockCrystalizedMenrilBlockStairsConfig());
        configHandler.addConfigurable(new BlockCrystalizedMenrilBrickStairsConfig());
        configHandler.addConfigurable(new BlockDelayConfig());
        configHandler.addConfigurable(new BlockFluidLiquidChorusConfig());
        configHandler.addConfigurable(new BlockCrystalizedChorusBlockConfig());
        configHandler.addConfigurable(new BlockCrystalizedChorusBrickConfig());
        configHandler.addConfigurable(new BlockCrystalizedChorusBlockStairsConfig());
        configHandler.addConfigurable(new BlockCrystalizedChorusBrickStairsConfig());
        configHandler.addConfigurable(new BlockMechanicalSqueezerConfig());
        configHandler.addConfigurable(new BlockMechanicalDryingBasinConfig());
        configHandler.addConfigurable(new BlockCrystalizedMenrilBlockSlabConfig());
        configHandler.addConfigurable(new BlockCrystalizedMenrilBrickSlabConfig());
        configHandler.addConfigurable(new BlockCrystalizedChorusBlockSlabConfig());
        configHandler.addConfigurable(new BlockCrystalizedChorusBrickSlabConfig());

        // Items
        configHandler.addConfigurable(new ItemBucketLiquidChorusConfig());
        configHandler.addConfigurable(new ItemBucketMenrilResinConfig());
        configHandler.addConfigurable(new ItemWrenchConfig());
        configHandler.addConfigurable(new ItemVariableConfig());
        configHandler.addConfigurable(new ItemLabellerConfig());
        configHandler.addConfigurable(new ItemFacadeConfig());
        configHandler.addConfigurable(new ItemCrystalizedMenrilChunkConfig());
        configHandler.addConfigurable(new ItemVariableTransformerConfig(true));
        configHandler.addConfigurable(new ItemVariableTransformerConfig(false));
        configHandler.addConfigurable(new ItemMenrilBerriesConfig());
        configHandler.addConfigurable(new ItemPortableLogicProgrammerConfig());
        configHandler.addConfigurable(new ItemOnTheDynamicsOfIntegrationConfig());
        configHandler.addConfigurable(new ItemCrystalizedChorusChunkConfig());
        configHandler.addConfigurable(new ItemLogicDirectorConfig());
        configHandler.addConfigurable(new ItemProtoChorusConfig());
        configHandler.addConfigurable(new ItemEnhancementOffsetConfig());

        // World features
        configHandler.addConfigurable(new FoliagePlacerMenrilConfig());
        configHandler.addConfigurable(new TrunkPlacerMenrilConfig());

        // Entities
        configHandler.addConfigurable(new EntityItemTargettedConfig());

        // Guis
        configHandler.addConfigurable(new ContainerAspectSettingsConfig());
        configHandler.addConfigurable(new ContainerCoalGeneratorConfig());
        configHandler.addConfigurable(new ContainerDelayConfig());
        configHandler.addConfigurable(new ContainerLabellerConfig());
        configHandler.addConfigurable(new ContainerLogicProgrammerConfig());
        configHandler.addConfigurable(new ContainerLogicProgrammerPortableConfig());
        configHandler.addConfigurable(new ContainerMaterializerConfig());
        configHandler.addConfigurable(new ContainerMechanicalDryingBasinConfig());
        configHandler.addConfigurable(new ContainerMechanicalSqueezerConfig());
        configHandler.addConfigurable(new ContainerOnTheDynamicsOfIntegrationConfig());
        configHandler.addConfigurable(new ContainerPartDisplayConfig());
        configHandler.addConfigurable(new ContainerPartReaderConfig());
        configHandler.addConfigurable(new ContainerPartSettingsConfig());
        configHandler.addConfigurable(new ContainerPartOffsetConfig());
        configHandler.addConfigurable(new ContainerPartWriterConfig());
        configHandler.addConfigurable(new ContainerProxyConfig());
        configHandler.addConfigurable(new ContainerVariablestoreConfig());

        // Recipe types
        configHandler.addConfigurable(new RecipeTypeDryingBasinConfig());
        configHandler.addConfigurable(new RecipeTypeMechanicalDryingBasinConfig());
        configHandler.addConfigurable(new RecipeTypeSqueezerConfig());
        configHandler.addConfigurable(new RecipeTypeMechanicalSqueezerConfig());

        // Recipes
        configHandler.addConfigurable(new RecipeSerializerDryingBasinConfig());
        configHandler.addConfigurable(new RecipeSerializerMechanicalDryingBasinConfig());
        configHandler.addConfigurable(new RecipeSerializerSqueezerConfig());
        configHandler.addConfigurable(new RecipeSerializerMechanicalSqueezerConfig());
        configHandler.addConfigurable(new RecipeSerializerNbtClearConfig());
        configHandler.addConfigurable(new RecipeEnergyContainerCombinationConfig());
        configHandler.addConfigurable(new ItemVariableCopyRecipeConfig());
        configHandler.addConfigurable(new ItemFacadeRecipeConfig());
        configHandler.addConfigurable(new RecipeSerializerCraftingSpecialShapedOmniDirectionalConfig());
        configHandler.addConfigurable(new RecipeSerializerCraftingSpecialShapedOmniDirectional3Config());
        configHandler.addConfigurable(new RecipeSerializerCraftingSpecialShapelessOmniDirectionalConfig());

        // Criterion triggers
        configHandler.addConfigurable(new NetworkInitializedTriggerConfig());
        configHandler.addConfigurable(new PartReaderAspectTriggerConfig());
        configHandler.addConfigurable(new PartVariableDrivenAspectTriggerConfig());
        configHandler.addConfigurable(new PartWriterAspectTriggerConfig());
        configHandler.addConfigurable(new VariableCreatedTriggerConfig());

        // Loot conditions
        configHandler.addConfigurable(new LootConditionMatchWrenchConfig());

        // Loot functions
        configHandler.addConfigurable(new LootFunctionCopyEnergyBatteryDataConfig());
        configHandler.addConfigurable(new LootFunctionCopyMechanicalDryingBasinTanksConfig());
        configHandler.addConfigurable(new LootFunctionCopyMechanicalMachineEnergyConfig());
        configHandler.addConfigurable(new LootFunctionCopyMechanicalSqueezerTankConfig());
        configHandler.addConfigurable(new LootFunctionCopyProxyIdConfig());

        // Sounds
        configHandler.addConfigurable(new SoundEventEffectPageFlipMultipleConfig());
        configHandler.addConfigurable(new SoundEventEffectPageFlipSingleConfig());

        // Data components
        configHandler.addConfigurable(new DataComponentFacadeBlockConfig());
        configHandler.addConfigurable(new DataComponentFluidContentInOutConfig());
        configHandler.addConfigurable(new DataComponentOmnidirectionalGroupConfig());
        configHandler.addConfigurable(new DataComponentPartEnhancementConfig());
        configHandler.addConfigurable(new DataComponentPartStateConfig());
        configHandler.addConfigurable(new DataComponentProxyIdConfig());
        configHandler.addConfigurable(new DataComponentVariableFacadeConfig());
        configHandler.addConfigurable(new DataComponentWrenchTargetBlockPosConfig());
        configHandler.addConfigurable(new DataComponentWrenchTargetDirectionConfig());
        configHandler.addConfigurable(new DataComponentWrenchModeConfig());
    }

}
