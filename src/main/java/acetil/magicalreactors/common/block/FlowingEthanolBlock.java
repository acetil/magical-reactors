package acetil.magicalreactors.common.block;

import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FlowingFluid;

import java.util.function.Supplier;

public class FlowingEthanolBlock extends FlowingFluidBlock {
    public FlowingEthanolBlock(Supplier<? extends FlowingFluid> supplier) {
        super(supplier, Properties.create(Material.WATER));
        setRegistryName("ethanol_block");
    }
}
