package slimeknights.tconstruct.common.network;

import slimeknights.mantle.network.*;
import slimeknights.tconstruct.library.client.particle.*;
import net.minecraft.client.network.*;
import slimeknights.tconstruct.tools.*;
import net.minecraft.world.*;
import net.minecraft.network.*;
import io.netty.buffer.*;

public class SpawnParticlePacket extends AbstractPacketThreadsafe
{
    public Particles particle;
    private double x;
    private double y;
    private double z;
    private double xSpeed;
    private double ySpeed;
    private double zSpeed;
    private int[] data;
    
    public SpawnParticlePacket() {
    }
    
    public SpawnParticlePacket(final Particles particle, final double x, final double y, final double z, final double xSpeed, final double ySpeed, final double zSpeed, final int... data) {
        this.particle = particle;
        this.x = x;
        this.y = y;
        this.z = z;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.zSpeed = zSpeed;
        this.data = data;
    }
    
    public void handleClientSafe(final NetHandlerPlayClient netHandler) {
        TinkerTools.proxy.spawnParticle(this.particle, null, this.x, this.y, this.z, this.xSpeed, this.ySpeed, this.zSpeed, this.data);
    }
    
    public void handleServerSafe(final NetHandlerPlayServer netHandler) {
        throw new UnsupportedOperationException("Clientside only");
    }
    
    public void fromBytes(final ByteBuf buf) {
        this.particle = Particles.values()[buf.readInt()];
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        this.xSpeed = buf.readDouble();
        this.ySpeed = buf.readDouble();
        this.zSpeed = buf.readDouble();
        this.data = new int[buf.readInt()];
        for (int i = 0; i < this.data.length; ++i) {
            this.data[i] = buf.readInt();
        }
    }
    
    public void toBytes(final ByteBuf buf) {
        buf.writeInt(this.particle.ordinal());
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
        buf.writeDouble(this.xSpeed);
        buf.writeDouble(this.ySpeed);
        buf.writeDouble(this.zSpeed);
        buf.writeInt(this.data.length);
        for (final int i : this.data) {
            buf.writeInt(i);
        }
    }
}
