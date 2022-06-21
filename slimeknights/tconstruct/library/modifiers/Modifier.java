package slimeknights.tconstruct.library.modifiers;

import slimeknights.mantle.util.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.item.*;
import net.minecraft.enchantment.*;
import slimeknights.tconstruct.library.traits.*;
import java.util.*;
import net.minecraft.util.text.translation.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.nbt.*;
import javax.annotation.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;

public abstract class Modifier extends RecipeMatchRegistry implements IModifier
{
    public static final String LOC_Name = "modifier.%s.name";
    public static final String LOC_Desc = "modifier.%s.desc";
    public static final String LOC_Extra = "modifier.%s.extra";
    protected static final Random random;
    public final String identifier;
    protected final List<ModifierAspect> aspects;
    private static final AttributeModifier ANTI_KNOCKBACK_MOD;
    
    public Modifier(final String identifier) {
        this.aspects = (List<ModifierAspect>)Lists.newLinkedList();
        this.identifier = Util.sanitizeLocalizationString(identifier);
        TinkerRegistry.registerModifier(this);
    }
    
    public String getIdentifier() {
        return this.identifier;
    }
    
    public boolean isHidden() {
        return false;
    }
    
    protected void addAspects(final ModifierAspect... aspects) {
        this.aspects.addAll(Arrays.asList(aspects));
    }
    
    public final boolean canApply(final ItemStack stack, final ItemStack original) throws TinkerGuiException {
        final Set<Enchantment> enchantments = EnchantmentHelper.func_82781_a(stack).keySet();
        final NBTTagList traits = TagUtil.getTraitsTagList(stack);
        for (int i = 0; i < traits.func_74745_c(); ++i) {
            final String id = traits.func_150307_f(i);
            final ITrait trait = TinkerRegistry.getTrait(id);
            if (trait != null) {
                if (!this.canApplyTogether(trait) || !trait.canApplyTogether(this)) {
                    throw new TinkerGuiException(Util.translateFormatted("gui.error.incompatible_trait", this.getLocalizedName(), trait.getLocalizedName()));
                }
                canApplyWithEnchantment(trait, enchantments);
            }
        }
        final NBTTagList modifiers = TagUtil.getBaseModifiersTagList(stack);
        for (int j = 0; j < modifiers.func_74745_c(); ++j) {
            final String id2 = modifiers.func_150307_f(j);
            final IModifier mod = TinkerRegistry.getModifier(id2);
            if (mod != null) {
                if (!this.canApplyTogether(mod) || !mod.canApplyTogether(this)) {
                    throw new TinkerGuiException(Util.translateFormatted("gui.error.incompatible_modifiers", this.getLocalizedName(), mod.getLocalizedName()));
                }
                canApplyWithEnchantment(mod, enchantments);
            }
        }
        canApplyWithEnchantment(this, enchantments);
        for (final ModifierAspect aspect : this.aspects) {
            if (!aspect.canApply(stack, original)) {
                return false;
            }
        }
        return this.canApplyCustom(stack);
    }
    
    private static void canApplyWithEnchantment(final IToolMod iToolMod, final Set<Enchantment> enchantments) throws TinkerGuiException {
        for (final Enchantment enchantment : enchantments) {
            if (!iToolMod.canApplyTogether(enchantment)) {
                final String enchName = I18n.func_74838_a(enchantment.func_77320_a());
                throw new TinkerGuiException(Util.translateFormatted("gui.error.incompatible_enchantments", iToolMod.getLocalizedName(), enchName));
            }
        }
    }
    
    public boolean canApplyTogether(final Enchantment enchantment) {
        return true;
    }
    
    public boolean canApplyTogether(final IToolMod otherModifier) {
        return true;
    }
    
    protected boolean canApplyCustom(final ItemStack stack) throws TinkerGuiException {
        return true;
    }
    
    public void updateNBT(final NBTTagCompound modifierTag) {
    }
    
    public void apply(final ItemStack stack) {
        final NBTTagCompound root = TagUtil.getTagSafe(stack);
        this.apply(root);
        stack.func_77982_d(root);
    }
    
