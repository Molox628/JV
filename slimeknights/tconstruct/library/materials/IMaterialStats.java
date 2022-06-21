package slimeknights.tconstruct.library.materials;

import java.util.*;

public interface IMaterialStats
{
    String getIdentifier();
    
    String getLocalizedName();
    
    List<String> getLocalizedInfo();
    
    List<String> getLocalizedDesc();
}
