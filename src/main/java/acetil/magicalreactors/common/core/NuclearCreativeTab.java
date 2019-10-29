package acetil.magicalreactors.common.core;

import acetil.magicalreactors.common.items.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import acetil.magicalreactors.common.lib.LibMisc;

public class NuclearCreativeTab extends ItemGroup {

    public static final NuclearCreativeTab INSTANCE = new NuclearCreativeTab();
    public NuclearCreativeTab () {
        super(LibMisc.MODID);
    }


    @Override
    public ItemStack createIcon() {
        return new ItemStack(ModItems.URANIUM_INGOT);
    }
}
