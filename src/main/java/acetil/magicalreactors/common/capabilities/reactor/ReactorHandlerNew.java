package acetil.magicalreactors.common.capabilities.reactor;

import acetil.magicalreactors.common.MagicalReactors;
import acetil.magicalreactors.common.reactor.IReactorFuel;
import acetil.magicalreactors.common.reactor.ReactorFuelRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.logging.log4j.Level;

public class ReactorHandlerNew implements IReactorHandlerNew {
    private int heat;
    private int maxHeat;
    private IReactorFuel[] slots;
    private int numSlots;
    private int energyProduced;
    private boolean finished;
    private ItemStackHandler itemHandler;
    public ReactorHandlerNew () {
        heat = 0;
        energyProduced = 0;
        slots = new IReactorFuel[1];
        numSlots = 1;
        finished = false;
        maxHeat = 1; // if 0 may cause problems
        itemHandler = null;
    }
    public ReactorHandlerNew (int numSlots, int maxHeat) {
        heat = 0;
        energyProduced = 0;
        slots = new IReactorFuel[numSlots];
        this.numSlots = numSlots;
        finished = false;
        this.maxHeat = maxHeat;
        itemHandler = null;
    }
    public void setItemHandler (ItemStackHandler itemHandler) {
        this.itemHandler = itemHandler;
    }
    @Override
    public int getHeat() {
        return heat;
    }

    @Override
    public void setHeat(int heat) {
        this.heat = heat;
    }

    @Override
    public int getEnergyProduced() {
        return energyProduced;
    }

    @Override
    public void update() {
        if (finished) {
            return;
        }

        int heatProduced = 0;
        int currentEnergyProduced = 0;
        boolean hasNonNull = false;
        for (int i = 0; i < numSlots; i++) {
            IReactorFuel fuel = slots[i];
            if (fuel == null) {
                continue;
            }
            heatProduced += fuel.getHeatProduced();
            currentEnergyProduced += fuel.getEnergyProduced(heat, maxHeat);
            fuel.damage();
            if (fuel.getCurrentDurability() <= 0) {
                slots[i] = null;
            } else {
                hasNonNull = true;
            }
        }
        if (!hasNonNull) {
            finished = true;
        }
        heat += heatProduced;
        energyProduced = currentEnergyProduced;
    }

    @Override
    public void cool(int cooling) {
        heat = Math.max(0, heat - cooling);
    }

    @Override
    public boolean finished() {
        return finished;
    }

    @Override
    public CompoundNBT writeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("heat", heat);
        nbt.putInt("energy_produced", energyProduced);
        nbt.putBoolean("finished", finished);
        nbt.putInt("num_slots", numSlots);
        CompoundNBT slotCompound = new CompoundNBT();
        for (int i = 0; i < numSlots; i++) {
            slotCompound.putString("slot" + i, slots[i].getName());
        }
        nbt.put("slots", slotCompound);
        return nbt;
    }

    @Override
    public void readNBT(CompoundNBT nbt) {
        heat = nbt.getInt("heat");
        energyProduced = nbt.getInt("energy_produced");
        finished = nbt.getBoolean("finished");
        int tempNumSlots = numSlots;
        if (numSlots > nbt.getInt("num_slots")) {
            tempNumSlots = nbt.getInt("num_slots");
        }
        CompoundNBT slotCompound = nbt.getCompound("slots");
        for (int i = 0; i < tempNumSlots; i++) {
            slots[i] = ReactorFuelRegistry.getFuel(slotCompound.getString("slot" + i));
        }
    }
    public void setFuels (IReactorFuel[] mats) {
        if (numSlots != mats.length) {
            MagicalReactors.LOGGER.log(Level.INFO, "Incorrect number of materials for reactor. Should be " + numSlots
                    + ", encountered " + mats.length);
        } else {
            for (int i = 0; i < numSlots; i++) {
                slots[i] = mats[i];
            }
            finished = false;
        }
    }

    @Override
    public ItemStack getNextOutput(boolean simulate) {
        if (itemHandler == null) {
            return ItemStack.EMPTY;
        }
        ItemStack item = ItemStack.EMPTY;
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            if (!itemHandler.getStackInSlot(i).isEmpty()) {
                item = itemHandler.extractItem(i, itemHandler.getStackInSlot(i).getCount(), simulate);
                break;
            }
        }
        return item;
    }
    @Override
    public void setNumSlots (int numSlots) {
        IReactorFuel[] temp = new IReactorFuel[numSlots];
        if (Math.min(this.numSlots, numSlots) >= 0)
            System.arraycopy(slots, 0, temp, 0, Math.min(this.numSlots, numSlots));
        slots = temp;
    }
}
