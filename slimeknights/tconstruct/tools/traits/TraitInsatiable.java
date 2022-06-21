package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import slimeknights.tconstruct.library.potion.*;
import net.minecraft.util.text.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.library.*;

public class TraitInsatiable extends AbstractTrait
{
    public static TinkerPotion Insatiable;
    
    public TraitInsatiable() {
        super("insatiable", TextFormatting.DARK_PURPLE);
    }
    
    @Override
    public float damage(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damage, final float newDamage, final boolean isCritical) {
        final float bonus = TraitInsatiable.Insatiable.getLevel(player) / 3.0f;
        return super.damage(tool, player, target, damage, newDamage, isCritical) + bonus;
    }
    
    @Override
    public void afterHit(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damageDealt, final boolean wasCritical, final boolean wasHit) {
        int level = 1;
        level += TraitInsatiable.Insatiable.getLevel(player);
        level = Math.min(10, level);
        TraitInsatiable.Insatiable.apply(player, 100, level);
    }
    
    @Override
    public int onToolDamage(final ItemStack tool, final int damage, final int newDamage, final EntityLivingBase entity) {
        final int level = TraitInsatiable.Insatiable.getLevel(entity) / 3;
        return super.onToolDamage(tool, damage, newDamage, entity) + level;
    }
    
    static {
        TraitInsatiable.Insatiable = new TinkerPotion(Util.getResource("insatiable"), true, false);
    }
}
