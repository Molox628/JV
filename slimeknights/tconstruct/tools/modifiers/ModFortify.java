package slimeknights.tconstruct.tools.modifiers;

import slimeknights.tconstruct.tools.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import slimeknights.mantle.util.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.modifiers.*;
import net.minecraft.nbt.*;

public class ModFortify extends ToolModifier
{
    public final Material material;
    
    public ModFortify(final Material material) {
        super("fortify" + material.getIdentifier(), material.materialTextColor);
        if (!material.hasStats("head")) {
            throw new TinkerAPIException(String.format("Trying to add a fortify-modifier for a material without tool stats: %s", material.getIdentifier()));
        }
        this.material = material;
        this.addAspects(new ModifierAspect.SingleAspect(this), new ModifierAspect.DataAspect((T)this), ModifierAspect.harvestOnly);
        final ItemStack kit = TinkerTools.sharpeningKit.getItemstackWithMaterial(material);
        final ItemStack flint = new ItemStack(Items.field_151145_ak);
        this.addRecipeMatch((RecipeMatch)new RecipeMatch.ItemCombination(1, new ItemStack[] { kit, flint }));
    }
    
    @Override
    public String getLocalizedName() {
        return Util.translate("modifier.%s.name", "fortify") + " (" + this.material.getLocalizedName() + ")";
    }
    
    @Override
    public String getLocalizedDesc() {
        return Util.translateFormatted(String.format("modifier.%s.desc", "fortify"), this.material.getLocalizedName());
    }
    
    public void applyEffect(final NBTTagCompound rootCompound, final NBTTagCompound modifierTag) {
        final NBTTagCompound tag = TagUtil.getToolTag(rootCompound);
        final HeadMaterialStats stats = this.material.getStats("head");
        tag.func_74768_a("HarvestLevel", stats.harvestLevel);
        final NBTTagList tagList = TagUtil.getModifiersTagList(rootCompound);
        for (int i = 0; i < tagList.func_74745_c(); ++i) {
            final NBTTagCompound mod = tagList.func_150305_b(i);
            final ModifierNBT data = ModifierNBT.readTag(mod);
            if (data.identifier.equals(this.identifier)) {
                break;
            }
            if (data.identifier.startsWith("fortify")) {
                tagList.func_74744_a(i);
                --i;
            }
        }
        TagUtil.setModifiersTagList(rootCompound, tagList);
    }
    
    @Override
    public boolean hasTexturePerMaterial() {
        return true;
    }
}
