package slimeknights.tconstruct.library.book.content;

import slimeknights.tconstruct.library.book.*;
import net.minecraftforge.fml.relauncher.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.mantle.client.book.data.element.*;
import com.google.gson.annotations.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.tinkering.*;
import java.util.function.*;
import java.util.stream.*;
import slimeknights.mantle.client.book.data.*;
import java.util.*;
import slimeknights.mantle.client.gui.book.*;
import com.google.common.collect.*;
import slimeknights.mantle.client.gui.book.element.*;
import slimeknights.tconstruct.library.book.elements.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.common.*;

@SideOnly(Side.CLIENT)
public class ContentTool extends TinkerPage
{
    public static final transient String ID = "tool";
    public static final transient int TEX_SIZE = 256;
    public static final transient ImageData IMG_SLOTS;
    public static final transient ImageData IMG_SLOT_1;
    public static final transient ImageData IMG_TABLE;
    private transient ToolCore tool;
    private transient List<Collection<IToolPart>> parts;
    public TextData[] text;
    public String[] properties;
    @SerializedName("tool")
    public String toolName;
    
    public ContentTool() {
        this.text = new TextData[0];
        this.properties = new String[0];
    }
    
    public ContentTool(final ToolCore tool) {
        this.text = new TextData[0];
        this.properties = new String[0];
        this.tool = tool;
        this.toolName = tool.getIdentifier();
    }
    
    public void load() {
        if (this.toolName == null) {
            this.toolName = this.parent.name;
        }
        if (this.tool == null) {
            final RuntimeException ex;
            this.tool = TinkerRegistry.getTools().stream().filter(toolCore -> this.toolName.equals(toolCore.getIdentifier())).findFirst().orElseThrow(() -> {
                new RuntimeException("Unknown tool " + this.toolName);
                return ex;
            });
        }
        if (this.parts == null) {
            this.parts = this.tool.getToolBuildComponents().stream().map((Function<? super Object, ?>)PartMaterialType::getPossibleParts).collect((Collector<? super Object, ?, List<Collection<IToolPart>>>)Collectors.toList());
        }
    }
    
    private Collection<Item> convertIToolPartToItem(final Set<IToolPart> parts) {
        return parts.stream().map(part -> part).collect((Collector<? super Object, ?, Collection<Item>>)Collectors.toList());
    }
    
    public void build(final BookData book, final ArrayList<BookElement> list, final boolean rightSide) {
        this.addTitle((ArrayList)list, this.tool.getLocalizedName());
        final int padding = 5;
        final int h = GuiBook.PAGE_WIDTH / 3 - 10;
        int y = 16;
        list.add((BookElement)new ElementText(padding, y, GuiBook.PAGE_WIDTH - padding * 2, h, this.text));
        final ImageData img = ContentTool.IMG_SLOTS;
        final int imgX = GuiBook.PAGE_WIDTH - img.width - 8;
        final int imgY = GuiBook.PAGE_HEIGHT - img.height - 16;
        final int toolX = imgX + (img.width - 16) / 2;
        final int toolY = imgY + 28;
        y = imgY - 6;
        if (this.properties.length > 0) {
            final TextData head = new TextData(this.parent.translate("tool.properties"));
            head.underlined = true;
            list.add((BookElement)new ElementText(padding, y, 86 - padding, GuiBook.PAGE_HEIGHT - h - 20, new TextData[] { head }));
            final List<TextData> effectData = (List<TextData>)Lists.newArrayList();
            for (final String e : this.properties) {
                effectData.add(new TextData("\u25cf "));
                effectData.add(new TextData(e));
                effectData.add(new TextData("\n"));
            }
            y += 10;
            list.add((BookElement)new ElementText(padding, y, GuiBook.PAGE_WIDTH / 2 + 5, GuiBook.PAGE_HEIGHT - h - 20, (Collection)effectData));
        }
        final int[] slotX = { -21, -25, 0, 25, 21 };
        final int[] slotY = { 22, -4, -25, -4, 22 };
        list.add((BookElement)new ElementImage(imgX + (img.width - ContentTool.IMG_TABLE.width) / 2, imgY + 28, -1, -1, ContentTool.IMG_TABLE));
        list.add((BookElement)new ElementImage(imgX, imgY, -1, -1, img, book.appearance.slotColor));
        final ItemStack demo = this.tool.buildItemForRenderingInGui();
        final ElementTinkerItem toolItem = new ElementTinkerItem(toolX, toolY, 1.0f, demo);
        toolItem.noTooltip = true;
        list.add((BookElement)toolItem);
        list.add((BookElement)new ElementImage(toolX - 3, toolY - 3, -1, -1, ContentTool.IMG_SLOT_1, 16777215));
        for (int i = 0; i < this.parts.size(); ++i) {
            final Collection<IToolPart> items = this.parts.get(i);
            final Material material = this.tool.getMaterialForPartForGuiRendering(i);
            final ItemStack[] stacks = items.stream().map(part -> part.getItemstackWithMaterial(material)).toArray(ItemStack[]::new);
            final ElementTinkerItem partItem = new ElementTinkerItem(toolX + slotX[i], toolY + slotY[i], 1.0f, stacks);
            partItem.noTooltip = true;
            list.add((BookElement)partItem);
        }
    }
    
    static {
        IMG_SLOTS = new ImageData(ClientProxy.BOOK_MODIFY, 0, 0, 72, 72, 256, 256);
        IMG_SLOT_1 = ContentModifier.IMG_SLOT_1;
        IMG_TABLE = ContentModifier.IMG_TABLE;
    }
}
