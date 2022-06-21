package slimeknights.tconstruct.library.client.model;

import net.minecraft.client.renderer.block.statemap.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import javax.annotation.*;
import net.minecraft.client.renderer.block.model.*;
import com.google.common.collect.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import java.util.*;

public class PropertyStateMapper extends StateMapperBase
{
    private final PropertyEnum<?> prop;
    private final IProperty<?>[] ignore;
    private String name;
    
    public PropertyStateMapper(final String name, final PropertyEnum<?> prop, final IProperty<?>... ignore) {
        this.name = name + "_";
        this.prop = prop;
        this.ignore = ignore;
    }
    
    @Nonnull
    protected ModelResourceLocation func_178132_a(@Nonnull final IBlockState state) {
        final LinkedHashMap<IProperty<?>, Comparable<?>> map = (LinkedHashMap<IProperty<?>, Comparable<?>>)Maps.newLinkedHashMap((Map)state.func_177228_b());
        map.remove(this.prop);
        for (final IProperty<?> ignored : this.ignore) {
            map.remove(ignored);
        }
        final ResourceLocation res = new ResourceLocation(((ResourceLocation)Block.field_149771_c.func_177774_c((Object)state.func_177230_c())).func_110624_b(), this.name + ((IStringSerializable)state.func_177229_b((IProperty)this.prop)).func_176610_l());
        return new ModelResourceLocation(res, this.func_178131_a((Map)map));
    }
}
