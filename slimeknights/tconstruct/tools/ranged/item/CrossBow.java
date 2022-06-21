package slimeknights.tconstruct.tools.ranged.item;

import slimeknights.tconstruct.library.tools.ranged.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.library.tinkering.*;
import slimeknights.tconstruct.library.client.*;
import net.minecraft.world.*;
import net.minecraft.creativetab.*;
import slimeknights.tconstruct.tools.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import javax.annotation.*;
import net.minecraft.entity.player.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.tconstruct.common.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.tools.ranged.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.client.crosshair.*;
import net.minecraftforge.fml.relauncher.*;
import slimeknights.tconstruct.library.tools.*;

public class CrossBow extends BowCore implements ICustomCrosshairUser
{
    private static final String TAG_Loaded = "Loaded";
    protected static final ResourceLocation PROPERTY_IS_LOADED;
    private ImmutableList<Item> boltMatches;
    
    public CrossBow() {
        super(new PartMaterialType[] { PartMaterialType.crossbow(TinkerTools.toughToolRod), PartMaterialType.bow(TinkerTools.bowLimb), PartMaterialType.extra(TinkerTools.toughBinding), PartMaterialType.bowstring(TinkerTools.bowString) });
        this.boltMatches = null;
        this.func_185043_a(CrossBow.PROPERTY_PULL_PROGRESS, this.pullProgressPropertyGetter);
        this.func_185043_a(CrossBow.PROPERTY_IS_PULLING, this.isPullingPropertyGetter);
        this.func_185043_a(CrossBow.PROPERTY_IS_LOADED, (IItemPropertyGetter)new BooleanItemPropertyGetter() {
            @Override
            public boolean applyIf(final ItemStack stack, @Nullable final World worldIn, @Nullable final EntityLivingBase entityIn) {
                return entityIn != null && CrossBow.this.isLoaded(stack);
            }
        });
    }
    
    @Override
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> subItems) {
        if (this.func_194125_a(tab)) {
            this.addDefaultSubItems((List<ItemStack>)subItems, null, null, null, TinkerMaterials.string);
        }
    }
    
    @Override
    public float damagePotential() {
        return 0.8f;
    }
    
    @Override
    public double attackSpeed() {
        return 2.0;
    }
    
    @Override
    public float baseProjectileDamage() {
        return 3.0f;
    }
    
    @Override
    protected float baseProjectileSpeed() {
        return 7.0f;
    }
    
    @Override
    public float projectileDamageModifier() {
        return 1.3f;
    }
    
    @Override
    public int getDrawTime() {
        return 45;
    }
    
    public boolean isLoaded(final ItemStack stack) {
        return TagUtil.getTagSafe(stack).func_74767_n("Loaded");
    }
    
    public void setLoaded(final ItemStack stack, final boolean isLoaded) {
        final NBTTagCompound tag = TagUtil.getTagSafe(stack);
        tag.func_74757_a("Loaded", isLoaded);
        stack.func_77982_d(tag);
    }
    
    @Override
    public void func_77663_a(final ItemStack stack, final World worldIn, final Entity entityIn, final int itemSlot, final boolean isSelected) {
        this.preventSlowDown(entityIn, 0.195f);
        super.func_77663_a(stack, worldIn, entityIn, itemSlot, isSelected);
    }
    
    @Nonnull
    @Override
    public EnumAction func_77661_b(final ItemStack stack) {
        return EnumAction.NONE;
    }
    
    @Nonnull
    @Override
    public ActionResult<ItemStack> func_77659_a(final World worldIn, final EntityPlayer playerIn, final EnumHand hand) {
        final ItemStack itemStackIn = playerIn.func_184586_b(hand);
        if (this.isLoaded(itemStackIn) && !ToolHelper.isBroken(itemStackIn)) {
            super.func_77615_a(itemStackIn, worldIn, (EntityLivingBase)playerIn, 0);
            this.setLoaded(itemStackIn, false);
            return (ActionResult<ItemStack>)ActionResult.newResult(EnumActionResult.SUCCESS, (Object)itemStackIn);
        }
        return super.func_77659_a(worldIn, playerIn, hand);
    }
    
    @Override
    public void func_77615_a(final ItemStack stack, final World worldIn, final EntityLivingBase entityLiving, final int timeLeft) {
        if (!ToolHelper.isBroken(stack) && entityLiving instanceof EntityPlayer) {
            final int useTime = this.func_77626_a(stack) - timeLeft;
            if (this.getDrawbackProgress(stack, useTime) >= 1.0f) {
                Sounds.PlaySoundForPlayer((Entity)entityLiving, Sounds.crossbow_reload, 1.5f, 0.9f + CrossBow.field_77697_d.nextFloat() * 0.1f);
                this.setLoaded(stack, true);
            }
        }
    }
    
    @Override
    public void playShootSound(final float power, final World world, final EntityPlayer entityPlayer) {
        world.func_184148_a((EntityPlayer)null, entityPlayer.field_70165_t, entityPlayer.field_70163_u, entityPlayer.field_70161_v, SoundEvents.field_187737_v, SoundCategory.NEUTRAL, 1.0f, 0.5f + CrossBow.field_77697_d.nextFloat() * 0.1f);
    }
    
    @Override
    public ItemStack getAmmoToRender(final ItemStack weapon, final EntityLivingBase player) {
        if (!this.isLoaded(weapon)) {
            return ItemStack.field_190927_a;
        }
        return super.getAmmoToRender(weapon, player);
    }
    
    @Override
    protected List<Item> getAmmoItems() {
        if (this.boltMatches == null) {
            final ImmutableList.Builder<Item> builder = (ImmutableList.Builder<Item>)ImmutableList.builder();
            if (TinkerRangedWeapons.bolt != null) {
                builder.add((Object)TinkerRangedWeapons.bolt);
            }
            this.boltMatches = (ImmutableList<Item>)builder.build();
        }
        return (List<Item>)this.boltMatches;
    }
    
    @Override
    public ProjectileLauncherNBT buildTagData(final List<Material> materials) {
        final ProjectileLauncherNBT data = new ProjectileLauncherNBT();
        final HandleMaterialStats body = materials.get(0).getStatsOrUnknown("handle");
        final ExtraMaterialStats bodyExtra = materials.get(0).getStatsOrUnknown("extra");
        final HeadMaterialStats head = materials.get(1).getStatsOrUnknown("head");
        final BowMaterialStats limb = materials.get(1).getStatsOrUnknown("bow");
        final ExtraMaterialStats binding = materials.get(2).getStatsOrUnknown("extra");
        final BowStringMaterialStats bowstring = materials.get(3).getStatsOrUnknown("bowstring");
        data.head(head);
        data.limb(limb);
        data.extra(binding, bodyExtra);
        data.handle(body);
        data.bowstring(bowstring);
        final ProjectileLauncherNBT projectileLauncherNBT = data;
        projectileLauncherNBT.bonusDamage *= 1.5f;
        return data;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public ICrosshair getCrosshair(final ItemStack itemStack, final EntityPlayer player) {
        return Crosshairs.T;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public float getCrosshairState(final ItemStack itemStack, final EntityPlayer player) {
        if (this.isLoaded(itemStack)) {
            return 1.0f;
        }
        if (player.func_184607_cu() != itemStack) {
            return 0.0f;
        }
        return this.getDrawbackProgress(itemStack, (EntityLivingBase)player);
    }
    
    static {
        PROPERTY_IS_LOADED = new ResourceLocation("loaded");
    }
}
