package slimeknights.tconstruct;

import slimeknights.tconstruct.common.*;
import slimeknights.mantle.pulsar.pulse.*;
import org.apache.logging.log4j.*;
import slimeknights.tconstruct.tools.*;
import slimeknights.tconstruct.shared.*;
import slimeknights.tconstruct.library.materials.*;
import java.util.*;
import com.google.common.eventbus.*;
import net.minecraftforge.event.*;
import net.minecraft.block.*;
import net.minecraftforge.registries.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.item.crafting.*;
import net.minecraftforge.client.event.*;
import slimeknights.tconstruct.smeltery.*;
import net.minecraftforge.fml.common.event.*;
import com.google.common.collect.*;
import net.minecraftforge.fluids.*;
import net.minecraft.nbt.*;
import slimeknights.tconstruct.library.*;

@Pulse(id = "TinkerIntegration", forced = true)
public class TinkerIntegration extends TinkerPulse
{
    public static final String PulseId = "TinkerIntegration";
    static final Logger log;
    private static List<NBTTagList> alloys;
    
    @Subscribe
    public void preInit(final FMLPreInitializationEvent event) {
        integrate(TinkerMaterials.wood, "plankWood");
        integrate(TinkerMaterials.stone);
        integrate(TinkerMaterials.flint);
        integrate(TinkerMaterials.cactus);
        integrate(TinkerMaterials.bone);
        integrate(TinkerMaterials.obsidian, TinkerFluids.obsidian);
        integrate(TinkerMaterials.prismarine);
        integrate(TinkerMaterials.endstone);
        integrate(TinkerMaterials.paper);
        integrate(TinkerMaterials.sponge);
        integrate(TinkerMaterials.firewood);
        integrate(TinkerMaterials.iron, TinkerFluids.iron, "Iron").toolforge();
        integrate(TinkerMaterials.pigiron, TinkerFluids.pigIron, "Pigiron").toolforge();
        integrate(TinkerMaterials.knightslime, TinkerFluids.knightslime, "Knightslime").toolforge();
        integrate(TinkerMaterials.slime, "slimecrystalGreen");
        integrate(TinkerMaterials.blueslime, "slimecrystalBlue");
        integrate(TinkerMaterials.magmaslime, "slimecrystalMagma");
        TinkerRegistry.integrate(new MaterialIntegration(null, TinkerFluids.alubrass, "Alubrass", new String[] { "ingotCopper", "ingotAluminum" })).toolforge();
        integrate(TinkerMaterials.netherrack);
        integrate(TinkerMaterials.cobalt, TinkerFluids.cobalt, "Cobalt").toolforge();
        integrate(TinkerMaterials.ardite, TinkerFluids.ardite, "Ardite").toolforge();
        integrate(TinkerMaterials.manyullyn, TinkerFluids.manyullyn, "Manyullyn").toolforge();
        integrate(TinkerMaterials.copper, TinkerFluids.copper, "Copper").toolforge();
        integrate(TinkerMaterials.bronze, TinkerFluids.bronze, "Bronze").toolforge();
        integrate(TinkerMaterials.lead, TinkerFluids.lead, "Lead").toolforge();
        integrate(TinkerMaterials.silver, TinkerFluids.silver, "Silver").toolforge();
        integrate(TinkerMaterials.electrum, TinkerFluids.electrum, "Electrum").toolforge();
        integrate(TinkerMaterials.steel, TinkerFluids.steel, "Steel").toolforge();
        integrate(TinkerFluids.gold, "Gold").toolforge();
        integrate(TinkerFluids.brass, "Brass").toolforge();
        integrate(TinkerFluids.tin, "Tin").toolforge();
        integrate(TinkerFluids.nickel, "Nickel").toolforge();
        integrate(TinkerFluids.zinc, "Zinc").toolforge();
        integrate(TinkerFluids.aluminum, "Aluminum").toolforge();
        integrate(TinkerMaterials.string);
        integrate(TinkerMaterials.slimevine_blue);
        integrate(TinkerMaterials.slimevine_purple);
        integrate(TinkerMaterials.vine);
        integrate(TinkerMaterials.blaze);
        integrate(TinkerMaterials.reed);
        integrate(TinkerMaterials.ice);
        integrate(TinkerMaterials.endrod);
        integrate(TinkerMaterials.feather);
        integrate(TinkerMaterials.slimeleaf_blue);
        integrate(TinkerMaterials.slimeleaf_orange);
        integrate(TinkerMaterials.slimeleaf_purple);
        integrate(TinkerMaterials.leaf);
        for (final MaterialIntegration integration : TinkerRegistry.getMaterialIntegrations()) {
            integration.preInit();
        }
    }
    
