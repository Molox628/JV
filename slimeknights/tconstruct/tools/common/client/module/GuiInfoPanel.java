package slimeknights.tconstruct.tools.common.client.module;

import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import gnu.trove.list.*;
import slimeknights.mantle.client.gui.*;
import net.minecraft.inventory.*;
import slimeknights.tconstruct.common.*;
import gnu.trove.list.linked.*;
import com.google.common.collect.*;
import net.minecraft.client.*;
import slimeknights.mantle.util.*;
import net.minecraft.util.math.*;
import slimeknights.tconstruct.library.*;
import java.util.*;
import net.minecraft.util.text.*;
import net.minecraft.client.renderer.*;

public class GuiInfoPanel extends GuiModule
{
    private static int resW;
    private static int resH;
    private static ResourceLocation BACKGROUND;
    private static GuiElement topLeft;
    private static GuiElement topRight;
    private static GuiElement botLeft;
    private static GuiElement botRight;
    private static GuiElementScalable top;
    private static GuiElementScalable bot;
    private static GuiElementScalable left;
    private static GuiElementScalable right;
    private static GuiElementScalable background;
    private static GuiElement sliderNormal;
    private static GuiElement sliderHover;
    private static GuiElementScalable sliderBar;
    private static GuiElement sliderTop;
    private static GuiElement sliderBot;
    private GuiWidgetBorder border;
    private FontRenderer fontRenderer;
    private GuiWidgetSlider slider;
    protected String caption;
    protected List<String> text;
    protected List<String> tooltips;
    private TIntList tooltipLines;
    public float textScale;
    
    public GuiInfoPanel(final GuiMultiModule parent, final Container container) {
        super(parent, container, true, false);
        this.border = new GuiWidgetBorder();
        this.fontRenderer = ClientProxy.fontRenderer;
        this.slider = new GuiWidgetSlider(GuiInfoPanel.sliderNormal, GuiInfoPanel.sliderHover, GuiInfoPanel.sliderHover, GuiInfoPanel.sliderTop, GuiInfoPanel.sliderBot, GuiInfoPanel.sliderBar);
        this.tooltipLines = (TIntList)new TIntLinkedList();
        this.textScale = 1.0f;
        this.border.borderTop = GuiInfoPanel.top;
        this.border.borderBottom = GuiInfoPanel.bot;
        this.border.borderLeft = GuiInfoPanel.left;
        this.border.borderRight = GuiInfoPanel.right;
        this.border.cornerTopLeft = GuiInfoPanel.topLeft;
        this.border.cornerTopRight = GuiInfoPanel.topRight;
        this.border.cornerBottomLeft = GuiInfoPanel.botLeft;
        this.border.cornerBottomRight = GuiInfoPanel.botRight;
        this.field_146999_f = GuiInfoPanel.resW + 8;
        this.field_147000_g = GuiInfoPanel.resH + 8;
        this.caption = "Caption";
        this.text = (List<String>)Lists.newLinkedList();
        super.field_146289_q = this.fontRenderer;
    }
    
    public void func_146280_a(final Minecraft mc, final int width, final int height) {
        super.func_146280_a(mc, width, height);
        super.field_146289_q = this.fontRenderer;
    }
    
    public void updatePosition(final int parentX, final int parentY, final int parentSizeX, final int parentSizeY) {
        super.updatePosition(parentX, parentY, parentSizeX, parentSizeY);
        this.border.setPosition(this.field_147003_i, this.field_147009_r);
        this.border.setSize(this.field_146999_f, this.field_147000_g);
        this.slider.setPosition(this.guiRight() - this.border.w - 2, this.field_147009_r + this.border.h + 12);
        this.slider.setSize(this.field_147000_g - this.border.h * 2 - 2 - 12);
        this.updateSliderParameters();
    }
    
    public void setCaption(final String caption) {
        this.caption = caption;
        this.updateSliderParameters();
    }
    
    public void setText(final String... text) {
        this.setText(Lists.newArrayList((Object[])text), null);
    }
    
    public void setText(final List<String> text) {
        this.setText(text, null);
    }
    
    public void setText(List<String> text, final List<String> tooltips) {
        if (text != null) {
            text = (List<String>)Lists.newArrayList((Iterable)text);
            for (int i = 0; i < text.size(); ++i) {
                text.set(i, LocUtils.convertNewlines((String)text.get(i)));
            }
        }
        this.text = text;
        this.updateSliderParameters();
        this.setTooltips(tooltips);
    }
    
    protected void setTooltips(final List<String> tooltips) {
        if (tooltips != null) {
            for (int i = 0; i < tooltips.size(); ++i) {
                tooltips.set(i, LocUtils.convertNewlines((String)tooltips.get(i)));
            }
        }
        this.tooltips = tooltips;
    }
    
    public boolean hasCaption() {
        return this.caption != null && !this.caption.isEmpty();
    }
    
