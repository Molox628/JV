package slimeknights.tconstruct.tools.common.client;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import slimeknights.mantle.inventory.*;
import slimeknights.tconstruct.tools.common.client.module.*;
import net.minecraft.util.math.*;
import slimeknights.tconstruct.tools.common.inventory.*;
import org.apache.commons.lang3.tuple.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.inventory.*;
import slimeknights.mantle.client.gui.*;
import slimeknights.tconstruct.library.client.*;
import slimeknights.tconstruct.tools.common.block.*;
import slimeknights.mantle.common.*;
import slimeknights.tconstruct.tools.common.network.*;
import slimeknights.tconstruct.common.*;
import slimeknights.mantle.network.*;
import net.minecraft.init.*;
import net.minecraft.client.audio.*;
import net.minecraft.tileentity.*;
import slimeknights.tconstruct.library.*;

@SideOnly(Side.CLIENT)
public class GuiTinkerStation extends GuiMultiModule
{
    public static final ResourceLocation BLANK_BACK;
    protected final ContainerMultiModule<?> container;
    protected GuiTinkerTabs tinkerTabs;
    private final World world;
    
    public GuiTinkerStation(final World world, final BlockPos pos, final ContainerTinkerStation<?> container) {
        super((ContainerMultiModule)container);
        this.world = world;
        this.container = container;
        this.addModule((GuiModule)(this.tinkerTabs = new GuiTinkerTabs(this, (Container)container)));
        if (container.hasCraftingStation) {
            for (final Pair<BlockPos, IBlockState> pair : container.tinkerStationBlocks) {
                final IBlockState state = (IBlockState)pair.getRight();
                final BlockPos blockPos = (BlockPos)pair.getLeft();
                final ItemStack stack = state.func_177230_c().getDrops((IBlockAccess)world, blockPos, state, 0).get(0);
                this.tinkerTabs.addTab(stack, blockPos);
            }
        }
        for (int i = 0; i < this.tinkerTabs.tabData.size(); ++i) {
            if (this.tinkerTabs.tabData.get(i).equals((Object)pos)) {
                this.tinkerTabs.tabs.selected = i;
            }
        }
    }
    
    protected void drawIcon(final Slot slot, final GuiElement element) {
        this.field_146297_k.func_110434_K().func_110577_a(Icons.ICON);
        element.draw(slot.field_75223_e + this.cornerX - 1, slot.field_75221_f + this.cornerY - 1);
    }
    
    protected void drawIconEmpty(final Slot slot, final GuiElement element) {
        if (slot.func_75216_d()) {
            return;
        }
        this.drawIcon(slot, element);
    }
    
    public void onTabSelection(final int selection) {
        if (selection < 0 || selection > this.tinkerTabs.tabData.size()) {
            return;
        }
        final BlockPos pos = this.tinkerTabs.tabData.get(selection);
        final IBlockState state = this.world.func_180495_p(pos);
        if (state.func_177230_c() instanceof ITinkerStationBlock) {
            final TileEntity te = this.world.func_175625_s(pos);
            if (te instanceof IInventoryGui) {
                TinkerNetwork.sendToServer((AbstractPacket)new TinkerStationTabPacket(pos));
            }
            this.field_146297_k.func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_184371_a(SoundEvents.field_187909_gi, 1.0f));
        }
    }
    
    public void error(final String message) {
    }
    
    public void warning(final String message) {
    }
    
    public void updateDisplay() {
    }
    
    static {
        BLANK_BACK = Util.getResource("textures/gui/blank.png");
    }
}
