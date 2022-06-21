package slimeknights.tconstruct.library.modifiers;

import net.minecraft.util.text.*;
import net.minecraft.nbt.*;
import slimeknights.tconstruct.library.client.*;
import slimeknights.tconstruct.library.*;

public class ModifierNBT
{
    public String identifier;
    public int color;
    public int level;
    public String extraInfo;
    
    public ModifierNBT() {
        this.identifier = "";
        this.color = 16777215;
        this.level = 0;
    }
    
    public ModifierNBT(final IModifier modifier) {
        this.identifier = modifier.getIdentifier();
        this.level = 0;
        this.color = Util.enumChatFormattingToColor(TextFormatting.GRAY);
    }
    
    public ModifierNBT(final NBTTagCompound tag) {
        this();
        this.read(tag);
    }
    
    public static ModifierNBT readTag(final NBTTagCompound tag) {
        final ModifierNBT data = new ModifierNBT();
        if (tag != null) {
            data.read(tag);
        }
        return data;
    }
    
    public void read(final NBTTagCompound tag) {
        this.identifier = tag.func_74779_i("identifier");
        this.color = tag.func_74762_e("color");
        this.level = tag.func_74762_e("level");
        this.extraInfo = tag.func_74779_i("extraInfo");
    }
    
    public void write(final NBTTagCompound tag) {
        tag.func_74778_a("identifier", this.identifier);
        tag.func_74768_a("color", this.color);
        if (this.level > 0) {
            tag.func_74768_a("level", this.level);
        }
        else {
            tag.func_82580_o("level");
        }
        if (this.extraInfo != null && !this.extraInfo.isEmpty()) {
            tag.func_74778_a("extraInfo", this.extraInfo);
        }
    }
    
    public String getColorString() {
        return CustomFontColor.encodeColor(this.color);
    }
    
    public static <T extends ModifierNBT> T readTag(final NBTTagCompound tag, final Class<T> clazz) {
        try {
            final T data = clazz.newInstance();
            data.read(tag);
            return data;
        }
        catch (ReflectiveOperationException e) {
            TinkerRegistry.log.error((Object)e);
            return null;
        }
    }
    
    public static IntegerNBT readInteger(final NBTTagCompound tag) {
        return readTag(tag, IntegerNBT.class);
    }
    
    public static BooleanNBT readBoolean(final NBTTagCompound tag) {
        return readTag(tag, BooleanNBT.class);
    }
    
    public static class BooleanNBT extends ModifierNBT
    {
        public boolean status;
        
        public BooleanNBT() {
        }
        
        public BooleanNBT(final IModifier modifier, final boolean status) {
            super(modifier);
            this.status = status;
        }
        
        @Override
        public void write(final NBTTagCompound tag) {
            super.write(tag);
            tag.func_74757_a("status", this.status);
        }
        
        @Override
        public void read(final NBTTagCompound tag) {
            super.read(tag);
            this.status = tag.func_74767_n("status");
        }
    }
    
    public static class IntegerNBT extends ModifierNBT
    {
        public int current;
        public int max;
        
        public IntegerNBT() {
        }
        
        public IntegerNBT(final IModifier modifier, final int current, final int max) {
            super(modifier);
            this.current = current;
            this.max = max;
            this.extraInfo = this.calcInfo();
        }
        
        @Override
        public void write(final NBTTagCompound tag) {
            this.calcInfo();
            super.write(tag);
            tag.func_74768_a("current", this.current);
            tag.func_74768_a("max", this.max);
        }
        
        @Override
        public void read(final NBTTagCompound tag) {
            super.read(tag);
            this.current = tag.func_74762_e("current");
            this.max = tag.func_74762_e("max");
            this.extraInfo = this.calcInfo();
        }
        
        public String calcInfo() {
            if (this.max > 0) {
                return String.format("%d / %d", this.current, this.max);
            }
            return (this.current > 0) ? String.valueOf(this.current) : "";
        }
    }
}
