package slimeknights.tconstruct.common;

import slimeknights.tconstruct.common.config.*;
import slimeknights.tconstruct.common.network.*;
import slimeknights.tconstruct.smeltery.network.*;
import slimeknights.tconstruct.tools.common.network.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import slimeknights.mantle.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.chunk.*;
import java.util.*;

public class TinkerNetwork extends NetworkWrapper
{
    public static TinkerNetwork instance;
    
    public TinkerNetwork() {
        super("tconstruct");
    }
    
    public void setup() {
        this.registerPacketClient((Class)ConfigSyncPacket.class);
        this.registerPacketClient((Class)SpawnParticlePacket.class);
        this.registerPacket((Class)StencilTableSelectionPacket.class);
        this.registerPacket((Class)PartCrafterSelectionPacket.class);
        this.registerPacket((Class)ToolStationSelectionPacket.class);
        this.registerPacket((Class)ToolStationTextPacket.class);
        this.registerPacketServer((Class)TinkerStationTabPacket.class);
        this.registerPacketServer((Class)InventoryCraftingSyncPacket.class);
        this.registerPacketClient((Class)InventorySlotSyncPacket.class);
        this.registerPacketClient((Class)EntityMovementChangePacket.class);
        this.registerPacketClient((Class)ToolBreakAnimationPacket.class);
        this.registerPacketClient((Class)SmelteryFluidUpdatePacket.class);
        this.registerPacketClient((Class)HeatingStructureFuelUpdatePacket.class);
        this.registerPacketClient((Class)SmelteryInventoryUpdatePacket.class);
        this.registerPacketServer((Class)SmelteryFluidClicked.class);
        this.registerPacketClient((Class)FluidUpdatePacket.class);
        this.registerPacketClient((Class)FaucetActivationPacket.class);
        this.registerPacketClient((Class)ChannelConnectionPacket.class);
        this.registerPacketClient((Class)ChannelFlowPacket.class);
        this.registerPacketServer((Class)BouncedPacket.class);
        this.registerPacketClient((Class)LastRecipeMessage.class);
    }
    
    public static void sendPacket(final Entity player, final Packet<?> packet) {
        if (player instanceof EntityPlayerMP && ((EntityPlayerMP)player).field_71135_a != null) {
            ((EntityPlayerMP)player).field_71135_a.func_147359_a((Packet)packet);
        }
    }
    
    public static void sendToAll(final AbstractPacket packet) {
        TinkerNetwork.instance.network.sendToAll((IMessage)packet);
    }
    
    public static void sendTo(final AbstractPacket packet, final EntityPlayerMP player) {
        TinkerNetwork.instance.network.sendTo((IMessage)packet, player);
    }
    
    public static void sendToAllAround(final AbstractPacket packet, final NetworkRegistry.TargetPoint point) {
        TinkerNetwork.instance.network.sendToAllAround((IMessage)packet, point);
    }
    
    public static void sendToDimension(final AbstractPacket packet, final int dimensionId) {
        TinkerNetwork.instance.network.sendToDimension((IMessage)packet, dimensionId);
    }
    
    public static void sendToServer(final AbstractPacket packet) {
        TinkerNetwork.instance.network.sendToServer((IMessage)packet);
    }
    
    public static void sendToClients(final WorldServer world, final BlockPos pos, final AbstractPacket packet) {
        final Chunk chunk = world.func_175726_f(pos);
        for (final EntityPlayer player : world.field_73010_i) {
            if (!(player instanceof EntityPlayerMP)) {
                continue;
            }
            final EntityPlayerMP playerMP = (EntityPlayerMP)player;
            if (!world.func_184164_w().func_72694_a(playerMP, chunk.field_76635_g, chunk.field_76647_h)) {
                continue;
            }
            sendTo(packet, playerMP);
        }
    }
    
    static {
        TinkerNetwork.instance = new TinkerNetwork();
    }
}
