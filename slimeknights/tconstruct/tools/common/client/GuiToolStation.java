package slimeknights.tconstruct.tools.common.client;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.tools.common.client.module.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import slimeknights.tconstruct.tools.common.tileentity.*;
import slimeknights.mantle.client.gui.*;
import org.lwjgl.input.*;
import slimeknights.tconstruct.common.*;
import slimeknights.mantle.network.*;
import org.lwjgl.util.*;
import net.minecraft.inventory.*;
import net.minecraft.util.text.translation.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.util.text.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.nbt.*;
import slimeknights.tconstruct.library.modifiers.*;
import java.io.*;
import slimeknights.tconstruct.tools.common.network.*;
import slimeknights.tconstruct.tools.common.inventory.*;
import slimeknights.tconstruct.library.client.*;
import net.minecraft.client.renderer.*;
import slimeknights.tconstruct.library.tinkering.*;
import net.minecraft.client.gui.*;

@SideOnly(Side.CLIENT)
public class GuiToolStation extends GuiTinkerStation
{
    private static final ResourceLocation BACKGROUND;
    private static final GuiElement TextFieldActive;
    private static final GuiElement ItemCover;
    private static final GuiElement SlotBackground;
    private static final GuiElement SlotBorder;
    private static final GuiElement SlotSpaceTop;
    private static final GuiElement SlotSpaceBottom;
    private static final GuiElement PanelSpaceL;
    private static final GuiElement PanelSpaceR;
    private static final GuiElement BeamLeft;
    private static final GuiElement BeamRight;
    private static final GuiElementScalable BeamCenter;
    public static final int Column_Count = 5;
    private static final int Table_slot_count = 6;
    protected GuiElement buttonDecorationTop;
    protected GuiElement buttonDecorationBot;
    protected GuiElement panelDecorationL;
    protected GuiElement panelDecorationR;
    protected GuiElement beamL;
    protected GuiElement beamR;
    protected GuiElementScalable beamC;
    protected GuiButtonsToolStation buttons;
    protected int activeSlots;
    public GuiTextField textField;
    protected GuiInfoPanel toolInfo;
    protected GuiInfoPanel traitInfo;
    public ToolBuildGuiInfo currentInfo;
    
    public GuiToolStation(final InventoryPlayer playerInv, final World world, final BlockPos pos, final TileToolStation tile) {
        super(world, pos, (ContainerTinkerStation<?>)tile.createContainer(playerInv, world, pos));
        this.buttonDecorationTop = GuiToolStation.SlotSpaceTop;
        this.buttonDecorationBot = GuiToolStation.SlotSpaceBottom;
        this.panelDecorationL = GuiToolStation.PanelSpaceL;
        this.panelDecorationR = GuiToolStation.PanelSpaceR;
        this.beamL = new GuiElement(0, 0, 0, 0);
        this.beamR = new GuiElement(0, 0, 0, 0);
        this.beamC = new GuiElementScalable(0, 0, 0, 0);
        this.currentInfo = GuiButtonRepair.info;
        this.addModule((GuiModule)(this.buttons = new GuiButtonsToolStation(this, this.field_147002_h)));
        this.addModule((GuiModule)(this.toolInfo = new GuiInfoPanel(this, this.field_147002_h)));
        this.addModule((GuiModule)(this.traitInfo = new GuiInfoPanel(this, this.field_147002_h)));
        this.toolInfo.yOffset = 5;
        this.traitInfo.yOffset = this.toolInfo.field_147000_g + 9;
        this.field_147000_g = 174;
        this.wood();
    }
    
    public void func_73866_w_() {
        super.func_73866_w_();
        Keyboard.enableRepeatEvents(true);
        this.field_147009_r += 4;
        this.cornerY += 4;
        (this.textField = new GuiTextField(0, this.field_146289_q, this.cornerX + 70, this.cornerY + 7, 92, 12)).func_146185_a(false);
        this.textField.func_146203_f(40);
        this.buttons.xOffset = -2;
        this.buttons.yOffset = this.beamC.h + this.buttonDecorationTop.h;
        this.toolInfo.xOffset = 2;
        this.toolInfo.yOffset = this.beamC.h + this.panelDecorationL.h;
        this.traitInfo.xOffset = this.toolInfo.xOffset;
        this.traitInfo.yOffset = this.toolInfo.yOffset + this.toolInfo.field_147000_g + 4;
        for (final GuiModule guiModule : this.modules) {
            final GuiModule module = guiModule;
            guiModule.field_147009_r += 4;
        }
        this.updateGUI();
    }
    
