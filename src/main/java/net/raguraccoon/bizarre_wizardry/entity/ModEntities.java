package net.raguraccoon.bizarre_wizardry.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.raguraccoon.bizarre_wizardry.BizarreWizardry;
import net.raguraccoon.bizarre_wizardry.entity.magicians_red.MagiciansRed;
import net.raguraccoon.bizarre_wizardry.entity.mana_reaper.ManaReaper;

public class ModEntities {

    //Deferred Register
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, BizarreWizardry.MOD_ID);


    //Magicians Red!
    public static final RegistryObject<EntityType<MagiciansRed>> MAGICIANS_RED =
            ENTITY_TYPES.register("magicians_red", () ->
                    EntityType.Builder.<MagiciansRed>of(MagiciansRed::new, MobCategory.MISC)
                            .sized(1f, 1f).build("magicians_red"));

    //Mana Reaper
    public static final RegistryObject<EntityType<ManaReaper>> MANA_REAPER =
            ENTITY_TYPES.register("mana_reaper", () ->
                    EntityType.Builder.<ManaReaper>of(ManaReaper::new, MobCategory.MONSTER)
                            .sized(1f, 2.5f).build("mana_reaper"));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

}
