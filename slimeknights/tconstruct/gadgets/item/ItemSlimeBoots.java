package slimeknights.tconstruct.gadgets.item;

import slimeknights.mantle.item.*;
import net.minecraft.inventory.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import slimeknights.tconstruct.shared.block.*;
import net.minecraft.entity.ai.attributes.*;
import com.google.common.collect.*;
import slimeknights.mantle.util.*;
import javax.annotation.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraftforge.event.entity.living.*;
import slimeknights.tconstruct.tools.common.network.*;
import slimeknights.tconstruct.common.*;
import slimeknights.mantle.network.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import slimeknights.tconstruct.library.*;
import net.minecraftforge.common.util.*;

public class ItemSlimeBoots extends ItemArmorTooltip
{
    public static ItemArmor.ArmorMaterial SLIME_MATERIAL;
    
    public ItemSlimeBoots() {
        super(ItemSlimeBoots.SLIME_MATERIAL, 0, EntityEquipmentSlot.FEET);
        this.func_77637_a((CreativeTabs)TinkerRegistry.tabGadgets);
        this.func_77625_d(1);
        this.field_77787_bX = true;
    }
    
    public boolean isValidArmor(final ItemStack stack, final EntityEquipmentSlot armorType, final Entity entity) {
        return armorType == EntityEquipmentSlot.FEET;
    }
    
    public ItemStack onItemRightClick(final ItemStack stack, final World world, final EntityPlayer player) {
        final ItemStack itemstack = player.func_184582_a(EntityEquipmentSlot.FEET);
        if (itemstack.func_190926_b()) {
            player.func_184201_a(EntityEquipmentSlot.FEET, stack.func_77946_l());
            stack.func_190918_g(1);
        }
        return stack;
    }
    
    public boolean func_82816_b_(final ItemStack stack) {
        return true;
    }
    
    public int func_82814_b(final ItemStack stack) {
        final BlockSlime.SlimeType type = BlockSlime.SlimeType.fromMeta(stack.func_77960_j());
        return type.getBallColor();
    }
    
    public boolean hasOverlay(final ItemStack stack) {
        return true;
    }
    
    public Multimap<String, AttributeModifier> getAttributeModifiers(final EntityEquipmentSlot slot, final ItemStack stack) {
        return (Multimap<String, AttributeModifier>)HashMultimap.create();
    }
    
    @Nonnull
    public String func_77667_c(final ItemStack stack) {
        final int meta = stack.func_77960_j();
        if (meta < BlockSlime.SlimeType.values().length) {
            return super.func_77667_c(stack) + "." + LocUtils.makeLocString(BlockSlime.SlimeType.values()[meta].name());
        }
        return super.func_77667_c(stack);
    }
    
    @SideOnly(Side.CLIENT)
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> subItems) {
        if (this.func_194125_a(tab)) {
            for (final BlockSlime.SlimeType type : BlockSlime.SlimeType.values()) {
                subItems.add((Object)new ItemStack((Item)this, 1, type.getMeta()));
            }
        }
    }
    
    @SubscribeEvent
    public void onFall(final LivingFallEvent event) {
        final EntityLivingBase entity = event.getEntityLiving();
        if (entity == null) {
            return;
        }
        final ItemStack feet = entity.func_184582_a(EntityEquipmentSlot.FEET);
        if (feet.func_77973_b() != this) {
            return;
        }
        final boolean isClient = entity.func_130014_f_().field_72995_K;
        if (!entity.func_70093_af() && event.getDistance() > 2.0f) {
            event.setDamageMultiplier(0.0f);
            entity.field_70143_R = 0.0f;
            if (isClient) {
                final EntityLivingBase entityLivingBase = entity;
                entityLivingBase.field_70181_x *= -0.9;
                entity.field_70160_al = true;
                entity.field_70122_E = false;
                final double f = 0.9500000000000001;
                final EntityLivingBase entityLivingBase2 = entity;
                entityLivingBase2.field_70159_w /= f;
                final EntityLivingBase entityLivingBase3 = entity;
                entityLivingBase3.field_70179_y /= f;
                TinkerNetwork.sendToServer((AbstractPacket)new BouncedPacket());
            }
            else {
                event.setCanceled(true);
            }
            entity.func_184185_a(SoundEvents.field_187886_fs, 1.0f, 1.0f);
            SlimeBounceHandler.addBounceHandler(entity, entity.field_70181_x);
        }
        else if (!isClient && entity.func_70093_af()) {
            event.setDamageMultiplier(0.2f);
        }
    }
    
    static {
        ItemSlimeBoots.SLIME_MATERIAL = EnumHelper.addArmorMaterial("SLIME", Util.resource("slime"), 0, new int[] { 0, 0, 0, 0 }, 0, SoundEvents.field_187884_fr, 0.0f);
    }
}
