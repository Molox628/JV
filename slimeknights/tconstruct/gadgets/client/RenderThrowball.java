package slimeknights.tconstruct.gadgets.client;

import slimeknights.tconstruct.gadgets.entity.*;
import net.minecraftforge.fml.client.registry.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;
import javax.annotation.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.entity.*;
import slimeknights.tconstruct.gadgets.*;
import net.minecraft.client.*;

public class RenderThrowball extends RenderSnowball<EntityThrowball>
{
    public static final IRenderFactory<EntityThrowball> FACTORY;
    
    public RenderThrowball(final RenderManager renderManagerIn, final Item p_i46137_2_, final RenderItem p_i46137_3_) {
        super(renderManagerIn, p_i46137_2_, p_i46137_3_);
    }
    
    @Nonnull
    public ItemStack getStackToRender(final EntityThrowball entityIn) {
        if (entityIn.type != null) {
            return new ItemStack(this.field_177084_a, 1, entityIn.type.ordinal());
        }
        return ItemStack.field_190927_a;
    }
    
    static {
        FACTORY = (IRenderFactory)new Factory();
    }
    
    private static class Factory implements IRenderFactory<EntityThrowball>
    {
        public Render<? super EntityThrowball> createRenderFor(final RenderManager manager) {
            return (Render<? super EntityThrowball>)new RenderThrowball(manager, (Item)TinkerGadgets.throwball, Minecraft.func_71410_x().func_175599_af());
        }
    }
}
