package slimeknights.tconstruct.world;

import slimeknights.mantle.pulsar.pulse.*;
import org.apache.logging.log4j.*;
import slimeknights.tconstruct.common.*;
import slimeknights.tconstruct.world.block.*;
import net.minecraftforge.event.*;
import net.minecraft.block.*;
import net.minecraftforge.registries.*;
import net.minecraftforge.fml.common.eventhandler.*;
import slimeknights.mantle.item.*;
import net.minecraft.block.properties.*;
import slimeknights.tconstruct.world.item.*;
import slimeknights.tconstruct.world.entity.*;
import slimeknights.tconstruct.*;
import net.minecraft.world.storage.loot.*;
import net.minecraftforge.client.event.*;
import com.google.common.eventbus.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.*;
import net.minecraftforge.fml.common.*;
import slimeknights.tconstruct.world.worldgen.*;
import net.minecraftforge.common.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.item.*;

@Pulse(id = "TinkerWorld", description = "Everything that's found in the world and worldgen")
public class TinkerWorld extends TinkerPulse
{
    public static final String PulseId = "TinkerWorld";
    static final Logger log;
    @SidedProxy(clientSide = "slimeknights.tconstruct.world.WorldClientProxy", serverSide = "slimeknights.tconstruct.common.CommonProxy")
    public static CommonProxy proxy;
    public static BlockSlimeDirt slimeDirt;
    public static BlockSlimeGrass slimeGrass;
    public static BlockSlimeLeaves slimeLeaves;
    public static BlockTallSlimeGrass slimeGrassTall;
    public static BlockSlimeSapling slimeSapling;
    public static BlockSlimeVine slimeVineBlue1;
    public static BlockSlimeVine slimeVinePurple1;
    public static BlockSlimeVine slimeVineBlue2;
    public static BlockSlimeVine slimeVinePurple2;
    public static BlockSlimeVine slimeVineBlue3;
    public static BlockSlimeVine slimeVinePurple3;
    public static final EnumPlantType slimePlantType;
    
    @SubscribeEvent
    public void registerBlocks(final RegistryEvent.Register<Block> event) {
        final IForgeRegistry<Block> registry = (IForgeRegistry<Block>)event.getRegistry();
        TinkerWorld.slimeDirt = TinkerPulse.registerBlock(registry, new BlockSlimeDirt(), "slime_dirt");
        TinkerWorld.slimeGrass = TinkerPulse.registerBlock(registry, new BlockSlimeGrass(), "slime_grass");
        TinkerWorld.slimeLeaves = TinkerPulse.registerBlock(registry, new BlockSlimeLeaves(), "slime_leaves");
        TinkerWorld.slimeGrassTall = TinkerPulse.registerBlock(registry, new BlockTallSlimeGrass(), "slime_grass_tall");
        TinkerWorld.slimeSapling = TinkerPulse.registerBlock(registry, new BlockSlimeSapling(), "slime_sapling");
        TinkerWorld.slimeVineBlue3 = TinkerPulse.registerBlock(registry, new BlockSlimeVine(BlockSlimeGrass.FoliageType.BLUE, null), "slime_vine_blue_end");
        TinkerWorld.slimeVineBlue2 = TinkerPulse.registerBlock(registry, new BlockSlimeVine(BlockSlimeGrass.FoliageType.BLUE, TinkerWorld.slimeVineBlue3), "slime_vine_blue_mid");
        TinkerWorld.slimeVineBlue1 = TinkerPulse.registerBlock(registry, new BlockSlimeVine(BlockSlimeGrass.FoliageType.BLUE, TinkerWorld.slimeVineBlue2), "slime_vine_blue");
        TinkerWorld.slimeVinePurple3 = TinkerPulse.registerBlock(registry, new BlockSlimeVine(BlockSlimeGrass.FoliageType.PURPLE, null), "slime_vine_purple_end");
        TinkerWorld.slimeVinePurple2 = TinkerPulse.registerBlock(registry, new BlockSlimeVine(BlockSlimeGrass.FoliageType.PURPLE, TinkerWorld.slimeVinePurple3), "slime_vine_purple_mid");
        TinkerWorld.slimeVinePurple1 = TinkerPulse.registerBlock(registry, new BlockSlimeVine(BlockSlimeGrass.FoliageType.PURPLE, TinkerWorld.slimeVinePurple2), "slime_vine_purple");
    }
    
