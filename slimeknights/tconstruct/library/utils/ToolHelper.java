package slimeknights.tconstruct.library.utils;

import net.minecraft.item.*;
import slimeknights.tconstruct.library.tinkering.*;
import slimeknights.tconstruct.library.tools.*;
import net.minecraft.enchantment.*;
import slimeknights.tconstruct.tools.*;
import slimeknights.tconstruct.library.traits.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.block.state.*;
import net.minecraft.nbt.*;
import java.util.*;
import net.minecraft.block.*;
import com.google.common.collect.*;
import net.minecraft.block.material.*;
import slimeknights.tconstruct.library.events.*;
import net.minecraft.util.math.*;
import slimeknights.tconstruct.common.*;
import net.minecraft.network.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.event.*;
import net.minecraft.client.*;
import net.minecraft.network.play.client.*;
import net.minecraft.tileentity.*;
import net.minecraft.client.network.*;
import net.minecraftforge.common.*;
import slimeknights.tconstruct.*;
import net.minecraft.entity.item.*;
import slimeknights.tconstruct.tools.common.network.*;
import slimeknights.mantle.network.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import slimeknights.tconstruct.library.tools.ranged.*;
import net.minecraft.stats.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.network.play.server.*;
import java.util.function.*;

public final class ToolHelper
{
    private ToolHelper() {
    }
    
    public static boolean hasCategory(final ItemStack stack, final Category category) {
        return !stack.func_190926_b() && stack.func_77973_b() instanceof TinkersItem && ((TinkersItem)stack.func_77973_b()).hasCategory(category);
    }
    
    public static int getDurabilityStat(final ItemStack stack) {
        return getIntTag(stack, "Durability");
    }
    
    public static int getHarvestLevelStat(final ItemStack stack) {
        return getIntTag(stack, "HarvestLevel");
    }
    
    public static float getMiningSpeedStat(final ItemStack stack) {
        return getfloatTag(stack, "MiningSpeed");
    }
    
    public static float getAttackStat(final ItemStack stack) {
        return getfloatTag(stack, "Attack");
    }
    
    public static float getActualAttack(final ItemStack stack) {
        float damage = getAttackStat(stack);
        if (!stack.func_190926_b() && stack.func_77973_b() instanceof ToolCore) {
            damage *= ((ToolCore)stack.func_77973_b()).damagePotential();
        }
        return damage;
    }
    
    public static float getAttackSpeedStat(final ItemStack stack) {
        return getfloatTag(stack, "AttackSpeedMultiplier");
    }
    
    public static float getActualAttackSpeed(final ItemStack stack) {
        float speed = getAttackSpeedStat(stack);
        if (!stack.func_190926_b() && stack.func_77973_b() instanceof ToolCore) {
            speed *= (float)((ToolCore)stack.func_77973_b()).attackSpeed();
        }
        return speed;
    }
    
    public static float getActualMiningSpeed(final ItemStack stack) {
        float speed = getMiningSpeedStat(stack);
        if (!stack.func_190926_b() && stack.func_77973_b() instanceof ToolCore) {
            speed *= ((ToolCore)stack.func_77973_b()).miningSpeedModifier();
        }
        return speed;
    }
    
    public static int getFreeModifiers(final ItemStack stack) {
        return getIntTag(stack, "FreeModifiers");
    }
    
    public static int getFortuneLevel(final ItemStack stack) {
        final int fortune = EnchantmentHelper.func_77506_a(Enchantments.field_185308_t, stack);
        final int luck = TinkerModifiers.modLuck.getLuckLevel(stack);
        return Math.max(fortune, luck);
    }
    
    public static List<ITrait> getTraits(final ItemStack stack) {
        final List<ITrait> traits = (List<ITrait>)Lists.newLinkedList();
        final NBTTagList traitsTagList = TagUtil.getTraitsTagList(stack);
        for (int i = 0; i < traitsTagList.func_74745_c(); ++i) {
            final ITrait trait = TinkerRegistry.getTrait(traitsTagList.func_150307_f(i));
            if (trait != null) {
                traits.add(trait);
            }
        }
        return traits;
    }
    
