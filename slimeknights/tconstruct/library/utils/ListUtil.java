package slimeknights.tconstruct.library.utils;

import net.minecraft.util.*;
import java.util.*;

public final class ListUtil
{
    public static <E> NonNullList<E> getListFrom(final E... element) {
        final NonNullList<E> list = (NonNullList<E>)NonNullList.func_191196_a();
        list.addAll((Collection)Arrays.asList(element));
        return list;
    }
    
    private ListUtil() {
    }
}
