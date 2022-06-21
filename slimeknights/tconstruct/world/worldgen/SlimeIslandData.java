package slimeknights.tconstruct.world.worldgen;

import net.minecraft.world.storage.*;
import net.minecraft.world.gen.structure.*;
import net.minecraft.util.math.*;
import com.google.common.collect.*;
import java.util.concurrent.*;
import javax.annotation.*;
import net.minecraft.nbt.*;
import java.util.*;

public class SlimeIslandData extends WorldSavedData
{
    private final List<StructureBoundingBox> islands;
    private final Map<ChunkPos, Long> chunksToGenerate;
    
    public SlimeIslandData(final String name) {
        super(name);
        this.islands = (List<StructureBoundingBox>)Lists.newArrayList();
        this.chunksToGenerate = new ConcurrentHashMap<ChunkPos, Long>();
    }
    
    public void markChunkForGeneration(final int chunkX, final int chunkZ, final long seed) {
        this.chunksToGenerate.put(new ChunkPos(chunkX, chunkZ), seed);
    }
    
    public Optional<Long> getSeedForChunkToGenerate(final int chunkX, final int chunkZ) {
        return Optional.ofNullable(this.chunksToGenerate.get(new ChunkPos(chunkX, chunkZ)));
    }
    
    public boolean markChunkAsGenerated(final int chunkX, final int chunkZ) {
        return this.chunksToGenerate.remove(new ChunkPos(chunkX, chunkZ)) != null;
    }
    
    public List<StructureBoundingBox> getIslands() {
        return this.islands;
    }
    
    public void func_76184_a(@Nonnull final NBTTagCompound nbt) {
        this.islands.clear();
        final NBTTagList tagList = nbt.func_150295_c("slimeislands", 11);
        for (int i = 0; i < tagList.func_74745_c(); ++i) {
            this.islands.add(new StructureBoundingBox(tagList.func_150306_c(i)));
        }
    }
    
    @Nonnull
    public NBTTagCompound func_189551_b(@Nonnull final NBTTagCompound nbt) {
        final NBTTagList tagList = new NBTTagList();
        for (final StructureBoundingBox sbb : this.islands) {
            tagList.func_74742_a((NBTBase)sbb.func_151535_h());
        }
        nbt.func_74782_a("slimeislands", (NBTBase)tagList);
        return nbt;
    }
}
