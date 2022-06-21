package slimeknights.tconstruct.library.utils;

import net.minecraft.item.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.library.tinkering.*;
import net.minecraft.nbt.*;
import net.minecraft.util.math.*;

public final class TagUtil
{
    public static int TAG_TYPE_STRING;
    public static int TAG_TYPE_COMPOUND;
    
    private TagUtil() {
    }
    
    public static NBTTagCompound getTagSafe(final ItemStack stack) {
        if (stack == null || stack.func_77973_b() == null || stack.func_190926_b() || !stack.func_77942_o()) {
            return new NBTTagCompound();
        }
        return stack.func_77978_p();
    }
    
    public static NBTTagCompound getTagSafe(final NBTTagCompound tag, final String key) {
        if (tag == null) {
            return new NBTTagCompound();
        }
        return tag.func_74775_l(key);
    }
    
    public static NBTTagList getTagListSafe(final NBTTagCompound tag, final String key, final int type) {
        if (tag == null) {
            return new NBTTagList();
        }
        return tag.func_150295_c(key, type);
    }
    
    public static NBTTagCompound getBaseTag(final ItemStack stack) {
        return getBaseTag(getTagSafe(stack));
    }
    
    public static NBTTagCompound getBaseTag(final NBTTagCompound root) {
        return getTagSafe(root, "TinkerData");
    }
    
    public static void setBaseTag(final ItemStack stack, final NBTTagCompound tag) {
        final NBTTagCompound root = getTagSafe(stack);
        setBaseTag(root, tag);
        stack.func_77982_d(root);
    }
    
    public static void setBaseTag(final NBTTagCompound root, final NBTTagCompound tag) {
        if (root != null) {
            root.func_74782_a("TinkerData", (NBTBase)tag);
        }
    }
    
    public static NBTTagList getBaseModifiersTagList(final ItemStack stack) {
        return getBaseModifiersTagList(getTagSafe(stack));
    }
    
    public static NBTTagList getBaseModifiersTagList(final NBTTagCompound root) {
        return getTagListSafe(getBaseTag(root), "Modifiers", TagUtil.TAG_TYPE_STRING);
    }
    
    public static void setBaseModifiersTagList(final ItemStack stack, final NBTTagList tagList) {
        final NBTTagCompound root = getTagSafe(stack);
        setBaseModifiersTagList(root, tagList);
        stack.func_77982_d(root);
    }
    
    public static void setBaseModifiersTagList(final NBTTagCompound root, final NBTTagList tagList) {
        final NBTTagCompound baseTag = getBaseTag(root);
        baseTag.func_74782_a("Modifiers", (NBTBase)tagList);
        setBaseTag(root, baseTag);
    }
    
    public static NBTTagList getBaseMaterialsTagList(final ItemStack stack) {
        return getBaseMaterialsTagList(getTagSafe(stack));
    }
    
    public static NBTTagList getBaseMaterialsTagList(final NBTTagCompound root) {
        return getTagListSafe(getBaseTag(root), "Materials", TagUtil.TAG_TYPE_STRING);
    }
    
    public static void setBaseMaterialsTagList(final ItemStack stack, final NBTTagList tagList) {
        final NBTTagCompound root = getTagSafe(stack);
        setBaseMaterialsTagList(root, tagList);
        stack.func_77982_d(root);
    }
    
    public static void setBaseMaterialsTagList(final NBTTagCompound root, final NBTTagList tagList) {
        getBaseTag(root).func_74782_a("Materials", (NBTBase)tagList);
    }
    
    public static int getBaseModifiersUsed(final NBTTagCompound root) {
        return getBaseTag(root).func_74762_e("UsedModifiers");
    }
    
    public static void setBaseModifiersUsed(final NBTTagCompound root, final int count) {
        getBaseTag(root).func_74768_a("UsedModifiers", count);
    }
    
    public static NBTTagCompound getToolTag(final ItemStack stack) {
        return getToolTag(getTagSafe(stack));
    }
    
    public static NBTTagCompound getToolTag(final NBTTagCompound root) {
        return getTagSafe(root, "Stats");
    }
    
    public static void setToolTag(final ItemStack stack, final NBTTagCompound tag) {
        final NBTTagCompound root = getTagSafe(stack);
        setToolTag(root, tag);
        stack.func_77982_d(root);
    }
    
    public static void setToolTag(final NBTTagCompound root, final NBTTagCompound tag) {
        if (root != null) {
            root.func_74782_a("Stats", (NBTBase)tag);
        }
    }
    
    public static NBTTagList getModifiersTagList(final ItemStack stack) {
        return getModifiersTagList(getTagSafe(stack));
    }
    
    public static NBTTagList getModifiersTagList(final NBTTagCompound root) {
        return getTagListSafe(root, "Modifiers", TagUtil.TAG_TYPE_COMPOUND);
    }
    
    public static void setModifiersTagList(final ItemStack stack, final NBTTagList tagList) {
        final NBTTagCompound root = getTagSafe(stack);
        setModifiersTagList(root, tagList);
        stack.func_77982_d(root);
    }
    
    public static void setModifiersTagList(final NBTTagCompound root, final NBTTagList tagList) {
        if (root != null) {
            root.func_74782_a("Modifiers", (NBTBase)tagList);
        }
    }
    
    public static NBTTagList getTraitsTagList(final ItemStack stack) {
        return getTraitsTagList(getTagSafe(stack));
    }
    
