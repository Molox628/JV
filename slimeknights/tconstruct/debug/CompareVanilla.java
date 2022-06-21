package slimeknights.tconstruct.debug;

import net.minecraft.server.*;
import javax.annotation.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.tools.harvest.*;
import slimeknights.tconstruct.library.materials.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.tools.melee.*;
import net.minecraft.command.*;
import net.minecraft.block.*;
import java.io.*;
import slimeknights.tconstruct.tools.*;
import net.minecraft.init.*;
import slimeknights.tconstruct.library.modifiers.*;
import slimeknights.tconstruct.library.tinkering.*;
import net.minecraft.nbt.*;
import net.minecraft.enchantment.*;
import net.minecraft.block.state.*;
import net.minecraft.client.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.*;
import java.util.*;
import java.awt.*;

public class CompareVanilla extends CommandBase
{
    public int func_82362_a() {
        return 0;
    }
    
    public String func_71517_b() {
        return "compareVanilla";
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "";
    }
    
    public void func_184881_a(@Nonnull final MinecraftServer server, @Nonnull final ICommandSender sender, @Nonnull final String[] args) throws CommandException {
        final ImmutableList<Material> woodMaterials = (ImmutableList<Material>)ImmutableList.of((Object)TinkerMaterials.wood, (Object)TinkerMaterials.wood, (Object)TinkerMaterials.wood);
        final ImmutableList<Material> stoneMaterials = (ImmutableList<Material>)ImmutableList.of((Object)TinkerMaterials.wood, (Object)TinkerMaterials.stone, (Object)TinkerMaterials.stone);
        final ImmutableList<Material> ironMaterials = (ImmutableList<Material>)ImmutableList.of((Object)TinkerMaterials.wood, (Object)TinkerMaterials.iron, (Object)TinkerMaterials.iron);
        final ImmutableList<Material> cobaltMaterials = (ImmutableList<Material>)ImmutableList.of((Object)TinkerMaterials.wood, (Object)TinkerMaterials.cobalt, (Object)TinkerMaterials.cobalt);
        final ImmutableList<Material> manyMaterials = (ImmutableList<Material>)ImmutableList.of((Object)TinkerMaterials.wood, (Object)TinkerMaterials.manyullyn, (Object)TinkerMaterials.manyullyn);
        ItemStack wood = TinkerHarvestTools.pickaxe.buildItem((List<Material>)woodMaterials);
        ItemStack stone = TinkerHarvestTools.pickaxe.buildItem((List<Material>)stoneMaterials);
        ItemStack iron = TinkerHarvestTools.pickaxe.buildItem((List<Material>)ironMaterials);
        ItemStack extra = TinkerHarvestTools.pickaxe.buildItem((List<Material>)cobaltMaterials);
        this.testTools(Blocks.field_150347_e, wood, stone, iron, extra, new ItemStack(Items.field_151039_o), new ItemStack(Items.field_151050_s), new ItemStack(Items.field_151035_b), new ItemStack(Items.field_151046_w), new ItemStack(Items.field_151005_D));
        wood = TinkerHarvestTools.shovel.buildItem((List<Material>)woodMaterials);
        stone = TinkerHarvestTools.shovel.buildItem((List<Material>)stoneMaterials);
        iron = TinkerHarvestTools.shovel.buildItem((List<Material>)ironMaterials);
        extra = TinkerHarvestTools.shovel.buildItem((List<Material>)cobaltMaterials);
        this.testTools(Blocks.field_150346_d, wood, stone, iron, extra, new ItemStack(Items.field_151038_n), new ItemStack(Items.field_151051_r), new ItemStack(Items.field_151037_a), new ItemStack(Items.field_151047_v), new ItemStack(Items.field_151011_C));
        wood = TinkerHarvestTools.hatchet.buildItem((List<Material>)woodMaterials);
        stone = TinkerHarvestTools.hatchet.buildItem((List<Material>)stoneMaterials);
        iron = TinkerHarvestTools.hatchet.buildItem((List<Material>)ironMaterials);
        extra = TinkerHarvestTools.hatchet.buildItem((List<Material>)cobaltMaterials);
        this.testTools(Blocks.field_150364_r, wood, stone, iron, extra, new ItemStack(Items.field_151053_p), new ItemStack(Items.field_151049_t), new ItemStack(Items.field_151036_c), new ItemStack(Items.field_151056_x), new ItemStack(Items.field_151006_E));
        wood = TinkerMeleeWeapons.broadSword.buildItem((List<Material>)woodMaterials);
        stone = TinkerMeleeWeapons.broadSword.buildItem((List<Material>)stoneMaterials);
        iron = TinkerMeleeWeapons.broadSword.buildItem((List<Material>)ironMaterials);
        extra = TinkerMeleeWeapons.broadSword.buildItem((List<Material>)manyMaterials);
        this.testTools(Blocks.field_150440_ba, wood, stone, iron, extra, new ItemStack(Items.field_151041_m), new ItemStack(Items.field_151052_q), new ItemStack(Items.field_151040_l), new ItemStack(Items.field_151048_u), new ItemStack(Items.field_151010_B));
    }
    
