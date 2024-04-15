package net.raguraccoon.bizarre_wizardry.spell_capacity;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpellCapacityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {


    public static Capability<SpellCapacity> SPELL_CAPACITY = CapabilityManager.get(new CapabilityToken<SpellCapacity>() {});

    private SpellCapacity spellCapacity = null;

    private final LazyOptional<SpellCapacity> optional = LazyOptional.of(this::createSpellCapacity);



    private SpellCapacity createSpellCapacity() {

        if (this.spellCapacity == null)
            this.spellCapacity = new SpellCapacity();

        return this.spellCapacity;

    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if (capability == SPELL_CAPACITY)
            return optional.cast();

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createSpellCapacity().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createSpellCapacity().loadNBTData(nbt);
    }

}
