package slimeknights.tconstruct.debug;

import net.minecraft.server.*;
import javax.annotation.*;
import net.minecraft.entity.player.*;
import slimeknights.tconstruct.tools.traits.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.tconstruct.library.modifiers.*;
import net.minecraft.util.text.*;
import net.minecraft.item.*;
import net.minecraft.command.*;

public class GetToolGrowth extends CommandBase
{
    public int func_82362_a() {
        return 0;
    }
    
    public String func_71517_b() {
        return "getToolGrowth";
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "Hold tool while calling /getToolGrowth";
    }
    
    public void func_184881_a(@Nonnull final MinecraftServer server, @Nonnull final ICommandSender sender, @Nonnull final String[] args) throws CommandException {
        if (sender.func_174793_f() instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)sender.func_174793_f();
            final ItemStack item = player.field_71071_by.func_70448_g();
            final TraitProgressiveStats.StatNBT bonus = ModifierNBT.readTag(TagUtil.getTagSafe(TagUtil.getExtraTag(item), "toolgrowthStatBonus"), TraitProgressiveStats.StatNBT.class);
            final TraitProgressiveStats.StatNBT pool = ModifierNBT.readTag(TagUtil.getTagSafe(TagUtil.getExtraTag(item), "toolgrowthStatPool"), TraitProgressiveStats.StatNBT.class);
            if (bonus != null) {
                final String b = String.format("Applied bonus:\n  Durability: %d\n  Speed: %f\n  Attack: %f", bonus.durability, bonus.speed, bonus.attack);
                sender.func_145747_a((ITextComponent)new TextComponentString(b));
            }
            else {
                sender.func_145747_a((ITextComponent)new TextComponentString("No bonus"));
            }
            if (pool != null) {
                final String p = String.format("Applied bonus:\n  Durability: %d\n  Speed: %f\n  Attack: %f", pool.durability, pool.speed, pool.attack);
                sender.func_145747_a((ITextComponent)new TextComponentString(p));
            }
            else {
                sender.func_145747_a((ITextComponent)new TextComponentString("No bonus"));
            }
        }
    }
}