    @SubscribeEvent
    public void registerBlocks(final RegistryEvent.Register<Block> event) {
        final IForgeRegistry<Block> registry = (IForgeRegistry<Block>)event.getRegistry();
        for (final MaterialIntegration integration : TinkerRegistry.getMaterialIntegrations()) {
            integration.registerFluidBlock(registry);
        }
    }
    
    @SubscribeEvent
    public void registerRecipes(final RegistryEvent.Register<IRecipe> event) {
        final IForgeRegistry<IRecipe> registry = (IForgeRegistry<IRecipe>)event.getRegistry();
        if (isToolsLoaded()) {
            for (final MaterialIntegration integration : TinkerRegistry.getMaterialIntegrations()) {
                integration.registerToolForgeRecipe(registry);
            }
        }
    }
    
    @SubscribeEvent
    public void registerModels(final ModelRegistryEvent event) {
        for (final MaterialIntegration integration : TinkerRegistry.getMaterialIntegrations()) {
            integration.registerFluidModel();
        }
    }
    
    public static void addAlloyNBTTag(final NBTTagList alloyTagList) {
        TinkerIntegration.alloys.add(alloyTagList);
    }
    
    public static boolean isIntegrated(final Fluid fluid) {
        final String name = FluidRegistry.getFluidName(fluid);
        if (name != null) {
            for (final MaterialIntegration integration : TinkerRegistry.getMaterialIntegrations()) {
                if (integration.isIntegrated() && integration.fluid != null && name.equals(integration.fluid.getName())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Subscribe
    public void init(final FMLInitializationEvent event) {
        this.handleIMCs();
        this.handleAlloyIMCs();
    }
    
    @Subscribe
    public void postInit(final FMLPostInitializationEvent event) {
        for (final MaterialIntegration integration : TinkerRegistry.getMaterialIntegrations()) {
            integration.integrate();
        }
        TinkerSmeltery.registerAlloys();
        TinkerSmeltery.registerRecipeOredictMelting();
        TinkerRegistry.removeHiddenMaterials();
    }
    
    private void handleIMCs() {
        for (final FMLInterModComms.IMCMessage message : FMLInterModComms.fetchRuntimeMessages((Object)TConstruct.instance)) {
            try {
                if (message.key.equals("integrateSmeltery")) {
                    IMCIntegration.integrateSmeltery(message);
                }
                else if (message.key.equals("alloy")) {
                    IMCIntegration.alloy(message);
                }
                else if (message.key.equals("blacklistMelting")) {
                    IMCIntegration.blacklistMelting(message);
                }
                else {
                    if (!message.key.equals("addDryingRecipe")) {
                        continue;
                    }
                    IMCIntegration.addDryingRecipe(message);
                }
            }
            catch (ClassCastException e) {
                TinkerIntegration.log.error("Got invalid " + message.key + " IMC from " + message.getSender());
            }
        }
    }
    
    private void handleAlloyIMCs() {
        for (final NBTTagList taglist : TinkerIntegration.alloys) {
            final List<FluidStack> fluids = (List<FluidStack>)Lists.newLinkedList();
            for (int i = 0; i < taglist.func_74745_c(); ++i) {
                final NBTTagCompound tag = taglist.func_150305_b(i);
                final FluidStack fs = FluidStack.loadFluidStackFromNBT(tag);
                if (fs == null) {
                    fluids.clear();
                    break;
                }
                fluids.add(fs);
            }
            if (fluids.size() > 2) {
                final FluidStack output = fluids.get(0);
                FluidStack[] input = new FluidStack[fluids.size() - 1];
                input = fluids.subList(1, fluids.size()).toArray(input);
                TinkerRegistry.registerAlloy(output, input);
                TinkerIntegration.log.debug("Added integration alloy: " + output.getLocalizedName());
            }
        }
    }
    
    private static MaterialIntegration integrate(final Material material) {
        return add(new MaterialIntegration(material));
    }
    
    private static MaterialIntegration integrate(final Material material, final Fluid fluid) {
        return add(new MaterialIntegration(material, fluid));
    }
    
    private static MaterialIntegration integrate(final Material material, final String oreRequirement) {
        final MaterialIntegration materialIntegration = new MaterialIntegration(oreRequirement, material, null, null);
        materialIntegration.setRepresentativeItem(oreRequirement);
        return add(materialIntegration);
    }
    
    private static MaterialIntegration integrate(final Material material, final Fluid fluid, final String oreSuffix) {
        return add(new MaterialIntegration(material, fluid, oreSuffix));
    }
    
    private static MaterialIntegration integrate(final Fluid fluid, final String oreSuffix) {
        return add(new MaterialIntegration(null, fluid, oreSuffix));
    }
    
    private static MaterialIntegration add(final MaterialIntegration integration) {
        return TinkerRegistry.integrate(integration);
    }
    
    static {
        log = Util.getLogger("TinkerIntegration");
        TinkerIntegration.alloys = (List<NBTTagList>)Lists.newLinkedList();
    }
}
