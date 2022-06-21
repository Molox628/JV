package slimeknights.tconstruct.world.client;

import net.minecraft.client.renderer.block.statemap.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.block.state.*;
import javax.annotation.*;
import net.minecraft.client.renderer.block.model.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.block.properties.*;

@SideOnly(Side.CLIENT)
public class CustomStateMap extends StateMapperBase
{
    private final String customName;
    
    public CustomStateMap(final String customName) {
        this.customName = customName;
    }
    
    @Nonnull
    protected ModelResourceLocation func_178132_a(@Nonnull final IBlockState state) {
        final LinkedHashMap<IProperty<?>, Comparable<?>> linkedhashmap = (LinkedHashMap<IProperty<?>, Comparable<?>>)Maps.newLinkedHashMap((Map)state.func_177228_b());
        final ResourceLocation res = new ResourceLocation(((ResourceLocation)Block.field_149771_c.func_177774_c((Object)state.func_177230_c())).func_110624_b(), this.customName);
        return new ModelResourceLocation(res, this.func_178131_a((Map)linkedhashmap));
    }
}