    public static float calcDigSpeed(final ItemStack stack, final IBlockState blockState) {
        if (blockState == null) {
            return 0.0f;
        }
        if (!stack.func_77942_o()) {
            return 1.0f;
        }
        if (isBroken(stack)) {
            return 0.3f;
        }
        if (!canHarvest(stack, blockState)) {
            return 1.0f;
        }
        final NBTTagCompound tag = TagUtil.getToolTag(stack);
        float speed = tag.func_74760_g("MiningSpeed");
        if (stack.func_77973_b() instanceof ToolCore) {
            speed *= ((ToolCore)stack.func_77973_b()).miningSpeedModifier();
        }
        return speed;
    }
    
    public static boolean isToolEffective(final ItemStack stack, final IBlockState state) {
        for (final String type : stack.func_77973_b().getToolClasses(stack)) {
            if (state.func_177230_c().isToolEffective(type, state)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isToolEffective2(final ItemStack stack, final IBlockState state) {
        if (isToolEffective(stack, state)) {
            return true;
        }
        if (TinkerUtil.hasModifier(TagUtil.getTagSafe(stack), TinkerModifiers.modBlasting.getIdentifier()) && state.func_185904_a().func_76229_l()) {
            return !state.func_185904_a().func_76224_d();
        }
        return stack.func_77973_b() instanceof ToolCore && ((ToolCore)stack.func_77973_b()).isEffective(state);
    }
    
    public static boolean canHarvest(final ItemStack stack, final IBlockState state) {
        final Block block = state.func_177230_c();
        if (state.func_185904_a().func_76229_l()) {
            return true;
        }
        final String type = block.getHarvestTool(state);
        final int level = block.getHarvestLevel(state);
        return stack.func_77973_b().getHarvestLevel(stack, type, (EntityPlayer)null, state) >= level;
    }
    
    public static ImmutableList<BlockPos> calcAOEBlocks(final ItemStack stack, final World world, final EntityPlayer player, final BlockPos origin, final int width, final int height, final int depth) {
        return calcAOEBlocks(stack, world, player, origin, width, height, depth, -1);
    }
    
    public static ImmutableList<BlockPos> calcAOEBlocks(final ItemStack stack, final World world, final EntityPlayer player, final BlockPos origin, int width, int height, int depth, int distance) {
        if (stack.func_190926_b() || !(stack.func_77973_b() instanceof ToolCore)) {
            return (ImmutableList<BlockPos>)ImmutableList.of();
        }
        final IBlockState state = world.func_180495_p(origin);
        if (!isToolEffective2(stack, state)) {
            return (ImmutableList<BlockPos>)ImmutableList.of();
        }
        if (state.func_185904_a() == Material.field_151579_a) {
            return (ImmutableList<BlockPos>)ImmutableList.of();
        }
        RayTraceResult mop = ((ToolCore)stack.func_77973_b()).func_77621_a(world, player, true);
        if (mop == null || !origin.equals((Object)mop.func_178782_a())) {
            mop = ((ToolCore)stack.func_77973_b()).func_77621_a(world, player, false);
            if (mop == null || !origin.equals((Object)mop.func_178782_a())) {
                return (ImmutableList<BlockPos>)ImmutableList.of();
            }
        }
        final TinkerToolEvent.ExtraBlockBreak event = TinkerToolEvent.ExtraBlockBreak.fireEvent(stack, player, state, width, height, depth, distance);
        if (event.isCanceled()) {
            return (ImmutableList<BlockPos>)ImmutableList.of();
        }
        width = event.width;
        height = event.height;
        depth = event.depth;
        distance = event.distance;
        BlockPos start = origin;
        int x = 0;
        int y = 0;
        int z = 0;
        switch (mop.field_178784_b) {
            case DOWN:
            case UP: {
                final Vec3i vec = player.func_174811_aO().func_176730_m();
                x = vec.func_177958_n() * height + vec.func_177952_p() * width;
                y = mop.field_178784_b.func_176743_c().func_179524_a() * -depth;
                z = vec.func_177958_n() * width + vec.func_177952_p() * height;
                start = start.func_177982_a(-x / 2, 0, -z / 2);
                if (x % 2 == 0) {
                    if (x > 0 && mop.field_72307_f.field_72450_a - mop.func_178782_a().func_177958_n() > 0.5) {
                        start = start.func_177982_a(1, 0, 0);
                    }
                    else if (x < 0 && mop.field_72307_f.field_72450_a - mop.func_178782_a().func_177958_n() < 0.5) {
                        start = start.func_177982_a(-1, 0, 0);
                    }
                }
                if (z % 2 != 0) {
                    break;
                }
                if (z > 0 && mop.field_72307_f.field_72449_c - mop.func_178782_a().func_177952_p() > 0.5) {
                    start = start.func_177982_a(0, 0, 1);
                    break;
                }
                if (z < 0 && mop.field_72307_f.field_72449_c - mop.func_178782_a().func_177952_p() < 0.5) {
                    start = start.func_177982_a(0, 0, -1);
                    break;
                }
                break;
            }
            case NORTH:
            case SOUTH: {
                x = width;
                y = height;
                z = mop.field_178784_b.func_176743_c().func_179524_a() * -depth;
                start = start.func_177982_a(-x / 2, -y / 2, 0);
                if (x % 2 == 0 && mop.field_72307_f.field_72450_a - mop.func_178782_a().func_177958_n() > 0.5) {
                    start = start.func_177982_a(1, 0, 0);
                }
                if (y % 2 == 0 && mop.field_72307_f.field_72448_b - mop.func_178782_a().func_177956_o() > 0.5) {
                    start = start.func_177982_a(0, 1, 0);
                    break;
                }
                break;
            }
            case WEST:
            case EAST: {
                x = mop.field_178784_b.func_176743_c().func_179524_a() * -depth;
                y = height;
                z = width;
                start = start.func_177982_a(0, -y / 2, -z / 2);
                if (y % 2 == 0 && mop.field_72307_f.field_72448_b - mop.func_178782_a().func_177956_o() > 0.5) {
                    start = start.func_177982_a(0, 1, 0);
                }
                if (z % 2 == 0 && mop.field_72307_f.field_72449_c - mop.func_178782_a().func_177952_p() > 0.5) {
                    start = start.func_177982_a(0, 0, 1);
                    break;
                }
                break;
            }
            default: {
                y = (x = (z = 0));
                break;
            }
        }
        final ImmutableList.Builder<BlockPos> builder = (ImmutableList.Builder<BlockPos>)ImmutableList.builder();
        for (int xp = start.func_177958_n(); xp != start.func_177958_n() + x; xp += x / MathHelper.func_76130_a(x)) {
            for (int yp = start.func_177956_o(); yp != start.func_177956_o() + y; yp += y / MathHelper.func_76130_a(y)) {
                for (int zp = start.func_177952_p(); zp != start.func_177952_p() + z; zp += z / MathHelper.func_76130_a(z)) {
                    if (xp != origin.func_177958_n() || yp != origin.func_177956_o() || zp != origin.func_177952_p()) {
                        if (distance <= 0 || MathHelper.func_76130_a(xp - origin.func_177958_n()) + MathHelper.func_76130_a(yp - origin.func_177956_o()) + MathHelper.func_76130_a(zp - origin.func_177952_p()) <= distance) {
                            final BlockPos pos = new BlockPos(xp, yp, zp);
                            if (isToolEffective2(stack, world.func_180495_p(pos))) {
                                builder.add((Object)pos);
                            }
                        }
                    }
                }
            }
        }
        return (ImmutableList<BlockPos>)builder.build();
    }
    
    private static boolean canBreakExtraBlock(final ItemStack stack, final World world, final EntityPlayer player, final BlockPos pos, final BlockPos refPos) {
        if (world.func_175623_d(pos)) {
            return false;
        }
        final IBlockState state = world.func_180495_p(pos);
        final Block block = state.func_177230_c();
        if (!isToolEffective2(stack, state)) {
            return false;
        }
        final IBlockState refState = world.func_180495_p(refPos);
        final float refStrength = ForgeHooks.blockStrength(refState, player, world, refPos);
        final float strength = ForgeHooks.blockStrength(state, player, world, pos);
        if (!ForgeHooks.canHarvestBlock(block, player, (IBlockAccess)world, pos) || refStrength / strength > 10.0f) {
            return false;
        }
        if (player.field_71075_bZ.field_75098_d) {
            block.func_176208_a(world, pos, state, player);
            if (block.removedByPlayer(state, world, pos, player, false)) {
                block.func_176206_d(world, pos, state);
            }
            if (!world.field_72995_K) {
                TinkerNetwork.sendPacket((Entity)player, (Packet<?>)new SPacketBlockChange(world, pos));
            }
            return false;
        }
        return true;
    }
    
    public static void breakExtraBlock(final ItemStack stack, final World world, final EntityPlayer player, final BlockPos pos, final BlockPos refPos) {
        if (!canBreakExtraBlock(stack, world, player, pos, refPos)) {
            return;
        }
        final IBlockState state = world.func_180495_p(pos);
        final Block block = state.func_177230_c();
        stack.func_179548_a(world, state, pos, player);
        if (!world.field_72995_K) {
            final int xp = ForgeHooks.onBlockBreakEvent(world, ((EntityPlayerMP)player).field_71134_c.func_73081_b(), (EntityPlayerMP)player, pos);
            if (xp == -1) {
                return;
            }
            final TileEntity tileEntity = world.func_175625_s(pos);
            if (block.removedByPlayer(state, world, pos, player, true)) {
                block.func_176206_d(world, pos, state);
                block.func_180657_a(world, player, pos, state, tileEntity, stack);
                block.func_180637_b(world, pos, xp);
            }
            TinkerNetwork.sendPacket((Entity)player, (Packet<?>)new SPacketBlockChange(world, pos));
        }
        else {
            world.func_175669_a(2001, pos, Block.func_176210_f(state));
            if (block.removedByPlayer(state, world, pos, player, true)) {
                block.func_176206_d(world, pos, state);
            }
            stack.func_179548_a(world, state, pos, player);
            if (stack.func_190916_E() == 0 && stack == player.func_184614_ca()) {
                ForgeEventFactory.onPlayerDestroyItem(player, stack, EnumHand.MAIN_HAND);
                player.func_184611_a(EnumHand.MAIN_HAND, ItemStack.field_190927_a);
            }
            final NetHandlerPlayClient netHandlerPlayClient = Minecraft.func_71410_x().func_147114_u();
            assert netHandlerPlayClient != null;
            netHandlerPlayClient.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, Minecraft.func_71410_x().field_71476_x.field_178784_b));
        }
    }
    
    public static void shearExtraBlock(final ItemStack stack, final World world, final EntityPlayer player, final BlockPos pos, final BlockPos refPos) {
        if (!canBreakExtraBlock(stack, world, player, pos, refPos)) {
            return;
        }
        if (!shearBlock(stack, world, player, pos)) {
            breakExtraBlock(stack, world, player, pos, refPos);
        }
    }
    
    public static boolean shearBlock(final ItemStack itemstack, final World world, final EntityPlayer player, final BlockPos pos) {
        if (world.field_72995_K) {
            return false;
        }
        final IBlockState state = world.func_180495_p(pos);
        final Block block = state.func_177230_c();
        if (block instanceof IShearable) {
            final IShearable target = (IShearable)block;
            if (target.isShearable(itemstack, (IBlockAccess)world, pos)) {
                final int fortune = EnchantmentHelper.func_77506_a(Enchantments.field_185308_t, itemstack);
                final List<ItemStack> drops = (List<ItemStack>)target.onSheared(itemstack, (IBlockAccess)world, pos, fortune);
                for (final ItemStack stack : drops) {
                    final float f = 0.7f;
                    final double d = TConstruct.random.nextFloat() * f + (1.0f - f) * 0.5;
                    final double d2 = TConstruct.random.nextFloat() * f + (1.0f - f) * 0.5;
                    final double d3 = TConstruct.random.nextFloat() * f + (1.0f - f) * 0.5;
                    final EntityItem entityitem = new EntityItem(player.func_130014_f_(), pos.func_177958_n() + d, pos.func_177956_o() + d2, pos.func_177952_p() + d3, stack);
                    entityitem.func_174869_p();
                    world.func_72838_d((Entity)entityitem);
                }
                itemstack.func_179548_a(world, state, pos, player);
                world.func_175698_g(pos);
                return true;
            }
        }
        return false;
    }
    
    public static int getCurrentDurability(final ItemStack stack) {
        return stack.func_77958_k() - stack.func_77952_i();
    }
    
    public static int getMaxDurability(final ItemStack stack) {
        return stack.func_77958_k();
    }
    
    public static void damageTool(final ItemStack stack, final int amount, final EntityLivingBase entity) {
        if (amount == 0 || isBroken(stack)) {
            return;
        }
        int actualAmount = amount;
        for (final ITrait trait : TinkerUtil.getTraitsOrdered(stack)) {
            if (amount > 0) {
                actualAmount = trait.onToolDamage(stack, amount, actualAmount, entity);
            }
            else {
                actualAmount = trait.onToolHeal(stack, amount, actualAmount, entity);
            }
        }
        if (actualAmount > 0 && TagUtil.getTagSafe(stack).func_74767_n("Unbreakable")) {
            actualAmount = 0;
        }
        actualAmount = Math.min(actualAmount, getCurrentDurability(stack));
        stack.func_77964_b(stack.func_77952_i() + actualAmount);
        if (getCurrentDurability(stack) == 0) {
            breakTool(stack, entity);
        }
    }
    
    public static void healTool(final ItemStack stack, final int amount, final EntityLivingBase entity) {
        damageTool(stack, -amount, entity);
    }
    
    public static boolean isBroken(final ItemStack stack) {
        return TagUtil.getToolTag(stack).func_74767_n("Broken");
    }
    
    public static void breakTool(final ItemStack stack, final EntityLivingBase entity) {
        final NBTTagCompound tag = TagUtil.getToolTag(stack);
        tag.func_74757_a("Broken", true);
        TagUtil.setToolTag(stack, tag);
        if (entity instanceof EntityPlayerMP) {
            TinkerNetwork.sendTo((AbstractPacket)new ToolBreakAnimationPacket(stack), (EntityPlayerMP)entity);
        }
    }
    
    public static void unbreakTool(final ItemStack stack) {
        if (isBroken(stack)) {
            stack.func_77964_b(stack.func_77958_k());
            final NBTTagCompound tag = TagUtil.getToolTag(stack);
            tag.func_74757_a("Broken", false);
            TagUtil.setToolTag(stack, tag);
        }
    }
    
    public static void repairTool(final ItemStack stack, final int amount) {
        repairTool(stack, amount, null);
    }
    
    public static void repairTool(final ItemStack stack, final int amount, final EntityLivingBase entity) {
        unbreakTool(stack);
        TinkerToolEvent.OnRepair.fireEvent(stack, amount);
        healTool(stack, amount, entity);
    }
    
    public static boolean attackEntity(final ItemStack stack, final ToolCore tool, final EntityLivingBase attacker, final Entity targetEntity) {
        return attackEntity(stack, tool, attacker, targetEntity, null);
    }
    
    public static boolean attackEntity(final ItemStack stack, final ToolCore tool, final EntityLivingBase attacker, final Entity targetEntity, final Entity projectileEntity) {
        return attackEntity(stack, tool, attacker, targetEntity, projectileEntity, true);
    }
    
    public static boolean attackEntity(final ItemStack stack, final ToolCore tool, final EntityLivingBase attacker, final Entity targetEntity, final Entity projectileEntity, final boolean applyCooldown) {
        if (targetEntity == null || !targetEntity.func_70075_an() || targetEntity.func_85031_j((Entity)attacker) || !stack.func_77942_o()) {
            return false;
        }
        if (isBroken(stack)) {
            return false;
        }
        if (attacker == null) {
            return false;
        }
        final boolean isProjectile = projectileEntity != null;
        EntityLivingBase target = null;
        EntityPlayer player = null;
        if (targetEntity instanceof EntityLivingBase) {
            target = (EntityLivingBase)targetEntity;
        }
        if (attacker instanceof EntityPlayer) {
            player = (EntityPlayer)attacker;
            if (target instanceof EntityPlayer && !player.func_96122_a((EntityPlayer)target)) {
                return false;
            }
        }
        final List<ITrait> traits = TinkerUtil.getTraitsOrdered(stack);
        final float baseDamage = (float)attacker.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e();
        final float baseKnockback = attacker.func_70051_ag() ? 1.0f : 0.0f;
        boolean isCritical = attacker.field_70143_R > 0.0f && !attacker.field_70122_E && !attacker.func_70617_f_() && !attacker.func_70090_H() && !attacker.func_70644_a(MobEffects.field_76440_q) && !attacker.func_184218_aH();
        for (final ITrait trait : traits) {
            if (trait.isCriticalHit(stack, attacker, target)) {
                isCritical = true;
            }
        }
        float damage = baseDamage;
        if (target != null) {
            for (final ITrait trait2 : traits) {
                damage = trait2.damage(stack, attacker, target, baseDamage, damage, isCritical);
            }
        }
        if (isCritical) {
            damage *= 1.5f;
        }
        damage = calcCutoffDamage(damage, tool.damageCutoff());
        float knockback = baseKnockback;
        if (target != null) {
            for (final ITrait trait3 : traits) {
                knockback = trait3.knockBack(stack, attacker, target, damage, baseKnockback, knockback, isCritical);
            }
        }
        float oldHP = 0.0f;
        double oldVelX = targetEntity.field_70159_w;
        double oldVelY = targetEntity.field_70181_x;
        double oldVelZ = targetEntity.field_70179_y;
        if (target != null) {
            oldHP = target.func_110143_aJ();
        }
        if (player != null) {
            final float cooldown = ((EntityPlayer)attacker).func_184825_o(0.5f);
            damage *= 0.2f + cooldown * cooldown * 0.8f;
        }
        if (target != null) {
            final int hurtResistantTime = target.field_70172_ad;
            for (final ITrait trait4 : traits) {
                trait4.onHit(stack, attacker, target, damage, isCritical);
                target.field_70172_ad = hurtResistantTime;
            }
        }
        boolean hit = false;
        if (isProjectile && tool instanceof IProjectile) {
            hit = ((IProjectile)tool).dealDamageRanged(stack, projectileEntity, attacker, targetEntity, damage);
        }
        else {
            hit = tool.dealDamage(stack, attacker, targetEntity, damage);
        }
        if (hit && target != null) {
            final float damageDealt = oldHP - target.func_110143_aJ();
            final EntityLivingBase entityLivingBase = target;
            final double field_70159_w = oldVelX + (target.field_70159_w - oldVelX) * tool.knockback();
            entityLivingBase.field_70159_w = field_70159_w;
            oldVelX = field_70159_w;
            final EntityLivingBase entityLivingBase2 = target;
            final double field_70181_x = oldVelY + (target.field_70181_x - oldVelY) * tool.knockback() / 3.0;
            entityLivingBase2.field_70181_x = field_70181_x;
            oldVelY = field_70181_x;
            final EntityLivingBase entityLivingBase3 = target;
            final double field_70179_y = oldVelZ + (target.field_70179_y - oldVelZ) * tool.knockback();
            entityLivingBase3.field_70179_y = field_70179_y;
            oldVelZ = field_70179_y;
            if (knockback > 0.0f) {
                final double velX = -MathHelper.func_76126_a(attacker.field_70177_z * 3.1415927f / 180.0f) * knockback * 0.5f;
                final double velZ = MathHelper.func_76134_b(attacker.field_70177_z * 3.1415927f / 180.0f) * knockback * 0.5f;
                targetEntity.func_70024_g(velX, 0.1, velZ);
                attacker.field_70159_w *= 0.6000000238418579;
                attacker.field_70179_y *= 0.6000000238418579;
                attacker.func_70031_b(false);
            }
            if (targetEntity instanceof EntityPlayerMP && targetEntity.field_70133_I) {
                TinkerNetwork.sendPacket((Entity)player, (Packet<?>)new SPacketEntityVelocity(targetEntity));
                targetEntity.field_70133_I = false;
                targetEntity.field_70159_w = oldVelX;
                targetEntity.field_70181_x = oldVelY;
                targetEntity.field_70179_y = oldVelZ;
            }
            if (player != null) {
                if (isCritical) {
                    player.func_71009_b((Entity)target);
                }
                if (damage > baseDamage) {
                    player.func_71047_c(targetEntity);
                }
            }
            attacker.func_130011_c((Entity)target);
            for (final ITrait trait5 : traits) {
                trait5.afterHit(stack, attacker, target, damageDealt, isCritical, true);
            }
            if (player != null) {
                stack.func_77961_a(target, player);
                if (!player.field_71075_bZ.field_75098_d && !isProjectile) {
                    tool.reduceDurabilityOnHit(stack, player, damage);
                }
                player.func_71064_a(StatList.field_188111_y, Math.round(damageDealt * 10.0f));
                player.func_71020_j(0.3f);
                if (player.func_130014_f_() instanceof WorldServer && damageDealt > 2.0f) {
                    final int k = (int)(damageDealt * 0.5);
                    ((WorldServer)player.func_130014_f_()).func_175739_a(EnumParticleTypes.DAMAGE_INDICATOR, targetEntity.field_70165_t, targetEntity.field_70163_u + targetEntity.field_70131_O * 0.5f, targetEntity.field_70161_v, k, 0.1, 0.0, 0.1, 0.2, new int[0]);
                }
                if (!isProjectile && applyCooldown) {
                    player.func_184821_cY();
                }
            }
            else if (!isProjectile) {
                tool.reduceDurabilityOnHit(stack, null, damage);
            }
        }
        return true;
    }
    
    public static float calcCutoffDamage(float damage, final float cutoff) {
        float p = 1.0f;
        float d = damage;
        damage = 0.0f;
        while (d > cutoff) {
            damage += p * cutoff;
            if (p <= 0.001f) {
                damage += p * cutoff * (d / cutoff - 1.0f);
                return damage;
            }
            p *= 0.9f;
            d -= cutoff;
        }
        damage += p * d;
        return damage;
    }
    
    public static float getActualDamage(final ItemStack stack, final EntityLivingBase player) {
        float damage = (float)SharedMonsterAttributes.field_111264_e.func_111110_b();
        if (player != null) {
            damage = (float)player.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e();
        }
        damage += getActualAttack(stack);
        if (stack.func_77973_b() instanceof ToolCore) {
            damage = calcCutoffDamage(damage, ((ToolCore)stack.func_77973_b()).damageCutoff());
        }
        return damage;
    }
    
    public static void swingItem(final int speed, final EntityLivingBase entity) {
        if (!entity.field_82175_bq || entity.field_110158_av >= 3 || entity.field_110158_av < 0) {
            entity.field_110158_av = Math.min(4, -1 + speed);
            entity.field_82175_bq = true;
            if (entity.func_130014_f_() instanceof WorldServer) {
                ((WorldServer)entity.func_130014_f_()).func_73039_n().func_151247_a((Entity)entity, (Packet)new SPacketAnimation((Entity)entity, 0));
            }
        }
    }
    
    public static ItemStack playerIsHoldingItemWith(final EntityPlayer player, final Predicate<ItemStack> predicate) {
        ItemStack tool = player.func_184614_ca();
        if (!predicate.test(tool)) {
            tool = player.func_184592_cb();
            if (!predicate.test(tool)) {
                return ItemStack.field_190927_a;
            }
        }
        return tool;
    }
    
    static int getIntTag(final ItemStack stack, final String key) {
        final NBTTagCompound tag = TagUtil.getToolTag(stack);
        return tag.func_74762_e(key);
    }
    
    static float getfloatTag(final ItemStack stack, final String key) {
        final NBTTagCompound tag = TagUtil.getToolTag(stack);
        return tag.func_74760_g(key);
    }
}
