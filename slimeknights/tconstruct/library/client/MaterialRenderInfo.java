package slimeknights.tconstruct.library.client;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.*;
import slimeknights.tconstruct.library.client.texture.*;

@SideOnly(Side.CLIENT)
public interface MaterialRenderInfo
{
    TextureAtlasSprite getTexture(final ResourceLocation p0, final String p1);
    
    boolean isStitched();
    
    boolean useVertexColoring();
    
    int getVertexColor();
    
    String getTextureSuffix();
    
    MaterialRenderInfo setTextureSuffix(final String p0);
    
    public abstract static class AbstractMaterialRenderInfo implements MaterialRenderInfo
    {
        private String suffix;
        
        @Override
        public boolean isStitched() {
            return true;
        }
        
        @Override
        public boolean useVertexColoring() {
            return false;
        }
        
        @Override
        public int getVertexColor() {
            return -1;
        }
        
        @Override
        public String getTextureSuffix() {
            return this.suffix;
        }
        
        @Override
        public MaterialRenderInfo setTextureSuffix(final String suffix) {
            this.suffix = suffix;
            return this;
        }
    }
    
    public static class Default extends AbstractMaterialRenderInfo
    {
        public final int color;
        
        public Default(final int color) {
            this.color = color;
        }
        
        @Override
        public TextureAtlasSprite getTexture(final ResourceLocation baseTexture, final String location) {
            return TinkerTexture.loadManually(baseTexture);
        }
        
        @Override
        public boolean isStitched() {
            return false;
        }
        
        @Override
        public boolean useVertexColoring() {
            return true;
        }
        
        @Override
        public int getVertexColor() {
            return this.color;
        }
    }
    
    public static class MultiColor extends AbstractMaterialRenderInfo
    {
        protected final int low;
        protected final int mid;
        protected final int high;
        
        public MultiColor(final int low, final int mid, final int high) {
            this.low = low;
            this.mid = mid;
            this.high = high;
        }
        
        @Override
        public TextureAtlasSprite getTexture(final ResourceLocation baseTexture, final String location) {
            return new SimpleColoredTexture(this.low, this.mid, this.high, baseTexture, location);
        }
    }
    
    public static class InverseMultiColor extends MultiColor
    {
        public InverseMultiColor(final int low, final int mid, final int high) {
            super(low, mid, high);
        }
        
        @Override
        public TextureAtlasSprite getTexture(final ResourceLocation baseTexture, final String location) {
            return new InverseColoredTexture(this.low, this.mid, this.high, baseTexture, location);
        }
    }
    
    public static class Metal extends AbstractMaterialRenderInfo
    {
        protected float shinyness;
        protected float brightness;
        protected float hueshift;
        public int color;
        
        public Metal(final int color, final float shinyness, final float brightness, final float hueshift) {
            this.color = color;
            this.shinyness = shinyness;
            this.brightness = brightness;
            this.hueshift = hueshift;
        }
        
        public Metal(final int color) {
            this(color, 0.4f, 0.4f, 0.1f);
        }
        
        @Override
        public TextureAtlasSprite getTexture(final ResourceLocation baseTexture, final String location) {
            return new MetalColoredTexture(baseTexture, location, this.color, this.shinyness, this.brightness, this.hueshift);
        }
    }
    
    public static class MetalTextured extends Metal
    {
        protected ResourceLocation extraTexture;
        
        public MetalTextured(final ResourceLocation extraTexture, final int color, final float shinyness, final float brightness, final float hueshift) {
            super(color, shinyness, brightness, hueshift);
            this.extraTexture = extraTexture;
        }
        
        @Override
        public TextureAtlasSprite getTexture(final ResourceLocation baseTexture, final String location) {
            return new MetalTextureTexture(this.extraTexture, baseTexture, location, this.color, this.shinyness, this.brightness, this.hueshift);
        }
    }
    
    public static class BlockTexture extends AbstractMaterialRenderInfo
    {
        protected ResourceLocation texturePath;
        
        public BlockTexture(final ResourceLocation texturePath) {
            this.texturePath = texturePath;
        }
        
        @Override
        public TextureAtlasSprite getTexture(final ResourceLocation baseTexture, final String location) {
            TextureAtlasSprite blockTexture = Minecraft.func_71410_x().func_147117_R().getTextureExtry(this.texturePath.toString());
            if (blockTexture == null) {
                blockTexture = TinkerTexture.loadManually(this.texturePath);
            }
            final TextureColoredTexture sprite = new TextureColoredTexture(new ResourceLocation(blockTexture.func_94215_i()), baseTexture, location);
            sprite.stencil = false;
            return sprite;
        }
    }
    
    public static class AnimatedTexture extends AbstractMaterialRenderInfo
    {
        protected String texturePath;
        
        public AnimatedTexture(final String texturePath) {
            this.texturePath = texturePath;
        }
        
        @Override
        public TextureAtlasSprite getTexture(final ResourceLocation baseTexture, final String location) {
            TextureAtlasSprite blockTexture = Minecraft.func_71410_x().func_147117_R().getTextureExtry(this.texturePath);
            if (blockTexture == null) {
                blockTexture = Minecraft.func_71410_x().func_147117_R().func_174944_f();
            }
            final TextureColoredTexture sprite = new AnimatedColoredTexture(new ResourceLocation(blockTexture.func_94215_i()), baseTexture, location);
            return sprite;
        }
    }
}
