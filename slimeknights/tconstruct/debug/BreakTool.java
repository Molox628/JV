package slimeknights.tconstruct.debug;

import net.minecraft.server.*;
import net.minecraft.entity.player.*;
import slimeknights.tconstruct.library.tools.*;
import net.minecraft.command.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;

public class BreakTool extends CommandBase
{
    public String func_71517_b() {
        return "breakTool";
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "/breakTool";
    }
    
    public void func_184881_a(final MinecraftServer server, final ICommandSender sender, final String[] args) throws CommandException {
        final EntityPlayer player = (EntityPlayer)sender.func_174793_f();
        final ItemStack tool = player.field_71071_by.func_70448_g();
        if (tool.func_190926_b() || !(tool.func_77973_b() instanceof ToolCore)) {
            throw new CommandException("Hold the tinkers tool to test in your hand", new Object[0]);
        }
        ToolHelper.breakTool(tool, (EntityLivingBase)player);
    }
}
