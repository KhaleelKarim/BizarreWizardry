package net.raguraccoon.bizarre_wizardry.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.raguraccoon.bizarre_wizardry.BizarreWizardry;

public class ModEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, BizarreWizardry.MOD_ID);



    public static final RegistryObject<MobEffect> CRYSTALLINE_SHIELD = MOB_EFFECTS
            .register("crystalline_shield",
                    () -> new CrystallineShieldEffect(MobEffectCategory.BENEFICIAL, 16733695));


    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }

}
