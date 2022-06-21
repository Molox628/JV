package slimeknights.tconstruct.plugin;

import slimeknights.mantle.pulsar.pulse.*;
import slimeknights.tconstruct.smeltery.block.*;
import slimeknights.tconstruct.smeltery.*;
import net.minecraft.block.*;
import slimeknights.tconstruct.gadgets.block.*;
import slimeknights.tconstruct.gadgets.*;
import com.google.common.eventbus.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraftforge.fml.common.event.*;

@Pulse(id = "chiselIntegration", modsRequired = "chisel", defaultEnable = true)
public class Chisel
{
    public static final String modid = "chisel";
    public static final String PulseId = "chiselIntegration";
    
    @Subscribe
    public void init(final FMLInitializationEvent event) {
        for (final BlockSeared.SearedType type : BlockSeared.SearedType.values()) {
            if (type != BlockSeared.SearedType.COBBLE) {
                this.addChiselVariation((Block)TinkerSmeltery.searedBlock, type.getMeta(), "seared_block");
            }
        }
        for (final BlockBrownstone.BrownstoneType type2 : BlockBrownstone.BrownstoneType.values()) {
            if (type2 != BlockBrownstone.BrownstoneType.ROUGH) {
                this.addChiselVariation((Block)TinkerGadgets.brownstone, type2.getMeta(), "brownstone_tconstruct");
            }
        }
    }
    
    protected void addChiselVariation(final Block block, final int meta, final String groupName) {
        if (block != null) {
            final NBTTagCompound nbt = new NBTTagCompound();
            nbt.func_74778_a("group", groupName);
            nbt.func_74782_a("stack", (NBTBase)new ItemStack(block, 1, meta).func_77955_b(new NBTTagCompound()));
            nbt.func_74778_a("block", block.getRegistryName().toString());
            nbt.func_74768_a("meta", meta);
            FMLInterModComms.sendMessage("chisel", "add_variation", nbt);
        }
    }
}