    public static NBTTagList getTraitsTagList(final NBTTagCompound root) {
        return getTagListSafe(root, "Traits", TagUtil.TAG_TYPE_STRING);
    }
    
    public static void setTraitsTagList(final ItemStack stack, final NBTTagList tagList) {
        final NBTTagCompound root = getTagSafe(stack);
        setTraitsTagList(root, tagList);
        stack.func_77982_d(root);
    }
    
    public static void setTraitsTagList(final NBTTagCompound root, final NBTTagList tagList) {
        if (root != null) {
            root.func_74782_a("Traits", (NBTBase)tagList);
        }
    }
    
    public static ToolNBT getToolStats(final ItemStack stack) {
        return getToolStats(getTagSafe(stack));
    }
    
    public static ToolNBT getToolStats(final NBTTagCompound root) {
        return new ToolNBT(getToolTag(root));
    }
    
    public static ToolNBT getOriginalToolStats(final ItemStack stack) {
        return getOriginalToolStats(getTagSafe(stack));
    }
    
    public static ToolNBT getOriginalToolStats(final NBTTagCompound root) {
        return new ToolNBT(getTagSafe(root, "StatsOriginal"));
    }
    
    public static NBTTagCompound getExtraTag(final ItemStack stack) {
        return getExtraTag(getTagSafe(stack));
    }
    
    public static NBTTagCompound getExtraTag(final NBTTagCompound root) {
        return getTagSafe(root, "Special");
    }
    
    public static void setExtraTag(final ItemStack stack, final NBTTagCompound tag) {
        final NBTTagCompound root = getTagSafe(stack);
        setExtraTag(root, tag);
        stack.func_77982_d(root);
    }
    
    public static void setExtraTag(final NBTTagCompound root, final NBTTagCompound tag) {
        root.func_74782_a("Special", (NBTBase)tag);
    }
    
    public static Category[] getCategories(final NBTTagCompound root) {
        final NBTTagList categories = getTagListSafe(getExtraTag(root), "Categories", 8);
        final Category[] out = new Category[categories.func_74745_c()];
        for (int i = 0; i < out.length; ++i) {
            out[i] = Category.categories.get(categories.func_150307_f(i));
        }
        return out;
    }
    
    public static void setCategories(final ItemStack stack, final Category[] categories) {
        final NBTTagCompound root = getTagSafe(stack);
        setCategories(root, categories);
        stack.func_77982_d(root);
    }
    
    public static void setCategories(final NBTTagCompound root, final Category[] categories) {
        final NBTTagList list = new NBTTagList();
        for (final Category category : categories) {
            list.func_74742_a((NBTBase)new NBTTagString(category.name));
        }
        final NBTTagCompound extra = getExtraTag(root);
        extra.func_74782_a("Categories", (NBTBase)list);
        setExtraTag(root, extra);
    }
    
    public static void setEnchantEffect(final ItemStack stack, final boolean active) {
        final NBTTagCompound root = getTagSafe(stack);
        setEnchantEffect(root, active);
        stack.func_77982_d(root);
    }
    
    public static void setEnchantEffect(final NBTTagCompound root, final boolean active) {
        if (active) {
            root.func_74757_a("EnchantEffect", true);
        }
        else {
            root.func_82580_o("EnchantEffect");
        }
    }
    
    public static boolean hasEnchantEffect(final ItemStack stack) {
        return hasEnchantEffect(getTagSafe(stack));
    }
    
    public static boolean hasEnchantEffect(final NBTTagCompound root) {
        return root.func_74767_n("EnchantEffect");
    }
    
    public static void setResetFlag(final ItemStack stack, final boolean active) {
        final NBTTagCompound root = getTagSafe(stack);
        root.func_74757_a("ResetFlag", active);
        stack.func_77982_d(root);
    }
    
    public static boolean getResetFlag(final ItemStack stack) {
        return getTagSafe(stack).func_74767_n("ResetFlag");
    }
    
    public static void setNoRenameFlag(final ItemStack stack, final boolean active) {
        final NBTTagCompound root = getTagSafe(stack);
        setNoRenameFlag(root, active);
        stack.func_77982_d(root);
    }
    
    public static void setNoRenameFlag(final NBTTagCompound root, final boolean active) {
        final NBTTagCompound displayTag = root.func_74775_l("display");
        if (displayTag.func_74764_b("Name")) {
            displayTag.func_74757_a("NoRename", active);
            root.func_74782_a("display", (NBTBase)displayTag);
        }
    }
    
    public static boolean getNoRenameFlag(final ItemStack stack) {
        final NBTTagCompound root = getTagSafe(stack);
        final NBTTagCompound displayTag = root.func_74775_l("display");
        return displayTag.func_74767_n("NoRename");
    }
    
    public static NBTTagCompound writePos(final BlockPos pos) {
        final NBTTagCompound tag = new NBTTagCompound();
        if (pos != null) {
            tag.func_74768_a("X", pos.func_177958_n());
            tag.func_74768_a("Y", pos.func_177956_o());
            tag.func_74768_a("Z", pos.func_177952_p());
        }
        return tag;
    }
    
    public static BlockPos readPos(final NBTTagCompound tag) {
        if (tag != null) {
            return new BlockPos(tag.func_74762_e("X"), tag.func_74762_e("Y"), tag.func_74762_e("Z"));
        }
        return null;
    }
    
    static {
        TagUtil.TAG_TYPE_STRING = new NBTTagString().func_74732_a();
        TagUtil.TAG_TYPE_COMPOUND = new NBTTagCompound().func_74732_a();
    }
}
