package slimeknights.tconstruct.gadgets.item;

import slimeknights.mantle.item.*;
import net.minecraft.inventory.*;
import net.minecraftforge.common.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import javax.annotation.*;
import net.minecraftforge.items.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.server.*;
import slimeknights.tconstruct.common.*;
import net.minecraft.network.*;
import net.minecraft.potion.*;
import net.minecraftforge.event.*;
import slimeknights.tconstruct.library.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.init.*;
import net.minecraftforge.common.util.*;
import slimeknights.tconstruct.library.potion.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.gadgets.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.library.capability.piggyback.*;
import net.minecraft.client.*;
import net.minecraftforge.fml.relauncher.*;
import slimeknights.tconstruct.library.client.*;
import slimeknights.mantle.client.gui.*;

public class ItemPiggybackPack extends ItemArmorTooltip
{
    private static final int MAX_ENTITY_STACK = 3;
    public static ItemArmor.ArmorMaterial PIGGYBACK_MATERIAL;
    
    public ItemPiggybackPack() {
        super(ItemPiggybackPack.PIGGYBACK_MATERIAL, 0, EntityEquipmentSlot.CHEST);
        this.func_77625_d(16);
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @Nonnull
    public ActionResult<ItemStack> func_77659_a(final World worldIn, final EntityPlayer playerIn, final EnumHand hand) {
        final ItemStack itemStackIn = playerIn.func_184586_b(hand);
        return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.PASS, (Object)itemStackIn);
    }
    
    public boolean func_111207_a(final ItemStack stack, final EntityPlayer playerIn, final EntityLivingBase target, final EnumHand hand) {
        ItemStack chestArmor = playerIn.func_184582_a(this.field_77881_a);
        if (chestArmor.func_77973_b() != this && playerIn.field_71071_by.func_70447_i() == -1) {
            return false;
        }
        if (this.pickupEntity(playerIn, (Entity)target)) {
            if (chestArmor.func_77973_b() != this) {
                ItemHandlerHelper.giveItemToPlayer(playerIn, chestArmor);
                chestArmor = ItemStack.field_190927_a;
            }
            if (chestArmor.func_190926_b()) {
                playerIn.func_184201_a(this.field_77881_a, stack.func_77979_a(1));
            }
            else if (chestArmor.func_190916_E() < this.getEntitiesCarriedCount((EntityLivingBase)playerIn)) {
                stack.func_77979_a(1);
                chestArmor.func_190917_f(1);
            }
            return true;
        }
        return false;
    }
    
    public boolean pickupEntity(final EntityPlayer player, final Entity target) {
        if (player.func_130014_f_().field_72995_K) {
            return false;
        }
        if (target.func_184187_bx() == player || player.func_184187_bx() == target) {
            return false;
        }
        int count = 0;
        Entity toRide = (Entity)player;
        while (toRide.func_184207_aI() && count < 3) {
            toRide = toRide.func_184188_bt().get(0);
            ++count;
            if (toRide instanceof EntityPlayer && target instanceof EntityPlayer) {
                return false;
            }
        }
        if (!toRide.func_184207_aI() && count < 3 && target.func_184205_a(toRide, true)) {
            if (player instanceof EntityPlayerMP) {
                TinkerNetwork.sendPacket((Entity)player, (Packet<?>)new SPacketSetPassengers((Entity)player));
            }
            return true;
        }
        return false;
    }
    
    public int getEntitiesCarriedCount(final EntityLivingBase player) {
        int count = 0;
        for (Entity ridden = (Entity)player; ridden.func_184207_aI(); ridden = ridden.func_184188_bt().get(0)) {
            ++count;
        }
        return count;
    }
    
    public void matchCarriedEntitiesToCount(final EntityLivingBase player, final int maxCount) {
        int count = 0;
        Entity ridden = (Entity)player;
        while (ridden.func_184207_aI()) {
            ridden = ridden.func_184188_bt().get(0);
            if (++count > maxCount) {
                ridden.func_184210_p();
            }
        }
    }
    