    public void func_146281_b() {
        super.func_146281_b();
        Keyboard.enableRepeatEvents(false);
    }
    
    public Set<ToolCore> getBuildableItems() {
        return TinkerRegistry.getToolStationCrafting();
    }
    
    public void onToolSelection(final ToolBuildGuiInfo info) {
        this.activeSlots = Math.min(info.positions.size(), 6);
        this.currentInfo = info;
        ToolCore tool = null;
        if (info.tool.func_77973_b() instanceof ToolCore) {
            tool = (ToolCore)info.tool.func_77973_b();
        }
        ((ContainerToolStation)this.field_147002_h).setToolSelection(tool, this.activeSlots);
        TinkerNetwork.sendToServer((AbstractPacket)new ToolStationSelectionPacket(tool, this.activeSlots));
        this.updateGUI();
    }
    
    public void onToolSelectionPacket(final ToolStationSelectionPacket packet) {
        ToolBuildGuiInfo info = TinkerRegistryClient.getToolBuildInfoForTool(packet.tool);
        if (info == null) {
            info = GuiButtonRepair.info;
        }
        this.activeSlots = packet.activeSlots;
        this.currentInfo = info;
        this.buttons.setSelectedButtonByTool(this.currentInfo.tool);
        this.updateGUI();
    }
    
    public void updateGUI() {
        int i;
        for (i = 0; i < this.activeSlots; ++i) {
            final Point point = this.currentInfo.positions.get(i);
            final Slot slot = this.field_147002_h.func_75139_a(i);
            slot.field_75223_e = point.getX();
            slot.field_75221_f = point.getY();
        }
        int stillFilled = 0;
        while (i < 6) {
            final Slot slot = this.field_147002_h.func_75139_a(i);
            if (slot.func_75216_d()) {
                slot.field_75223_e = 87 + 20 * stillFilled;
                slot.field_75221_f = 62;
                ++stillFilled;
            }
            else {
                slot.field_75223_e = 0;
                slot.field_75221_f = 0;
            }
            ++i;
        }
        this.updateDisplay();
    }
    