    protected void testTools(final Block block, final ItemStack wood, final ItemStack stone, final ItemStack iron, final ItemStack extra1, final ItemStack vanillaWood, final ItemStack vanillaStone, final ItemStack vanillaIron, final ItemStack vanillaDiamond, final ItemStack vanillaGold) {
        final File file = new File("test/" + wood.func_77973_b().getClass().getSimpleName() + ".html");
        PrintWriter pw;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            pw = new PrintWriter(file);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        catch (IOException e2) {
            e2.printStackTrace();
            return;
        }
        pw.println("<html>");
        pw.println("<head>");
        pw.println("</head>");
        pw.println("<body>");
        pw.println("<table border=\"1\">");
        pw.println(this.genSection("Wood", "#806517"));
        this.performToolTests(pw, block, wood, vanillaWood);
        pw.println(this.genSection("Stone", "#837E7C"));
        this.performToolTests(pw, block, stone, vanillaStone);
        pw.println(this.genSection("Iron", "#CECECE"));
        this.performToolTests(pw, block, iron, vanillaIron);
        pw.println(this.genSection("Diamond", "#5CB3FF"));
        this.performToolTests(pw, block, extra1, vanillaDiamond);
        pw.println(this.genSection("Gold", "#EAC117"));
        this.performToolTests(pw, block, extra1, vanillaGold);
        pw.println("</table>");
        pw.println("</body>");
        pw.println("<html>");
        pw.close();
    }
    
    protected void performToolTests(final PrintWriter pw, final Block block, final ItemStack tinker, final ItemStack vanilla) {
        pw.println(this.genHeader("", tinker.func_82833_r(), vanilla.func_82833_r()));
        pw.println(this.genSection("Unmodified", ""));
        pw.println(this.testTool(block, tinker, vanilla));
        pw.println(this.genSection("Haste/Efficiency V", ""));
        ItemStack tinkerModified = this.applyModifier(TinkerModifiers.modHaste, tinker);
        ItemStack vanillaModified = this.applyEnchantment(Enchantments.field_185305_q, vanilla);
        pw.println(this.testToolSpeed(block, tinkerModified, vanillaModified));
        pw.println(this.genSection("Sharpness V", ""));
        tinkerModified = this.applyModifier(TinkerModifiers.modSharpness, tinker);
        vanillaModified = this.applyEnchantment(Enchantments.field_185302_k, vanilla);
        pw.println(this.testToolAttack(tinkerModified, vanillaModified));
    }
    
    protected ItemStack applyModifier(final IModifier modifier, ItemStack tool) {
        tool = tool.func_77946_l();
        try {
            while (modifier.canApply(tool, tool)) {
                modifier.apply(tool);
            }
        }
        catch (TinkerGuiException ex) {}
        try {
            ToolBuilder.rebuildTool(tool.func_77978_p(), (TinkersItem)tool.func_77973_b());
        }
        catch (TinkerGuiException ex2) {}
        return tool;
    }
    
    protected ItemStack applyEnchantment(final Enchantment enchantment, ItemStack tool) {
        tool = tool.func_77946_l();
        final NBTTagCompound tag = new NBTTagCompound();
        for (int i = 0; i < enchantment.func_77325_b(); ++i) {
            ToolBuilder.addEnchantment(tag, enchantment);
        }
        tool.func_77982_d(tag);
        return tool;
    }
    
    protected String testTool(final Block block, final ItemStack tinker, final ItemStack vanilla) {
        return this.testToolDurability(tinker, vanilla) + this.testToolSpeed(block, tinker, vanilla) + this.testToolAttack(tinker, vanilla);
    }
    
    protected String testToolDurability(final ItemStack tinker, final ItemStack vanilla) {
        final int durability1 = tinker.func_77958_k();
        final int durability2 = vanilla.func_77958_k();
        return this.genRow("Durability", durability1, durability2);
    }
    
    protected String testToolSpeed(final Block block, final ItemStack tinker, final ItemStack vanilla) {
        final IBlockState state = block.func_176223_P();
        final float speed1 = tinker.func_77973_b().func_150893_a(tinker, state);
        float speed2 = vanilla.func_77973_b().func_150893_a(vanilla, state);
        final int efficiencyLevel = EnchantmentHelper.func_77506_a(Enchantments.field_185305_q, vanilla);
        if (efficiencyLevel > 0) {
            speed2 += efficiencyLevel * efficiencyLevel + 1;
        }
        return this.genRow("Speed", speed1, speed2);
    }
    
    protected String testToolAttack(final ItemStack tinker, final ItemStack vanilla) {
        final float attack1 = ToolHelper.getActualDamage(tinker, (EntityLivingBase)Minecraft.func_71410_x().field_71439_g);
        float attack2 = 1.0f;
        for (final AttributeModifier mod : vanilla.func_77973_b().getAttributeModifiers(EntityEquipmentSlot.MAINHAND, vanilla).get((Object)SharedMonsterAttributes.field_111264_e.func_111108_a())) {
            attack2 += (float)mod.func_111164_d();
        }
        attack2 += EnchantmentHelper.func_152377_a(vanilla, EnumCreatureAttribute.UNDEFINED);
        return this.genRow("Attack", attack1, attack2);
    }
    
    private String genHeader(final String desc, final Object v1, final Object v2) {
        return "<tr><th>" + desc + "</th><th>" + v1 + "</th><th>" + v2 + "</th></tr>";
    }
    
    private String genSection(final String desc, final String color) {
        return "<tr><td colspan=\"3\" align=\"center\" bgcolor=\"" + color + "\">" + desc + "</td></tr>";
    }
    
    private String genRow(final String desc, final Number v1, final Number v2) {
        final Number max = (v1.floatValue() > v2.floatValue()) ? v1 : v2;
        final String c1 = Integer.toHexString(this.floatToCol(v1.floatValue() / max.floatValue()));
        final String c2 = Integer.toHexString(this.floatToCol(v2.floatValue() / max.floatValue()));
        return "<tr><td bgcolor=\"lightgray\">" + desc + "</td><td bgcolor=\"" + c1 + "\">" + v1 + "</td><td bgcolor=\"" + c2 + "\">" + v2 + "</td></tr>";
    }
    
    private int floatToCol(final float f) {
        return Color.HSBtoRGB(f / 3.0f, 0.65f, 0.8f) & 0xFFFFFF;
    }
}
