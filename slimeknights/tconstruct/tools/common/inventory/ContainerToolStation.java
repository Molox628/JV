package slimeknights.tconstruct.tools.common.inventory;

import slimeknights.tconstruct.tools.common.tileentity.*;
import slimeknights.tconstruct.library.tools.*;
import net.minecraft.tileentity.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import slimeknights.mantle.network.*;
import slimeknights.mantle.inventory.*;
import slimeknights.tconstruct.tools.common.network.*;
import net.minecraft.client.*;
import slimeknights.tconstruct.tools.common.client.*;
import net.minecraft.client.gui.*;
import slimeknights.tconstruct.library.modifiers.*;
import net.minecraft.world.*;
import slimeknights.tconstruct.common.*;
import slimeknights.tconstruct.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.library.events.*;
import slimeknights.tconstruct.library.tinkering.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.mantle.util.*;
import java.util.*;
import slimeknights.tconstruct.library.*;

public class ContainerToolStation extends ContainerTinkerStation<TileToolStation>
{
    private final EntityPlayer player;
    protected SlotToolStationOut out;
    protected ToolCore selectedTool;
    protected int activeSlots;
    public String toolName;
    
    public ContainerToolStation(final InventoryPlayer playerInventory, final TileToolStation tile) {
        super((TileEntity)tile);
        this.player = playerInventory.field_70458_d;
        int i;
        for (i = 0; i < tile.func_70302_i_(); ++i) {
            this.func_75146_a((Slot)new SlotToolStationIn((IInventory)tile, i, 0, 0, (Container)this));
        }
        this.func_75146_a((Slot)(this.out = new SlotToolStationOut(i, 124, 38, this)));
        this.addPlayerInventory(playerInventory, 8, 92);
        this.func_75130_a((IInventory)playerInventory);
    }
    
    public ItemStack getResult() {
        return this.out.func_75211_c();
    }
    
    protected void syncNewContainer(final EntityPlayerMP player) {
        this.activeSlots = ((TileToolStation)this.tile).func_70302_i_();
        TinkerNetwork.sendTo((AbstractPacket)new ToolStationSelectionPacket(null, ((TileToolStation)this.tile).func_70302_i_()), player);
    }
    
    protected void syncWithOtherContainer(final BaseContainer<TileToolStation> otherContainer, final EntityPlayerMP player) {
        this.syncWithOtherContainer((ContainerToolStation)otherContainer, player);
    }
    
    protected void syncWithOtherContainer(final ContainerToolStation otherContainer, final EntityPlayerMP player) {
        this.setToolSelection(otherContainer.selectedTool, otherContainer.activeSlots);
        this.setToolName(otherContainer.toolName);
        TinkerNetwork.sendTo((AbstractPacket)new ToolStationSelectionPacket(otherContainer.selectedTool, otherContainer.activeSlots), player);
        if (otherContainer.toolName != null && !otherContainer.toolName.isEmpty()) {
            TinkerNetwork.sendTo((AbstractPacket)new ToolStationTextPacket(otherContainer.toolName), player);
        }
    }
    
    public void setToolSelection(final ToolCore tool, int activeSlots) {
        if (activeSlots > ((TileToolStation)this.tile).func_70302_i_()) {
            activeSlots = ((TileToolStation)this.tile).func_70302_i_();
        }
        this.activeSlots = activeSlots;
        this.selectedTool = tool;
        for (int i = 0; i < ((TileToolStation)this.tile).func_70302_i_(); ++i) {
            final Slot slot = this.field_75151_b.get(i);
            if (slot instanceof SlotToolStationIn) {
                final SlotToolStationIn slotToolPart = (SlotToolStationIn)slot;
                slotToolPart.setRestriction(null);
                if (i >= activeSlots) {
                    slotToolPart.deactivate();
                }
                else {
                    slotToolPart.activate();
                    if (tool != null) {
                        final List<PartMaterialType> pmts = tool.getToolBuildComponents();
                        if (i < pmts.size()) {
                            slotToolPart.setRestriction(pmts.get(i));
                        }
                    }
                }
                if (this.world.field_72995_K) {
                    slotToolPart.updateIcon();
                }
            }
        }
    }
    
    public void setToolName(final String name) {
        this.toolName = name;
        if (this.world.field_72995_K) {
            final GuiScreen screen = Minecraft.func_71410_x().field_71462_r;
            if (screen instanceof GuiToolStation) {
                ((GuiToolStation)screen).textField.func_146180_a(name);
            }
        }
        this.func_75130_a((IInventory)this.tile);
        if (this.out.func_75216_d()) {
            if (name != null && !name.isEmpty()) {
                this.out.field_75224_c.func_70301_a(0).func_151001_c(name);
            }
            else {
                this.out.field_75224_c.func_70301_a(0).func_135074_t();
            }
        }
    }
    
    public void func_75130_a(final IInventory inventoryIn) {
        this.updateGUI();
        try {
            ItemStack result = this.repairTool(false);
            if (result.func_190926_b()) {
                result = this.replaceToolParts(false);
            }
            if (result.func_190926_b()) {
                result = this.modifyTool(false);
            }
            if (result.func_190926_b()) {
                result = this.renameTool();
            }
            if (result.func_190926_b()) {
                result = this.buildTool();
            }
            this.out.field_75224_c.func_70299_a(0, result);
            this.updateGUI();
        }
        catch (TinkerGuiException e) {
            this.out.field_75224_c.func_70299_a(0, ItemStack.field_190927_a);
            this.error(e.getMessage());
        }
        if (!this.world.field_72995_K) {
            final WorldServer server = (WorldServer)this.world;
            for (final EntityPlayer player : server.field_73010_i) {
                if (player.field_71070_bA != this && player.field_71070_bA instanceof ContainerToolStation && this.sameGui((BaseContainer)player.field_71070_bA)) {
                    ((ContainerToolStation)player.field_71070_bA).out.field_75224_c.func_70299_a(0, this.out.func_75211_c());
                }
            }
        }
    }
    
