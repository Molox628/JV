package slimeknights.tconstruct.debug;

import net.minecraft.server.*;
import net.minecraft.command.*;
import slimeknights.tconstruct.tools.harvest.*;
import com.google.common.base.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.tconstruct.library.materials.*;
import javax.annotation.*;
import net.minecraft.util.text.*;
import slimeknights.tconstruct.library.tools.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.library.*;
import java.util.*;
import org.apache.commons.lang3.tuple.*;

public class FindBestTool extends CommandBase
{
    public int func_82362_a() {
        return 0;
    }
    
    public String func_71517_b() {
        return "findBestTool";
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "/findBestTool [number of tools to display] <filter type>";
    }
    
    public void func_184881_a(@Nonnull final MinecraftServer server, @Nonnull final ICommandSender sender, @Nonnull final String[] args) throws CommandException {
        if (args.length < 1) {
            throw new CommandException("Too few arguments", new Object[0]);
        }
        int num;
        int filtertype;
        if (args.length < 2) {
            num = 100;
            filtertype = Integer.valueOf(args[0]);
        }
        else {
            num = Integer.valueOf(args[0]);
            filtertype = Integer.valueOf(args[1]);
        }
        final Predicate<Triple<ItemStack, ImmutableList<Material>, Object[]>>[] filter = (Predicate<Triple<ItemStack, ImmutableList<Material>, Object[]>>[])new Predicate[3];
        if (num < 0 || filtertype < 0 || filtertype > filter.length) {
            throw new CommandException("Inavlid arguments", new Object[0]);
        }
        final ToolCore tool = TinkerHarvestTools.pickaxe;
        final List<Triple<ItemStack, ImmutableList<Material>, Object[]>> results = (List<Triple<ItemStack, ImmutableList<Material>, Object[]>>)Lists.newArrayList();
        final Function<ItemStack, ?>[] functions = (Function<ItemStack, ?>[])new Function[] { (Function)new Function<ItemStack, Integer>() {
                public Integer apply(final ItemStack itemStack) {
                    return ToolHelper.getDurabilityStat(itemStack);
                }
            }, (Function)new Function<ItemStack, Float>() {
                public Float apply(final ItemStack itemStack) {
                    return ToolHelper.getMiningSpeedStat(itemStack);
                }
            }, (Function)new Function<ItemStack, Float>() {
                public Float apply(final ItemStack itemStack) {
                    return ToolHelper.getAttackStat(itemStack);
                }
            } };
        this.recurse(tool, (ImmutableList<Material>)ImmutableList.of(), results, functions);
        final List<Integer> durabilities = (List<Integer>)Lists.transform((List)results, (Function)new Function<Triple<ItemStack, ImmutableList<Material>, Object[]>, Integer>() {
            @Nullable
            public Integer apply(final Triple<ItemStack, ImmutableList<Material>, Object[]> input) {
                return (Integer)((Object[])input.getRight())[0];
            }
        });
        final List<Float> speeds = (List<Float>)Lists.transform((List)results, (Function)new Function<Triple<ItemStack, ImmutableList<Material>, Object[]>, Float>() {
            @Nullable
            public Float apply(final Triple<ItemStack, ImmutableList<Material>, Object[]> input) {
                return (Float)((Object[])input.getRight())[1];
            }
        });
        final List<Float> attacks = (List<Float>)Lists.transform((List)results, (Function)new Function<Triple<ItemStack, ImmutableList<Material>, Object[]>, Float>() {
            @Nullable
            public Float apply(final Triple<ItemStack, ImmutableList<Material>, Object[]> input) {
                return (Float)((Object[])input.getRight())[2];
            }
        });
        float percentile = 0.5f;
        int durPercentile = (int)this.getPercentile((List<Number>)Ordering.natural().reverse().sortedCopy((Iterable)durabilities), percentile);
        float speedPercentile = (float)this.getPercentile((List<Number>)Ordering.natural().reverse().sortedCopy((Iterable)speeds), percentile);
        float attackPercentile = (float)this.getPercentile((List<Number>)Ordering.natural().reverse().sortedCopy((Iterable)attacks), percentile);
        Collection<Triple<ItemStack, ImmutableList<Material>, Object[]>> best;
        do {
            percentile /= 2.0f;
            final int durPercentile2 = (int)this.getPercentile((List<Number>)Ordering.natural().reverse().sortedCopy((Iterable)durabilities), percentile);
            final float speedPercentile2 = (float)this.getPercentile((List<Number>)Ordering.natural().reverse().sortedCopy((Iterable)speeds), percentile);
            final float attackPercentile2 = (float)this.getPercentile((List<Number>)Ordering.natural().reverse().sortedCopy((Iterable)attacks), percentile);
            durPercentile = durPercentile2;
            speedPercentile = speedPercentile2;
            attackPercentile = attackPercentile2;
            filter[0] = (Predicate<Triple<ItemStack, ImmutableList<Material>, Object[]>>)new Predicate<Triple<ItemStack, ImmutableList<Material>, Object[]>>() {
                public boolean apply(@Nullable final Triple<ItemStack, ImmutableList<Material>, Object[]> entry) {
                    return (int)((Object[])entry.getRight())[0] > durPercentile2 && (float)((Object[])entry.getRight())[1] > speedPercentile2;
                }
            };
            filter[1] = (Predicate<Triple<ItemStack, ImmutableList<Material>, Object[]>>)new Predicate<Triple<ItemStack, ImmutableList<Material>, Object[]>>() {
                public boolean apply(@Nullable final Triple<ItemStack, ImmutableList<Material>, Object[]> entry) {
                    return (int)((Object[])entry.getRight())[0] > durPercentile2 && (float)((Object[])entry.getRight())[2] > attackPercentile2;
                }
            };
            filter[2] = (Predicate<Triple<ItemStack, ImmutableList<Material>, Object[]>>)new Predicate<Triple<ItemStack, ImmutableList<Material>, Object[]>>() {
                public boolean apply(@Nullable final Triple<ItemStack, ImmutableList<Material>, Object[]> entry) {
                    return (int)((Object[])entry.getRight())[0] > durPercentile2 && (float)((Object[])entry.getRight())[1] > speedPercentile2 && (float)((Object[])entry.getRight())[2] > attackPercentile2;
                }
            };
            best = (Collection<Triple<ItemStack, ImmutableList<Material>, Object[]>>)Collections2.filter((Collection)results, (Predicate)filter[filtertype]);
        } while (best.size() > num);
        sender.func_145747_a((ITextComponent)new TextComponentString(String.format("%d are in the top %d percentile of stats (%d; %f; %f)", best.size(), (int)(percentile * 100.0f), durPercentile, speedPercentile, attackPercentile)));
        final Collection<Triple<ItemStack, ImmutableList<Material>, Object[]>> sortedDurability = (Collection<Triple<ItemStack, ImmutableList<Material>, Object[]>>)new Ordering<Triple<ItemStack, ImmutableList<Material>, Object[]>>() {
            public int compare(@Nullable final Triple<ItemStack, ImmutableList<Material>, Object[]> left, @Nullable final Triple<ItemStack, ImmutableList<Material>, Object[]> right) {
                return (int)((Object[])right.getRight())[0] - (int)((Object[])left.getRight())[0];
            }
        }.sortedCopy((Iterable)best);
        final Collection<Triple<ItemStack, ImmutableList<Material>, Object[]>> sortedSpeed = (Collection<Triple<ItemStack, ImmutableList<Material>, Object[]>>)new Ordering<Triple<ItemStack, ImmutableList<Material>, Object[]>>() {
            public int compare(@Nullable final Triple<ItemStack, ImmutableList<Material>, Object[]> left, @Nullable final Triple<ItemStack, ImmutableList<Material>, Object[]> right) {
                return (int)((float)((Object[])right.getRight())[1] * 10.0f) - (int)((float)((Object[])left.getRight())[1] * 10.0f);
            }
        }.sortedCopy((Iterable)best);
        final Collection<Triple<ItemStack, ImmutableList<Material>, Object[]>> sortedAttack = (Collection<Triple<ItemStack, ImmutableList<Material>, Object[]>>)new Ordering<Triple<ItemStack, ImmutableList<Material>, Object[]>>() {
            public int compare(@Nullable final Triple<ItemStack, ImmutableList<Material>, Object[]> left, @Nullable final Triple<ItemStack, ImmutableList<Material>, Object[]> right) {
                return (int)((float)((Object[])right.getRight())[2] * 10.0f) - (int)((float)((Object[])left.getRight())[2] * 10.0f);
            }
        }.sortedCopy((Iterable)best);
        for (final Triple<ItemStack, ImmutableList<Material>, Object[]> foo : best) {
            final StringBuilder text = new StringBuilder();
            text.append("Materials: ");
            for (final Material mat : (ImmutableList)foo.getMiddle()) {
                text.append(mat.getIdentifier());
                text.append(" ");
            }
            text.append("- ");
            text.append("Dur: ");
            text.append(((Object[])foo.getRight())[0]);
            text.append(" Speed: ");
            text.append((float)((Object[])foo.getRight())[1] * tool.miningSpeedModifier());
            text.append(" Dmg: ");
            text.append((float)((Object[])foo.getRight())[2] * tool.damagePotential());
            sender.func_145747_a(((ItemStack)foo.getLeft()).func_151000_E().func_150257_a((ITextComponent)new TextComponentString(text.toString())));
        }
        sender.func_145747_a((ITextComponent)new TextComponentString("Top 5 Durability:"));
        Iterator<Triple<ItemStack, ImmutableList<Material>, Object[]>> iter = sortedDurability.iterator();
        for (int i = 0; i < 5 && iter.hasNext(); ++i) {
            final Triple<ItemStack, ImmutableList<Material>, Object[]> foo2 = iter.next();
            final StringBuilder text2 = new StringBuilder();
            text2.append(((Object[])foo2.getRight())[0]);
            text2.append(" - ");
            for (final Material mat2 : (ImmutableList)foo2.getMiddle()) {
                text2.append(mat2.getIdentifier());
                text2.append(" ");
            }
            sender.func_145747_a(((ItemStack)foo2.getLeft()).func_151000_E().func_150257_a((ITextComponent)new TextComponentString(text2.toString())));
        }
        sender.func_145747_a((ITextComponent)new TextComponentString("Top 5 Speed:"));
        iter = sortedSpeed.iterator();
        for (int i = 0; i < 5 && iter.hasNext(); ++i) {
            final Triple<ItemStack, ImmutableList<Material>, Object[]> foo2 = iter.next();
            final StringBuilder text2 = new StringBuilder();
            text2.append(((Object[])foo2.getRight())[1]);
            text2.append(" - ");
            for (final Material mat2 : (ImmutableList)foo2.getMiddle()) {
                text2.append(mat2.getIdentifier());
                text2.append(" ");
            }
            sender.func_145747_a(((ItemStack)foo2.getLeft()).func_151000_E().func_150257_a((ITextComponent)new TextComponentString(text2.toString())));
        }
        sender.func_145747_a((ITextComponent)new TextComponentString("Top 5 Attack:"));
        iter = sortedAttack.iterator();
        for (int i = 0; i < 5 && iter.hasNext(); ++i) {
            final Triple<ItemStack, ImmutableList<Material>, Object[]> foo2 = iter.next();
            final StringBuilder text2 = new StringBuilder();
            text2.append(((Object[])foo2.getRight())[2]);
            text2.append(" - ");
            for (final Material mat2 : (ImmutableList)foo2.getMiddle()) {
                text2.append(mat2.getIdentifier());
                text2.append(" ");
            }
            sender.func_145747_a(((ItemStack)foo2.getLeft()).func_151000_E().func_150257_a((ITextComponent)new TextComponentString(text2.toString())));
        }
    }
    