    @SubscribeEvent
    public void registerItems(final RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = (IForgeRegistry<Item>)event.getRegistry();
        TinkerWorld.slimeDirt = TinkerPulse.registerEnumItemBlock(registry, TinkerWorld.slimeDirt);
        TinkerWorld.slimeGrass = TinkerPulse.registerItemBlockProp(registry, (ItemBlock)new ItemBlockMeta((Block)TinkerWorld.slimeGrass), (IProperty<?>)BlockSlimeGrass.TYPE);
        TinkerWorld.slimeLeaves = TinkerPulse.registerItemBlockProp(registry, (ItemBlock)new ItemBlockLeaves((Block)TinkerWorld.slimeLeaves), (IProperty<?>)BlockSlimeGrass.FOLIAGE);
        TinkerWorld.slimeGrassTall = TinkerPulse.registerItemBlockProp(registry, (ItemBlock)new ItemBlockMeta((Block)TinkerWorld.slimeGrassTall), (IProperty<?>)BlockTallSlimeGrass.TYPE);
        TinkerWorld.slimeSapling = TinkerPulse.registerItemBlockProp(registry, (ItemBlock)new ItemBlockMeta((Block)TinkerWorld.slimeSapling), (IProperty<?>)BlockSlimeGrass.FOLIAGE);
        TinkerWorld.slimeVineBlue3 = TinkerPulse.registerItemBlock(registry, TinkerWorld.slimeVineBlue3);
        TinkerWorld.slimeVineBlue2 = TinkerPulse.registerItemBlock(registry, TinkerWorld.slimeVineBlue2);
        TinkerWorld.slimeVineBlue1 = TinkerPulse.registerItemBlock(registry, TinkerWorld.slimeVineBlue1);
        TinkerWorld.slimeVinePurple3 = TinkerPulse.registerItemBlock(registry, TinkerWorld.slimeVinePurple3);
        TinkerWorld.slimeVinePurple2 = TinkerPulse.registerItemBlock(registry, TinkerWorld.slimeVinePurple2);
        TinkerWorld.slimeVinePurple1 = TinkerPulse.registerItemBlock(registry, TinkerWorld.slimeVinePurple1);
    }
    
    @SubscribeEvent
    public void registerEntities(final RegistryEvent.Register<EntityEntry> event) {
        EntityRegistry.registerModEntity(Util.getResource("blueslime"), (Class)EntityBlueSlime.class, Util.prefix("blueslime"), 1, (Object)TConstruct.instance, 64, 5, true, 4714485, 11337716);
        LootTableList.func_186375_a(EntityBlueSlime.LOOT_TABLE);
    }
    
    @SubscribeEvent
    public void registerModels(final ModelRegistryEvent event) {
        TinkerWorld.proxy.registerModels();
    }
    
    @Subscribe
    public void preInit(final FMLPreInitializationEvent event) {
        TinkerWorld.proxy.preInit();
    }
    
    @Subscribe
    public void init(final FMLInitializationEvent event) {
        TinkerWorld.proxy.init();
    }
    
    @Subscribe
    public void postInit(final FMLPostInitializationEvent event) {
        GameRegistry.registerWorldGenerator((IWorldGenerator)SlimeIslandGenerator.INSTANCE, 25);
        GameRegistry.registerWorldGenerator((IWorldGenerator)MagmaSlimeIslandGenerator.INSTANCE, 25);
        MinecraftForge.EVENT_BUS.register((Object)new WorldEvents());
        TinkerWorld.proxy.postInit();
        TinkerRegistry.tabWorld.setDisplayIcon(new ItemStack((Block)TinkerWorld.slimeSapling));
    }
    
    static {
        log = Util.getLogger("TinkerWorld");
        slimePlantType = EnumPlantType.getPlantType("slime");
    }
}
