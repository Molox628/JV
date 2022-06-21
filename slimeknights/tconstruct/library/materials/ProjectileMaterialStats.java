package slimeknights.tconstruct.library.materials;

import java.util.*;
import com.google.common.collect.*;

public class ProjectileMaterialStats extends AbstractMaterialStats
{
    public ProjectileMaterialStats() {
        super("projectile");
    }
    
    @Override
    public List<String> getLocalizedInfo() {
        return (List<String>)ImmutableList.of();
    }
    
    @Override
    public List<String> getLocalizedDesc() {
        return (List<String>)ImmutableList.of();
    }
}
