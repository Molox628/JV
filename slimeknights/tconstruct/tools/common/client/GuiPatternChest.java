package slimeknights.tconstruct.tools.common.client;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import slimeknights.tconstruct.tools.common.tileentity.*;
import slimeknights.mantle.inventory.*;
import slimeknights.tconstruct.tools.common.inventory.*;
import slimeknights.mantle.client.gui.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.tools.common.client.module.*;

@SideOnly(Side.CLIENT)
public class GuiPatternChest extends GuiTinkerStation
{
    private static final ResourceLocation BACKGROUND;
    protected static final GuiElementScalable background;
    public GuiScalingChest guiInventory;
    
    public GuiPatternChest(final InventoryPlayer playerInv, final World world, final BlockPos pos, final TilePatternChest tile) {
        super(world, pos, (ContainerTinkerStation<?>)tile.createContainer(playerInv, world, pos));
        this.addModule((GuiModule)(this.guiInventory = new GuiScalingChest(this, (BaseContainer)this.container.getSubContainer((Class)ContainerPatternChest.DynamicChestInventory.class))));
    }
    
    protected void func_146976_a(final float partialTicks, final int mouseX, final int mouseY) {
        this.drawBackground(GuiPatternChest.BACKGROUND);
        this.guiInventory.update(mouseX, mouseY);
        super.func_146976_a(partialTicks, mouseX, mouseY);
    }
    
    static {
        BACKGROUND = Util.getResource("textures/gui/blank.png");
        background = GuiGeneric.slotEmpty;
    }
}
