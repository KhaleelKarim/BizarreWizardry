package net.raguraccoon.bizarre_wizardry.capability.magical_class;

import net.minecraft.nbt.CompoundTag;

public class MagicalClass {

    private int magicalClass;

    public int getMagicalClass() {
        return this.magicalClass;
    }

    public void setMagicalClass(int magicalClass) {
        this.magicalClass = magicalClass;
    }

    public void copyFrom(MagicalClass source) {
        this.magicalClass = source.magicalClass;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("bizarre_wizardry.magical_class", magicalClass);
    }

    public void loadNBTData(CompoundTag nbt) {
        magicalClass = nbt.getInt("bizarre_wizardry.magical_class");
    }

}
