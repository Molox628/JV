package slimeknights.tconstruct.library.client.deserializer;

import net.minecraft.client.renderer.block.model.*;
import net.minecraftforge.fml.relauncher.*;
import org.lwjgl.util.vector.*;
import java.lang.reflect.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import com.google.gson.*;

@SideOnly(Side.CLIENT)
public class ItemTransformVec3fDeserializer implements JsonDeserializer<ItemTransformVec3f>
{
    public static final ItemTransformVec3fDeserializer INSTANCE;
    private static final Vector3f ROTATION_DEFAULT;
    private static final Vector3f TRANSLATION_DEFAULT;
    private static final Vector3f SCALE_DEFAULT;
    
    public ItemTransformVec3f deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
        final JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
        final Vector3f vector3f = this.parseVector3f(jsonobject, "rotation", ItemTransformVec3fDeserializer.ROTATION_DEFAULT);
        final Vector3f vector3f2 = this.parseVector3f(jsonobject, "translation", ItemTransformVec3fDeserializer.TRANSLATION_DEFAULT);
        vector3f2.scale(0.0625f);
        vector3f2.x = MathHelper.func_76131_a(vector3f2.x, -5.0f, 5.0f);
        vector3f2.y = MathHelper.func_76131_a(vector3f2.y, -5.0f, 5.0f);
        vector3f2.z = MathHelper.func_76131_a(vector3f2.z, -5.0f, 5.0f);
        final Vector3f vector3f3 = this.parseVector3f(jsonobject, "scale", ItemTransformVec3fDeserializer.SCALE_DEFAULT);
        vector3f3.x = MathHelper.func_76131_a(vector3f3.x, -4.0f, 4.0f);
        vector3f3.y = MathHelper.func_76131_a(vector3f3.y, -4.0f, 4.0f);
        vector3f3.z = MathHelper.func_76131_a(vector3f3.z, -4.0f, 4.0f);
        return new ItemTransformVec3f(vector3f, vector3f2, vector3f3);
    }
    
    private Vector3f parseVector3f(final JsonObject jsonObject, final String key, final Vector3f defaultValue) {
        if (!jsonObject.has(key)) {
            return defaultValue;
        }
        final JsonArray jsonarray = JsonUtils.func_151214_t(jsonObject, key);
        if (jsonarray.size() != 3) {
            throw new JsonParseException("Expected 3 " + key + " values, found: " + jsonarray.size());
        }
        final float[] afloat = new float[3];
        for (int i = 0; i < afloat.length; ++i) {
            afloat[i] = JsonUtils.func_151220_d(jsonarray.get(i), key + "[" + i + "]");
        }
        return new Vector3f(afloat[0], afloat[1], afloat[2]);
    }
    
    static {
        INSTANCE = new ItemTransformVec3fDeserializer();
        ROTATION_DEFAULT = new Vector3f(0.0f, 0.0f, 0.0f);
        TRANSLATION_DEFAULT = new Vector3f(0.0f, 0.0f, 0.0f);
        SCALE_DEFAULT = new Vector3f(1.0f, 1.0f, 1.0f);
    }
}
