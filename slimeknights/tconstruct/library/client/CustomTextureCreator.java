package slimeknights.tconstruct.library.client;

import org.apache.logging.log4j.*;
import net.minecraft.util.*;
import net.minecraftforge.client.event.*;
import slimeknights.tconstruct.library.client.material.*;
import net.minecraft.client.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.renderer.texture.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.library.materials.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.library.client.model.*;
import java.lang.reflect.*;
import net.minecraftforge.client.model.*;
import java.io.*;
import net.minecraft.client.resources.*;
import javax.annotation.*;
import java.util.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.client.texture.*;

public class CustomTextureCreator implements IResourceManagerReloadListener
{
    public static final CustomTextureCreator INSTANCE;
    private static Logger log;
    public static Map<String, Map<String, TextureAtlasSprite>> sprites;
    private static Set<ResourceLocation> baseTextures;
    private static Map<ResourceLocation, Set<IToolPart>> texturePartMapping;
    public static ResourceLocation patternModelLocation;
    public static ResourceLocation castModelLocation;
    public static String patternLocString;
    public static String castLocString;
    public static final Material guiMaterial;
    private int createdTextures;
    
    public static void registerTextures(final Collection<ResourceLocation> textures) {
        CustomTextureCreator.baseTextures.addAll(textures);
    }
    
    public static void registerTexture(final ResourceLocation texture) {
        CustomTextureCreator.baseTextures.add(texture);
    }
    
    public static void registerTextureForPart(final ResourceLocation texture, final IToolPart toolPart) {
        if (!CustomTextureCreator.texturePartMapping.containsKey(texture)) {
            CustomTextureCreator.texturePartMapping.put(texture, Sets.newHashSet());
        }
        CustomTextureCreator.texturePartMapping.get(texture).add(toolPart);
        registerTexture(texture);
    }
    
    @SubscribeEvent(priority = EventPriority.LOW)
    public void createCustomTextures(final TextureStitchEvent.Pre event) {
        MaterialRenderInfoLoader.INSTANCE.func_110549_a(Minecraft.func_71410_x().func_110442_L());
        this.createdTextures = 0;
        this.createMaterialTextures(event.getMap());
        this.createPatterntextures(event.getMap());
        CustomTextureCreator.log.debug("Generated " + this.createdTextures + " Textures for Materials");
    }
    
    private void createMaterialTextures(final TextureMap map) {
        for (final ResourceLocation baseTexture : CustomTextureCreator.baseTextures) {
            if (baseTexture.toString().equals("minecraft:missingno")) {
                continue;
            }
            final Set<IToolPart> parts = CustomTextureCreator.texturePartMapping.get(baseTexture);
            final Map<String, TextureAtlasSprite> builtSprites = (Map<String, TextureAtlasSprite>)Maps.newHashMap();
            for (final Material material : TinkerRegistry.getAllMaterials()) {
                boolean usable;
                if (parts == null || material instanceof MaterialGUI) {
                    usable = true;
                }
                else {
                    usable = false;
                    for (final IToolPart toolPart : parts) {
                        usable |= toolPart.canUseMaterialForRendering(material);
                    }
                }
                if (usable) {
                    final TextureAtlasSprite sprite = this.createTexture(material, baseTexture, map);
                    if (sprite == null) {
                        continue;
                    }
                    builtSprites.put(material.identifier, sprite);
                }
            }
            if (belongsToToolPart(baseTexture)) {
                final TextureAtlasSprite sprite2 = this.createTexture(CustomTextureCreator.guiMaterial, baseTexture, map);
                if (sprite2 != null) {
                    builtSprites.put(CustomTextureCreator.guiMaterial.identifier, sprite2);
                }
            }
            CustomTextureCreator.sprites.put(baseTexture.toString(), builtSprites);
        }
    }
    
    private TextureAtlasSprite createTexture(final Material material, ResourceLocation baseTexture, final TextureMap map) {
        final String location = baseTexture.toString() + "_" + material.identifier;
        TextureAtlasSprite sprite;
        if (exists(location)) {
            sprite = map.func_174942_a(new ResourceLocation(location));
        }
        else {
            if (material.renderInfo == null) {
                return null;
            }
            if (material.renderInfo.getTextureSuffix() != null) {
                final String loc2 = baseTexture.toString() + "_" + material.renderInfo.getTextureSuffix();
                TextureAtlasSprite base2 = map.getTextureExtry(loc2);
                if (base2 == null && exists(loc2)) {
                    base2 = TinkerTexture.loadManually(new ResourceLocation(loc2));
                    map.setTextureEntry(base2);
                }
                if (base2 != null) {
                    baseTexture = new ResourceLocation(base2.func_94215_i());
                }
            }
            sprite = material.renderInfo.getTexture(baseTexture, location);
            ++this.createdTextures;
        }
        if (sprite != null && material.renderInfo.isStitched()) {
            map.setTextureEntry(sprite);
        }
        return sprite;
    }
    
