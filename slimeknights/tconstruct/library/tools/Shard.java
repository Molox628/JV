package slimeknights.tconstruct.library.tools;

import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.common.config.*;
import net.minecraft.world.*;
import javax.annotation.*;
import java.util.*;
import net.minecraft.client.util.*;

public class Shard extends ToolPart
{
    public Shard() {
        super(72);
    }
    
    @Override
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> subItems) {
        if (this.func_194125_a(tab)) {
            for (final Material mat : TinkerRegistry.getAllMaterials()) {
                if (mat.hasStats("head") && (mat.isCraftable() || mat.isCastable())) {
                    subItems.add((Object)this.getItemstackWithMaterial(mat));
                    if (!Config.listAllMaterials) {
                        break;
                    }
                    continue;
                }
            }
        }
    }
    
    @Override
    public boolean canUseMaterial(final Material mat) {
        return true;
    }
    
    @Override
    public void func_77624_a(final ItemStack stack, @Nullable final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn) {
    }
}
