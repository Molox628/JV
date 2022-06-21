package slimeknights.tconstruct.tools.modifiers;

import slimeknights.tconstruct.library.modifiers.*;
import net.minecraftforge.common.*;
import net.minecraft.nbt.*;
import net.minecraftforge.common.util.*;
import net.minecraft.entity.item.*;
import slimeknights.tconstruct.library.utils.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.event.entity.player.*;

public class ModSoulbound extends ToolModifier
{
    public ModSoulbound() {
        super("soulbound", 16120748);
        this.addAspects(new ModifierAspect.DataAspect((T)this), new ModifierAspect.SingleAspect(this));
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public void applyEffect(final NBTTagCompound rootCompound, final NBTTagCompound modifierTag) {
    }
    
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onPlayerDeath(final PlayerDropsEvent event) {
        if (event.getEntityPlayer() == null || event.getEntityPlayer() instanceof FakePlayer || event.isCanceled()) {
            return;
        }
        if (event.getEntityPlayer().func_130014_f_().func_82736_K().func_82766_b("keepInventory")) {
            return;
        }
        final ListIterator<EntityItem> iter = event.getDrops().listIterator();
        while (iter.hasNext()) {
            final EntityItem ei = iter.next();
            final ItemStack stack = ei.func_92059_d();
            if (TinkerUtil.hasModifier(stack.func_77978_p(), this.identifier)) {
                event.getEntityPlayer().field_71071_by.func_70441_a(stack);
                iter.remove();
            }
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onPlayerClone(final PlayerEvent.Clone evt) {
        if (!evt.isWasDeath() || evt.isCanceled()) {
            return;
        }
        if (evt.getOriginal() == null || evt.getEntityPlayer() == null || evt.getEntityPlayer() instanceof FakePlayer) {
            return;
        }
        if (evt.getEntityPlayer().func_130014_f_().func_82736_K().func_82766_b("keepInventory")) {
            return;
        }
        for (int i = 0; i < evt.getOriginal().field_71071_by.field_70462_a.size(); ++i) {
            final ItemStack stack = (ItemStack)evt.getOriginal().field_71071_by.field_70462_a.get(i);
            if (TinkerUtil.hasModifier(stack.func_77978_p(), this.identifier)) {
                evt.getEntityPlayer().field_71071_by.func_70441_a(stack);
                evt.getOriginal().field_71071_by.field_70462_a.set(i, (Object)ItemStack.field_190927_a);
            }
        }
    }
}
