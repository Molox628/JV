package slimeknights.tconstruct.library.client.deserializer;

import net.minecraftforge.fml.relauncher.*;
import java.lang.reflect.*;
import net.minecraft.client.renderer.block.model.*;
import com.google.gson.*;

@SideOnly(Side.CLIENT)
public class ItemCameraTransformsDeserializer implements JsonDeserializer<ItemCameraTransforms>
{
    public static final ItemCameraTransformsDeserializer INSTANCE;
    
    public ItemCameraTransforms deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
        final JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
        final ItemTransformVec3f itemtransformvec3f = this.func_181683_a(p_deserialize_3_, jsonobject, "thirdperson_righthand");
        ItemTransformVec3f itemtransformvec3f2 = this.func_181683_a(p_deserialize_3_, jsonobject, "thirdperson_lefthand");
        if (itemtransformvec3f2 == ItemTransformVec3f.field_178366_a) {
            itemtransformvec3f2 = itemtransformvec3f;
        }
        final ItemTransformVec3f itemtransformvec3f3 = this.func_181683_a(p_deserialize_3_, jsonobject, "firstperson_righthand");
        ItemTransformVec3f itemtransformvec3f4 = this.func_181683_a(p_deserialize_3_, jsonobject, "firstperson_lefthand");
        if (itemtransformvec3f4 == ItemTransformVec3f.field_178366_a) {
            itemtransformvec3f4 = itemtransformvec3f3;
        }
        final ItemTransformVec3f itemtransformvec3f5 = this.func_181683_a(p_deserialize_3_, jsonobject, "head");
        final ItemTransformVec3f itemtransformvec3f6 = this.func_181683_a(p_deserialize_3_, jsonobject, "gui");
        final ItemTransformVec3f itemtransformvec3f7 = this.func_181683_a(p_deserialize_3_, jsonobject, "ground");
        final ItemTransformVec3f itemtransformvec3f8 = this.func_181683_a(p_deserialize_3_, jsonobject, "fixed");
        return new ItemCameraTransforms(itemtransformvec3f2, itemtransformvec3f, itemtransformvec3f4, itemtransformvec3f3, itemtransformvec3f5, itemtransformvec3f6, itemtransformvec3f7, itemtransformvec3f8);
    }
    
    private ItemTransformVec3f func_181683_a(final JsonDeserializationContext p_181683_1_, final JsonObject p_181683_2_, final String p_181683_3_) {
        return (ItemTransformVec3f)(p_181683_2_.has(p_181683_3_) ? p_181683_1_.deserialize(p_181683_2_.get(p_181683_3_), (Type)ItemTransformVec3f.class) : ItemTransformVec3f.field_178366_a);
    }
    
    static {
        INSTANCE = new ItemCameraTransformsDeserializer();
    }
}
