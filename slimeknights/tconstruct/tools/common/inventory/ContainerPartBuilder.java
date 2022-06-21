package slimeknights.tconstruct.tools.common.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import slimeknights.tconstruct.shared.inventory.*;
import slimeknights.mantle.inventory.*;
import slimeknights.tconstruct.tools.common.tileentity.*;
import org.apache.commons.lang3.tuple.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import slimeknights.tconstruct.tools.common.block.*;
import net.minecraft.block.properties.*;
import java.util.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.tconstruct.library.events.*;
import slimeknights.tconstruct.library.modifiers.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.library.*;
import javax.annotation.*;
import net.minecraft.client.*;
import slimeknights.tconstruct.tools.common.client.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.inventory.*;

public class ContainerPartBuilder extends ContainerTinkerStation<TilePartBuilder> implements IContainerCraftingCustom
{
    public IInventory craftResult;
    private final Slot patternSlot;
    private final Slot secondarySlot;
    private final Slot input1;
    private final Slot input2;
    private final boolean partCrafter;
    private final EntityPlayer player;
    public final IInventory patternChest;
    
    public ContainerPartBuilder(final InventoryPlayer playerInventory, final TilePartBuilder tile) {
        super((TileEntity)tile);
        final InventoryCraftingPersistent craftMatrix = new InventoryCraftingPersistent((Container)this, (IInventory)tile, 1, 3);
        this.craftResult = (IInventory)new InventoryCraftResult();
        this.player = playerInventory.field_70458_d;
        this.func_75146_a((Slot)new SlotCraftingCustom((IContainerCraftingCustom)this, playerInventory.field_70458_d, (InventoryCrafting)craftMatrix, this.craftResult, 0, 106, 35));
        this.func_75146_a(this.secondarySlot = (Slot)new SlotOut((IInventory)tile, 3, 132, 35));
        this.func_75146_a(this.patternSlot = new SlotStencil((IInventory)craftMatrix, 2, 26, 35, false));
        this.func_75146_a(this.input1 = new Slot((IInventory)craftMatrix, 0, 48, 26));
        this.func_75146_a(this.input2 = new Slot((IInventory)craftMatrix, 1, 48, 44));
        final TilePatternChest chest = (TilePatternChest)this.detectTE((Class)TilePatternChest.class);
        if (chest != null) {
            boolean hasCraftingStation = false;
            boolean hasStencilTable = false;
            for (final Pair<BlockPos, IBlockState> pair : this.tinkerStationBlocks) {
                if (!((IBlockState)pair.getRight()).func_177228_b().containsKey((Object)BlockToolTable.TABLES)) {
                    continue;
                }
                final BlockToolTable.TableTypes type = (BlockToolTable.TableTypes)((IBlockState)pair.getRight()).func_177229_b((IProperty)BlockToolTable.TABLES);
                if (type == null) {
                    continue;
                }
                if (type == BlockToolTable.TableTypes.CraftingStation) {
                    hasCraftingStation = true;
                }
                else {
                    if (type != BlockToolTable.TableTypes.StencilTable) {
                        continue;
                    }
                    hasStencilTable = true;
                }
            }
            this.partCrafter = (hasStencilTable && hasCraftingStation);
            final Container sideInventory = (Container)new ContainerPatternChest.DynamicChestInventory(chest, (IInventory)chest, -6, 8, 6);
            this.addSubContainer(sideInventory, true);
            this.patternChest = (IInventory)chest;
        }
        else {
            this.partCrafter = false;
            this.patternChest = null;
        }
        this.addPlayerInventory(playerInventory, 8, 84);
        this.func_75130_a((IInventory)playerInventory);
    }
    
    public boolean isPartCrafter() {
        return this.partCrafter;
    }
    
    public void func_75130_a(final IInventory inventoryIn) {
        this.updateResult();
    }
    
