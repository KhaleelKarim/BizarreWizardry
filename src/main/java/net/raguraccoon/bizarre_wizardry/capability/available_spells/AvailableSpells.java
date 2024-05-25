package net.raguraccoon.bizarre_wizardry.capability.available_spells;

import net.minecraft.nbt.CompoundTag;
import net.raguraccoon.bizarre_wizardry.client.ClientSpellData;


public class AvailableSpells {

    /*
    List of integers which is the same length of the spell library
    The arrays are parallel which means element 3 of this array corresponds
    to element 3 of the spell library. If the value in this array is 0,
    the spell is not available. If it is 1, the spell is available.
     */
    private int[] availableSpells = new int[ClientSpellData.SPELLS_LIBRARY.length];


    public int[] getAvailableSpells() {
        return this.availableSpells;
    }

    //Sets a specific index to 1 meaning that it has been unlocked
    public void unlockSpell(int spellIndex) {
        availableSpells[spellIndex] = 1;
    }


    //Method to copy capability on player death, rejoin world, etc.
    public void copyFrom(AvailableSpells source) {
        this.availableSpells = source.availableSpells;
    }

    //Using NBT tags to load and save data about spell capacity
    public void saveNBTData(CompoundTag nbt) {
        nbt.putIntArray("bizarre_wizardry.available_spells", this.availableSpells);
    }

    public void loadNBTData(CompoundTag nbt) {
        this.availableSpells = nbt.getIntArray("bizarre_wizardry.available_spells");
    }

}
