package slimeknights.tconstruct.debug;

import slimeknights.mantle.pulsar.pulse.*;
import org.apache.logging.log4j.*;
import slimeknights.tconstruct.common.config.*;
import net.minecraftforge.common.*;
import com.google.common.eventbus.*;
import net.minecraftforge.client.*;
import net.minecraft.command.*;
import net.minecraftforge.fml.common.event.*;
import slimeknights.tconstruct.library.modifiers.*;
import net.minecraft.init.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.item.*;
import net.minecraftforge.fluids.*;
import java.util.*;
import net.minecraft.block.state.*;

@Pulse(id = "TinkerDebug", description = "Debug utilities", defaultEnable = false)
public class TinkerDebug
{
    public static final String PulseId = "TinkerDebug";
    static final Logger log;
    
    @Subscribe
    public void preInit(final FMLPreInitializationEvent event) {
        if (Config.dumpTextureMap) {
            MinecraftForge.EVENT_BUS.register((Object)new TextureDump());
        }
    }
    
    @Subscribe
    public void postInit(final FMLPostInitializationEvent event) {
        if (event.getSide().isClient()) {
            ClientCommandHandler.instance.func_71560_a((ICommand)new ReloadResources());
        }
    }
    
    @Subscribe
    public void serverStart(final FMLServerStartingEvent event) {
        event.registerServerCommand((ICommand)new DamageTool());
        event.registerServerCommand((ICommand)new TestTool());
        event.registerServerCommand((ICommand)new GenValidModifiers());
        event.registerServerCommand((ICommand)new BreakTool());
        if (event.getSide().isClient()) {
            ClientCommandHandler.instance.func_71560_a((ICommand)new LocalizationCheckCommand());
            ClientCommandHandler.instance.func_71560_a((ICommand)new DumpMaterialTest());
            ClientCommandHandler.instance.func_71560_a((ICommand)new FindBestTool());
            ClientCommandHandler.instance.func_71560_a((ICommand)new GetToolGrowth());
            ClientCommandHandler.instance.func_71560_a((ICommand)new CompareVanilla());
            ClientCommandHandler.instance.func_71560_a((ICommand)new ListValidModifiers());
        }
        sanityCheck();
    }
    
    public static void sanityCheck() {
        for (final IModifier modifier : TinkerRegistry.getAllModifiers()) {
            try {
                modifier.matches(ListUtil.getListFrom(new ItemStack(Items.field_151055_y)));
                modifier.matches((NonNullList<ItemStack>)NonNullList.func_191197_a(1, (Object)ItemStack.field_190927_a));
            }
            catch (Exception e) {
                TinkerDebug.log.error("Caught exception in modifier " + modifier.getIdentifier(), (Throwable)e);
            }
        }
        for (final ResourceLocation identifier : Block.field_149771_c.func_148742_b()) {
            if (!identifier.func_110624_b().equals(Util.RESOURCE)) {
                continue;
            }
            final Block block = (Block)Block.field_149771_c.func_82594_a((Object)identifier);
            for (int i = 0; i < 16; ++i) {
                try {
                    final IBlockState state = block.func_176203_a(i);
                    state.func_177230_c().func_176201_c(state);
                }
                catch (Exception e2) {
                    TinkerDebug.log.error("Caught exception when checking block " + identifier + ":" + i, (Throwable)e2);
                }
            }
        }
        for (final ResourceLocation identifier : Item.field_150901_e.func_148742_b()) {
            if (!identifier.func_110624_b().equals(Util.RESOURCE)) {
                continue;
            }
            final Item item = (Item)Item.field_150901_e.func_82594_a((Object)identifier);
            for (int i = 0; i < 32767; ++i) {
                try {
                    item.func_77647_b(i);
                }
                catch (Exception e2) {
                    TinkerDebug.log.error("Caught exception when checking item " + identifier + ":" + i, (Throwable)e2);
                }
            }
        }
        for (final Map.Entry<String, Fluid> entry : FluidRegistry.getRegisteredFluids().entrySet()) {
            if (entry.getKey() == null || entry.getKey().isEmpty()) {
                TinkerDebug.log.error("Fluid " + entry.getValue().getUnlocalizedName() + " has an empty name registered!");
            }
            final String name = FluidRegistry.getFluidName((Fluid)entry.getValue());
            if (name == null || name.isEmpty()) {
                TinkerDebug.log.error("Fluid " + entry.getValue().getUnlocalizedName() + " is registered with an empty name!");
            }
        }
        TinkerDebug.log.info("Sanity Check Complete");
    }
    
    static {
        log = Util.getLogger("TinkerDebug");
    }
}
