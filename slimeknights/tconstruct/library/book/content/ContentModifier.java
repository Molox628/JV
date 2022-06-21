package slimeknights.tconstruct.library.book.content;

import slimeknights.tconstruct.library.book.*;
import net.minecraftforge.fml.relauncher.*;
import slimeknights.mantle.client.book.data.element.*;
import com.google.gson.annotations.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.util.*;
import slimeknights.mantle.client.book.data.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.modifiers.*;
import java.util.*;
import slimeknights.tconstruct.library.client.*;
import slimeknights.mantle.client.gui.book.*;
import slimeknights.mantle.client.gui.book.element.*;
import slimeknights.tconstruct.library.book.elements.*;
import slimeknights.mantle.util.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.tools.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.common.*;

@SideOnly(Side.CLIENT)
public class ContentModifier extends TinkerPage
{
    public static final transient String ID = "modifier";
    public static final transient int TEX_SIZE = 256;
    public static final transient ImageData IMG_SLOT_1;
    public static final transient ImageData IMG_SLOT_2;
    public static final transient ImageData IMG_SLOT_3;
    public static final transient ImageData IMG_SLOT_5;
    public static final transient ImageData IMG_TABLE;
    private transient IModifier modifier;
    private transient List<Item> tool;
    public TextData[] text;
    public String[] effects;
    @SerializedName("modifier")
    public String modifierName;
    public String[] demoTool;
    
    public ContentModifier() {
        this.demoTool = new String[] { Util.getResource("pickaxe").toString() };
    }
    
    public ContentModifier(final IModifier modifier) {
        this.demoTool = new String[] { Util.getResource("pickaxe").toString() };
        this.modifier = modifier;
        this.modifierName = modifier.getIdentifier();
    }
    
    public void load() {
        if (this.modifierName == null) {
            this.modifierName = this.parent.name;
        }
        if (this.modifier == null) {
            this.modifier = TinkerRegistry.getModifier(this.modifierName);
        }
        if (this.tool == null) {
            this.tool = (List<Item>)Lists.newArrayList();
            for (final String entry : this.demoTool) {
                final Item item = (Item)Item.field_150901_e.func_82594_a((Object)new ResourceLocation(entry));
                if (item != null) {
                    this.tool.add(item);
                }
            }
        }
    }
    