    public void recurse(final ToolCore tool, final ImmutableList<Material> materials, final List<Triple<ItemStack, ImmutableList<Material>, Object[]>> results, final Function<ItemStack, ?>[] fns) {
        if (tool.getRequiredComponents().size() > materials.size()) {
            for (final Material mat : TinkerRegistry.getAllMaterials()) {
                if (!mat.hasStats("head")) {
                    continue;
                }
                final ImmutableList.Builder<Material> mats = (ImmutableList.Builder<Material>)ImmutableList.builder();
                mats.addAll((Iterable)materials);
                mats.add((Object)mat);
                this.recurse(tool, (ImmutableList<Material>)mats.build(), results, fns);
            }
        }
        else {
            final ItemStack stack = tool.buildItem((List<Material>)materials);
            final Object[] values = new Object[fns.length];
            for (int i = 0; i < fns.length; ++i) {
                values[i] = fns[i].apply((Object)stack);
            }
            results.add((Triple<ItemStack, ImmutableList<Material>, Object[]>)Triple.of((Object)stack, (Object)materials, (Object)values));
        }
    }
    
    private <T extends Number> double getPercentile(final List<T> entries, final float percentile) {
        final int coeff = (int)(1.0f / percentile);
        if (entries.size() % 2 == 1) {
            return entries.get(entries.size() / coeff).doubleValue();
        }
        final T v1 = entries.get(entries.size() / coeff);
        final T v2 = entries.get(entries.size() / coeff + 1);
        return (v1.doubleValue() + v2.doubleValue()) / 2.0;
    }
    
    private enum Comp implements Comparator<Pair<ImmutableList<Material>, Integer>>
    {
        INSTANCE;
        
        @Override
        public int compare(final Pair<ImmutableList<Material>, Integer> o1, final Pair<ImmutableList<Material>, Integer> o2) {
            return (int)o2.getRight() - (int)o1.getRight();
        }
    }
}
