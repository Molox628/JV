package slimeknights.tconstruct.library.events;

import net.minecraft.item.*;
import slimeknights.tconstruct.library.tools.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.state.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import slimeknights.tconstruct.library.tools.ranged.*;

public abstract class TinkerToolEvent extends TinkerEvent
{
    public final ItemStack itemStack;
    public final ToolCore tool;
    
    public TinkerToolEvent(final ItemStack itemStack) {
        this.itemStack = itemStack;
        this.tool = (ToolCore)itemStack.func_77973_b();
    }
    
    @Cancelable
    public static class ExtraBlockBreak extends TinkerToolEvent
    {
        public final EntityPlayer player;
        public final IBlockState state;
        public int width;
        public int height;
        public int depth;
        public int distance;
        
        public ExtraBlockBreak(final ItemStack itemStack, final EntityPlayer player, final IBlockState state) {
            super(itemStack);
            this.player = player;
            this.state = state;
        }
        
        public static ExtraBlockBreak fireEvent(final ItemStack itemStack, final EntityPlayer player, final IBlockState state, final int width, final int height, final int depth, final int distance) {
            final ExtraBlockBreak event = new ExtraBlockBreak(itemStack, player, state);
            event.width = width;
            event.height = height;
            event.depth = depth;
            event.distance = distance;
            MinecraftForge.EVENT_BUS.post((Event)event);
            return event;
        }
    }
    
    public static class OnRepair extends TinkerToolEvent
    {
        public final int amount;
        
        public OnRepair(final ItemStack itemStack, final int amount) {
            super(itemStack);
            this.amount = amount;
        }
        
        public static boolean fireEvent(final ItemStack itemStack, final int amount) {
            final OnRepair event = new OnRepair(itemStack, amount);
            return !MinecraftForge.EVENT_BUS.post((Event)event);
        }
    }
    
    public static class OnMattockHoe extends TinkerToolEvent
    {
        public final BlockPos pos;
        public final World world;
        public final EntityPlayer player;
        
        public OnMattockHoe(final ItemStack itemStack, final EntityPlayer player, final World world, final BlockPos pos) {
            super(itemStack);
            this.player = player;
            this.pos = pos;
            this.world = world;
        }
        
        public static void fireEvent(final ItemStack itemStack, final EntityPlayer player, final World world, final BlockPos pos) {
            MinecraftForge.EVENT_BUS.post((Event)new OnMattockHoe(itemStack, player, world, pos));
        }
    }
    
    public static class OnShovelMakePath extends TinkerToolEvent
    {
        public final BlockPos pos;
        public final EntityPlayer player;
        public final World world;
        
        public OnShovelMakePath(final ItemStack itemStack, final EntityPlayer player, final World world, final BlockPos pos) {
            super(itemStack);
            this.pos = pos;
            this.player = player;
            this.world = world;
        }
        
        public static void fireEvent(final ItemStack itemStack, final EntityPlayer player, final World world, final BlockPos pos) {
            MinecraftForge.EVENT_BUS.post((Event)new OnShovelMakePath(itemStack, player, world, pos));
        }
    }
    
    @Event.HasResult
    @Cancelable
    public static class OnScytheHarvest extends TinkerToolEvent
    {
        public final BlockPos pos;
        public final EntityPlayer player;
        public final IBlockState blockState;
        public final World world;
        public final boolean harvestable;
        
        public OnScytheHarvest(final ItemStack itemStack, final EntityPlayer player, final World world, final BlockPos pos, final IBlockState blockState, final boolean harvestable) {
            super(itemStack);
            this.pos = pos;
            this.player = player;
            this.world = world;
            this.blockState = blockState;
            this.harvestable = harvestable;
        }
        
        public static OnScytheHarvest fireEvent(final ItemStack itemStack, final EntityPlayer player, final World world, final BlockPos pos, final IBlockState blockState, final boolean harvestable) {
            final OnScytheHarvest event = new OnScytheHarvest(itemStack, player, world, pos, blockState, harvestable);
            MinecraftForge.EVENT_BUS.post((Event)event);
            return event;
        }
    }
    
    public static class OnBowShoot extends TinkerToolEvent
    {
        public final EntityPlayer entityPlayer;
        public final BowCore bowCore;
        public final ItemStack ammo;
        public final int useTime;
        private float baseInaccuracy;
        public int projectileCount;
        public boolean consumeAmmoPerProjectile;
        public boolean consumeDurabilityPerProjectile;
        public float bonusInaccuracy;
        
        public OnBowShoot(final ItemStack bow, final ItemStack ammo, final EntityPlayer entityPlayer, final int useTime, final float baseInaccuracy) {
            super(bow);
            this.projectileCount = 1;
            this.consumeAmmoPerProjectile = true;
            this.consumeDurabilityPerProjectile = true;
            this.bonusInaccuracy = 0.0f;
            this.bowCore = (BowCore)bow.func_77973_b();
            this.ammo = ammo;
            this.entityPlayer = entityPlayer;
            this.useTime = useTime;
            this.baseInaccuracy = baseInaccuracy;
        }
        
        public static OnBowShoot fireEvent(final ItemStack bow, final ItemStack ammo, final EntityPlayer entityPlayer, final int useTime, final float baseInaccuracy) {
            final OnBowShoot event = new OnBowShoot(bow, ammo, entityPlayer, useTime, baseInaccuracy);
            MinecraftForge.EVENT_BUS.post((Event)event);
            return event;
        }
        
        public void setProjectileCount(final int projectileCount) {
            this.projectileCount = projectileCount;
        }
        
        public void setConsumeAmmoPerProjectile(final boolean consumeAmmoPerProjectile) {
            this.consumeAmmoPerProjectile = consumeAmmoPerProjectile;
        }
        
        public void setConsumeDurabilityPerProjectile(final boolean consumeDurabilityPerProjectile) {
            this.consumeDurabilityPerProjectile = consumeDurabilityPerProjectile;
        }
        
        public void setBonusInaccuracy(final float bonusInaccuracy) {
            this.bonusInaccuracy = bonusInaccuracy;
        }
        
        public float getBaseInaccuracy() {
            return this.baseInaccuracy;
        }
        
        public void setBaseInaccuracy(final float baseInaccuracy) {
            this.baseInaccuracy = baseInaccuracy;
        }
    }
}
