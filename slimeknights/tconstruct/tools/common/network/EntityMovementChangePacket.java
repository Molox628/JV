package slimeknights.tconstruct.tools.common.network;

import slimeknights.mantle.network.*;
import net.minecraft.entity.*;
import net.minecraft.client.network.*;
import net.minecraft.client.*;
import net.minecraft.network.*;
import io.netty.buffer.*;

public class EntityMovementChangePacket extends AbstractPacketThreadsafe
{
    public int entityID;
    public double x;
    public double y;
    public double z;
    public float yaw;
    public float pitch;
    
    public EntityMovementChangePacket() {
    }
    
    public EntityMovementChangePacket(final Entity entity) {
        this.entityID = entity.func_145782_y();
        this.x = entity.field_70159_w;
        this.y = entity.field_70181_x;
        this.z = entity.field_70179_y;
        this.yaw = entity.field_70177_z;
        this.pitch = entity.field_70125_A;
    }
    
    public void handleClientSafe(final NetHandlerPlayClient netHandler) {
        final Entity entity = Minecraft.func_71410_x().field_71441_e.func_73045_a(this.entityID);
        if (entity != null) {
            entity.field_70159_w = this.x;
            entity.field_70181_x = this.y;
            entity.field_70179_y = this.z;
            entity.field_70177_z = this.yaw;
            entity.field_70125_A = this.pitch;
        }
    }
    
    public void handleServerSafe(final NetHandlerPlayServer netHandler) {
        throw new UnsupportedOperationException("Serverside only");
    }
    
    public void fromBytes(final ByteBuf buf) {
        this.entityID = buf.readInt();
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        this.yaw = buf.readFloat();
        this.pitch = buf.readFloat();
    }
    
    public void toBytes(final ByteBuf buf) {
        buf.writeInt(this.entityID);
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
        buf.writeFloat(this.yaw);
        buf.writeFloat(this.pitch);
    }
}
