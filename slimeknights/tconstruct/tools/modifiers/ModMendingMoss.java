package slimeknights.tconstruct.tools.modifiers;

import net.minecraftforge.common.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.nbt.*;
import slimeknights.tconstruct.library.modifiers.*;
import slimeknights.tconstruct.library.*;
import com.google.common.collect.*;

public class ModMendingMoss extends ModifierTrait
{
    public static final int MENDING_MOSS_LEVELS = 10;
    private static final String TAG_STORED_XP = "stored_xp";
    private static final String TAG_LAST_HEAL = "heal_timestamp";
    private static final int DELAY = 150;
    
    public ModMendingMoss() {
        super("mending_moss", 4434738, 3, 0);
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @Override
    public void onUpdate(final ItemStack tool, final World world, final Entity entity, final int itemSlot, final boolean isSelected) {
        if (!world.field_72995_K && entity instanceof EntityLivingBase) {
            if (entity instanceof EntityPlayer && !InventoryPlayer.func_184435_e(itemSlot) && ((EntityPlayer)entity).func_184592_cb() != tool) {
                return;
            }
            if (this.needsRepair(tool) && this.useXp(tool, world)) {
                ToolHelper.healTool(tool, this.getDurabilityPerXP(tool), (EntityLivingBase)entity);
            }
        }
    }
    
    @SubscribeEvent
    public void onPickupXp(final PlayerPickupXpEvent event) {
        final List<ItemStack> tools = (List<ItemStack>)Lists.newArrayList((Object[])new ItemStack[] { event.getEntityPlayer().func_184614_ca(), event.getEntityPlayer().func_184592_cb() });
        final EntityXPOrb entityXPOrb = event.getOrb();
        for (final ItemStack itemStack : tools) {
            if (!itemStack.func_190926_b() && this.isMendingMossModified(itemStack)) {
                final int stored = this.storeXp(entityXPOrb.field_70530_e, itemStack);
                final EntityXPOrb entityXPOrb2 = entityXPOrb;
                entityXPOrb2.field_70530_e -= stored;
            }
        }
    }
    
    private boolean isMendingMossModified(final ItemStack itemStack) {
        return TinkerUtil.hasModifier(TagUtil.getTagSafe(itemStack), this.getModifierIdentifier());
    }
    
    private boolean needsRepair(final ItemStack itemStack) {
        return !itemStack.func_190926_b() && itemStack.func_77952_i() > 0 && !ToolHelper.isBroken(itemStack);
    }
    
    private int getDurabilityPerXP(final ItemStack itemStack) {
        return 2 + ModifierTagHolder.getModifier(itemStack, this.getModifierIdentifier()).getTagData(Data.class).level;
    }
    
    private int getMaxXp(final int level) {
        if (level <= 1) {
            return 100;
        }
        return this.getMaxXp(level - 1) * 3;
    }
    
    private boolean canStoreXp(final Data data) {
        return data.storedXp < this.getMaxXp(data.level);
    }
    
    private int storeXp(final int amount, final ItemStack itemStack) {
        final ModifierTagHolder modtag = ModifierTagHolder.getModifier(itemStack, this.getModifierIdentifier());
        final Data data = modtag.getTagData(Data.class);
        int change = 0;
        if (this.canStoreXp(data)) {
            final int max = this.getMaxXp(data.level);
            change = Math.min(amount, max - data.storedXp);
            final Data data2 = data;
            data2.storedXp += change;
            modtag.save();
        }
        return change;
    }
    
    private boolean useXp(final ItemStack itemStack, final World world) {
        final ModifierTagHolder modtag = ModifierTagHolder.getModifier(itemStack, this.getModifierIdentifier());
        final Data data = modtag.getTagData(Data.class);
        if (data.storedXp > 0 && world.func_82737_E() - data.lastHeal > 150L) {
            final Data data2 = data;
            --data2.storedXp;
            data.lastHeal = world.func_82737_E();
            modtag.save();
            return true;
        }
        return false;
    }
    
    @Override
    public List<String> getExtraInfo(final ItemStack tool, final NBTTagCompound modifierTag) {
        final Data data = ModifierNBT.readTag(modifierTag, Data.class);
        assert data != null;
        final String loc = String.format("modifier.%s.extra", this.getIdentifier());
        return (List<String>)ImmutableList.of((Object)Util.translateFormatted(loc, data.storedXp));
    }
    
    public static class Data extends ModifierNBT
    {
        public int storedXp;
        public long lastHeal;
        
        @Override
        public void read(final NBTTagCompound tag) {
            super.read(tag);
            this.storedXp = tag.func_74762_e("stored_xp");
            this.lastHeal = tag.func_74763_f("heal_timestamp");
        }
        
        @Override
        public void write(final NBTTagCompound tag) {
            super.write(tag);
            tag.func_74768_a("stored_xp", this.storedXp);
            tag.func_74772_a("heal_timestamp", this.lastHeal);
        }
    }
}