    public void onResultTaken(final EntityPlayer playerIn, final ItemStack stack) {
        boolean resultTaken = false;
        try {
            resultTaken = (!this.repairTool(true).func_190926_b() || !this.replaceToolParts(true).func_190926_b() || !this.modifyTool(true).func_190926_b() || !this.renameTool().func_190926_b());
        }
        catch (TinkerGuiException e) {
            e.printStackTrace();
        }
        if (resultTaken) {
            this.updateSlotsAfterToolAction();
        }
        else {
            try {
                final ItemStack tool = this.buildTool();
                if (!tool.func_190926_b()) {
                    for (int i = 0; i < ((TileToolStation)this.tile).func_70302_i_(); ++i) {
                        ((TileToolStation)this.tile).func_70298_a(i, 1);
                    }
                    this.setToolName("");
                }
            }
            catch (TinkerGuiException e) {
                e.printStackTrace();
            }
        }
        this.func_75130_a(null);
        this.playCraftSound(playerIn);
    }
    
    protected void playCraftSound(final EntityPlayer player) {
        Sounds.playSoundForAll((Entity)player, Sounds.saw, 0.8f, 0.8f + 0.4f * TConstruct.random.nextFloat());
    }
    
    private ItemStack repairTool(final boolean remove) {
        final ItemStack repairable = this.getToolStack();
        if (repairable.func_190926_b() || !(repairable.func_77973_b() instanceof IRepairable)) {
            return ItemStack.field_190927_a;
        }
        return ToolBuilder.tryRepairTool(this.getInputs(), repairable, remove);
    }
    
    private ItemStack replaceToolParts(final boolean remove) throws TinkerGuiException {
        final ItemStack tool = this.getToolStack();
        if (tool.func_190926_b() || !(tool.func_77973_b() instanceof TinkersItem)) {
            return ItemStack.field_190927_a;
        }
        final NonNullList<ItemStack> inputs = this.getInputs();
        final ItemStack result = ToolBuilder.tryReplaceToolParts(tool, inputs, remove);
        if (!result.func_190926_b()) {
            TinkerCraftingEvent.ToolPartReplaceEvent.fireEvent(result, this.player, inputs);
        }
        return result;
    }
    
    private ItemStack modifyTool(final boolean remove) throws TinkerGuiException {
        final ItemStack modifyable = this.getToolStack();
        if (modifyable.func_190926_b() || !(modifyable.func_77973_b() instanceof IModifyable)) {
            return ItemStack.field_190927_a;
        }
        final ItemStack result = ToolBuilder.tryModifyTool(this.getInputs(), modifyable, remove);
        if (!result.func_190926_b()) {
            TinkerCraftingEvent.ToolModifyEvent.fireEvent(result, this.player, modifyable.func_77946_l());
        }
        return result;
    }
    
    private ItemStack renameTool() throws TinkerGuiException {
        final ItemStack tool = this.getToolStack();
        if (tool.func_190926_b() || !(tool.func_77973_b() instanceof TinkersItem) || StringUtils.func_151246_b(this.toolName) || tool.func_82833_r().equals(this.toolName)) {
            return ItemStack.field_190927_a;
        }
        final ItemStack result = tool.func_77946_l();
        if (TagUtil.getNoRenameFlag(result)) {
            throw new TinkerGuiException(Util.translate("gui.error.no_rename", new Object[0]));
        }
        result.func_151001_c(this.toolName);
        return result;
    }
    
    private ItemStack buildTool() throws TinkerGuiException {
        final NonNullList<ItemStack> input = (NonNullList<ItemStack>)ItemStackList.withSize(((TileToolStation)this.tile).func_70302_i_());
        for (int i = 0; i < input.size(); ++i) {
            input.set(i, (Object)((TileToolStation)this.tile).func_70301_a(i));
        }
        final ItemStack result = ToolBuilder.tryBuildTool(input, this.toolName, this.getBuildableTools());
        if (!result.func_190926_b()) {
            TinkerCraftingEvent.ToolCraftingEvent.fireEvent(result, this.player, input);
        }
        return result;
    }
    
    protected Set<ToolCore> getBuildableTools() {
        return TinkerRegistry.getToolStationCrafting();
    }
    
    private ItemStack getToolStack() {
        return this.field_75151_b.get(0).func_75211_c();
    }
    
    private void updateSlotsAfterToolAction() {
        ((TileToolStation)this.tile).func_70299_a(0, ItemStack.field_190927_a);
        for (int i = 1; i < ((TileToolStation)this.tile).func_70302_i_(); ++i) {
            if (!((TileToolStation)this.tile).func_70301_a(i).func_190926_b() && ((TileToolStation)this.tile).func_70301_a(i).func_190916_E() == 0) {
                ((TileToolStation)this.tile).func_70299_a(i, ItemStack.field_190927_a);
            }
        }
    }
    
    private NonNullList<ItemStack> getInputs() {
        final NonNullList<ItemStack> input = (NonNullList<ItemStack>)NonNullList.func_191197_a(((TileToolStation)this.tile).func_70302_i_() - 1, (Object)ItemStack.field_190927_a);
        for (int i = 1; i < ((TileToolStation)this.tile).func_70302_i_(); ++i) {
            input.set(i - 1, (Object)((TileToolStation)this.tile).func_70301_a(i));
        }
        return input;
    }
    
    public boolean func_94530_a(final ItemStack stack, final Slot slot) {
        return slot != this.out && super.func_94530_a(stack, slot);
    }
}
