package net.raguraccoon.bizarre_wizardry.magical_class;

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

public class MagicalClassProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<MagicalClass> MAGICAL_CLASS = CapabilityManager.get(new CapabilityToken<MagicalClass>() {});

    private MagicalClass magicalClass = null;

    private final LazyOptional<MagicalClass> optional = LazyOptional.of(this::createMagicalClass);

    private MagicalClass createMagicalClass() {
        if (this.magicalClass == null) {
            this.magicalClass = new MagicalClass();
        }
        return this.magicalClass;

    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if (capability == MAGICAL_CLASS) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createMagicalClass().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createMagicalClass().loadNBTData(nbt);
    }

}
