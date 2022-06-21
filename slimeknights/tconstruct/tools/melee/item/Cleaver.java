package slimeknights.tconstruct.tools.melee.item;

import slimeknights.tconstruct.tools.*;
import slimeknights.tconstruct.library.tinkering.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import javax.annotation.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.library.client.particle.*;
import java.util.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.library.materials.*;
import net.minecraft.nbt.*;
import slimeknights.tconstruct.tools.modifiers.*;

public class Cleaver extends SwordCore
{
    public static final float DURABILITY_MODIFIER = 2.0f;
    
    public Cleaver() {
        super(new PartMaterialType[] { PartMaterialType.handle(TinkerTools.toughToolRod), PartMaterialType.head(TinkerTools.largeSwordBlade), PartMaterialType.head(TinkerTools.largePlate), PartMaterialType.extra(TinkerTools.toughToolRod) });
        this.addCategory(Category.WEAPON);
    }
    
    @Nonnull
    public ActionResult<ItemStack> func_77659_a(final World worldIn, final EntityPlayer playerIn, final EnumHand hand) {
        final ItemStack itemStackIn = playerIn.func_184586_b(hand);
        return (ActionResult<ItemStack>)ActionResult.newResult(EnumActionResult.SUCCESS, (Object)itemStackIn);
    }
    
    @Override
    public float damagePotential() {
        return 1.2f;
    }
    
    @Override
    public double attackSpeed() {
        return 0.7;
    }
    
    @Override
    public float damageCutoff() {
        return 25.0f;
    }
    
    @Override
    public int[] getRepairParts() {
        return new int[] { 1, 2 };
    }
    
    @Override
    public boolean dealDamage(final ItemStack stack, final EntityLivingBase player, final Entity entity, final float damage) {
        final boolean hit = super.dealDamage(stack, player, entity, damage);
        if (hit && this.readyForSpecialAttack(player)) {
            TinkerTools.proxy.spawnAttackParticle(Particles.CLEAVER_ATTACK, (Entity)player, 0.85);
        }
        return hit;
    }
    
    @Override
    public float getRepairModifierForPart(final int index) {
        return (index == 1) ? 2.0f : 1.5f;
    }
    
    public ToolNBT buildTagData(final List<Material> materials) {
        final HandleMaterialStats handle = materials.get(0).getStatsOrUnknown("handle");
        final HeadMaterialStats head = materials.get(1).getStatsOrUnknown("head");
        final HeadMaterialStats shield = materials.get(2).getStatsOrUnknown("head");
        final ExtraMaterialStats guard = materials.get(3).getStatsOrUnknown("extra");
        final ToolNBT data = new ToolNBT();
        data.head(head, shield);
        data.extra(guard);
        data.handle(handle);
        final ToolNBT toolNBT = data;
        toolNBT.attack *= 1.3f;
        final ToolNBT toolNBT2 = data;
        toolNBT2.attack += 3.0f;
        final ToolNBT toolNBT3 = data;
        toolNBT3.durability *= (int)2.0f;
        return data;
    }
    
    @Override
    public void addMaterialTraits(final NBTTagCompound root, final List<Material> materials) {
        super.addMaterialTraits(root, materials);
        ModBeheading.CLEAVER_BEHEADING_MOD.apply(root);
        ModBeheading.CLEAVER_BEHEADING_MOD.apply(root);
    }
}
