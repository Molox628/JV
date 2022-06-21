package slimeknights.tconstruct.shared.client;

import slimeknights.mantle.client.model.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.*;
import slimeknights.tconstruct.library.client.model.*;
import net.minecraft.block.state.*;
import com.google.common.collect.*;
import net.minecraftforge.client.model.pipeline.*;
import java.util.*;
import javax.annotation.*;

@SideOnly(Side.CLIENT)
public class BakedColoredItemModel extends BakedWrapper
{
    public static final int MAX_SUPPORTED_TINT_INDEX = 7;
    private final ImmutableMap<EnumFacing, ImmutableList<BakedQuad>> coloredQuads;
    private final ImmutableList<BakedQuad> coloredDefaultQuads;
    
    public BakedColoredItemModel(final ItemStack itemStack, final IBakedModel parent) {
        super(parent);
        Map<EnumFacing, List<BakedQuad>> quads = null;
        boolean didColorQuads = false;
        for (int i = 0; i < 7; ++i) {
            final int color = Minecraft.func_71410_x().getItemColors().func_186728_a(itemStack, i);
            if (color != -1) {
                if (quads == null) {
                    quads = (Map<EnumFacing, List<BakedQuad>>)Maps.newHashMap();
                    for (final EnumFacing facing : ModelHelper.MODEL_SIDES) {
                        quads.put(facing, Lists.newArrayList((Iterable)parent.func_188616_a((IBlockState)null, facing, -1L)));
                    }
                }
                final float b = (color & 0xFF) / 255.0f;
                final float g = (color >>> 8 & 0xFF) / 255.0f;
                final float r = (color >>> 16 & 0xFF) / 255.0f;
                float a = (color >>> 24 & 0xFF) / 255.0f;
                if (a == 0.0f) {
                    a = 1.0f;
                }
                for (final EnumFacing facing2 : ModelHelper.MODEL_SIDES) {
                    final ListIterator<BakedQuad> iter = quads.get(facing2).listIterator();
                    while (iter.hasNext()) {
                        final BakedQuad quad = iter.next();
                        if (quad.func_178211_c() == i) {
                            final UnpackedBakedQuad.Builder quadBuilder = new UnpackedBakedQuad.Builder(quad.getFormat());
                            final LightUtil.ItemConsumer itemConsumer = new LightUtil.ItemConsumer((IVertexConsumer)quadBuilder);
                            itemConsumer.setAuxColor(new float[] { r, g, b, a });
                            quad.pipe((IVertexConsumer)itemConsumer);
                            iter.set((BakedQuad)quadBuilder.build());
                            didColorQuads = true;
                        }
                    }
                }
            }
        }
        if (didColorQuads) {
            final ImmutableMap.Builder<EnumFacing, ImmutableList<BakedQuad>> mapBuilder = (ImmutableMap.Builder<EnumFacing, ImmutableList<BakedQuad>>)ImmutableMap.builder();
            for (final EnumFacing facing3 : EnumFacing.values()) {
                mapBuilder.put((Object)facing3, (Object)ImmutableList.copyOf((Collection)quads.get(facing3)));
            }
            this.coloredQuads = (ImmutableMap<EnumFacing, ImmutableList<BakedQuad>>)mapBuilder.build();
            this.coloredDefaultQuads = (ImmutableList<BakedQuad>)ImmutableList.copyOf((Collection)quads.get(null));
        }
        else {
            this.coloredQuads = (ImmutableMap<EnumFacing, ImmutableList<BakedQuad>>)ImmutableMap.of();
            this.coloredDefaultQuads = (ImmutableList<BakedQuad>)ImmutableList.of();
        }
    }
    
    @Nonnull
    public List<BakedQuad> func_188616_a(final IBlockState state, final EnumFacing side, final long rand) {
        if (this.coloredQuads.isEmpty()) {
            return (List<BakedQuad>)super.func_188616_a(state, side, rand);
        }
        if (side == null) {
            return (List<BakedQuad>)this.coloredDefaultQuads;
        }
        return (List<BakedQuad>)this.coloredQuads.get((Object)side);
    }
}