    public void apply(final NBTTagCompound root) {
        if (!TinkerUtil.hasModifier(root, this.getIdentifier())) {
            final NBTTagList tagList = TagUtil.getBaseModifiersTagList(root);
            tagList.func_74742_a((NBTBase)new NBTTagString(this.getIdentifier()));
            TagUtil.setBaseModifiersTagList(root, tagList);
        }
        NBTTagCompound modifierTag = new NBTTagCompound();
        final NBTTagList tagList = TagUtil.getModifiersTagList(root);
        final int index = TinkerUtil.getIndexInList(tagList, this.identifier);
        if (index >= 0) {
            modifierTag = tagList.func_150305_b(index);
        }
        for (final ModifierAspect aspect : this.aspects) {
            aspect.updateNBT(root, modifierTag);
        }
        this.updateNBT(modifierTag);
        if (!modifierTag.func_82582_d()) {
            final ModifierNBT data = ModifierNBT.readTag(modifierTag);
            if (!this.identifier.equals(data.identifier)) {
                data.identifier = this.identifier;
                data.write(modifierTag);
            }
        }
        if (index >= 0) {
            tagList.func_150304_a(index, (NBTBase)modifierTag);
        }
        else {
            tagList.func_74742_a((NBTBase)modifierTag);
        }
        TagUtil.setModifiersTagList(root, tagList);
        this.applyEffect(root, modifierTag);
    }
    
    public String getTooltip(final NBTTagCompound modifierTag, final boolean detailed) {
        final StringBuilder sb = new StringBuilder();
        final ModifierNBT data = ModifierNBT.readTag(modifierTag);
        sb.append(this.getLocalizedName());
        if (data.level > 1) {
            sb.append(" ");
            sb.append(TinkerUtil.getRomanNumeral(data.level));
        }
        return sb.toString();
    }
    
    public String getLeveledTooltip(final NBTTagCompound modifierTag, final boolean detailed) {
        final ModifierNBT data = ModifierNBT.readInteger(modifierTag);
        return this.getLeveledTooltip(data.level, detailed ? (" " + data.extraInfo) : "");
    }
    
    public String getLeveledTooltip(final int level, @Nullable final String suffix) {
        String basic = this.getLocalizedName();
        if (level == 0) {
            return basic;
        }
        if (level > 1) {
            basic = basic + " " + TinkerUtil.getRomanNumeral(level);
        }
        for (int i = level; i > 1; --i) {
            if (I18n.func_94522_b(String.format("modifier.%s.name" + i, this.getIdentifier()))) {
                basic = I18n.func_74838_a(String.format("modifier.%s.name" + i, this.getIdentifier()));
                break;
            }
        }
        if (suffix != null) {
            basic += suffix;
        }
        return basic;
    }
    
    public String getLocalizedName() {
        return Util.translate("modifier.%s.name", this.getIdentifier());
    }
    
    public String getLocalizedDesc() {
        return Util.translate("modifier.%s.desc", this.getIdentifier());
    }
    
    public List<String> getExtraInfo(final ItemStack tool, final NBTTagCompound modifierTag) {
        return (List<String>)ImmutableList.of();
    }
    
    public boolean equalModifier(final NBTTagCompound modifierTag1, final NBTTagCompound modifierTag2) {
        final ModifierNBT data1 = ModifierNBT.readTag(modifierTag1);
        final ModifierNBT data2 = ModifierNBT.readTag(modifierTag2);
        return data1.identifier.equals(data2.identifier) && data1.level == data2.level;
    }
    
    public boolean hasTexturePerMaterial() {
        return false;
    }
    
    protected static boolean attackEntitySecondary(final DamageSource source, final float damage, final Entity entity, final boolean ignoreInvulv, final boolean resetInvulv) {
        return attackEntitySecondary(source, damage, entity, ignoreInvulv, resetInvulv, true);
    }
    
    protected static boolean attackEntitySecondary(final DamageSource source, final float damage, final Entity entity, final boolean ignoreInvulv, final boolean resetInvulv, final boolean noKnockback) {
        IAttributeInstance knockbackAttribute = null;
        float oldLastDamage = 0.0f;
        if (entity instanceof EntityLivingBase) {
            oldLastDamage = ((EntityLivingBase)entity).field_110153_bc;
            if (noKnockback) {
                knockbackAttribute = ((EntityLivingBase)entity).func_110148_a(SharedMonsterAttributes.field_111266_c);
            }
        }
        if (knockbackAttribute != null) {
            knockbackAttribute.func_111121_a(Modifier.ANTI_KNOCKBACK_MOD);
        }
        if (ignoreInvulv) {
            entity.field_70172_ad = 0;
        }
        final boolean hit = entity.func_70097_a(source, damage);
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
            entityLivingBase.field_110153_bc += oldLastDamage;
        }
        if (hit && resetInvulv) {
            entity.field_70172_ad = 0;
        }
        if (knockbackAttribute != null) {
            knockbackAttribute.func_111124_b(Modifier.ANTI_KNOCKBACK_MOD);
        }
        return hit;
    }
    
    public boolean hasItemsToApplyWith() {
        return !this.items.isEmpty();
    }
    
    static {
        random = new Random();
        ANTI_KNOCKBACK_MOD = new AttributeModifier("Anti Modifier Knockback", 1.0, 0);
    }
}
