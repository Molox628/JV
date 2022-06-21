package slimeknights.tconstruct.library.client.model;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.client.*;
import slimeknights.tconstruct.shared.client.*;
import net.minecraft.util.*;
import com.google.common.base.*;
import net.minecraft.client.resources.*;
import org.apache.commons.io.*;
import net.minecraftforge.common.model.*;
import slimeknights.tconstruct.library.client.model.format.*;
import com.google.common.collect.*;
import net.minecraft.client.renderer.block.model.*;
import javax.vecmath.*;
import java.util.*;
import com.google.gson.stream.*;
import java.lang.reflect.*;
import java.io.*;
import com.google.gson.*;
import slimeknights.tconstruct.library.client.deserializer.*;

@SideOnly(Side.CLIENT)
public class ModelHelper extends slimeknights.mantle.client.ModelHelper
{
    public static final EnumFacing[] MODEL_SIDES;
    private static final Gson GSON;
    
    public static IBakedModel getBakedModelForItem(final ItemStack stack, final World world, final EntityLivingBase entity) {
        IBakedModel model = Minecraft.func_71410_x().func_175599_af().func_184393_a(stack, world, entity);
        if (model == null || model.func_188618_c()) {
            model = Minecraft.func_71410_x().func_175599_af().func_175037_a().func_178083_a().func_174951_a();
        }
        else {
            model = (IBakedModel)new BakedColoredItemModel(stack, model);
        }
        return model;
    }
    
    public static Reader getReaderForResource(final ResourceLocation location) throws IOException {
        return getReaderForResource(location, Minecraft.func_71410_x().func_110442_L());
    }
    
    public static Reader getReaderForResource(final ResourceLocation location, final IResourceManager resourceManager) throws IOException {
        final ResourceLocation file = new ResourceLocation(location.func_110624_b(), location.func_110623_a() + ".json");
        final IResource iresource = resourceManager.func_110536_a(file);
        return new BufferedReader(new InputStreamReader(iresource.func_110527_b(), Charsets.UTF_8));
    }
    
    public static Map<String, String> loadTexturesFromJson(final ResourceLocation location) throws IOException {
        final Reader reader = getReaderForResource(location);
        try {
            return (Map<String, String>)ModelHelper.GSON.fromJson(reader, ModelTextureDeserializer.TYPE);
        }
        finally {
            IOUtils.closeQuietly(reader);
        }
    }
    
    public static Offset loadOffsetFromJson(final ResourceLocation location) throws IOException {
        final Reader reader = getReaderForResource(location);
        try {
            return (Offset)ModelHelper.GSON.fromJson(reader, Offset.OffsetDeserializer.TYPE);
        }
        finally {
            IOUtils.closeQuietly(reader);
        }
    }
    
    public static AmmoPosition loadAmmoPositionFromJson(final ResourceLocation location) throws IOException {
        final Reader reader = getReaderForResource(location);
        try {
            return (AmmoPosition)ModelHelper.GSON.fromJson(reader, AmmoPosition.AmmoPositionDeserializer.TYPE);
        }
        finally {
            IOUtils.closeQuietly(reader);
        }
    }
    
    public static ImmutableList<ToolModelOverride> loadToolModelOverridesFromJson(final ResourceLocation location) throws IOException {
        final Reader reader = getReaderForResource(location);
        try {
            return (ImmutableList<ToolModelOverride>)ModelHelper.GSON.fromJson(reader, ToolModelOverride.ToolModelOverrideListDeserializer.TYPE);
        }
        finally {
            IOUtils.closeQuietly(reader);
        }
    }
    
    public static ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> loadTransformFromJson(final ResourceLocation location) throws IOException {
        return loadTransformFromJson(location, "display");
    }
    
    public static ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> loadTransformFromJson(final ResourceLocation location, final String tag) throws IOException {
        final Reader reader = getReaderForResource(location);
        try {
            TransformDeserializer.tag = tag;
            final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms = (ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation>)ModelHelper.GSON.fromJson(reader, TransformDeserializer.TYPE);
            final ImmutableMap.Builder<ItemCameraTransforms.TransformType, TRSRTransformation> builder = (ImmutableMap.Builder<ItemCameraTransforms.TransformType, TRSRTransformation>)ImmutableMap.builder();
            for (final Map.Entry<ItemCameraTransforms.TransformType, TRSRTransformation> entry : transforms.entrySet()) {
                if (!entry.getValue().equals((Object)TRSRTransformation.identity())) {
                    builder.put((Object)entry.getKey(), (Object)entry.getValue());
                }
            }
            return (ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation>)builder.build();
        }
        finally {
            IOUtils.closeQuietly(reader);
        }
    }
    
