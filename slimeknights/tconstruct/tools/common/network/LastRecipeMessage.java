package slimeknights.tconstruct.tools.common.network;

import slimeknights.mantle.network.*;
import net.minecraft.client.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraft.client.*;
import slimeknights.tconstruct.tools.common.inventory.*;
import net.minecraft.inventory.*;
import net.minecraft.network.*;
import io.netty.buffer.*;
import net.minecraft.item.crafting.*;

public class LastRecipeMessage extends AbstractPacket
{
    private IRecipe recipe;
    
    public LastRecipeMessage() {
    }
    
    public LastRecipeMessage(final IRecipe recipe) {
        this.recipe = recipe;
    }
    
    public IMessage handleClient(final NetHandlerPlayClient netHandler) {
        final Container container = Minecraft.func_71410_x().field_71439_g.field_71070_bA;
        if (container instanceof ContainerCraftingStation) {
            ((ContainerCraftingStation)container).updateLastRecipeFromServer(this.recipe);
        }
        return null;
    }
    
    public IMessage handleServer(final NetHandlerPlayServer netHandler) {
        throw new UnsupportedOperationException("Clientside only");
    }
    
    public void fromBytes(final ByteBuf buf) {
        this.recipe = (IRecipe)CraftingManager.field_193380_a.func_148754_a(buf.readInt());
    }
    
    public void toBytes(final ByteBuf buf) {
        buf.writeInt(CraftingManager.field_193380_a.func_148757_b((Object)this.recipe));
    }
}