    public boolean hasTooltips() {
        return this.tooltips != null && !this.tooltips.isEmpty();
    }
    
    public int calcNeededHeight() {
        int neededHeight = 0;
        if (this.hasCaption()) {
            neededHeight += this.fontRenderer.field_78288_b;
            neededHeight += 3;
        }
        neededHeight += (int)((this.fontRenderer.field_78288_b + 0.5f) * this.getTotalLines().size());
        return neededHeight;
    }
    
    protected void updateSliderParameters() {
        this.slider.hide();
        final int h = this.field_147000_g - 10;
        if (this.calcNeededHeight() <= h) {
            return;
        }
        this.slider.show();
        final int neededHeight = this.calcNeededHeight();
        int hiddenRows = (neededHeight - h) / this.fontRenderer.field_78288_b;
        if ((neededHeight - h) % this.fontRenderer.field_78288_b > 0) {
            ++hiddenRows;
        }
        this.slider.setSliderParameters(0, hiddenRows, 1);
    }
    
    protected List<String> getTotalLines() {
        int w = this.field_146999_f - this.border.w * 2 + 2;
        if (!this.slider.isHidden()) {
            w -= this.slider.width + 3;
        }
        w /= (int)this.textScale;
        final List<String> lines = (List<String>)Lists.newLinkedList();
        this.tooltipLines.clear();
        for (final String line : this.text) {
            this.tooltipLines.add(lines.size());
            if (line == null || line.isEmpty()) {
                lines.add("");
            }
            else {
                lines.addAll(this.fontRenderer.func_78271_c(line, w));
            }
        }
        return lines;
    }
    
    public GuiInfoPanel wood() {
        this.shift(GuiInfoPanel.resW + 8, 0);
        this.shiftSlider(6, 0);
        return this;
    }
    
    public GuiInfoPanel metal() {
        this.shift(GuiInfoPanel.resW + 8, GuiInfoPanel.resH + 8);
        this.shiftSlider(12, 0);
        return this;
    }
    
    private void shift(final int xd, final int yd) {
        this.border.borderTop = GuiInfoPanel.top.shift(xd, yd);
        this.border.borderBottom = GuiInfoPanel.bot.shift(xd, yd);
        this.border.borderLeft = GuiInfoPanel.left.shift(xd, yd);
        this.border.borderRight = GuiInfoPanel.right.shift(xd, yd);
        this.border.cornerTopLeft = GuiInfoPanel.topLeft.shift(xd, yd);
        this.border.cornerTopRight = GuiInfoPanel.topRight.shift(xd, yd);
        this.border.cornerBottomLeft = GuiInfoPanel.botLeft.shift(xd, yd);
        this.border.cornerBottomRight = GuiInfoPanel.botRight.shift(xd, yd);
    }
    
    private void shiftSlider(final int xd, final int yd) {
        this.slider = new GuiWidgetSlider(GuiInfoPanel.sliderNormal.shift(xd, yd), GuiInfoPanel.sliderHover.shift(xd, yd), GuiInfoPanel.sliderHover.shift(xd, yd), GuiInfoPanel.sliderTop.shift(xd, yd), GuiInfoPanel.sliderBot.shift(xd, yd), GuiInfoPanel.sliderBar.shift(xd, yd));
    }
    
    protected void func_146979_b(int mouseX, final int mouseY) {
        if (this.tooltips == null) {
            return;
        }
        if (mouseX < this.field_147003_i || mouseX > this.guiRight()) {
            return;
        }
        if (this.hasTooltips() && mouseX >= this.guiRight() - this.border.w - this.fontRenderer.func_78263_a('?') / 2 && mouseX < this.guiRight() && mouseY > this.field_147009_r + 5 && mouseY < this.field_147009_r + 5 + this.fontRenderer.field_78288_b) {
            final int w = MathHelper.func_76125_a(this.field_146294_l - mouseX - 12, 10, 200);
            this.func_146283_a(this.fontRenderer.func_78271_c(Util.translate("gui.general.hover", new Object[0]), w), mouseX - this.field_147003_i, mouseY - this.field_147009_r);
        }
        float y = (float)(5 + this.field_147009_r);
        if (this.hasCaption()) {
            y += this.fontRenderer.field_78288_b + 3;
        }
        final float textHeight = this.fontRenderer.field_78288_b * this.textScale + 0.5f;
        final float lowerBound = (this.field_147009_r + this.field_147000_g - 5) / this.textScale;
        int index = -1;
        final ListIterator<String> iter = this.getTotalLines().listIterator(this.slider.getValue());
        while (iter.hasNext()) {
            if (y + textHeight > lowerBound) {
                break;
            }
            if (mouseY > y && mouseY <= y + textHeight) {
                index = iter.nextIndex();
                break;
            }
            iter.next();
            y += textHeight;
        }
        if (index < 0) {
            return;
        }
        int i;
        for (i = 0; this.tooltipLines.size() > i && index > this.tooltipLines.get(i); ++i) {}
        if (i >= this.tooltips.size() || this.tooltips.get(i) == null) {
            return;
        }
        int w2 = MathHelper.func_76125_a(this.field_146294_l - mouseX - 12, 0, 200);
        if (w2 < 100) {
            mouseX -= 100 - w2;
            w2 = 100;
        }
        final List<String> lines = (List<String>)this.fontRenderer.func_78271_c((String)this.tooltips.get(i), w2);
        this.func_146283_a((List)lines, mouseX - this.field_147003_i, mouseY - this.field_147009_r - lines.size() * this.fontRenderer.field_78288_b / 2);
    }
    
