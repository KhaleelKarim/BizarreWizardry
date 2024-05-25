package net.raguraccoon.bizarre_wizardry.capability.current_spells;

import net.minecraft.nbt.CompoundTag;
import net.raguraccoon.bizarre_wizardry.client.ClientSpellData;

public class CurrentSpells {

    /*
    List of integers corresponding to the indices of spells in spell library
     */
    private int[] currentSpells = new int[ClientSpellData.getSpellCapacity()];


    public int[] getCurrentSpells() {
        return this.currentSpells;
    }


    public void setSpell(int spell, int index) {
        currentSpells[index] = spell;
    }


    //Method to copy capability on player death, rejoin world, etc.
    public void copyFrom(CurrentSpells source) {
        this.currentSpells = source.currentSpells;
    }

    //Using NBT tags to load and save data about spell capacity
    public void saveNBTData(CompoundTag nbt) {
        nbt.putIntArray("bizarre_wizardry.current_spells", this.currentSpells);
    }

    public void loadNBTData(CompoundTag nbt) {
        this.currentSpells = nbt.getIntArray("bizarre_wizardry.current_spells");
    }

}
