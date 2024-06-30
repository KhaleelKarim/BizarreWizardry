package net.raguraccoon.bizarre_wizardry.capability.mana_spill;

import net.minecraft.nbt.CompoundTag;

public class ManaSpill {

    private int manaSpill; //Measure of how much mana has spilled

    public ManaSpill() {
        manaSpill = 0; //Default set to 0
    }

    public int getManaSpill() {
        return manaSpill;
    }

    public void setManaSpill(int manaSpill) {
        this.manaSpill = manaSpill;
    }

    public void addManaSpill(int toAdd) {
        this.manaSpill += toAdd;
    }

    //Misc methods

    //Method to copy capability on player death, rejoin world, etc.
    public void copyFrom(ManaSpill source) {
        this.manaSpill = source.manaSpill;
    }

    //Using NBT tags to load and save data about mana
    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("bizarre_wizardry.mana_spill", this.manaSpill);
    }

    public void loadNBTData(CompoundTag nbt) {
        this.manaSpill = nbt.getInt("bizarre_wizardry.mana_spill");
    }
}
