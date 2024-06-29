package net.raguraccoon.bizarre_wizardry.capability.mana;

import net.minecraft.nbt.CompoundTag;


/*
Capability class for the player's mana level
 */
public class Mana {

    private int manaCapacity;  //Maximum amount of mana a player can have
    private int manaLevel;     //The current amount of mana a player has
    private int manaRegenRate; //The rate (per tick) at which mana regenerates


    //No argument constructor for default instantiation
    public Mana() {
        manaCapacity = 5000;
        manaLevel = 5000;
        manaRegenRate = 1;
    }

    //Regular constructor
    public Mana(int manaCapacity, int manaLevel, int manaRegenRate) {
        this.manaCapacity = manaCapacity;
        this.manaLevel = manaLevel;
        this.manaRegenRate = manaRegenRate;
    }

    public int getManaCapacity() {
        return manaCapacity;
    }

    //Ensures that the capacity never goes below the level
    public void setManaCapacity(int manaCapacity) {

        if (manaLevel > manaCapacity)
            manaLevel = manaCapacity;

        this.manaCapacity = manaCapacity;
    }

    //Increments the mana capacity by the argument
    public void addCapacity(int increase) {
        this.manaCapacity += increase;
    }

    //Decrements the mana capacity by the argument, ensures
    //that capacity never goes below the level or becomes negative
    public void subtractCapacity(int decrease) {

        //Prevent mana capacity from being 0 or less
        if (decrease >= manaCapacity)
            throw new RuntimeException();

        //If the decrease causes capacity to be less than the level, adjust level
        if ((manaCapacity - decrease) < manaLevel)
            manaLevel = manaCapacity - decrease;

        manaCapacity -= decrease;

    }




    public int getManaLevel() {
        return manaLevel;
    }

    //Ensure the mana level is not set above capacity
    public void setManaLevel(int manaLevel) {

        if (manaLevel > manaCapacity) {
            this.manaLevel = manaCapacity;
            return;
        }

        this.manaLevel = manaLevel;
    }

    //Increments the mana level by argument, prevents level
    //being greater than the capacity
    public void addLevel(int increase) {

        if ((manaLevel + increase) > manaCapacity) {
            manaLevel = manaCapacity;
            return;
        }

        manaLevel += increase;

    }

    //Decrements the mana level by argument, prevents
    //negative levels
    public void subtractLevel(int decrease) {

        //Prevents negative level
        if (decrease > manaLevel) {
            manaLevel = 0;
            return;
        }

        manaLevel -= decrease;

    }




    public int getManaRegenRate() {
        return manaRegenRate;
    }

    public void setManaRegenRate(int manaRegenRate) {
        this.manaRegenRate = manaRegenRate;
    }

    public void addRate(int increase) {
        manaRegenRate += increase;
    }

    //Decrements regen rate and prevents negative rates or 0
    public void subtractRate(int decrease) {

        if (decrease >= manaRegenRate) {
            manaRegenRate = 1;
            return;
        }

        manaRegenRate -= decrease;

    }



    //Misc methods

    //Method to copy capability on player death, rejoin world, etc.
    public void copyFrom(Mana source) {
        this.manaCapacity = source.manaCapacity;
        this.manaLevel = source.manaLevel;
        this.manaRegenRate = source.manaRegenRate;
    }

    //Using NBT tags to load and save data about mana
    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("bizarre_wizardry.mana.capacity", this.manaCapacity);
        nbt.putInt("bizarre_wizardry.mana.level", this.manaLevel);
        nbt.putInt("bizarre_wizardry.mana.regen_rate", this.manaRegenRate);
    }

    public void loadNBTData(CompoundTag nbt) {
        this.manaCapacity = nbt.getInt("bizarre_wizardry.mana.capacity");
        this.manaLevel = nbt.getInt("bizarre_wizardry.mana.level");
        this.manaRegenRate = nbt.getInt("bizarre_wizardry.mana.regen_rate");
    }

}