    @Override
    public void updateDisplay() {
        final ContainerToolStation container = (ContainerToolStation)this.field_147002_h;
        ItemStack toolStack = container.getResult();
        if (toolStack.func_190926_b()) {
            toolStack = this.field_147002_h.func_75139_a(0).func_75211_c();
        }
        if (toolStack.func_77973_b() instanceof IModifyable) {
            if (toolStack.func_77973_b() instanceof IToolStationDisplay) {
                final IToolStationDisplay tool = (IToolStationDisplay)toolStack.func_77973_b();
                this.toolInfo.setCaption(tool.getLocalizedToolName());
                this.toolInfo.setText(tool.getInformation(toolStack));
            }
            else {
                this.toolInfo.setCaption(toolStack.func_82833_r());
                this.toolInfo.setText(new String[0]);
            }
            this.traitInfo.setCaption(I18n.func_74838_a("gui.toolstation.traits"));
            final List<String> mods = (List<String>)Lists.newLinkedList();
            final List<String> tips = (List<String>)Lists.newLinkedList();
            final NBTTagList tagList = TagUtil.getModifiersTagList(toolStack);
            for (int i = 0; i < tagList.func_74745_c(); ++i) {
                final NBTTagCompound tag = tagList.func_150305_b(i);
                final ModifierNBT data = ModifierNBT.readTag(tag);
                final IModifier modifier = TinkerRegistry.getModifier(data.identifier);
                if (modifier != null) {
                    if (!modifier.isHidden()) {
                        mods.add(data.getColorString() + modifier.getTooltip(tag, true));
                        tips.add(data.getColorString() + modifier.getLocalizedDesc());
                    }
                }
            }
            if (mods.isEmpty()) {
                mods.add(I18n.func_74838_a("gui.toolstation.noTraits"));
            }
            this.traitInfo.setText(mods, tips);
        }
        else if (this.currentInfo.tool.func_190926_b()) {
            this.toolInfo.setCaption(I18n.func_74838_a("gui.toolstation.repair"));
            this.toolInfo.setText(new String[0]);
            this.traitInfo.setCaption(null);
            final String c = TextFormatting.DARK_GRAY.toString();
            final String[] art = { c + "", c + "", c + "       .", c + "     /( _________", c + "     |  >:=========`", c + "     )(  ", c + "     \"\"" };
            this.traitInfo.setText(art);
        }
        else {
            final ToolCore tool2 = (ToolCore)this.currentInfo.tool.func_77973_b();
            this.toolInfo.setCaption(tool2.getLocalizedToolName());
            this.toolInfo.setText(tool2.getLocalizedDescription());
            final List<String> text = (List<String>)Lists.newLinkedList();
            final List<PartMaterialType> pms = tool2.getRequiredComponents();
            for (int i = 0; i < pms.size(); ++i) {
                final PartMaterialType pmt = pms.get(i);
                final StringBuilder sb = new StringBuilder();
                final ItemStack slotStack = container.func_75139_a(i).func_75211_c();
                if (!pmt.isValid(slotStack)) {
                    sb.append(TextFormatting.RED);
                    if (slotStack.func_77973_b() instanceof IToolPart && pmt.isValidItem((IToolPart)slotStack.func_77973_b())) {
                        this.warning(Util.translate("gui.error.wrong_material_part", new Object[0]));
                    }
                }
                sb.append(" * ");
                for (final IToolPart part : pmt.getPossibleParts()) {
                    if (part instanceof Item) {
                        sb.append(((Item)part).func_77653_i(new ItemStack((Item)part)));
                        sb.append("/");
                    }
                }
                sb.deleteCharAt(sb.length() - 1);
                text.add(sb.toString());
            }
            this.traitInfo.setCaption(I18n.func_74838_a("gui.toolstation.components"));
            this.traitInfo.setText((String[])text.toArray(new String[text.size()]));
        }
    }
    
