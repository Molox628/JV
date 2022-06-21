package slimeknights.tconstruct.common.config;

import slimeknights.mantle.network.*;
import com.google.common.collect.*;
import net.minecraft.client.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraft.network.*;
import io.netty.buffer.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.common.config.*;
import java.util.*;

public class ConfigSyncPacket extends AbstractPacket
{
    public List<ConfigCategory> categories;
    
    public ConfigSyncPacket() {
        this.categories = (List<ConfigCategory>)Lists.newLinkedList();
    }
    
    public IMessage handleClient(final NetHandlerPlayClient netHandler) {
        ConfigSync.syncConfig(this.categories);
        return null;
    }
    
    public IMessage handleServer(final NetHandlerPlayServer netHandler) {
        return null;
    }
    
    public void fromBytes(final ByteBuf buf) {
        for (short categoryCount = buf.readShort(), i = 0; i < categoryCount; ++i) {
            final int propCount = buf.readInt();
            final String categoryName = ByteBufUtils.readUTF8String(buf);
            final ConfigCategory category = new ConfigCategory(categoryName);
            this.categories.add(category);
            for (int j = 0; j < propCount; ++j) {
                final String name = ByteBufUtils.readUTF8String(buf);
                final char type = buf.readChar();
                final String value = ByteBufUtils.readUTF8String(buf);
                category.put(name, new Property(name, value, Property.Type.tryParse(type)));
            }
        }
    }
    
    public void toBytes(final ByteBuf buf) {
        buf.writeShort(this.categories.size());
        for (final ConfigCategory category : this.categories) {
            buf.writeInt(category.values().size());
            ByteBufUtils.writeUTF8String(buf, category.getName());
            for (final Property prop : category.values()) {
                ByteBufUtils.writeUTF8String(buf, prop.getName());
                buf.writeChar((int)prop.getType().getID());
                ByteBufUtils.writeUTF8String(buf, prop.getString());
            }
        }
    }
}
