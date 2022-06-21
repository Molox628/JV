package slimeknights.tconstruct.library.utils;

import net.minecraft.item.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.traits.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.nbt.*;
import slimeknights.tconstruct.library.tinkering.*;
import java.util.*;
import java.util.function.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.library.modifiers.*;
import com.google.common.collect.*;

public final class TinkerUtil
{
    private TinkerUtil() {
    }
    
    public static Material getMaterialFromStack(final ItemStack stack) {
        if (!(stack.func_77973_b() instanceof IMaterialItem)) {
            return Material.UNKNOWN;
        }
        return ((IMaterialItem)stack.func_77973_b()).getMaterial(stack);
    }
    
    public static List<ITrait> getTraitsOrdered(final ItemStack tool) {
        final List<ITrait> traits = new ArrayList<ITrait>();
        final NBTTagList list = TagUtil.getTraitsTagList(tool);
        for (int i = 0; i < list.func_74745_c(); ++i) {
            final ITrait trait = TinkerRegistry.getTrait(list.func_150307_f(i));
            if (trait != null) {
                traits.add(trait);
            }
        }
        traits.sort(Comparator.comparingInt(ITrait::getPriority).reversed());
        return traits;
    }
    
    public static boolean hasCategory(final NBTTagCompound root, final Category category) {
        return Arrays.stream(TagUtil.getCategories(root)).anyMatch(category::equals);
    }
    
    public static boolean hasTrait(final NBTTagCompound root, final String identifier) {
        final NBTTagList tagList = TagUtil.getTraitsTagList(root);
        for (int i = 0; i < tagList.func_74745_c(); ++i) {
            if (identifier.equals(tagList.func_150307_f(i))) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean hasModifier(final NBTTagCompound root, final String identifier) {
        final NBTTagList tagList = TagUtil.getBaseModifiersTagList(root);
        for (int i = 0; i < tagList.func_74745_c(); ++i) {
            if (identifier.equals(tagList.func_150307_f(i))) {
                return true;
            }
        }
        return false;
    }
    
    public static int getIndexInList(final NBTTagList tagList, final String identifier) {
        if (tagList.func_150303_d() == TagUtil.TAG_TYPE_STRING) {
            return getIndexInStringList(tagList, identifier);
        }
        if (tagList.func_150303_d() == TagUtil.TAG_TYPE_COMPOUND) {
            return getIndexInCompoundList(tagList, identifier);
        }
        return -1;
    }
    
    private static int getIndexInStringList(final NBTTagList tagList, final String identifier) {
        for (int i = 0; i < tagList.func_74745_c(); ++i) {
            final String data = tagList.func_150307_f(i);
            if (identifier.equals(data)) {
                return i;
            }
        }
        return -1;
    }
    
    public static int getIndexInCompoundList(final NBTTagList tagList, final String identifier) {
        for (int i = 0; i < tagList.func_74745_c(); ++i) {
            final ModifierNBT data = ModifierNBT.readTag(tagList.func_150305_b(i));
            if (identifier.equals(data.identifier)) {
                return i;
            }
        }
        return -1;
    }
    
    public static NBTTagCompound getModifierTag(final ItemStack stack, final String identifier) {
        final NBTTagList tagList = TagUtil.getModifiersTagList(stack);
        final int index = getIndexInCompoundList(tagList, identifier);
        return tagList.func_150305_b(index);
    }
    
    public static NBTTagCompound getModifierTag(final NBTTagCompound root, final String identifier) {
        final NBTTagList tagList = TagUtil.getModifiersTagList(root);
        final int index = getIndexInCompoundList(tagList, identifier);
        return tagList.func_150305_b(index);
    }
    
    public static NonNullList<IModifier> getModifiers(final ItemStack itemStack) {
        final NonNullList<IModifier> result = (NonNullList<IModifier>)NonNullList.func_191196_a();
        final NBTTagList modifierList = TagUtil.getModifiersTagList(itemStack);
        for (int i = 0; i < modifierList.func_74745_c(); ++i) {
            final NBTTagCompound tag = modifierList.func_150305_b(i);
            final ModifierNBT data = ModifierNBT.readTag(tag);
            final IModifier modifier = TinkerRegistry.getModifier(data.identifier);
            if (modifier != null) {
                result.add((Object)modifier);
            }
        }
        return result;
    }
    
    public static List<Material> getMaterialsFromTagList(final NBTTagList tagList) {
        final List<Material> materials = (List<Material>)Lists.newLinkedList();
        if (tagList.func_150303_d() != TagUtil.TAG_TYPE_STRING) {
            return materials;
        }
        for (int i = 0; i < tagList.func_74745_c(); ++i) {
            final String identifier = tagList.func_150307_f(i);
            final Material mat = TinkerRegistry.getMaterial(identifier);
            materials.add(mat);
        }
        return materials;
    }
    
    public static String getRomanNumeral(int value) {
        if (value < 1 || value > 3999) {
            return "Really big";
        }
        final StringBuilder sb = new StringBuilder();
        while (value >= 1000) {
            sb.append("M");
            value -= 1000;
        }
        while (value >= 900) {
            sb.append("CM");
            value -= 900;
        }
        while (value >= 500) {
            sb.append("D");
            value -= 500;
        }
        while (value >= 400) {
            sb.append("CD");
            value -= 400;
        }
        while (value >= 100) {
            sb.append("C");
            value -= 100;
        }
        while (value >= 90) {
            sb.append("XC");
            value -= 90;
        }
        while (value >= 50) {
            sb.append("L");
            value -= 50;
        }
        while (value >= 40) {
            sb.append("XL");
            value -= 40;
        }
        while (value >= 10) {
            sb.append("X");
            value -= 10;
        }
        while (value >= 9) {
            sb.append("IX");
            value -= 9;
        }
        while (value >= 5) {
            sb.append("V");
            value -= 5;
        }
        while (value >= 4) {
            sb.append("IV");
            value -= 4;
        }
        while (value >= 1) {
            sb.append("I");
            --value;
        }
        return sb.toString();
    }
}
