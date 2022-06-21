package slimeknights.tconstruct.debug;

import net.minecraft.server.*;
import javax.annotation.*;
import net.minecraft.client.*;
import net.minecraft.command.*;

public class ReloadResources extends CommandBase
{
    public String func_71517_b() {
        return "reloadResources";
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "/reloadResources";
    }
    
    public void func_184881_a(@Nonnull final MinecraftServer server, @Nonnull final ICommandSender sender, @Nonnull final String[] args) throws CommandException {
        if (sender.func_130014_f_().field_72995_K) {
            Minecraft.func_71410_x().func_110436_a();
        }
    }
    
    public int func_82362_a() {
        return 0;
    }
}
