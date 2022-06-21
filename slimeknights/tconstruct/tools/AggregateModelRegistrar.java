package slimeknights.tconstruct.tools;

import slimeknights.mantle.pulsar.pulse.*;
import org.apache.logging.log4j.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.event.*;
import org.apache.commons.lang3.tuple.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.event.*;
import com.google.common.eventbus.*;
import slimeknights.tconstruct.library.tinkering.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.common.*;
import slimeknights.tconstruct.library.modifiers.*;
import slimeknights.tconstruct.tools.modifiers.*;

@Pulse(id = "TinkerModelRegister", description = "Registers tool models and co", pulsesRequired = "TinkerTools", forced = true)
public class AggregateModelRegistrar extends AbstractToolPulse
{
    public static final String PulseId = "TinkerModelRegister";
    static final Logger log;
    @SidedProxy(clientSide = "slimeknights.tconstruct.tools.AggregateModelRegistrar$AggregateClientProxy", serverSide = "slimeknights.tconstruct.common.CommonProxy")
    public static CommonProxy proxy;
    
    @SubscribeEvent
    @Override
    public void registerItems(final RegistryEvent.Register<Item> event) {
        for (final Pair<Item, ToolPart> toolPartPattern : AggregateModelRegistrar.toolPartPatterns) {
            this.registerStencil((Item)toolPartPattern.getLeft(), (ToolPart)toolPartPattern.getRight());
        }
    }
    
    @SubscribeEvent
    public void registerModels(final ModelRegistryEvent event) {
        AggregateModelRegistrar.proxy.registerModels();
    }
    
    @Subscribe
    public void preInit(final FMLPreInitializationEvent event) {
        AggregateModelRegistrar.proxy.preInit();
    }
    
    private void registerStencil(final Item pattern, final ToolPart toolPart) {
        for (final ToolCore toolCore : TinkerRegistry.getTools()) {
            for (final PartMaterialType partMaterialType : toolCore.getRequiredComponents()) {
                if (partMaterialType.getPossibleParts().contains(toolPart)) {
                    final ItemStack stencil = new ItemStack(pattern);
                    Pattern.setTagForPart(stencil, toolPart);
                    TinkerRegistry.registerStencilTableCrafting(stencil);
                }
            }
        }
    }
    
    static {
        log = Util.getLogger("TinkerModelRegister");
    }
    
    public static class AggregateClientProxy extends ClientProxy
    {
        @Override
        public void registerModels() {
            super.registerModels();
            for (final ToolPart part : AbstractToolPulse.toolparts) {
                ModelRegisterUtil.registerPartModel(part);
            }
            for (final ToolCore tool : AbstractToolPulse.tools) {
                ModelRegisterUtil.registerToolModel(tool);
            }
            this.registerModifierModels();
        }
        
        private void registerModifierModels() {
            for (final IModifier modifier : AbstractToolPulse.modifiers) {
                if (modifier != TinkerModifiers.modCreative && modifier != TinkerModifiers.modHarvestWidth) {
                    if (modifier == TinkerModifiers.modHarvestHeight) {
                        continue;
                    }
                    ModelRegisterUtil.registerModifierModel(modifier, Util.getModifierResource(modifier.getIdentifier()));
                }
            }
            ModelRegisterUtil.registerModifierModel(new ModFortifyDisplay(), Util.getResource("models/item/modifiers/fortify"));
            new ModExtraTraitDisplay();
        }
    }
}
