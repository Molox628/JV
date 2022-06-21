package slimeknights.tconstruct.library.utils;

import net.minecraft.item.*;
import slimeknights.tconstruct.library.modifiers.*;
import net.minecraft.nbt.*;

public class ModifierTagHolder
{
    private final ItemStack itemStack;
    private final NBTTagList tagList;
    private final int index;
    public final NBTTagCompound tag;
    private ModifierNBT modifierNBT;
    
    private ModifierTagHolder(final ItemStack itemStack, final String modifier) {
        this.itemStack = itemStack;
        this.tagList = TagUtil.getModifiersTagList(itemStack);
        this.index = TinkerUtil.getIndexInCompoundList(this.tagList, modifier);
        this.tag = this.tagList.func_150305_b(this.index);
    }
    
    public <T extends ModifierNBT> T getTagData(final Class<T> clazz) {
        final T data = ModifierNBT.readTag(this.tag, clazz);
        return (T)(this.modifierNBT = data);
    }
    
    public void save() {
        if (this.modifierNBT != null) {
            this.modifierNBT.write(this.tag);
        }
        this.tagList.func_150304_a(this.index, (NBTBase)this.tag);
        TagUtil.setModifiersTagList(this.itemStack, this.tagList);
    }
    
    public static ModifierTagHolder getModifier(final ItemStack itemStack, final String modifier) {
        return new ModifierTagHolder(itemStack, modifier);
    }
}
