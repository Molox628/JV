package slimeknights.tconstruct.library.tools;

import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.nbt.*;
import javax.annotation.*;
import slimeknights.tconstruct.common.config.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.client.util.*;

public class Pattern extends Item implements IPattern
{
    public static final String TAG_PARTTYPE = "PartType";
    
    public Pattern() {
        this.func_77637_a((CreativeTabs)TinkerRegistry.tabParts);
        this.func_77627_a(true);
    }
    
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> subItems) {
        if (this.func_194125_a(tab)) {
            subItems.add((Object)new ItemStack((Item)this));
            for (final Item toolpart : this.getSubItemToolparts()) {
                final ItemStack stack = new ItemStack((Item)this);
                setTagForPart(stack, toolpart);
                if (this.isValidSubitem(toolpart)) {
                    subItems.add((Object)stack);
                }
            }
        }
    }
    
    protected Collection<Item> getSubItemToolparts() {
        return TinkerRegistry.getPatternItems();
    }
    
    protected boolean isValidSubitem(final Item toolpart) {
        if (toolpart instanceof IToolPart) {
            for (final Material material : TinkerRegistry.getAllMaterials()) {
                if (this.isValidSubitemMaterial(material) && ((IToolPart)toolpart).canUseMaterial(material)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }
    
    protected boolean isValidSubitemMaterial(final Material material) {
        return material.isCraftable();
    }
    
    @Nonnull
    public String func_77653_i(@Nonnull final ItemStack stack) {
        final Item part = getPartFromTag(stack);
        final String unloc = this.func_77657_g(stack);
        if (part == null) {
            return Util.translate(unloc + ".blank", new Object[0]);
        }
        return Util.translateFormatted(unloc + ".name", part.func_77653_i(ItemStack.field_190927_a));
    }
    
    public static ItemStack setTagForPart(final ItemStack stack, final Item toolPart) {
        final NBTTagCompound tag = TagUtil.getTagSafe(stack);
        tag.func_74778_a("PartType", toolPart.getRegistryName().toString());
        stack.func_77982_d(tag);
        return stack;
    }
    
    @Nullable
    public static Item getPartFromTag(final ItemStack stack) {
        final NBTTagCompound tag = TagUtil.getTagSafe(stack);
        final String part = tag.func_74779_i("PartType");
        return Item.func_111206_d(part);
    }
    
    public boolean isBlankPattern(final ItemStack stack) {
        return !stack.func_190926_b() && stack.func_77973_b() instanceof IPattern && (!stack.func_77942_o() || Config.reuseStencil || !stack.func_77978_p().func_74764_b("PartType"));
    }
    
    public void func_77624_a(final ItemStack stack, @Nullable final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn) {
        final Item part = getPartFromTag(stack);
        if (part != null && part instanceof IToolPart) {
            final float cost = ((IToolPart)part).getCost() / 144.0f;
            tooltip.add(Util.translateFormatted("tooltip.pattern.cost", Util.df.format(cost)));
        }
    }
    
    public static String getTextureIdentifier(final Item item) {
        String identifier = item.getRegistryName().toString();
        if (identifier.contains(":")) {
            identifier = identifier.substring(identifier.lastIndexOf(58) + 1);
        }
        return "_" + identifier;
    }
}
