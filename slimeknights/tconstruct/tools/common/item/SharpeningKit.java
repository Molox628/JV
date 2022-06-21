package slimeknights.tconstruct.tools.common.item;

import slimeknights.tconstruct.library.tools.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.common.config.*;
import net.minecraft.world.*;
import javax.annotation.*;
import net.minecraft.client.util.*;
import slimeknights.tconstruct.library.*;
import slimeknights.mantle.util.*;
import java.util.*;
import slimeknights.tconstruct.library.materials.*;

public class SharpeningKit extends ToolPart
{
    public SharpeningKit() {
        super(288);
    }
    
    @Override
    public boolean canUseMaterial(final Material mat) {
        return mat.hasStats("head");
    }
    
    @Override
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> subItems) {
        if (this.func_194125_a(tab)) {
            for (final Material mat : TinkerRegistry.getAllMaterialsWithStats("head")) {
                subItems.add((Object)this.getItemstackWithMaterial(mat));
                if (!Config.listAllMaterials) {
                    break;
                }
            }
        }
    }
    
    @Override
    public void func_77624_a(final ItemStack stack, @Nullable final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn) {
        tooltip.addAll(LocUtils.getTooltips(Util.translate("item.tconstruct.sharpening_kit.tooltip", new Object[0])));
        if (!this.checkMissingMaterialTooltip(stack, tooltip, "head")) {
            final Material material = this.getMaterial(stack);
            final HeadMaterialStats stats = material.getStats("head");
            if (stats != null) {
                tooltip.add(HeadMaterialStats.formatHarvestLevel(stats.harvestLevel));
            }
        }
    }
}