    public void func_77663_a(final ItemStack stack, final World worldIn, final Entity entityIn, final int itemSlot, final boolean isSelected) {
        if (entityIn instanceof EntityLivingBase) {
            final EntityLivingBase entityLivingBase = (EntityLivingBase)entityIn;
            if (entityLivingBase.func_184582_a(EntityEquipmentSlot.CHEST) == stack && entityIn.func_184207_aI()) {
                final int amplifier = this.getEntitiesCarriedCount(entityLivingBase) - 1;
                entityLivingBase.func_70690_d(new PotionEffect((Potion)CarryPotionEffect.INSTANCE, 1, amplifier, true, false));
            }
        }
    }
    
    @SubscribeEvent
    public void attachCapability(final AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(Util.getResource("piggyback"), (ICapabilityProvider)new TinkerPiggybackSerializer((EntityPlayer)event.getObject()));
        }
    }
    
    static {
        ItemPiggybackPack.PIGGYBACK_MATERIAL = EnumHelper.addArmorMaterial("PIGGYBACK", Util.resource("piggyback"), 0, new int[] { 0, 0, 0, 0 }, 0, SoundEvents.field_187884_fr, 0.0f);
    }
    
    public static class CarryPotionEffect extends TinkerPotion
    {
        public static final CarryPotionEffect INSTANCE;
        public static final String UUID = "ff4de63a-2b24-11e6-b67b-9e71128cae77";
        
        protected CarryPotionEffect() {
            super(Util.getResource("carry"), false, true);
            this.func_111184_a(SharedMonsterAttributes.field_111263_d, "ff4de63a-2b24-11e6-b67b-9e71128cae77", -0.05, 2);
        }
        
        public boolean func_76397_a(final int duration, final int amplifier) {
            return true;
        }
        
        public void func_76394_a(@Nonnull final EntityLivingBase entityLivingBaseIn, final int p_76394_2_) {
            final ItemStack chestArmor = entityLivingBaseIn.func_184582_a(EntityEquipmentSlot.CHEST);
            if (chestArmor.func_190926_b()) {
                TinkerGadgets.piggybackPack.matchCarriedEntitiesToCount(entityLivingBaseIn, 0);
            }
            else if (chestArmor.func_77973_b() == TinkerGadgets.piggybackPack) {
                TinkerGadgets.piggybackPack.matchCarriedEntitiesToCount(entityLivingBaseIn, chestArmor.func_190916_E());
                if (!entityLivingBaseIn.func_130014_f_().field_72995_K && entityLivingBaseIn.hasCapability((Capability)CapabilityTinkerPiggyback.PIGGYBACK, (EnumFacing)null)) {
                    final ITinkerPiggyback piggyback = (ITinkerPiggyback)entityLivingBaseIn.getCapability((Capability)CapabilityTinkerPiggyback.PIGGYBACK, (EnumFacing)null);
                    piggyback.updatePassengers();
                }
            }
        }
        
        @SideOnly(Side.CLIENT)
        public void renderInventoryEffect(final int x, final int y, final PotionEffect effect, final Minecraft mc) {
            this.renderHUDEffect(x, y, effect, mc, 1.0f);
        }
        
        @SideOnly(Side.CLIENT)
        public void renderHUDEffect(final int x, final int y, final PotionEffect effect, final Minecraft mc, final float alpha) {
            mc.func_110434_K().func_110577_a(Icons.ICON);
            GuiElement element = null;
            switch (effect.func_76458_c()) {
                case 0: {
                    element = Icons.ICON_PIGGYBACK_1;
                    break;
                }
                case 1: {
                    element = Icons.ICON_PIGGYBACK_2;
                    break;
                }
                case 2: {
                    element = Icons.ICON_PIGGYBACK_3;
                    break;
                }
                default: {
                    element = Icons.ICON_PIGGYBACK_3;
                    break;
                }
            }
            element.draw(x + 6, y + 7);
        }
        
        static {
            INSTANCE = new CarryPotionEffect();
        }
    }
}
