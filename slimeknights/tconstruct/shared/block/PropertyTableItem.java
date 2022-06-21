package slimeknights.tconstruct.shared.block;

import net.minecraftforge.common.property.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.block.model.*;

public class PropertyTableItem implements IUnlistedProperty<TableItems>
{
    public String getName() {
        return "TableItems";
    }
    
    public boolean isValid(final TableItems value) {
        return value != null && value.items != null;
    }
    
    public Class<TableItems> getType() {
        return TableItems.class;
    }
    
    public String valueToString(final TableItems value) {
        return value.toString();
    }
    
    public static class TableItems
    {
        public static final TableItems EMPTY;
        public List<TableItem> items;
        
        public TableItems() {
            this.items = (List<TableItem>)Lists.newLinkedList();
        }
        
        static {
            EMPTY = new TableItems();
            TableItems.EMPTY.items = (List<TableItem>)ImmutableList.of();
        }
    }
    
    public static class TableItem
    {
        public final ItemStack stack;
        public final IBakedModel model;
        public float x;
        public float y;
        public float z;
        public float s;
        public float r;
        
        public TableItem(final ItemStack stack, final IBakedModel model) {
            this(stack, model, 0.0f, 0.0f, 0.0f);
        }
        
        public TableItem(final ItemStack stack, final IBakedModel model, final float x, final float y, final float z) {
            this(stack, model, x, y, z, 1.0f, 0.0f);
        }
        
        public TableItem(final ItemStack stack, final IBakedModel model, final float x, final float y, final float z, final float s, final float r) {
            this.stack = stack;
            this.model = model;
            this.x = x;
            this.y = y;
            this.z = z;
            this.s = s;
            this.r = r;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            final TableItem tableItem = (TableItem)o;
            return Float.compare(tableItem.x, this.x) == 0 && Float.compare(tableItem.y, this.y) == 0 && Float.compare(tableItem.z, this.z) == 0 && Float.compare(tableItem.s, this.s) == 0 && Float.compare(tableItem.r, this.r) == 0 && ((this.stack != null) ? ItemStack.func_77989_b(this.stack, tableItem.stack) : (tableItem.stack == null));
        }
        
        @Override
        public int hashCode() {
            int result = 0;
            if (this.stack != null) {
                result = this.stack.func_77973_b().hashCode();
                result = 31 * result + this.stack.func_190916_E();
                result = 31 * result + ((this.stack.func_77978_p() != null) ? this.stack.func_77978_p().hashCode() : 0);
            }
            result = 31 * result + ((this.x != 0.0f) ? Float.floatToIntBits(this.x) : 0);
            result = 31 * result + ((this.y != 0.0f) ? Float.floatToIntBits(this.y) : 0);
            result = 31 * result + ((this.z != 0.0f) ? Float.floatToIntBits(this.z) : 0);
            result = 31 * result + ((this.s != 0.0f) ? Float.floatToIntBits(this.s) : 0);
            result = 31 * result + ((this.r != 0.0f) ? Float.floatToIntBits(this.r) : 0);
            return result;
        }
    }
}
