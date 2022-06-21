package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.tools.*;
import slimeknights.tconstruct.shared.client.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.entity.player.*;
import slimeknights.tconstruct.common.*;
import slimeknights.tconstruct.library.modifiers.*;
import net.minecraft.nbt.*;

public class TraitShocking extends AbstractTrait
{
    public TraitShocking() {
        super("shocking", 16777215);
    }
    
    @Override
    public void onHit(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damage, final boolean isCritical) {
        if (player.func_130014_f_().field_72995_K) {
            return;
        }
        final ModifierTagHolder modtag = ModifierTagHolder.getModifier(tool, this.getModifierIdentifier());
        final Data data = modtag.getTagData(Data.class);
        if (data.charge >= 100.0f) {
            if (Modifier.attackEntitySecondary((DamageSource)new EntityDamageSource("lightningBolt", (Entity)player), 5.0f, (Entity)target, false, true, false)) {
                TinkerTools.proxy.spawnEffectParticle(ParticleEffect.Type.HEART_ELECTRO, (Entity)target, 5);
                this.discharge(tool, player, modtag, data);
                player.func_70690_d(new PotionEffect(MobEffects.field_76424_c, 50, 5));
            }
        }
        else if (player instanceof EntityPlayer) {
            this.addCharge(15.0f * ((EntityPlayer)player).func_184825_o(1.0f), tool, (Entity)player, data);
            modtag.save();
        }
    }
    
    @Override
    public void afterBlockBreak(final ItemStack tool, final World world, final IBlockState state, final BlockPos pos, final EntityLivingBase player, final boolean wasEffective) {
        if (player.func_130014_f_().field_72995_K) {
            return;
        }
        final ModifierTagHolder modtag = ModifierTagHolder.getModifier(tool, this.getModifierIdentifier());
        final Data data = modtag.getTagData(Data.class);
        this.addCharge(15.0f, tool, (Entity)player, data);
        modtag.save();
        if (data.charge >= 100.0f) {
            this.discharge(tool, player, modtag, data);
            player.func_70690_d(new PotionEffect(MobEffects.field_76422_e, 50, 2));
        }
    }
    
    @Override
    public void onUpdate(final ItemStack tool, final World world, final Entity entity, final int itemSlot, final boolean isSelected) {
        if (!isSelected || world.field_72995_K || world.func_82737_E() % 5L > 0L) {
            return;
        }
        if (entity instanceof EntityPlayer) {
            final ItemStack stackInUse = ((EntityPlayer)entity).func_184607_cu();
            if (!stackInUse.func_190926_b() && !tool.func_77973_b().shouldCauseBlockBreakReset(tool, stackInUse)) {
                return;
            }
        }
        final ModifierTagHolder modtag = ModifierTagHolder.getModifier(tool, this.getModifierIdentifier());
        final Data data = modtag.getTagData(Data.class);
        if (data.charge >= 100.0f) {
            return;
        }
        final double dx = entity.field_70165_t - data.x;
        final double dy = entity.field_70163_u - data.y;
        final double dz = entity.field_70161_v - data.z;
        double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
        if (dist < 0.10000000149011612) {
            return;
        }
        if (dist > 5.0) {
            dist = 5.0;
        }
        this.addCharge((float)(dist * 2.0), tool, entity, data);
        data.x = entity.field_70165_t;
        data.y = entity.field_70163_u;
        data.z = entity.field_70161_v;
        modtag.save();
    }
    
    private void addCharge(final float change, final ItemStack tool, final Entity entity, final Data data) {
        data.charge += change;
        if (data.charge >= 100.0f) {
            TagUtil.setEnchantEffect(tool, true);
            if (entity instanceof EntityPlayerMP) {
                Sounds.PlaySoundForPlayer(entity, Sounds.shocking_charged, 0.8f, 0.8f + 0.2f * TraitShocking.random.nextFloat());
            }
        }
    }
    
    private void discharge(final ItemStack tool, final EntityLivingBase player, final ModifierTagHolder modtag, final Data data) {
        if (player instanceof EntityPlayerMP) {
            Sounds.playSoundForAll((Entity)player, Sounds.shocking_discharge, 1.0f, 1.0f);
        }
        data.charge = 0.0f;
        modtag.save();
        TagUtil.setEnchantEffect(tool, false);
    }
    
    public static class Data extends ModifierNBT
    {
        float charge;
        double x;
        double y;
        double z;
        
        @Override
        public void read(final NBTTagCompound tag) {
            super.read(tag);
            this.charge = tag.func_74760_g("charge");
            this.x = tag.func_74769_h("x");
            this.y = tag.func_74769_h("y");
            this.z = tag.func_74769_h("z");
        }
        
        @Override
        public void write(final NBTTagCompound tag) {
            super.write(tag);
            tag.func_74776_a("charge", this.charge);
            tag.func_74780_a("x", this.x);
            tag.func_74780_a("y", this.y);
            tag.func_74780_a("z", this.z);
        }
    }
}
