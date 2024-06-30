package net.raguraccoon.bizarre_wizardry.capability.mana_spill;

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

public class ManaSpillProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<ManaSpill> MANA_SPILL = CapabilityManager.get(new CapabilityToken<ManaSpill>() {});

    private ManaSpill manaSpill = null;

    private final LazyOptional<ManaSpill> optional = LazyOptional.of(this::createManaSpill);



    private ManaSpill createManaSpill() {

        if (this.manaSpill == null)
            this.manaSpill = new ManaSpill();

        return this.manaSpill;

    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if (capability == MANA_SPILL)
            return optional.cast();

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createManaSpill().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createManaSpill().loadNBTData(nbt);
    }

}