    private void createPatterntextures(final TextureMap map) {
        if (CustomTextureCreator.patternModelLocation != null) {
            CustomTextureCreator.patternLocString = this.createPatternTexturesFor(map, CustomTextureCreator.patternModelLocation, TinkerRegistry.getPatternItems(), PatternTexture.class);
        }
        if (CustomTextureCreator.castModelLocation != null) {
            CustomTextureCreator.castLocString = this.createPatternTexturesFor(map, CustomTextureCreator.castModelLocation, TinkerRegistry.getCastItems(), CastTexture.class);
        }
    }
    
    public String createPatternTexturesFor(final TextureMap map, final ResourceLocation baseTextureLoc, final Iterable<Item> items, final Class<? extends TextureColoredTexture> clazz) {
        Constructor<? extends TextureColoredTexture> constructor;
        ResourceLocation patternLocation;
        String baseTextureString;
        try {
            constructor = clazz.getConstructor(ResourceLocation.class, ResourceLocation.class, String.class);
            final IModel patternModel = ModelLoaderRegistry.getModel(baseTextureLoc);
            patternLocation = patternModel.getTextures().iterator().next();
            baseTextureString = patternLocation.toString();
        }
        catch (Exception e) {
            CustomTextureCreator.log.error((Object)e);
            return null;
        }
        for (final Item item : items) {
            try {
                final String identifier = Pattern.getTextureIdentifier(item);
                final String partPatternLocation = baseTextureString + identifier;
                if (exists(partPatternLocation)) {
                    final TextureAtlasSprite partPatternTexture = map.func_174942_a(new ResourceLocation(partPatternLocation));
                    map.setTextureEntry(partPatternTexture);
                }
                else {
                    final ResourceLocation modelLocation = item.getRegistryName();
                    final IModel partModel = ModelLoaderRegistry.getModel(new ResourceLocation(modelLocation.func_110624_b(), "item/parts/" + modelLocation.func_110623_a() + MaterialModelLoader.EXTENSION));
                    final ResourceLocation partTexture = partModel.getTextures().iterator().next();
                    if (partModel == ModelLoaderRegistry.getMissingModel()) {
                        continue;
                    }
                    final TextureAtlasSprite partPatternTexture = (TextureAtlasSprite)constructor.newInstance(partTexture, patternLocation, partPatternLocation);
                    if (partModel instanceof IPatternOffset) {
                        final IPatternOffset offset = (IPatternOffset)partModel;
                        ((TextureColoredTexture)partPatternTexture).setOffset(offset.getXOffset(), offset.getYOffset());
                    }
                    map.setTextureEntry(partPatternTexture);
                }
            }
            catch (Exception e2) {
                CustomTextureCreator.log.error((Object)e2);
            }
        }
        return baseTextureString;
    }
    
    public static boolean exists(final String res) {
        try {
            ResourceLocation loc = new ResourceLocation(res);
            loc = new ResourceLocation(loc.func_110624_b(), "textures/" + loc.func_110623_a() + ".png");
            Minecraft.func_71410_x().func_110442_L().func_135056_b(loc);
            return true;
        }
        catch (IOException e) {
            return false;
        }
    }
    
    public void func_110549_a(@Nonnull final IResourceManager resourceManager) {
        CustomTextureCreator.baseTextures.clear();
        for (final Map map : CustomTextureCreator.sprites.values()) {
            map.clear();
        }
        CustomTextureCreator.sprites.clear();
    }
    
    public static boolean belongsToToolPart(final ResourceLocation location) {
        for (final IToolPart toolpart : TinkerRegistry.getToolParts()) {
            if (!(toolpart instanceof Item)) {
                continue;
            }
            try {
                final Optional<ResourceLocation> storedResourceLocation = MaterialModelLoader.getToolPartModelLocation(toolpart);
                if (!storedResourceLocation.isPresent()) {
                    continue;
                }
                final ResourceLocation stored = storedResourceLocation.get();
                final ResourceLocation modelLocation = new ResourceLocation(stored.func_110624_b(), "item/" + stored.func_110623_a());
                final IModel partModel = ModelLoaderRegistry.getModel(modelLocation);
                final ResourceLocation baseTexture = partModel.getTextures().iterator().next();
                if (baseTexture.toString().equals(location.toString())) {
                    return true;
                }
                continue;
            }
            catch (Exception e) {
                return false;
            }
        }
        return false;
    }
    
    static {
        INSTANCE = new CustomTextureCreator();
        CustomTextureCreator.log = Util.getLogger("TextureGen");
        CustomTextureCreator.sprites = (Map<String, Map<String, TextureAtlasSprite>>)Maps.newHashMap();
        CustomTextureCreator.baseTextures = (Set<ResourceLocation>)Sets.newHashSet();
        CustomTextureCreator.texturePartMapping = (Map<ResourceLocation, Set<IToolPart>>)Maps.newHashMap();
        (guiMaterial = new MaterialGUI("_internal_gui")).setRenderInfo(new MaterialRenderInfo.AbstractMaterialRenderInfo() {
            @Override
            public TextureAtlasSprite getTexture(final ResourceLocation baseTexture, final String location) {
                return new GuiOutlineTexture(baseTexture, location);
            }
        });
    }
}
