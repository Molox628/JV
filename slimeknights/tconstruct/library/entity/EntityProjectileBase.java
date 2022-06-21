package slimeknights.tconstruct.library.entity;

import net.minecraft.entity.projectile.*;
import net.minecraftforge.fml.common.registry.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.traits.*;
import java.util.*;
import net.minecraftforge.common.capabilities.*;
import slimeknights.tconstruct.library.capability.projectile.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import slimeknights.tconstruct.common.*;
import net.minecraft.init.*;
import slimeknights.tconstruct.library.tools.*;
import net.minecraft.inventory.*;
import slimeknights.tconstruct.library.tools.ranged.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.library.events.*;
import javax.annotation.*;
import net.minecraft.nbt.*;
import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.*;

public abstract class EntityProjectileBase extends EntityArrow implements IEntityAdditionalSpawnData
{
    protected static final UUID PROJECTILE_POWER_MODIFIER;
    private static final AxisAlignedBB ON_BLOCK_AABB;
    public TinkerProjectileHandler tinkerProjectile;
    public boolean bounceOnNoDamage;
    public boolean defused;
    
    public EntityProjectileBase(final World world) {
        super(world);
        this.tinkerProjectile = new TinkerProjectileHandler();
        this.bounceOnNoDamage = true;
        this.defused = false;
        this.init();
    }
    
    public EntityProjectileBase(final World world, final double d, final double d1, final double d2) {
        this(world);
        this.func_70107_b(d, d1, d2);
    }
    
    public EntityProjectileBase(final World world, final EntityPlayer player, final float speed, final float inaccuracy, final float power, final ItemStack stack, final ItemStack launchingStack) {
        this(world);
        this.field_70250_c = (Entity)player;
        this.field_70251_a = (player.func_184812_l_() ? EntityArrow.PickupStatus.CREATIVE_ONLY : EntityArrow.PickupStatus.ALLOWED);
        this.func_70012_b(player.field_70165_t, player.field_70163_u + player.func_70047_e(), player.field_70161_v, player.field_70177_z, player.field_70125_A);
        this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
        this.field_70159_w = -MathHelper.func_76126_a(this.field_70177_z / 180.0f * 3.1415927f) * MathHelper.func_76134_b(this.field_70125_A / 180.0f * 3.1415927f);
        this.field_70179_y = MathHelper.func_76134_b(this.field_70177_z / 180.0f * 3.1415927f) * MathHelper.func_76134_b(this.field_70125_A / 180.0f * 3.1415927f);
        this.field_70181_x = -MathHelper.func_76126_a(this.field_70125_A / 180.0f * 3.1415927f);
        this.func_70186_c(this.field_70159_w, this.field_70181_x, this.field_70179_y, speed, inaccuracy);
        this.tinkerProjectile.setItemStack(stack);
        this.tinkerProjectile.setLaunchingStack(launchingStack);
        this.tinkerProjectile.setPower(power);
        for (final IProjectileTrait trait : this.tinkerProjectile.getProjectileTraits()) {
            trait.onLaunch(this, world, (EntityLivingBase)player);
        }
    }
    
    protected void init() {
    }
    
    public boolean hasCapability(final Capability<?> capability, final EnumFacing facing) {
        return capability == CapabilityTinkerProjectile.PROJECTILE_CAPABILITY || super.hasCapability((Capability)capability, facing);
    }
    
    public <T> T getCapability(final Capability<T> capability, final EnumFacing facing) {
        if (capability == CapabilityTinkerProjectile.PROJECTILE_CAPABILITY) {
            return (T)this.tinkerProjectile;
        }
        return (T)super.getCapability((Capability)capability, facing);
    }
    
    public boolean isDefused() {
        return this.defused;
    }
    
    protected void defuse() {
        this.defused = true;
    }
    
    @Nonnull
    protected ItemStack func_184550_j() {
        return this.tinkerProjectile.getItemStack();
    }
    
    protected void playHitBlockSound(final float speed, final IBlockState state) {
        final Material material = state.func_185904_a();
        if (material == Material.field_151575_d) {
            this.func_184185_a(Sounds.wood_hit, 1.0f, 1.0f);
        }
        else if (material == Material.field_151576_e) {
            this.func_184185_a(Sounds.stone_hit, 1.0f, 1.0f);
        }
        this.func_184185_a(state.func_177230_c().func_185467_w().func_185844_d(), 0.8f, 1.0f);
    }
    
