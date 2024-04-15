package net.raguraccoon.bizarre_wizardry.available_spells;

import net.minecraft.nbt.CompoundTag;
import net.raguraccoon.bizarre_wizardry.client.ClientSpellData;

public class AvailableSpells {

    //The integer list of available spells is as long as the list of all possible spells
    //It contains only 1's and 0's
    //1 meaning the spell at that index is unlocked and 0 meaning the opposite
    private int[] availableSpells = new int[ClientSpellData.spellsLibrary.length];


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
