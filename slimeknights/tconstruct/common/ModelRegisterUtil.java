package slimeknights.tconstruct.common;

import net.minecraftforge.fml.relauncher.*;
import net.minecraftforge.client.model.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import slimeknights.mantle.item.*;
import slimeknights.tconstruct.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.library.client.model.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.library.modifiers.*;
import net.minecraft.client.renderer.*;
import javax.annotation.*;

@SideOnly(Side.CLIENT)
public final class ModelRegisterUtil
{
    public static final String VARIANT_INVENTORY = "inventory";
    
    public static void registerItemModel(final ItemStack itemStack, final ResourceLocation name) {
        if (!itemStack.func_190926_b() && name != null) {
            ModelLoader.registerItemVariants(itemStack.func_77973_b(), new ResourceLocation[] { name });
            ModelLoader.setCustomModelResourceLocation(itemStack.func_77973_b(), itemStack.func_77960_j(), new ModelResourceLocation(name, "inventory"));
        }
    }
    
    public static ResourceLocation registerItemModel(final Item item) {
        ResourceLocation itemLocation = null;
        if (item != null) {
            itemLocation = item.getRegistryName();
        }
        if (itemLocation != null) {
            itemLocation = registerIt(item, itemLocation);
        }
        return itemLocation;
    }
    
    public static ResourceLocation registerItemModel(final Block block) {
        return registerItemModel(Item.func_150898_a(block));
    }
    
    public static void registerItemBlockMeta(final Block block) {
        if (block != null) {
            final Item item = Item.func_150898_a(block);
            if (item instanceof ItemBlockMeta) {
                ((ItemBlockMeta)item).registerItemModels();
            }
            else {
                TConstruct.log.error("Trying to register an ItemBlockMeta model for a non itemblockmeta block: " + block.getRegistryName());
            }
        }
    }
    
    public static void registerItemModel(final Item item, final int meta) {
        registerItemModel(item, meta, "inventory");
    }
    
    public static void registerItemModel(final Item item, final int meta, final String variant) {
        if (item != null) {
            registerItemModel(item, meta, item.getRegistryName(), variant);
        }
    }
    
    public static void registerItemModel(final Item item, final int meta, final ResourceLocation location, final String variant) {
        if (item != null && !StringUtils.func_151246_b(variant)) {
            ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), variant));
        }
    }
    
    public static ResourceLocation registerToolModel(final ToolCore tool) {
        if (tool == null || tool.getRegistryName() == null) {
            return null;
        }
        final ResourceLocation itemLocation = tool.getRegistryName();
        final String path = "tools/" + itemLocation.func_110623_a() + ToolModelLoader.EXTENSION;
        final ResourceLocation location = new ResourceLocation(itemLocation.func_110624_b(), path);
        ToolModelLoader.addPartMapping(location, tool);
        return registerToolModel(tool, location);
    }
    
    public static ResourceLocation registerToolModel(final Item item, final ResourceLocation location) {
        if (!location.func_110623_a().endsWith(ToolModelLoader.EXTENSION)) {
            TConstruct.log.error("The material-model " + location.toString() + " does not end with '" + ToolModelLoader.EXTENSION + "' and will therefore not be loaded by the custom model loader!");
        }
        return registerIt(item, location);
    }
    
    public static <T extends net.minecraft.item.Item> ResourceLocation registerPartModel(final T item) {
        if (item == null || ((Item)item).getRegistryName() == null) {
            return null;
        }
        final ResourceLocation itemLocation = ((Item)item).getRegistryName();
        final String path = "parts/" + itemLocation.func_110623_a() + MaterialModelLoader.EXTENSION;
        final ResourceLocation location = new ResourceLocation(itemLocation.func_110624_b(), path);
        MaterialModelLoader.addPartMapping(location, (IToolPart)item);
        return registerMaterialModel((Item)item, location);
    }
    
    public static ResourceLocation registerMaterialItemModel(final Item item) {
        if (item == null || item.getRegistryName() == null) {
            return null;
        }
        ResourceLocation itemLocation = item.getRegistryName();
        itemLocation = new ResourceLocation(itemLocation.func_110624_b(), itemLocation.func_110623_a() + MaterialModelLoader.EXTENSION);
        return registerMaterialModel(item, itemLocation);
    }
    
    static ResourceLocation registerMaterialModel(final Item item, final ResourceLocation location) {
        if (!location.func_110623_a().endsWith(MaterialModelLoader.EXTENSION)) {
            TConstruct.log.error("The material-model " + location.toString() + " does not end with '" + MaterialModelLoader.EXTENSION + "' and will therefore not be loaded by the custom model loader!");
        }
        return registerIt(item, location);
    }
    
    public static void registerModifierModel(final IModifier modifier, final ResourceLocation location) {
        ClientProxy.modifierLoader.registerModifierFile(modifier.getIdentifier(), location);
    }
    
    private static ResourceLocation registerIt(final Item item, final ResourceLocation location) {
        ModelLoader.setCustomMeshDefinition(item, (ItemMeshDefinition)new ItemMeshDefinition() {
            @Nonnull
            public ModelResourceLocation func_178113_a(@Nonnull final ItemStack stack) {
                return new ModelResourceLocation(location, "inventory");
            }
        });
        ModelLoader.registerItemVariants(item, new ResourceLocation[] { location });
        return location;
    }
    
    private ModelRegisterUtil() {
    }
}