    protected void playHitEntitySound() {
        this.func_184185_a(SoundEvents.field_187731_t, 1.0f, 1.2f / (this.field_70146_Z.nextFloat() * 0.2f + 0.9f));
    }
    
    public double getStuckDepth() {
        return 0.4000000059604645;
    }
    
    protected void onEntityHit(final Entity entityHit) {
        this.func_70106_y();
    }
    
    protected float getSpeed() {
        return MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y);
    }
    
    public void onHitBlock(final RayTraceResult raytraceResult) {
        final BlockPos blockpos = raytraceResult.func_178782_a();
        this.field_145791_d = blockpos.func_177958_n();
        this.field_145792_e = blockpos.func_177956_o();
        this.field_145789_f = blockpos.func_177952_p();
        final IBlockState iblockstate = this.func_130014_f_().func_180495_p(blockpos);
        this.field_145790_g = iblockstate.func_177230_c();
        this.field_70253_h = this.field_145790_g.func_176201_c(iblockstate);
        this.field_70159_w = (float)(raytraceResult.field_72307_f.field_72450_a - this.field_70165_t);
        this.field_70181_x = (float)(raytraceResult.field_72307_f.field_72448_b - this.field_70163_u);
        this.field_70179_y = (float)(raytraceResult.field_72307_f.field_72449_c - this.field_70161_v);
        final float speed = this.getSpeed();
        this.field_70165_t -= this.field_70159_w / speed * 0.05000000074505806;
        this.field_70163_u -= this.field_70181_x / speed * 0.05000000074505806;
        this.field_70161_v -= this.field_70179_y / speed * 0.05000000074505806;
        this.playHitBlockSound(speed, iblockstate);
        ProjectileEvent.OnHitBlock.fireEvent(this, speed, blockpos, iblockstate);
        this.field_70254_i = true;
        this.field_70249_b = 7;
        this.func_70243_d(false);
        if (iblockstate.func_185904_a() != Material.field_151579_a) {
            this.field_145790_g.func_180634_a(this.func_130014_f_(), blockpos, iblockstate, (Entity)this);
        }
        this.defuse();
    }
    
    public void onHitEntity(final RayTraceResult raytraceResult) {
        final ItemStack item = this.tinkerProjectile.getItemStack();
        final ItemStack launcher = this.tinkerProjectile.getLaunchingStack();
        boolean bounceOff = false;
        final Entity entityHit = raytraceResult.field_72308_g;
        if (item.func_77973_b() instanceof ToolCore && this.field_70250_c instanceof EntityLivingBase) {
            final EntityLivingBase attacker = (EntityLivingBase)this.field_70250_c;
            ItemStack inventoryItem = AmmoHelper.getMatchingItemstackFromInventory(this.tinkerProjectile.getItemStack(), (Entity)attacker, false);
            if (inventoryItem.func_190926_b() || inventoryItem.func_77973_b() != item.func_77973_b()) {
                inventoryItem = item;
            }
            final boolean brokenStateDiffers = ToolHelper.isBroken(inventoryItem) != ToolHelper.isBroken(item);
            if (brokenStateDiffers) {
                this.toggleBroken(inventoryItem);
            }
            Multimap<String, AttributeModifier> projectileAttributes = null;
            if (!this.func_130014_f_().field_72995_K) {
                this.unequip(attacker, EntityEquipmentSlot.OFFHAND);
                this.unequip(attacker, EntityEquipmentSlot.MAINHAND);
                if (item.func_77973_b() instanceof IProjectile) {
                    projectileAttributes = ((IProjectile)item.func_77973_b()).getProjectileAttributeModifier(inventoryItem);
                    if (launcher.func_77973_b() instanceof ILauncher) {
                        ((ILauncher)launcher.func_77973_b()).modifyProjectileAttributes(projectileAttributes, this.tinkerProjectile.getLaunchingStack(), this.tinkerProjectile.getItemStack(), this.tinkerProjectile.getPower());
                    }
                    projectileAttributes.put((Object)SharedMonsterAttributes.field_111264_e.func_111108_a(), (Object)new AttributeModifier(EntityProjectileBase.PROJECTILE_POWER_MODIFIER, "Weapon damage multiplier", (double)(this.tinkerProjectile.getPower() - 1.0f), 2));
                    attacker.func_110140_aT().func_111147_b((Multimap)projectileAttributes);
                }
                final float speed = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y);
                bounceOff = !this.dealDamage(speed, inventoryItem, attacker, entityHit);
                if (!bounceOff) {
                    for (final IProjectileTrait trait : this.tinkerProjectile.getProjectileTraits()) {
                        trait.afterHit(this, this.func_130014_f_(), inventoryItem, attacker, entityHit, speed);
                    }
                }
                if (brokenStateDiffers) {
                    this.toggleBroken(inventoryItem);
                }
                if (item.func_77973_b() instanceof IProjectile) {
                    assert projectileAttributes != null;
                    attacker.func_110140_aT().func_111148_a((Multimap)projectileAttributes);
                }
                this.equip(attacker, EntityEquipmentSlot.MAINHAND);
                this.equip(attacker, EntityEquipmentSlot.OFFHAND);
            }
            if (!bounceOff) {
                this.onEntityHit(entityHit);
            }
        }
        if (bounceOff) {
            if (!this.bounceOnNoDamage) {
                this.func_70106_y();
            }
            this.field_70159_w *= -0.10000000149011612;
            this.field_70181_x *= -0.10000000149011612;
            this.field_70179_y *= -0.10000000149011612;
            this.field_70177_z += 180.0f;
            this.field_70126_B += 180.0f;
            this.field_70257_an = 0;
        }
        this.playHitEntitySound();
    }
    
    private void unequip(final EntityLivingBase entity, final EntityEquipmentSlot slot) {
        final ItemStack stack = entity.func_184582_a(slot);
        if (!stack.func_190926_b()) {
            entity.func_110140_aT().func_111148_a(stack.func_111283_C(slot));
        }
    }
    
    private void equip(final EntityLivingBase entity, final EntityEquipmentSlot slot) {
        final ItemStack stack = entity.func_184582_a(slot);
        if (!stack.func_190926_b()) {
            entity.func_110140_aT().func_111147_b(stack.func_111283_C(slot));
        }
    }
    
    private void toggleBroken(final ItemStack stack) {
        final NBTTagCompound tag = TagUtil.getToolTag(stack);
        tag.func_74757_a("Broken", !tag.func_74767_n("Broken"));
        TagUtil.setToolTag(stack, tag);
    }
    
    public boolean dealDamage(final float speed, final ItemStack item, final EntityLivingBase attacker, final Entity target) {
        return ToolHelper.attackEntity(item, (ToolCore)item.func_77973_b(), attacker, target, (Entity)this);
    }
    
    public void func_70016_h(final double p_70016_1_, final double p_70016_3_, final double p_70016_5_) {
    }
    
    public void func_70071_h_() {
        this.func_70030_z();
        for (final IProjectileTrait trait : this.tinkerProjectile.getProjectileTraits()) {
            trait.onProjectileUpdate(this, this.func_130014_f_(), this.tinkerProjectile.getItemStack());
        }
        if (this.field_70249_b > 0) {
            --this.field_70249_b;
        }
        if (this.field_70127_C == 0.0f && this.field_70126_B == 0.0f) {
            final float f = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
            final float n = (float)(Math.atan2(this.field_70159_w, this.field_70179_y) * 180.0 / 3.141592653589793);
            this.field_70177_z = n;
            this.field_70126_B = n;
            final float n2 = (float)(Math.atan2(this.field_70181_x, f) * 180.0 / 3.141592653589793);
            this.field_70125_A = n2;
            this.field_70127_C = n2;
        }
        final BlockPos blockpos = new BlockPos(this.field_145791_d, this.field_145792_e, this.field_145789_f);
        final IBlockState iblockstate = this.func_130014_f_().func_180495_p(blockpos);
        if (iblockstate.func_185904_a() != Material.field_151579_a) {
            final AxisAlignedBB axisalignedbb = iblockstate.func_185890_d((IBlockAccess)this.func_130014_f_(), blockpos);
            assert axisalignedbb != null;
            if (axisalignedbb != Block.field_185506_k && axisalignedbb.func_186670_a(blockpos).func_72318_a(new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v))) {
                this.field_70254_i = true;
            }
        }
        if (this.field_70254_i) {
            this.updateInGround(iblockstate);
        }
        else {
            this.updateInAir();
        }
    }
    
    public void updateInGround(final IBlockState state) {
        final Block block = state.func_177230_c();
        final int meta = block.func_176201_c(state);
        if ((block == this.field_145790_g && meta == this.field_70253_h) || this.func_130014_f_().func_184143_b(EntityProjectileBase.ON_BLOCK_AABB.func_191194_a(this.func_174791_d()))) {
            ++this.field_70252_j;
            if (this.field_70252_j >= 1200) {
                this.func_70106_y();
            }
        }
        else {
            this.field_70254_i = false;
            this.field_70159_w *= this.field_70146_Z.nextFloat() * 0.2f;
            this.field_70181_x *= this.field_70146_Z.nextFloat() * 0.2f;
            this.field_70179_y *= this.field_70146_Z.nextFloat() * 0.2f;
            this.field_70252_j = 0;
            this.field_70257_an = 0;
        }
        ++this.field_184552_b;
    }
    
    public void updateInAir() {
        this.field_184552_b = 0;
        ++this.field_70257_an;
        final Vec3d oldPos = new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v);
        Vec3d newPos = new Vec3d(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
        RayTraceResult raytraceResult = this.func_130014_f_().func_147447_a(oldPos, newPos, false, true, false);
        if (raytraceResult != null) {
            newPos = new Vec3d(raytraceResult.field_72307_f.field_72450_a, raytraceResult.field_72307_f.field_72448_b, raytraceResult.field_72307_f.field_72449_c);
        }
        final Entity entity = this.func_184551_a(oldPos, newPos);
        if (entity != null) {
            raytraceResult = new RayTraceResult(entity);
        }
        if (raytraceResult != null && raytraceResult.field_72308_g != null && raytraceResult.field_72308_g instanceof EntityPlayer) {
            final EntityPlayer entityplayer = (EntityPlayer)raytraceResult.field_72308_g;
            if (entityplayer.field_71075_bZ.field_75102_a || (this.field_70250_c instanceof EntityPlayer && !((EntityPlayer)this.field_70250_c).func_96122_a(entityplayer))) {
                raytraceResult = null;
            }
        }
        if (raytraceResult != null && !MinecraftForge.EVENT_BUS.post((Event)this.getProjectileImpactEvent(raytraceResult))) {
            if (raytraceResult.field_72308_g != null) {
                this.onHitEntity(raytraceResult);
            }
            else {
                this.onHitBlock(raytraceResult);
            }
        }
        if (this.func_70241_g()) {
            this.drawCritParticles();
        }
        this.doMoveUpdate();
        double slowdown = 1.0 - this.getSlowdown();
        if (this.func_70090_H()) {
            for (int l = 0; l < 4; ++l) {
                final float f3 = 0.25f;
                this.func_130014_f_().func_175688_a(EnumParticleTypes.WATER_BUBBLE, this.field_70165_t - this.field_70159_w * f3, this.field_70163_u - this.field_70181_x * f3, this.field_70161_v - this.field_70179_y * f3, this.field_70159_w, this.field_70181_x, this.field_70179_y, new int[0]);
            }
            slowdown *= 0.6;
        }
        if (this.func_70026_G()) {
            this.func_70066_B();
        }
        this.field_70159_w *= slowdown;
        this.field_70181_x *= slowdown;
        this.field_70179_y *= slowdown;
        if (!this.func_189652_ae()) {
            this.field_70181_x -= this.getGravity();
        }
        for (final IProjectileTrait trait : this.tinkerProjectile.getProjectileTraits()) {
            trait.onMovement(this, this.func_130014_f_(), slowdown);
        }
        this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
        this.func_145775_I();
    }
    
    protected TinkerProjectileImpactEvent getProjectileImpactEvent(final RayTraceResult rayTraceResult) {
        return new TinkerProjectileImpactEvent((Entity)this, rayTraceResult, this.tinkerProjectile.getItemStack());
    }
    
    @Nullable
    protected Entity func_184551_a(@Nonnull final Vec3d start, @Nonnull final Vec3d end) {
        if (this.isDefused()) {
            return null;
        }
        return super.func_184551_a(start, end);
    }
    
    public void drawCritParticles() {
        for (int k = 0; k < 4; ++k) {
            this.func_130014_f_().func_175688_a(EnumParticleTypes.CRIT, this.field_70165_t + this.field_70159_w * k / 4.0, this.field_70163_u + this.field_70181_x * k / 4.0, this.field_70161_v + this.field_70179_y * k / 4.0, -this.field_70159_w, -this.field_70181_x + 0.2, -this.field_70179_y, new int[0]);
        }
    }
    
    protected void doMoveUpdate() {
        this.field_70165_t += this.field_70159_w;
        this.field_70163_u += this.field_70181_x;
        this.field_70161_v += this.field_70179_y;
        final double f2 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
        this.field_70177_z = (float)(Math.atan2(this.field_70159_w, this.field_70179_y) * 180.0 / 3.141592653589793);
        this.field_70125_A = (float)(Math.atan2(this.field_70181_x, f2) * 180.0 / 3.141592653589793);
        while (this.field_70125_A - this.field_70127_C < -180.0f) {
            this.field_70127_C -= 360.0f;
        }
        while (this.field_70125_A - this.field_70127_C >= 180.0f) {
            this.field_70127_C += 360.0f;
        }
        while (this.field_70177_z - this.field_70126_B < -180.0f) {
            this.field_70126_B -= 360.0f;
        }
        while (this.field_70177_z - this.field_70126_B >= 180.0f) {
            this.field_70126_B += 360.0f;
        }
        this.field_70125_A = this.field_70127_C + (this.field_70125_A - this.field_70127_C) * 0.2f;
        this.field_70177_z = this.field_70126_B + (this.field_70177_z - this.field_70126_B) * 0.2f;
    }
    
    public double getSlowdown() {
        return 0.01;
    }
    
    public double getGravity() {
        return 0.05;
    }
    
    public void func_70100_b_(@Nonnull final EntityPlayer player) {
        if (!this.func_130014_f_().field_72995_K && this.field_70254_i && this.field_70249_b <= 0) {
            boolean pickedUp = this.field_70251_a == EntityArrow.PickupStatus.ALLOWED || (this.field_70251_a == EntityArrow.PickupStatus.CREATIVE_ONLY && player.field_71075_bZ.field_75098_d);
            if (pickedUp) {
                pickedUp = this.tinkerProjectile.pickup((EntityLivingBase)player, this.field_70251_a != EntityArrow.PickupStatus.ALLOWED);
            }
            if (pickedUp) {
                this.func_184185_a(SoundEvents.field_187638_cR, 0.2f, ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                player.func_71001_a((Entity)this, 1);
                this.func_70106_y();
            }
        }
    }
    
    public void func_70014_b(final NBTTagCompound tags) {
        super.func_70014_b(tags);
        tags.func_74782_a("item", (NBTBase)this.tinkerProjectile.serializeNBT());
    }
    
    public void func_70037_a(final NBTTagCompound tags) {
        super.func_70037_a(tags);
        this.tinkerProjectile.deserializeNBT(tags.func_74775_l("item"));
    }
    
    public void writeSpawnData(final ByteBuf data) {
        data.writeFloat(this.field_70177_z);
        final int id = (this.field_70250_c == null) ? this.func_145782_y() : this.field_70250_c.func_145782_y();
        data.writeInt(id);
        data.writeDouble(this.field_70159_w);
        data.writeDouble(this.field_70181_x);
        data.writeDouble(this.field_70179_y);
        ByteBufUtils.writeItemStack(data, this.tinkerProjectile.getItemStack());
        ByteBufUtils.writeItemStack(data, this.tinkerProjectile.getLaunchingStack());
        data.writeFloat(this.tinkerProjectile.getPower());
    }
    
    public void readSpawnData(final ByteBuf data) {
        this.field_70177_z = data.readFloat();
        this.field_70250_c = this.func_130014_f_().func_73045_a(data.readInt());
        this.field_70159_w = data.readDouble();
        this.field_70181_x = data.readDouble();
        this.field_70179_y = data.readDouble();
        this.tinkerProjectile.setItemStack(ByteBufUtils.readItemStack(data));
        this.tinkerProjectile.setLaunchingStack(ByteBufUtils.readItemStack(data));
        this.tinkerProjectile.setPower(data.readFloat());
        this.field_70165_t -= MathHelper.func_76134_b(this.field_70177_z / 180.0f * 3.1415927f) * 0.16f;
        this.field_70163_u -= 0.10000000149011612;
        this.field_70161_v -= MathHelper.func_76126_a(this.field_70177_z / 180.0f * 3.1415927f) * 0.16f;
    }
    
    static {
        PROJECTILE_POWER_MODIFIER = UUID.fromString("c6aefc21-081a-4c4a-b076-8f9d6cef9122");
        ON_BLOCK_AABB = new AxisAlignedBB(-0.05, -0.05, -0.05, 0.05, 0.05, 0.05);
    }
}
