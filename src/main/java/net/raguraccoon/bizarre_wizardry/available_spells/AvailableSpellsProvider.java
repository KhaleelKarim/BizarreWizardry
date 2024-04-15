package net.raguraccoon.bizarre_wizardry.available_spells;

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

public class AvailableSpellsProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {


    public static Capability<AvailableSpells> AVAILABLE_SPELLS = CapabilityManager.get(new CapabilityToken<AvailableSpells>() {});

    private AvailableSpells availableSpells = null;

    private final LazyOptional<AvailableSpells> optional = LazyOptional.of(this::createAvailableSpells);



    private AvailableSpells createAvailableSpells() {

        if (this.availableSpells == null)
            this.availableSpells = new AvailableSpells();

        return this.availableSpells;

    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if (capability == AVAILABLE_SPELLS)
            return optional.cast();

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createAvailableSpells().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createAvailableSpells().loadNBTData(nbt);
    }

}
