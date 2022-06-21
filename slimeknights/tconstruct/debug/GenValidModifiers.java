package slimeknights.tconstruct.debug;

import net.minecraft.server.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.text.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.traits.*;
import slimeknights.tconstruct.tools.modifiers.*;
import net.minecraftforge.items.*;
import slimeknights.tconstruct.library.modifiers.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.command.*;

public class GenValidModifiers extends CommandBase
{
    public String func_71517_b() {
        return "genValidModifiers";
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "Hold tool while calling /genValidModifiers to generate all modified variants";
    }
    
    public void func_184881_a(final MinecraftServer server, final ICommandSender sender, final String[] args) throws CommandException {
        if (sender.func_174793_f() instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)sender.func_174793_f();
            final ItemStack item = player.field_71071_by.func_70448_g();
            sender.func_145747_a((ITextComponent)new TextComponentString(item.func_82833_r() + " accepts the following modifiers:"));
            for (final IModifier mod : TinkerRegistry.getAllModifiers()) {
                if (!mod.hasItemsToApplyWith()) {
                    continue;
                }
                try {
                    if ((!(mod instanceof ModifierTrait) && mod instanceof AbstractTrait) || !mod.canApply(item.func_77946_l(), item) || (!mod.getIdentifier().equals("fortified") && mod instanceof ModFortify)) {
                        continue;
                    }
                    final ItemStack copy = item.func_77946_l();
                    mod.apply(copy);
                    ItemHandlerHelper.giveItemToPlayer(player, copy);
                }
                catch (TinkerGuiException ex) {}
            }
        }
    }
}
