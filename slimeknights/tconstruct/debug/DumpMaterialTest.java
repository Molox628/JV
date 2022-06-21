package slimeknights.tconstruct.debug;

import net.minecraft.util.text.*;
import slimeknights.tconstruct.library.materials.*;
import net.minecraft.server.*;
import javax.annotation.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.tools.melee.item.*;
import slimeknights.tconstruct.tools.tools.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.command.*;
import java.io.*;
import java.util.*;
import java.text.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.item.*;
import java.awt.*;

public class DumpMaterialTest extends CommandBase
{
    public static String path;
    public Material baseMaterial;
    
    public DumpMaterialTest() {
        (this.baseMaterial = new Material("Baseline", TextFormatting.WHITE)).addStats(new HeadMaterialStats(500, 10.0f, 10.0f, 1));
    }
    
    public int func_82362_a() {
        return 0;
    }
    
    public String func_71517_b() {
        return "dumpMaterialTest";
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "";
    }
    
    public void func_184881_a(@Nonnull final MinecraftServer server, @Nonnull final ICommandSender sender, @Nonnull final String[] args) throws CommandException {
        this.printStats();
        this.printTool(new Pickaxe(), this.baseMaterial);
        this.printTool(new Hatchet(), this.baseMaterial);
        this.printTool(new BroadSword(), this.baseMaterial);
        this.printTool(new Shovel(), this.baseMaterial);
        for (final Material mat1 : TinkerRegistry.getAllMaterials()) {
            if (!mat1.hasStats("head")) {
                continue;
            }
            this.printTool(new Pickaxe(), mat1);
            this.printTool(new Hatchet(), mat1);
            this.printTool(new BroadSword(), mat1);
            this.printTool(new Shovel(), mat1);
        }
    }
    
    private void printStats() throws CommandException {
        final File file = new File("dumps/materials.html");
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(file);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new CommandException(e.getMessage(), new Object[0]);
        }
        final List<String> header = (List<String>)Lists.newArrayList((Object[])new String[] { "Materials", "Durability", "Speed", "Attack", "Handle", "Extra" });
        final List<List<String>> rows = (List<List<String>>)Lists.newArrayList();
        for (final Material mat1 : TinkerRegistry.getAllMaterials()) {
            if (!mat1.hasStats("head")) {
                continue;
            }
            final HeadMaterialStats stats = mat1.getStats("head");
            final List<String> row = (List<String>)Lists.newArrayList();
            rows.add(row);
            row.add("<td>" + mat1.getIdentifier() + "</td>");
        }
        final StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<head>");
        sb.append("Materials");
        sb.append("</head>");
        sb.append("<body>");
        sb.append(array2HTML(header, rows, false));
        sb.append("</body>");
        sb.append("</html>");
        pw.print(sb.toString());
        pw.close();
    }
    
    private void printTool(final ToolCore tool, final Material head) throws CommandException {
        final File file = new File("dumps/" + tool.getClass().getSimpleName() + "_" + head.getIdentifier() + ".html");
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(file);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new CommandException(e.getMessage(), new Object[0]);
        }
        final DecimalFormat df = new DecimalFormat("#.00");
        final HeadMaterialStats ref = this.baseMaterial.getStats("head");
        final List<String> header = (List<String>)Lists.newArrayList();
        header.add("");
        final List<List<String>> tableDur = (List<List<String>>)Lists.newArrayList();
        final List<List<String>> tableSpeed = (List<List<String>>)Lists.newArrayList();
        final List<List<String>> tableAttack = (List<List<String>>)Lists.newArrayList();
        for (final Material mat1 : TinkerRegistry.getAllMaterials()) {
            if (!mat1.hasStats("head")) {
                continue;
            }
            header.add(mat1.getIdentifier());
            final List<String> dur = (List<String>)Lists.newArrayList();
            tableDur.add(dur);
            final List<String> speed = (List<String>)Lists.newArrayList();
            tableSpeed.add(speed);
            final List<String> att = (List<String>)Lists.newArrayList();
            tableAttack.add(att);
            for (final Material mat2 : TinkerRegistry.getAllMaterials()) {
                if (!mat2.hasStats("head")) {
                    continue;
                }
                final ItemStack stack = tool.buildItem((List<Material>)ImmutableList.of((Object)mat1, (Object)head, (Object)mat2));
                final int d = ToolHelper.getDurabilityStat(stack);
                String s = String.format("<td bgcolor=\"%s\">%s</td>", Integer.toHexString(this.floatToCol(d / (float)ref.durability)), String.valueOf(d));
                dur.add(s);
                final float sp = ToolHelper.getMiningSpeedStat(stack);
                s = String.format("<td bgcolor=\"%s\">%s</td>", Integer.toHexString(this.floatToCol(sp / ref.miningspeed)), df.format(sp));
                speed.add(s);
                final float at = ToolHelper.getAttackStat(stack) * tool.damagePotential();
                s = String.format("<td bgcolor=\"%s\">%s</td>", Integer.toHexString(this.floatToCol(at / ref.attack)), df.format(at));
                att.add(s);
            }
        }
        final StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<head>");
        sb.append(tool.getClass().getSimpleName());
        sb.append("</head>");
        sb.append("<body>");
        header.set(0, "Durability");
        sb.append(array2HTML(header, tableDur, true));
        sb.append("<hr>");
        header.set(0, "Speed");
        sb.append(array2HTML(header, tableSpeed, true));
        sb.append("<hr>");
        header.set(0, "Attack");
        sb.append(array2HTML(header, tableAttack, true));
        sb.append("</body>");
        sb.append("</html>");
        pw.print(sb.toString());
        pw.close();
    }
    
    private int floatToCol(final float f) {
        return Color.HSBtoRGB(f / 3.0f, 0.65f, 0.8f) & 0xFFFFFF;
    }
    
    public static String array2HTML(final List<String> header, final List<List<String>> array, final boolean headerAsRowCaption) {
        final StringBuilder html = new StringBuilder("<table border=\"1\">");
        for (final Object elem : header) {
            html.append("<th>").append(elem.toString()).append("</th>");
        }
        for (int i = 0; i < array.size(); ++i) {
            final List<String> row = array.get(i);
            html.append("<tr>");
            if (headerAsRowCaption) {
                html.append("<td>").append(header.get(i + 1)).append("</td>");
            }
            for (final Object elem2 : row) {
                html.append(elem2.toString());
            }
            html.append("</tr>");
        }
        html.append("</table>");
        return html.toString();
    }
    
    static {
        DumpMaterialTest.path = "./dumps/";
    }
}
