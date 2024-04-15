package net.raguraccoon.bizarre_wizardry.spell_capacity;

import net.minecraft.nbt.CompoundTag;

public class SpellCapacity {

    private int spellCapacity = 3; //How many spells the player is allowed to have in their wand


    public int getSpellCapacity() {
        return this.spellCapacity;
    }

    //To set spell capacity to a specific number
    public void setSpellCapacity(int spellCapacity) {
        if (spellCapacity < 3)
            this.spellCapacity = 3;
        else
            this.spellCapacity = spellCapacity;
    }

    //To increase spell capacity by an int
    public void increaseSpellCapacity(int increase) {
        this.spellCapacity += increase;
    }


    //Method to copy capability on player death, rejoin world, etc.
    public void copyFrom(SpellCapacity source) {
        this.spellCapacity = source.spellCapacity;
    }

    //Using NBT tags to load and save data about spell capacity
    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("bizarre_wizardry.spell_capacity", this.spellCapacity);
    }

    public void loadNBTData(CompoundTag nbt) {
        this.spellCapacity = nbt.getInt("bizarre_wizardry.spell_capacity");
    }

}