    protected void func_73864_a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.func_73864_a(mouseX, mouseY, mouseButton);
        this.textField.func_146192_a(mouseX, mouseY, mouseButton);
    }
    
    protected void func_73869_a(final char typedChar, final int keyCode) throws IOException {
        if (!this.textField.func_146206_l()) {
            super.func_73869_a(typedChar, keyCode);
        }
        else {
            if (keyCode == 1) {
                this.field_146297_k.field_71439_g.func_71053_j();
            }
            this.textField.func_146201_a(typedChar, keyCode);
            TinkerNetwork.sendToServer((AbstractPacket)new ToolStationTextPacket(this.textField.func_146179_b()));
            ((ContainerToolStation)this.container).setToolName(this.textField.func_146179_b());
        }
    }
    
    public void func_73876_c() {
        super.func_73876_c();
        this.textField.func_146178_a();
    }
    
    public void func_146977_a(final Slot slotIn) {
        if (slotIn instanceof SlotToolStationIn && ((SlotToolStationIn)slotIn).isDormant() && !slotIn.func_75216_d()) {
            return;
        }
        super.func_146977_a(slotIn);
    }
    
    protected void func_146976_a(final float partialTicks, final int mouseX, final int mouseY) {
        this.drawBackground(GuiToolStation.BACKGROUND);
        if (this.textField != null) {
            if (this.textField.func_146206_l()) {
                GuiToolStation.TextFieldActive.draw(this.cornerX + 68, this.cornerY + 6);
            }
            this.textField.func_146194_f();
        }
        int x = 0;
        int y = 0;
        final float scale = 3.7f;
        final float xOff = 10.0f;
        final float yOff = 22.0f;
        GlStateManager.func_179109_b(10.0f, 22.0f, 0.0f);
        GlStateManager.func_179152_a(3.7f, 3.7f, 1.0f);
        final int logoX = (int)(this.cornerX / 3.7f);
        final int logoY = (int)(this.cornerY / 3.7f);
        if (this.currentInfo != null) {
            if (!this.currentInfo.tool.func_190926_b()) {
                this.field_146296_j.func_175042_a(this.currentInfo.tool, logoX, logoY);
            }
            else if (this.currentInfo == GuiButtonRepair.info) {
                this.field_146297_k.func_110434_K().func_110577_a(Icons.ICON);
                Icons.ICON_Anvil.draw(logoX, logoY);
            }
        }
        GlStateManager.func_179152_a(0.27027026f, 0.27027026f, 1.0f);
        GlStateManager.func_179109_b(-10.0f, -22.0f, 0.0f);
        this.field_146297_k.func_110434_K().func_110577_a(GuiToolStation.BACKGROUND);
        GlStateManager.func_179147_l();
        GlStateManager.func_179141_d();
        RenderHelper.func_74518_a();
        GlStateManager.func_179097_i();
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 0.82f);
        GuiToolStation.ItemCover.draw(this.cornerX + 7, this.cornerY + 18);
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 0.28f);
        for (int i = 0; i < this.activeSlots; ++i) {
            final Slot slot = this.field_147002_h.func_75139_a(i);
            GuiToolStation.SlotBackground.draw(x + this.cornerX + slot.field_75223_e - 1, y + this.cornerY + slot.field_75221_f - 1);
        }
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        for (int i = 0; i < 6; ++i) {
            final Slot slot = this.field_147002_h.func_75139_a(i);
            if (slot instanceof SlotToolStationIn && (!((SlotToolStationIn)slot).isDormant() || slot.func_75216_d())) {
                GuiToolStation.SlotBorder.draw(x + this.cornerX + slot.field_75223_e - 1, y + this.cornerY + slot.field_75221_f - 1);
            }
        }
        this.field_146297_k.func_110434_K().func_110577_a(Icons.ICON);
        if (this.currentInfo == GuiButtonRepair.info) {
            this.drawRepairSlotIcons();
        }
        else if (this.currentInfo.tool.func_77973_b() instanceof TinkersItem) {
            for (int i = 0; i < this.activeSlots; ++i) {
                final Slot slot = this.field_147002_h.func_75139_a(i);
                if (slot instanceof SlotToolStationIn) {
                    final ItemStack stack = ((SlotToolStationIn)slot).icon;
                    if (stack != null) {
                        this.field_146296_j.func_175042_a(stack, x + this.cornerX + slot.field_75223_e, y + this.cornerY + slot.field_75221_f);
                    }
                }
            }
        }
        this.field_146297_k.func_110434_K().func_110577_a(GuiToolStation.BACKGROUND);
        x = this.buttons.field_147003_i - this.beamL.w;
        y = this.cornerY;
        x += this.beamL.draw(x, y);
        x += this.beamC.drawScaledX(x, y, this.buttons.field_146999_f);
        this.beamR.draw(x, y);
        x = this.toolInfo.field_147003_i - this.beamL.w;
        x += this.beamL.draw(x, y);
        x += this.beamC.drawScaledX(x, y, this.toolInfo.field_146999_f);
        this.beamR.draw(x, y);
        for (final Object o : this.buttons.field_146292_n) {
            final GuiButton button = (GuiButton)o;
            this.buttonDecorationTop.draw(button.field_146128_h, button.field_146129_i - this.buttonDecorationTop.h);
            if (button.field_146127_k < this.buttons.field_146292_n.size() - 5) {
                this.buttonDecorationBot.draw(button.field_146128_h, button.field_146129_i + button.field_146121_g);
            }
        }
        this.panelDecorationL.draw(this.toolInfo.field_147003_i + 5, this.toolInfo.field_147009_r - this.panelDecorationL.h);
        this.panelDecorationR.draw(this.toolInfo.guiRight() - 5 - this.panelDecorationR.w, this.toolInfo.field_147009_r - this.panelDecorationR.h);
        this.panelDecorationL.draw(this.traitInfo.field_147003_i + 5, this.traitInfo.field_147009_r - this.panelDecorationL.h);
        this.panelDecorationR.draw(this.traitInfo.guiRight() - 5 - this.panelDecorationR.w, this.traitInfo.field_147009_r - this.panelDecorationR.h);
        GlStateManager.func_179126_j();
        super.func_146976_a(partialTicks, mouseX, mouseY);
    }
    
    protected void drawRepairSlotIcons() {
        for (int i = 0; i < this.activeSlots; ++i) {
            this.drawRepairSlotIcon(i);
        }
    }
    
    protected void drawRepairSlotIcon(final int i) {
        GuiElement icon = null;
        final Slot slot = this.field_147002_h.func_75139_a(i);
        if (slot.func_75216_d()) {
            return;
        }
        if (i == 0) {
            icon = Icons.ICON_Pickaxe;
        }
        else if (i == 1) {
            icon = Icons.ICON_Dust;
        }
        else if (i == 2) {
            icon = Icons.ICON_Lapis;
        }
        else if (i == 3) {
            icon = Icons.ICON_Ingot;
        }
        else if (i == 4) {
            icon = Icons.ICON_Gem;
        }
        else if (i == 5) {
            icon = Icons.ICON_Quartz;
        }
        if (icon != null) {
            this.drawIconEmpty(slot, icon);
        }
    }
    
    protected void wood() {
        this.toolInfo.wood();
        this.traitInfo.wood();
        this.buttonDecorationTop = GuiToolStation.SlotSpaceTop.shift(GuiToolStation.SlotSpaceTop.w, 0);
        this.buttonDecorationBot = GuiToolStation.SlotSpaceBottom.shift(GuiToolStation.SlotSpaceBottom.w, 0);
        this.panelDecorationL = GuiToolStation.PanelSpaceL.shift(18, 0);
        this.panelDecorationR = GuiToolStation.PanelSpaceR.shift(18, 0);
        this.buttons.wood();
        this.beamL = GuiToolStation.BeamLeft;
        this.beamR = GuiToolStation.BeamRight;
        this.beamC = GuiToolStation.BeamCenter;
    }
    
    protected void metal() {
        this.toolInfo.metal();
        this.traitInfo.metal();
        this.buttonDecorationTop = GuiToolStation.SlotSpaceTop.shift(GuiToolStation.SlotSpaceTop.w * 2, 0);
        this.buttonDecorationBot = GuiToolStation.SlotSpaceBottom.shift(GuiToolStation.SlotSpaceBottom.w * 2, 0);
        this.panelDecorationL = GuiToolStation.PanelSpaceL.shift(36, 0);
        this.panelDecorationR = GuiToolStation.PanelSpaceR.shift(36, 0);
        this.buttons.metal();
        this.beamL = GuiToolStation.BeamLeft.shift(0, GuiToolStation.BeamLeft.h);
        this.beamR = GuiToolStation.BeamRight.shift(0, GuiToolStation.BeamRight.h);
        this.beamC = GuiToolStation.BeamCenter.shift(0, GuiToolStation.BeamCenter.h);
    }
    
    @Override
    public void error(final String message) {
        this.toolInfo.setCaption(I18n.func_74838_a("gui.error"));
        this.toolInfo.setText(message);
        this.traitInfo.setCaption(null);
        this.traitInfo.setText(new String[0]);
    }
    
    @Override
    public void warning(final String message) {
        this.toolInfo.setCaption(I18n.func_74838_a("gui.warning"));
        this.toolInfo.setText(message);
        this.traitInfo.setCaption(null);
        this.traitInfo.setText(new String[0]);
    }
    
    static {
        BACKGROUND = Util.getResource("textures/gui/toolstation.png");
        TextFieldActive = new GuiElement(0, 210, 102, 12, 256, 256);
        ItemCover = new GuiElement(176, 18, 80, 64);
        SlotBackground = new GuiElement(176, 0, 18, 18);
        SlotBorder = new GuiElement(194, 0, 18, 18);
        SlotSpaceTop = new GuiElement(0, 176, 18, 2);
        SlotSpaceBottom = new GuiElement(0, 174, 18, 2);
        PanelSpaceL = new GuiElement(0, 174, 5, 4);
        PanelSpaceR = new GuiElement(9, 174, 9, 4);
        BeamLeft = new GuiElement(0, 180, 2, 7);
        BeamRight = new GuiElement(131, 180, 2, 7);
        BeamCenter = new GuiElementScalable(2, 180, 129, 7);
    }
}
