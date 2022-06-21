package slimeknights.tconstruct.gadgets;

import java.util.*;
import net.minecraft.world.storage.loot.functions.*;
import net.minecraft.item.*;
import net.minecraft.world.storage.loot.conditions.*;
import net.minecraftforge.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.google.common.collect.*;
import net.minecraft.world.storage.loot.*;

public class GadgetEvents
{
    private static final Set<String> SPAGHETTI_LOCATIONS;
    private final LootPool pool;
    
    public GadgetEvents() {
        final LootEntry entry = (LootEntry)new LootEntryItem((Item)TinkerGadgets.spaghetti, 1, 1, new LootFunction[0], new LootCondition[0], "moms_spaghetti");
        final LootCondition chance = (LootCondition)new RandomChance(0.05f);
        this.pool = new LootPool(new LootEntry[] { entry }, new LootCondition[] { chance }, new RandomValueRange(1.0f), new RandomValueRange(0.0f), "moms_spaghetti");
    }
    
    @SubscribeEvent
    public void onLootTableLoad(final LootTableLoadEvent event) {
        if (GadgetEvents.SPAGHETTI_LOCATIONS.contains(event.getName().toString())) {
            event.getTable().addPool(this.pool);
        }
    }
    
    static {
        SPAGHETTI_LOCATIONS = (Set)ImmutableSet.builder().add((Object)LootTableList.field_186429_k.toString()).add((Object)LootTableList.field_186427_i.toString()).add((Object)LootTableList.field_186430_l.toString()).build();
    }
}
