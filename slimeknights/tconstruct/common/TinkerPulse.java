package slimeknights.tconstruct.common;

import slimeknights.tconstruct.*;
import net.minecraft.block.*;
import java.util.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.block.properties.*;
import net.minecraft.item.*;
import slimeknights.mantle.block.*;
import slimeknights.mantle.item.*;
import net.minecraftforge.registries.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;
import net.minecraftforge.fml.common.registry.*;

public abstract class TinkerPulse
{
    protected static boolean isToolsLoaded() {
        return TConstruct.pulseManager.isPulseLoaded("TinkerTools");
    }
    
    protected static boolean isSmelteryLoaded() {
        return TConstruct.pulseManager.isPulseLoaded("TinkerSmeltery");
    }
    
    protected static boolean isWorldLoaded() {
        return TConstruct.pulseManager.isPulseLoaded("TinkerWorld");
    }
    
    protected static boolean isGadgetsLoaded() {
        return TConstruct.pulseManager.isPulseLoaded("TinkerGadgets");
    }
    
    protected static boolean isChiselPluginLoaded() {
        return TConstruct.pulseManager.isPulseLoaded("chiselIntegration");
    }
    
    protected static <T extends Block> T registerBlock(final IForgeRegistry<Block> registry, final T block, final String name) {
        if (!name.equals(name.toLowerCase(Locale.US))) {
            throw new IllegalArgumentException(String.format("Unlocalized names need to be all lowercase! Block: %s", name));
        }
        final String prefixedName = Util.prefix(name);
        block.func_149663_c(prefixedName);
        register(registry, block, name);
        return block;
    }
    
    protected static <E extends Enum> BlockStairsBase registerBlockStairsFrom(final IForgeRegistry<Block> registry, final EnumBlock<E> block, final E value, final String name) {
        return registerBlock(registry, new BlockStairsBase(block.func_176223_P().func_177226_a((IProperty)block.prop, (Comparable)value)), name);
    }
    
    protected static <T extends Block> T registerItemBlock(final IForgeRegistry<Item> registry, final T block) {
        final ItemBlock itemBlock = (ItemBlock)new ItemBlockMeta((Block)block);
        itemBlock.func_77655_b(block.func_149739_a());
        register((net.minecraftforge.registries.IForgeRegistry<Object>)registry, itemBlock, block.getRegistryName());
        return block;
    }
    
    protected static <T extends EnumBlock<?>> T registerEnumItemBlock(final IForgeRegistry<Item> registry, final T block) {
        final ItemBlock itemBlock = (ItemBlock)new ItemBlockMeta((Block)block);
        itemBlock.func_77655_b(block.func_149739_a());
        register((net.minecraftforge.registries.IForgeRegistry<Object>)registry, itemBlock, block.getRegistryName());
        ItemBlockMeta.setMappingProperty((Block)block, (IProperty)block.prop);
        return block;
    }
    
    protected static <T extends Block> T registerItemBlock(final IForgeRegistry<Item> registry, final ItemBlock itemBlock) {
        itemBlock.func_77655_b(itemBlock.func_179223_d().func_149739_a());
        register((net.minecraftforge.registries.IForgeRegistry<Object>)registry, itemBlock, itemBlock.func_179223_d().getRegistryName());
        return (T)itemBlock.func_179223_d();
    }
    
    protected static <T extends Block> T registerItemBlockProp(final IForgeRegistry<Item> registry, final ItemBlock itemBlock, final IProperty<?> property) {
        itemBlock.func_77655_b(itemBlock.func_179223_d().func_149739_a());
        register((net.minecraftforge.registries.IForgeRegistry<Object>)registry, itemBlock, itemBlock.func_179223_d().getRegistryName());
        ItemBlockMeta.setMappingProperty(itemBlock.func_179223_d(), (IProperty)property);
        return (T)itemBlock.func_179223_d();
    }
    
    protected static <T extends EnumBlockSlab<?>> T registerEnumItemBlockSlab(final IForgeRegistry<Item> registry, final T block) {
        final ItemBlock itemBlock = (ItemBlock)new ItemBlockSlab((EnumBlockSlab)block);
        itemBlock.func_77655_b(block.func_149739_a());
        register((net.minecraftforge.registries.IForgeRegistry<Object>)registry, itemBlock, block.getRegistryName());
        ItemBlockMeta.setMappingProperty((Block)block, (IProperty)block.prop);
        return block;
    }
    
    protected static <T extends Item> T registerItem(final IForgeRegistry<Item> registry, final T item, final String name) {
        if (!name.equals(name.toLowerCase(Locale.US))) {
            throw new IllegalArgumentException(String.format("Unlocalized names need to be all lowercase! Item: %s", name));
        }
        item.func_77655_b(Util.prefix(name));
        item.setRegistryName(Util.getResource(name));
        registry.register((IForgeRegistryEntry)item);
        return item;
    }
    
    protected static <T extends IForgeRegistryEntry<T>> T register(final IForgeRegistry<T> registry, final T thing, final String name) {
        thing.setRegistryName(Util.getResource(name));
        registry.register((IForgeRegistryEntry)thing);
        return thing;
    }
    
    protected static <T extends IForgeRegistryEntry<T>> T register(final IForgeRegistry<T> registry, final T thing, final ResourceLocation name) {
        thing.setRegistryName(name);
        registry.register((IForgeRegistryEntry)thing);
        return thing;
    }
    
    protected static void registerTE(final Class<? extends TileEntity> teClazz, final String name) {
        if (!name.equals(name.toLowerCase(Locale.US))) {
            throw new IllegalArgumentException(String.format("Unlocalized names need to be all lowercase! TE: %s", name));
        }
        GameRegistry.registerTileEntity((Class)teClazz, Util.prefix(name));
    }
}