    public static ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> loadTransformFromJsonBackup(final ResourceLocation location) throws IOException {
        final Reader reader = getReaderForResource(location);
        try {
            final ModelBlock modelBlock = ModelBlock.func_178307_a(reader);
            final ItemCameraTransforms itemCameraTransforms = modelBlock.func_181682_g();
            final ImmutableMap.Builder<ItemCameraTransforms.TransformType, TRSRTransformation> builder = (ImmutableMap.Builder<ItemCameraTransforms.TransformType, TRSRTransformation>)ImmutableMap.builder();
            for (final ItemCameraTransforms.TransformType type : ItemCameraTransforms.TransformType.values()) {
                if (itemCameraTransforms.func_181688_b(type) != ItemTransformVec3f.field_178366_a) {
                    builder.put((Object)type, (Object)new TRSRTransformation(itemCameraTransforms.func_181688_b(type)));
                }
            }
            return (ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation>)builder.build();
        }
        finally {
            IOUtils.closeQuietly(reader);
        }
    }
    
    public static ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> getTransforms(final IBakedModel model) {
        final ImmutableMap.Builder<ItemCameraTransforms.TransformType, TRSRTransformation> builder = (ImmutableMap.Builder<ItemCameraTransforms.TransformType, TRSRTransformation>)ImmutableMap.builder();
        for (final ItemCameraTransforms.TransformType type : ItemCameraTransforms.TransformType.values()) {
            final TRSRTransformation transformation = new TRSRTransformation((Matrix4f)model.handlePerspective(type).getRight());
            if (!transformation.equals((Object)TRSRTransformation.identity())) {
                builder.put((Object)type, (Object)TRSRTransformation.blockCenterToCorner(transformation));
            }
        }
        return (ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation>)builder.build();
    }
    
    public static ImmutableList<ResourceLocation> loadTextureListFromJson(final ResourceLocation location) throws IOException {
        final ImmutableList.Builder<ResourceLocation> builder = (ImmutableList.Builder<ResourceLocation>)ImmutableList.builder();
        for (final String s : loadTexturesFromJson(location).values()) {
            builder.add((Object)new ResourceLocation(s));
        }
        return (ImmutableList<ResourceLocation>)builder.build();
    }
    
    public static Float[] loadLayerRotations(final ResourceLocation location) throws IOException {
        final JsonReader reader = new JsonReader(getReaderForResource(location));
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                if ("layerrotation".equals(reader.nextName())) {
                    return (Float[])ModelHelper.GSON.fromJson(reader, (Type)Float[].class);
                }
                reader.skipValue();
            }
        }
        finally {
            IOUtils.closeQuietly((Closeable)reader);
        }
        return new Float[0];
    }
    
    public static ResourceLocation getModelLocation(final ResourceLocation location) {
        return new ResourceLocation(location.func_110624_b(), "models/" + location.func_110623_a() + ".json");
    }
    
    static {
        MODEL_SIDES = new EnumFacing[] { null, EnumFacing.DOWN, EnumFacing.UP, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.EAST };
        GSON = new GsonBuilder().registerTypeAdapter(ModelTextureDeserializer.TYPE, (Object)ModelTextureDeserializer.INSTANCE).registerTypeAdapter(Offset.OffsetDeserializer.TYPE, (Object)Offset.OffsetDeserializer.INSTANCE).registerTypeAdapter(TransformDeserializer.TYPE, (Object)TransformDeserializer.INSTANCE).registerTypeAdapter((Type)ItemCameraTransforms.class, (Object)ItemCameraTransformsDeserializer.INSTANCE).registerTypeAdapter((Type)ItemTransformVec3f.class, (Object)ItemTransformVec3fDeserializer.INSTANCE).registerTypeAdapter(ToolModelOverride.ToolModelOverrideListDeserializer.TYPE, (Object)ToolModelOverride.ToolModelOverrideListDeserializer.INSTANCE).registerTypeAdapter(AmmoPosition.AmmoPositionDeserializer.TYPE, (Object)AmmoPosition.AmmoPositionDeserializer.INSTANCE).create();
    }
}
