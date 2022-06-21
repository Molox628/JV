package slimeknights.tconstruct.tools.common.inventory;

import slimeknights.mantle.inventory.*;
import org.apache.commons.lang3.tuple.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.tools.common.block.*;
import net.minecraft.block.properties.*;
import java.util.*;
import net.minecraft.client.*;
import slimeknights.tconstruct.tools.common.client.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.fml.relauncher.*;

public class ContainerTinkerStation<T extends TileEntity> extends ContainerMultiModule<T>
{
    public final boolean hasCraftingStation;
    public final List<Pair<BlockPos, IBlockState>> tinkerStationBlocks;
    
    public ContainerTinkerStation(final T tile) {
        super((TileEntity)tile);
        this.tinkerStationBlocks = (List<Pair<BlockPos, IBlockState>>)Lists.newLinkedList();
        this.hasCraftingStation = this.detectedTinkerStationParts(((TileEntity)tile).func_145831_w(), ((TileEntity)tile).func_174877_v());
    }
    
    public <TE extends TileEntity> TE getTinkerTE(final Class<TE> clazz) {
        for (final Pair<BlockPos, IBlockState> pair : this.tinkerStationBlocks) {
            final TileEntity te = this.world.func_175625_s((BlockPos)pair.getLeft());
            if (te != null && clazz.isAssignableFrom(te.getClass())) {
                return (TE)te;
            }
        }
        return null;
    }
    
    public boolean detectedTinkerStationParts(final World world, final BlockPos start) {
        final Set<Integer> found = (Set<Integer>)Sets.newHashSet();
        final Set<BlockPos> visited = (Set<BlockPos>)Sets.newHashSet();
        final Set<IBlockState> ret = (Set<IBlockState>)Sets.newHashSet();
        boolean hasMaster = false;
        final Queue<BlockPos> queue = (Queue<BlockPos>)Queues.newPriorityQueue();
        queue.add(start);
        while (!queue.isEmpty()) {
            final BlockPos pos = queue.poll();
            if (visited.contains(pos)) {
                continue;
            }
            final IBlockState state = world.func_180495_p(pos);
            if (!(state.func_177230_c() instanceof ITinkerStationBlock)) {
                continue;
            }
            if (!visited.contains(pos.func_177978_c())) {
                queue.add(pos.func_177978_c());
            }
            if (!visited.contains(pos.func_177974_f())) {
                queue.add(pos.func_177974_f());
            }
            if (!visited.contains(pos.func_177968_d())) {
                queue.add(pos.func_177968_d());
            }
            if (!visited.contains(pos.func_177976_e())) {
                queue.add(pos.func_177976_e());
            }
            visited.add(pos);
            final ITinkerStationBlock tinker = (ITinkerStationBlock)state.func_177230_c();
            final Integer number = tinker.getGuiNumber(state);
            if (found.contains(number)) {
                continue;
            }
            found.add(number);
            this.tinkerStationBlocks.add((Pair<BlockPos, IBlockState>)Pair.of((Object)pos, (Object)state));
            ret.add(state);
            if (!state.func_177228_b().containsKey((Object)BlockToolTable.TABLES)) {
                continue;
            }
            final BlockToolTable.TableTypes type = (BlockToolTable.TableTypes)state.func_177229_b((IProperty)BlockToolTable.TABLES);
            if (type == null || type != BlockToolTable.TableTypes.CraftingStation) {
                continue;
            }
            hasMaster = true;
        }
        final TinkerBlockComp comp = new TinkerBlockComp();
        this.tinkerStationBlocks.sort(comp);
        return hasMaster;
    }
    
    public void updateGUI() {
        if (this.tile.func_145831_w().field_72995_K) {
            Minecraft.func_71410_x().func_152344_a((Runnable)new Runnable() {
                @Override
                public void run() {
                    clientGuiUpdate();
                }
            });
        }
    }
    
    public void error(final String message) {
        if (this.tile.func_145831_w().field_72995_K) {
            Minecraft.func_71410_x().func_152344_a((Runnable)new Runnable() {
                @Override
                public void run() {
                    clientError(message);
                }
            });
        }
    }
    
    public void warning(final String message) {
        if (this.tile.func_145831_w().field_72995_K) {
            Minecraft.func_71410_x().func_152344_a((Runnable)new Runnable() {
                @Override
                public void run() {
                    clientWarning(message);
                }
            });
        }
    }
    
    @SideOnly(Side.CLIENT)
    private static void clientGuiUpdate() {
        final GuiScreen screen = Minecraft.func_71410_x().field_71462_r;
        if (screen instanceof GuiTinkerStation) {
            ((GuiTinkerStation)screen).updateDisplay();
        }
    }
    
    @SideOnly(Side.CLIENT)
    private static void clientError(final String message) {
        final GuiScreen screen = Minecraft.func_71410_x().field_71462_r;
        if (screen instanceof GuiTinkerStation) {
            ((GuiTinkerStation)screen).error(message);
        }
    }
    
    @SideOnly(Side.CLIENT)
    private static void clientWarning(final String message) {
        final GuiScreen screen = Minecraft.func_71410_x().field_71462_r;
        if (screen instanceof GuiTinkerStation) {
            ((GuiTinkerStation)screen).warning(message);
        }
    }
    
    private static class TinkerBlockComp implements Comparator<Pair<BlockPos, IBlockState>>
    {
        @Override
        public int compare(final Pair<BlockPos, IBlockState> o1, final Pair<BlockPos, IBlockState> o2) {
            final IBlockState s1 = (IBlockState)o1.getRight();
            final IBlockState s2 = (IBlockState)o2.getRight();
            return ((ITinkerStationBlock)s2.func_177230_c()).getGuiNumber(s2) - ((ITinkerStationBlock)s1.func_177230_c()).getGuiNumber(s1);
        }
    }
}
