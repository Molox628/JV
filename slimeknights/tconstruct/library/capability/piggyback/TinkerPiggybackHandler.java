package slimeknights.tconstruct.library.capability.piggyback;

import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.server.*;
import slimeknights.tconstruct.common.*;
import net.minecraft.network.*;

public class TinkerPiggybackHandler implements ITinkerPiggyback
{
    private EntityPlayer riddenPlayer;
    private List<Entity> lastPassengers;
    
    @Override
    public void setRiddenPlayer(final EntityPlayer entityPlayer) {
        this.riddenPlayer = entityPlayer;
    }
    
    @Override
    public void updatePassengers() {
        if (this.riddenPlayer != null) {
            if (!this.riddenPlayer.func_184188_bt().equals(this.lastPassengers) && this.riddenPlayer instanceof EntityPlayerMP) {
                TinkerNetwork.sendPacket((Entity)this.riddenPlayer, (Packet<?>)new SPacketSetPassengers((Entity)this.riddenPlayer));
            }
            this.lastPassengers = (List<Entity>)this.riddenPlayer.func_184188_bt();
        }
    }
}
