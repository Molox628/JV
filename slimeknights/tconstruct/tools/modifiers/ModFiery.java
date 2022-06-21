package slimeknights.tconstruct.tools.modifiers;

import net.minecraft.item.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.library.modifiers.*;
import slimeknights.tconstruct.tools.*;
import slimeknights.tconstruct.shared.client.*;
import net.minecraft.nbt.*;
import java.util.*;
import slimeknights.tconstruct.library.*;
import com.google.common.collect.*;

public class ModFiery extends ModifierTrait
{
    public ModFiery() {
        super("fiery", 15375922, 5, 25);
    }
    
    @Override
    public void onHit(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damage, final boolean isCritical) {
        this.dealFireDamage(tool, player, target);
    }
    
    protected void dealFireDamage(final ItemStack tool, final EntityLivingBase attacker, final EntityLivingBase target) {
        final NBTTagCompound tag = TinkerUtil.getModifierTag(tool, this.identifier);
        final ModifierNBT.IntegerNBT data = ModifierNBT.readInteger(tag);
        final int duration = this.getFireDuration(data);
        target.func_70015_d(duration);
        final float fireDamage = this.getFireDamage(data);
        if (Modifier.attackEntitySecondary(new EntityDamageSource("onFire", (Entity)attacker).func_76361_j(), fireDamage, (Entity)target, false, true)) {
            final int count = Math.round(fireDamage);
            TinkerTools.proxy.spawnEffectParticle(ParticleEffect.Type.HEART_FIRE, (Entity)target, count);
        }
    }
    
    private float getFireDamage(final ModifierNBT.IntegerNBT data) {
        return data.current / 15.0f;
    }
    
    private int getFireDuration(final ModifierNBT.IntegerNBT data) {
        return 1 + data.current / 8;
    }
    
    @Override
    public List<String> getExtraInfo(final ItemStack tool, final NBTTagCompound modifierTag) {
        final String loc = String.format("modifier.%s.extra", this.getIdentifier());
        final ModifierNBT.IntegerNBT data = ModifierNBT.readInteger(modifierTag);
        final int duration = this.getFireDuration(data);
        final float dmg = this.getFireDamage(data);
        return (List<String>)ImmutableList.of((Object)Util.translateFormatted(loc, Util.df.format(dmg)), (Object)Util.translateFormatted(loc + 2, Util.df.format(duration)));
    }
}