    public void updateResult() {
        if (!this.patternSlot.func_75216_d() || (!this.input1.func_75216_d() && !this.input2.func_75216_d() && !this.secondarySlot.func_75216_d())) {
            this.craftResult.func_70299_a(0, ItemStack.field_190927_a);
            this.updateGUI();
        }
        else {
            Throwable throwable = null;
            NonNullList<ItemStack> toolPart;
            try {
                toolPart = ToolBuilder.tryBuildToolPart(this.patternSlot.func_75211_c(), ListUtil.getListFrom(this.input1.func_75211_c(), this.input2.func_75211_c()), false);
                if (toolPart != null && !((ItemStack)toolPart.get(0)).func_190926_b()) {
                    TinkerCraftingEvent.ToolPartCraftingEvent.fireEvent((ItemStack)toolPart.get(0), this.player);
                }
            }
            catch (TinkerGuiException e) {
                toolPart = null;
                throwable = e;
            }
            final ItemStack secondary = this.secondarySlot.func_75211_c();
            if (toolPart != null && (secondary.func_190926_b() || ((ItemStack)toolPart.get(1)).func_190926_b() || (ItemStack.func_179545_c(secondary, (ItemStack)toolPart.get(1)) && ItemStack.func_77970_a(secondary, (ItemStack)toolPart.get(1))))) {
                this.craftResult.func_70299_a(0, (ItemStack)toolPart.get(0));
            }
            else {
                this.craftResult.func_70299_a(0, ItemStack.field_190927_a);
            }
            if (throwable != null) {
                this.error(throwable.getMessage());
            }
            else {
                this.updateGUI();
            }
        }
    }
    
    public void setPattern(final ItemStack wanted) {
        if (this.patternChest == null) {
            return;
        }
        for (int i = 0; i < this.patternChest.func_70302_i_(); ++i) {
            if (ItemStack.func_77989_b(wanted, this.patternChest.func_70301_a(i))) {
                final ItemStack slotStack = this.patternSlot.func_75211_c();
                this.patternSlot.func_75215_d(this.patternChest.func_70301_a(i));
                this.patternChest.func_70299_a(i, slotStack);
                break;
            }
        }
    }
    
    public void onCrafting(final EntityPlayer player, final ItemStack output, final IInventory craftMatrix) {
        NonNullList<ItemStack> toolPart = (NonNullList<ItemStack>)NonNullList.func_191196_a();
        try {
            toolPart = ToolBuilder.tryBuildToolPart(this.patternSlot.func_75211_c(), ListUtil.getListFrom(this.input1.func_75211_c(), this.input2.func_75211_c()), true);
        }
        catch (TinkerGuiException ex) {}
        if (toolPart == null) {
            return;
        }
        final ItemStack secondOutput = (ItemStack)toolPart.get(1);
        final ItemStack secondary = this.secondarySlot.func_75211_c();
        if (secondary.func_190926_b()) {
            this.func_75141_a(this.secondarySlot.field_75222_d, secondOutput);
        }
        else if (!secondOutput.func_190926_b() && ItemStack.func_179545_c(secondary, secondOutput) && ItemStack.func_77970_a(secondary, secondOutput)) {
            secondary.func_190917_f(secondOutput.func_190916_E());
        }
        this.updateResult();
    }
    
    public boolean func_94530_a(final ItemStack p_94530_1_, final Slot p_94530_2_) {
        return p_94530_2_.field_75224_c != this.craftResult && super.func_94530_a(p_94530_1_, p_94530_2_);
    }
    
    public String getInventoryDisplayName() {
        if (this.partCrafter) {
            return Util.translate("gui.partcrafter.name", new Object[0]);
        }
        return super.getInventoryDisplayName();
    }
    
    @SideOnly(Side.CLIENT)
    public void func_75141_a(final int p_75141_1_, @Nonnull final ItemStack p_75141_2_) {
        super.func_75141_a(p_75141_1_, p_75141_2_);
        final Minecraft mc = Minecraft.func_71410_x();
        if (mc.field_71462_r instanceof GuiPartBuilder) {
            ((GuiPartBuilder)mc.field_71462_r).updateButtons();
        }
    }
    
    @SideOnly(Side.CLIENT)
    public ItemStack func_184996_a(final int slotId, final int dragType, final ClickType type, final EntityPlayer player) {
        final ItemStack ret = super.func_184996_a(slotId, dragType, type, player);
        final Minecraft mc = Minecraft.func_71410_x();
        if (mc.field_71462_r instanceof GuiPartBuilder) {
            ((GuiPartBuilder)mc.field_71462_r).updateButtons();
        }
        return ret;
    }
}
