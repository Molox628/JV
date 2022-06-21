package slimeknights.tconstruct.tools.ranged.item;

import slimeknights.tconstruct.library.tools.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.common.config.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.tconstruct.tools.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.client.util.*;
import slimeknights.tconstruct.library.*;
import java.util.*;
import net.minecraftforge.fml.relauncher.*;
import javax.annotation.*;
import net.minecraft.util.text.translation.*;
import java.util.stream.*;
import slimeknights.tconstruct.library.client.*;

public class BoltCore extends ToolPart
{
    public static ItemStack GUI_RENDER_ITEMSTACK;
    
    public BoltCore(final int cost) {
        super(cost);
    }
    
    @Override
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> subItems) {
        if (this.func_194125_a(tab)) {
            for (final Material mat : TinkerRegistry.getAllMaterials()) {
                if (this.canUseMaterial(mat) && mat.hasStats("shaft")) {
                    subItems.add((Object)getItemstackWithMaterials(mat, TinkerMaterials.iron));
                    if (!Config.listAllMaterials) {
                        break;
                    }
                    continue;
                }
            }
        }
    }
    
    @Override
    public Material getMaterial(final ItemStack stack) {
        final NBTTagList materials = TagUtil.getBaseMaterialsTagList(stack);
        return TinkerRegistry.getMaterial(materials.func_150307_f(0));
    }
    
    public static Material getHeadMaterial(final ItemStack stack) {
        final NBTTagList materials = TagUtil.getBaseMaterialsTagList(stack);
        return TinkerRegistry.getMaterial(materials.func_150307_f(1));
    }
    
    @Override
    public ItemStack getItemstackWithMaterial(final Material material) {
        if (material.hasStats("shaft")) {
            return getItemstackWithMaterials(material, Material.UNKNOWN);
        }
        return getItemstackWithMaterials(Material.UNKNOWN, material);
    }
    
    public static ItemStack getItemstackWithMaterials(final Material shaft, final Material head) {
        final ItemStack stack = new ItemStack((Item)TinkerTools.boltCore);
        final NBTTagList tagList = new NBTTagList();
        tagList.func_74742_a((NBTBase)new NBTTagString(shaft.getIdentifier()));
        tagList.func_74742_a((NBTBase)new NBTTagString(head.getIdentifier()));
        final NBTTagCompound rootTag = new NBTTagCompound();
        final NBTTagCompound baseTag = new NBTTagCompound();
        baseTag.func_74782_a("Materials", (NBTBase)tagList);
        rootTag.func_74782_a("TinkerData", (NBTBase)baseTag);
        stack.func_77982_d(rootTag);
        return stack;
    }
    
    public static ItemStack getHeadStack(final ItemStack boltCore) {
        return getItemstackWithMaterials(getHeadMaterial(boltCore), Material.UNKNOWN);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void func_77624_a(final ItemStack stack, @Nullable final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn) {
        final Material material = this.getMaterial(stack);
        final Material material2 = getHeadMaterial(stack);
        final boolean shift = Util.isShiftKeyDown();
        if (!this.checkMissingMaterialTooltip(stack, tooltip)) {
            tooltip.addAll(this.getTooltipTraitInfo(material));
            tooltip.addAll(this.getTooltipTraitInfo(material2));
        }
        if (Config.extraTooltips) {
            if (!shift) {
                tooltip.add("");
                tooltip.add(Util.translate("tooltip.tool.holdShift", new Object[0]));
            }
            else {
                tooltip.addAll(this.getTooltipStatsInfo(material));
                tooltip.addAll(this.getTooltipStatsInfo(material2));
            }
        }
        tooltip.addAll(this.getAddedByInfo(material2));
    }
    
    @Nonnull
    @Override
    public String func_77653_i(@Nonnull final ItemStack stack) {
        final Material material = this.getMaterial(stack);
        final Material material2 = getHeadMaterial(stack);
        final String originalItemName = ("" + I18n.func_74838_a(this.func_77657_g(stack) + ".name")).trim();
        final List<Material> materialList = Stream.of(new Material[] { material, material2 }).filter(mat -> mat != Material.UNKNOWN).collect((Collector<? super Material, ?, List<Material>>)Collectors.toList());
        return Material.getCombinedItemName(originalItemName, materialList);
    }
    
    @Override
    public boolean canBeCrafted() {
        return false;
    }
    
    @Override
    public boolean canBeCasted() {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public ItemStack getOutlineRenderStack() {
        if (BoltCore.GUI_RENDER_ITEMSTACK == null) {
            BoltCore.GUI_RENDER_ITEMSTACK = getItemstackWithMaterials(CustomTextureCreator.guiMaterial, CustomTextureCreator.guiMaterial);
        }
        return BoltCore.GUI_RENDER_ITEMSTACK;
    }
    
    @Override
    public boolean canUseMaterialForRendering(final Material mat) {
        return mat.hasStats("head") || this.canUseMaterial(mat);
    }
}