    public void build(final BookData book, final ArrayList<BookElement> list, final boolean rightSide) {
        if (this.modifier == null) {
            TinkerModifiers.log.error("MOdifier " + this.modifierName + " not found");
            return;
        }
        int color = 14540253;
        int inCount = 1;
        ItemStack[][] inputItems = null;
        if (this.modifier instanceof IModifierDisplay) {
            final IModifierDisplay modifierDisplay = (IModifierDisplay)this.modifier;
            color = modifierDisplay.getColor();
            final List<List<ItemStack>> inputList = modifierDisplay.getItems();
            inputItems = new ItemStack[5][];
            for (int i = 0; i < 5; ++i) {
                inputItems[i] = new ItemStack[inputList.size()];
                for (int j = 0; j < inputItems[i].length; ++j) {
                    inputItems[i][j] = ItemStack.field_190927_a;
                }
            }
            for (int i = 0; i < inputList.size(); ++i) {
                final List<ItemStack> inputs = new ArrayList<ItemStack>(inputList.get(i));
                if (inputs.size() > inCount) {
                    inCount = inputs.size();
                }
                for (int k = 0; k < inputs.size() && k < 5; ++k) {
                    ItemStack stack = inputs.get(k);
                    if (!stack.func_190926_b() && stack.func_77960_j() == 32767) {
                        stack = stack.func_77946_l();
                        stack.func_77964_b(0);
                    }
                    inputItems[k][i] = stack;
                }
            }
        }
        this.addTitle(list, CustomFontColor.encodeColor(color) + this.modifier.getLocalizedName(), true);
        final int h = GuiBook.PAGE_WIDTH / 3 - 10;
        list.add((BookElement)new ElementText(10, 20, GuiBook.PAGE_WIDTH - 20, h, this.text));
        if (this.effects.length > 0) {
            final TextData head = new TextData(this.parent.translate("modifier.effect"));
            head.underlined = true;
            list.add((BookElement)new ElementText(10, 20 + h, GuiBook.PAGE_WIDTH / 2 - 5, GuiBook.PAGE_HEIGHT - h - 20, new TextData[] { head }));
            final List<TextData> effectData = (List<TextData>)Lists.newArrayList();
            for (final String e : this.effects) {
                effectData.add(new TextData("\u25cf "));
                effectData.add(new TextData(e));
                effectData.add(new TextData("\n"));
            }
            list.add((BookElement)new ElementText(10, 30 + h, GuiBook.PAGE_WIDTH / 2 + 5, GuiBook.PAGE_HEIGHT - h - 20, (Collection)effectData));
        }
        ImageData img = null;
        switch (inCount) {
            case 1: {
                img = ContentModifier.IMG_SLOT_1;
                break;
            }
            case 2: {
                img = ContentModifier.IMG_SLOT_2;
                break;
            }
            case 3: {
                img = ContentModifier.IMG_SLOT_3;
                break;
            }
            default: {
                img = ContentModifier.IMG_SLOT_5;
                break;
            }
        }
        int imgX = GuiBook.PAGE_WIDTH / 2 + 20;
        int imgY = GuiBook.PAGE_HEIGHT / 2 + 30;
        imgX = imgX + 29 - img.width / 2;
        imgY = imgY + 20 - img.height / 2;
        final int[] slotX = { 3, 21, 39, 12, 30 };
        final int[] slotY = { 3, 3, 3, 22, 22 };
        list.add((BookElement)new ElementImage(imgX + (img.width - ContentModifier.IMG_TABLE.width) / 2, imgY - 24, -1, -1, ContentModifier.IMG_TABLE));
        list.add((BookElement)new ElementImage(imgX, imgY, -1, -1, img, book.appearance.slotColor));
        final ItemStackList demo = this.getDemoTools(inputItems);
        final ElementTinkerItem toolItem = new ElementTinkerItem(imgX + (img.width - 16) / 2, imgY - 24, 1.0f, (Collection<ItemStack>)demo);
        toolItem.noTooltip = true;
        list.add((BookElement)toolItem);
        list.add((BookElement)new ElementImage(imgX + (img.width - 22) / 2, imgY - 27, -1, -1, ContentModifier.IMG_SLOT_1, 16777215));
        if (inputItems != null) {
            for (int l = 0; l < inCount && l < 5; ++l) {
                list.add((BookElement)new ElementTinkerItem(imgX + slotX[l], imgY + slotY[l], 1.0f, inputItems[l]));
            }
        }
    }
    
    protected ItemStackList getDemoTools(final ItemStack[][] inputItems) {
        final ItemStackList demo = ItemStackList.withSize(this.tool.size());
        for (int i = 0; i < this.tool.size(); ++i) {
            if (this.tool.get(i) instanceof ToolCore) {
                final ToolCore core = this.tool.get(i);
                List<Material> mats = (List<Material>)ImmutableList.of((Object)TinkerMaterials.wood, (Object)TinkerMaterials.cobalt, (Object)TinkerMaterials.ardite, (Object)TinkerMaterials.manyullyn);
                mats = mats.subList(0, core.getRequiredComponents().size());
                demo.set(i, (Object)this.tool.get(i).buildItemForRendering(mats));
            }
            else if (this.tool != null) {
                demo.set(i, (Object)new ItemStack((Item)this.tool.get(i)));
            }
            if (!((ItemStack)demo.get(i)).func_190926_b()) {
                this.modifier.apply((ItemStack)demo.get(i));
            }
        }
        return demo;
    }
    
    static {
        IMG_SLOT_1 = new ImageData(ClientProxy.BOOK_MODIFY, 0, 75, 22, 22, 256, 256);
        IMG_SLOT_2 = new ImageData(ClientProxy.BOOK_MODIFY, 0, 97, 40, 22, 256, 256);
        IMG_SLOT_3 = new ImageData(ClientProxy.BOOK_MODIFY, 0, 119, 58, 22, 256, 256);
        IMG_SLOT_5 = new ImageData(ClientProxy.BOOK_MODIFY, 0, 141, 58, 41, 256, 256);
        IMG_TABLE = new ImageData(ClientProxy.BOOK_MODIFY, 214, 0, 42, 46, 256, 256);
    }
}
