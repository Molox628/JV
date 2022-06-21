package slimeknights.tconstruct.debug;

import net.minecraft.server.*;
import javax.annotation.*;
import net.minecraft.command.*;
import net.minecraft.entity.player.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;

public class DamageTool extends CommandBase
{
    public String func_71517_b() {
        return "damageTool";
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "/damageTool <amount>";
    }
    
    public void func_184881_a(@Nonnull final MinecraftServer server, @Nonnull final ICommandSender sender, @Nonnull final String[] args) throws CommandException {
        if (args.length != 1) {
            throw new CommandException("Invalid params", new Object[0]);
        }
        if (sender.func_174793_f() instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)sender.func_174793_f();
            final ItemStack item = player.field_71071_by.func_70448_g();
            ToolHelper.damageTool(item, Integer.valueOf(args[0]), (EntityLivingBase)player);
        }
    }
}
