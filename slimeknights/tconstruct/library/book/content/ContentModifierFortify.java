package slimeknights.tconstruct.library.book.content;

import net.minecraftforge.fml.relauncher.*;
import slimeknights.tconstruct.library.modifiers.*;
import net.minecraft.item.*;
import slimeknights.mantle.util.*;
import slimeknights.tconstruct.tools.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.materials.*;

@SideOnly(Side.CLIENT)
public class ContentModifierFortify extends ContentModifier
{
    public static final transient String ID = "modifier_fortify";
    
    public ContentModifierFortify() {
    }
    
    public ContentModifierFortify(final IModifier modifier) {
        super(modifier);
    }
    
    @Override
    protected ItemStackList getDemoTools(final ItemStack[][] inputItems) {
        if (inputItems.length == 0) {
            return ItemStackList.create();
        }
        final ItemStackList demo = super.getDemoTools(inputItems);
        final ItemStackList out = ItemStackList.create();
        for (int i = 0; i < inputItems[0].length; ++i) {
            if (inputItems[0][i].func_77973_b() == TinkerTools.sharpeningKit) {
                final Material material = TinkerTools.sharpeningKit.getMaterial(inputItems[0][i]);
                final IModifier modifier = TinkerRegistry.getModifier("fortify" + material.getIdentifier());
                if (modifier != null) {
                    final ItemStack stack = ((ItemStack)demo.get(i % demo.size())).func_77946_l();
                    modifier.apply(stack);
                    out.add((Object)stack);
                }
            }
        }
        return out;
    }
}
