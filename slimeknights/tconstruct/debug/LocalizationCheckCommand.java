package slimeknights.tconstruct.debug;

import net.minecraft.server.*;
import net.minecraft.util.math.*;
import javax.annotation.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.materials.*;
import java.util.*;
import net.minecraft.command.*;
import net.minecraft.util.text.translation.*;
import net.minecraft.util.text.*;

public class LocalizationCheckCommand extends CommandBase
{
    public int func_82362_a() {
        return 0;
    }
    
    @Nonnull
    public String func_71517_b() {
        return "checkLocalizationStrings";
    }
    
    @Nonnull
    public String func_71518_a(@Nonnull final ICommandSender sender) {
        return "/" + this.func_71517_b() + " [Material-Identifier]";
    }
    
    @Nonnull
    public List<String> func_184883_a(final MinecraftServer server, final ICommandSender sender, final String[] args, @Nullable final BlockPos pos) {
        if (args.length != 1) {
            return (List<String>)super.func_184883_a(server, sender, args, pos);
        }
        final List<String> completions = (List<String>)Lists.newLinkedList();
        final String matName = args[0].toLowerCase();
        for (final Material mat : TinkerRegistry.getAllMaterials()) {
            if (mat.identifier.toLowerCase().startsWith(matName)) {
                completions.add(mat.identifier);
            }
        }
        return completions;
    }
    
    public void func_184881_a(@Nonnull final MinecraftServer server, @Nonnull final ICommandSender sender, @Nonnull final String[] args) throws CommandException {
        if (args.length > 1) {
            throw new WrongUsageException(this.func_71518_a(sender), new Object[0]);
        }
        if (args.length > 0) {
            final Material mat = TinkerRegistry.getMaterial(args[0]);
            if (mat == Material.UNKNOWN) {
                throw new CommandException("Unknown material: " + args[0], new Object[0]);
            }
            this.scanMaterial(mat, sender);
        }
        else {
            for (final Material mat2 : TinkerRegistry.getAllMaterials()) {
                this.scanMaterial(mat2, sender);
            }
        }
    }
    
    private void scanMaterial(final Material material, final ICommandSender sender) {
        this.checkStr(String.format("material.%s.name", material.identifier), sender);
    }
    
    private void checkStr(final String str, final ICommandSender sender) {
        if (!I18n.func_94522_b(str)) {
            sender.func_145747_a((ITextComponent)new TextComponentString("Missing localization for name: " + str));
        }
    }
}
