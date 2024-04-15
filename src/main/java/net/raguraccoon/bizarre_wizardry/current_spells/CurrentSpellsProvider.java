package net.raguraccoon.bizarre_wizardry.current_spells;

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

public class CurrentSpellsProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {


    public static Capability<CurrentSpells> CURRENT_SPELLS = CapabilityManager.get(new CapabilityToken<CurrentSpells>() {});

    private CurrentSpells CurrentSpells = null;

    private final LazyOptional<CurrentSpells> optional = LazyOptional.of(this::createCurrentSpells);



    private CurrentSpells createCurrentSpells() {

        if (this.CurrentSpells == null)
            this.CurrentSpells = new CurrentSpells();

        return this.CurrentSpells;

    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if (capability == CURRENT_SPELLS)
            return optional.cast();

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createCurrentSpells().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createCurrentSpells().loadNBTData(nbt);
    }

}