    protected void func_146976_a(final float partialTicks, final int mouseX, final int mouseY) {
        this.field_146297_k.func_110434_K().func_110577_a(GuiInfoPanel.BACKGROUND);
        this.border.draw();
        GuiInfoPanel.background.drawScaled(this.field_147003_i + 4, this.field_147009_r + 4, this.field_146999_f - 8, this.field_147000_g - 8);
        float y = (float)(5 + this.field_147009_r);
        float x = (float)(5 + this.field_147003_i);
        final int color = -986896;
        if (this.hasTooltips()) {
            this.fontRenderer.func_175065_a("?", (float)(this.guiRight() - this.border.w - this.fontRenderer.func_78263_a('?') / 2), (float)(this.field_147009_r + 5), -10526881, false);
        }
        if (this.hasCaption()) {
            int x2 = this.field_146999_f / 2;
            x2 -= this.fontRenderer.func_78256_a(this.caption) / 2;
            this.fontRenderer.func_175063_a(TextFormatting.UNDERLINE + TextFormatting.func_110646_a(this.caption), (float)(this.field_147003_i + x2), y, color);
            y += this.fontRenderer.field_78288_b + 3;
        }
        if (this.text == null || this.text.size() == 0) {
            return;
        }
        final float textHeight = this.fontRenderer.field_78288_b * this.textScale + 0.5f;
        final float lowerBound = (this.field_147009_r + this.field_147000_g - 5) / this.textScale;
        GlStateManager.func_179152_a(this.textScale, this.textScale, 1.0f);
        x /= this.textScale;
        y /= this.textScale;
        for (ListIterator<String> iter = this.getTotalLines().listIterator(this.slider.getValue()); iter.hasNext() && y + textHeight - 0.5f <= lowerBound; y += textHeight) {
            final String line = iter.next();
            this.fontRenderer.func_175063_a(line, x, y, color);
        }
        GlStateManager.func_179152_a(1.0f / this.textScale, 1.0f / this.textScale, 1.0f);
        this.field_146297_k.func_110434_K().func_110577_a(GuiInfoPanel.BACKGROUND);
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        this.slider.update(mouseX, mouseY, !this.isMouseOverFullSlot(mouseX, mouseY) && this.isMouseInModule(mouseX, mouseY));
        this.slider.draw();
    }
    
    static {
        GuiInfoPanel.resW = 118;
        GuiInfoPanel.resH = 75;
        GuiInfoPanel.BACKGROUND = Util.getResource("textures/gui/panel.png");
        GuiInfoPanel.topLeft = new GuiElement(0, 0, 4, 4, 256, 256);
        GuiInfoPanel.topRight = new GuiElement(GuiInfoPanel.resW + 4, 0, 4, 4);
        GuiInfoPanel.botLeft = new GuiElement(0, GuiInfoPanel.resH + 4, 4, 4);
        GuiInfoPanel.botRight = new GuiElement(GuiInfoPanel.resW + 4, GuiInfoPanel.resH + 4, 4, 4);
        GuiInfoPanel.top = new GuiElementScalable(4, 0, GuiInfoPanel.resW, 4);
        GuiInfoPanel.bot = new GuiElementScalable(4, 4 + GuiInfoPanel.resH, GuiInfoPanel.resW, 4);
        GuiInfoPanel.left = new GuiElementScalable(0, 4, 4, GuiInfoPanel.resH);
        GuiInfoPanel.right = new GuiElementScalable(4 + GuiInfoPanel.resW, 4, 4, GuiInfoPanel.resH);
        GuiInfoPanel.background = new GuiElementScalable(4, 4, GuiInfoPanel.resW, GuiInfoPanel.resH);
        GuiInfoPanel.sliderNormal = new GuiElement(0, 83, 3, 5);
        GuiInfoPanel.sliderHover = GuiInfoPanel.sliderNormal.shift(GuiInfoPanel.sliderNormal.w, 0);
        GuiInfoPanel.sliderBar = new GuiElementScalable(0, 88, 3, 8);
        GuiInfoPanel.sliderTop = new GuiElement(3, 88, 3, 4);
        GuiInfoPanel.sliderBot = new GuiElement(3, 92, 3, 4);
    }
}
