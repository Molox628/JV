package slimeknights.tconstruct.debug;

import net.minecraft.server.*;
import javax.annotation.*;
import net.minecraft.entity.player.*;
import slimeknights.tconstruct.library.tools.*;
import net.minecraft.command.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.entity.*;
import net.minecraft.util.text.*;
import net.minecraft.item.*;

public class TestTool extends CommandBase
{
    public String func_71517_b() {
        return "testTool";
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "/testTool";
    }
    
    public void func_184881_a(@Nonnull final MinecraftServer server, @Nonnull final ICommandSender sender, @Nonnull final String[] args) throws CommandException {
        if (sender.func_174793_f() instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)sender.func_174793_f();
            final ItemStack item = player.field_71071_by.func_70448_g();
            if (item.func_190926_b() || !(item.func_77973_b() instanceof ToolCore)) {
                throw new CommandException("Hold the tinkers tool to test in your hand", new Object[0]);
            }
            int i = 0;
            while (!ToolHelper.isBroken(item)) {
                ToolHelper.damageTool(item, 1, (EntityLivingBase)player);
                ++i;
            }
            sender.func_145747_a((ITextComponent)new TextComponentString("Effective Durability: " + i));
        }
    }
}
