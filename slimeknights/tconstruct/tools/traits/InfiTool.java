package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraft.nbt.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.tconstruct.library.tools.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;

public class InfiTool extends AbstractTrait
{
    public static InfiTool INSTANCE;
    
    public InfiTool() {
        super("infitool", 16777215);
    }
    
    @Override
    public boolean isHidden() {
        return true;
    }
    
    @Override
    public void applyEffect(final NBTTagCompound rootCompound, final NBTTagCompound modifierTag) {
        super.applyEffect(rootCompound, modifierTag);
        final ToolNBT stats = TagUtil.getToolStats(rootCompound);
        stats.durability = 999999;
        stats.attack = 999999.0f;
        stats.speed = 999999.0f;
        stats.modifiers = 0;
        TagUtil.setToolTag(rootCompound, stats.get());
        TagUtil.setNoRenameFlag(rootCompound, true);
    }
    
    @Override
    public int onToolDamage(final ItemStack tool, final int damage, final int newDamage, final EntityLivingBase entity) {
        return 0;
    }
    
    static {
        InfiTool.INSTANCE = new InfiTool();
    }
}
