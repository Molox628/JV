package slimeknights.tconstruct.world;

import net.minecraft.world.biome.*;
import net.minecraft.entity.monster.*;
import slimeknights.tconstruct.world.entity.*;
import net.minecraftforge.event.world.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.world.worldgen.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class WorldEvents
{
    Biome.SpawnListEntry magmaSlimeSpawn;
    Biome.SpawnListEntry blueSlimeSpawn;
    
    public WorldEvents() {
        this.magmaSlimeSpawn = new Biome.SpawnListEntry((Class)EntityMagmaCube.class, 150, 4, 6);
        this.blueSlimeSpawn = new Biome.SpawnListEntry((Class)EntityBlueSlime.class, 15, 2, 4);
    }
    
    @SubscribeEvent
    public void extraSlimeSpawn(final WorldEvent.PotentialSpawns event) {
        if (event.getType() == EnumCreatureType.MONSTER) {
            if (MagmaSlimeIslandGenerator.INSTANCE.isSlimeIslandAt(event.getWorld(), event.getPos().func_177979_c(3))) {
                event.getList().clear();
                event.getList().add(this.magmaSlimeSpawn);
            }
            if (SlimeIslandGenerator.INSTANCE.isSlimeIslandAt(event.getWorld(), event.getPos().func_177979_c(3))) {
                event.getList().clear();
                event.getList().add(this.blueSlimeSpawn);
            }
        }
    }
}
