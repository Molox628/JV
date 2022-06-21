package slimeknights.tconstruct.library.traits;

import net.minecraft.util.text.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.world.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraftforge.event.entity.living.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.tconstruct.library.modifiers.*;
import net.minecraft.nbt.*;

public abstract class AbstractTrait extends Modifier implements ITrait
{
    public static final String LOC_Name = "modifier.%s.name";
    public static final String LOC_Desc = "modifier.%s.desc";
    protected final int color;
    
    public AbstractTrait(final String identifier, final TextFormatting color) {
        this(identifier, Util.enumChatFormattingToColor(color));
    }
    
    public AbstractTrait(final String identifier, final int color) {
        super(Util.sanitizeLocalizationString(identifier));
        this.color = color;
        this.addAspects(new ModifierAspect.SingleAspect(this));
    }
    
    @Override
    public String getIdentifier() {
        return this.identifier;
    }
    
    @Override
    public String getLocalizedName() {
        return Util.translate("modifier.%s.name", this.getIdentifier());
    }
    
    @Override
    public String getLocalizedDesc() {
        return Util.translate("modifier.%s.desc", this.getIdentifier());
    }
    
    @Override
    public boolean isHidden() {
        return false;
    }
    
    @Override
    public void onUpdate(final ItemStack tool, final World world, final Entity entity, final int itemSlot, final boolean isSelected) {
    }
    
    @Override
    public void onArmorTick(final ItemStack tool, final World world, final EntityPlayer player) {
    }
    
    @Override
    public void miningSpeed(final ItemStack tool, final PlayerEvent.BreakSpeed event) {
    }
    
    @Override
    public void beforeBlockBreak(final ItemStack tool, final BlockEvent.BreakEvent event) {
    }
    
    @Override
    public void afterBlockBreak(final ItemStack tool, final World world, final IBlockState state, final BlockPos pos, final EntityLivingBase player, final boolean wasEffective) {
    }
    
    @Override
    public void blockHarvestDrops(final ItemStack tool, final BlockEvent.HarvestDropsEvent event) {
    }
    
    @Override
    public boolean isCriticalHit(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target) {
        return false;
    }
    
    @Override
    public float damage(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damage, final float newDamage, final boolean isCritical) {
        return newDamage;
    }
    
    @Override
    public void onHit(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damage, final boolean isCritical) {
    }
    
    @Override
    public void afterHit(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damageDealt, final boolean wasCritical, final boolean wasHit) {
    }
    
    @Override
    public float knockBack(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damage, final float knockback, final float newKnockback, final boolean isCritical) {
        return newKnockback;
    }
    
    @Override
    public void onBlock(final ItemStack tool, final EntityPlayer player, final LivingHurtEvent event) {
    }
    
    @Override
    public int onToolDamage(final ItemStack tool, final int damage, final int newDamage, final EntityLivingBase entity) {
        return newDamage;
    }
    
    @Override
    public int onToolHeal(final ItemStack tool, final int amount, final int newAmount, final EntityLivingBase entity) {
        return newAmount;
    }
    
    @Override
    public void onRepair(final ItemStack tool, final int amount) {
    }
    
    public String getModifierIdentifier() {
        return this.identifier;
    }
    
    public boolean canApplyCustom(final ItemStack stack) {
        final NBTTagList tagList = TagUtil.getTraitsTagList(stack);
        final int index = TinkerUtil.getIndexInList(tagList, this.getIdentifier());
        return index < 0;
    }
    
    @Override
    public void updateNBT(final NBTTagCompound modifierTag) {
        this.updateNBTforTrait(modifierTag, this.color);
    }
    
    public void updateNBTforTrait(final NBTTagCompound modifierTag, final int newColor) {
        final ModifierNBT data = ModifierNBT.readTag(modifierTag);
        data.identifier = this.getModifierIdentifier();
        data.color = newColor;
        if (data.level == 0) {
            data.level = 1;
        }
        data.write(modifierTag);
    }
    
    public void applyEffect(final NBTTagCompound rootCompound, final NBTTagCompound modifierTag) {
        final NBTTagList traits = TagUtil.getTraitsTagList(rootCompound);
        for (int i = 0; i < traits.func_74745_c(); ++i) {
            if (this.identifier.equals(traits.func_150307_f(i))) {
                return;
            }
        }
        traits.func_74742_a((NBTBase)new NBTTagString(this.identifier));
        TagUtil.setTraitsTagList(rootCompound, traits);
    }
    
    protected boolean isToolWithTrait(final ItemStack itemStack) {
        return TinkerUtil.hasTrait(TagUtil.getTagSafe(itemStack), this.getIdentifier());
    }
}
