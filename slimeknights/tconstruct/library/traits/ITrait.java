package slimeknights.tconstruct.library.traits;

import slimeknights.tconstruct.library.modifiers.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.world.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraft.inventory.*;
import javax.annotation.*;
import com.google.common.collect.*;
import net.minecraft.entity.ai.attributes.*;

public interface ITrait extends IToolMod
{
    void onUpdate(final ItemStack p0, final World p1, final Entity p2, final int p3, final boolean p4);
    
    void onArmorTick(final ItemStack p0, final World p1, final EntityPlayer p2);
    
    void miningSpeed(final ItemStack p0, final PlayerEvent.BreakSpeed p1);
    
    void beforeBlockBreak(final ItemStack p0, final BlockEvent.BreakEvent p1);
    
    void afterBlockBreak(final ItemStack p0, final World p1, final IBlockState p2, final BlockPos p3, final EntityLivingBase p4, final boolean p5);
    
    void blockHarvestDrops(final ItemStack p0, final BlockEvent.HarvestDropsEvent p1);
    
    boolean isCriticalHit(final ItemStack p0, final EntityLivingBase p1, final EntityLivingBase p2);
    
    float damage(final ItemStack p0, final EntityLivingBase p1, final EntityLivingBase p2, final float p3, final float p4, final boolean p5);
    
    void onHit(final ItemStack p0, final EntityLivingBase p1, final EntityLivingBase p2, final float p3, final boolean p4);
    
    float knockBack(final ItemStack p0, final EntityLivingBase p1, final EntityLivingBase p2, final float p3, final float p4, final float p5, final boolean p6);
    
    void afterHit(final ItemStack p0, final EntityLivingBase p1, final EntityLivingBase p2, final float p3, final boolean p4, final boolean p5);
    
    void onBlock(final ItemStack p0, final EntityPlayer p1, final LivingHurtEvent p2);
    
    default void onPlayerHurt(final ItemStack tool, final EntityPlayer player, final EntityLivingBase attacker, final LivingHurtEvent event) {
    }
    
    int onToolDamage(final ItemStack p0, final int p1, final int p2, final EntityLivingBase p3);
    
    int onToolHeal(final ItemStack p0, final int p1, final int p2, final EntityLivingBase p3);
    
    void onRepair(final ItemStack p0, final int p1);
    
    default void getAttributeModifiers(@Nonnull final EntityEquipmentSlot slot, final ItemStack stack, final Multimap<String, AttributeModifier> attributeMap) {
    }
    
    default int getPriority() {
        return 100;
    }
}
