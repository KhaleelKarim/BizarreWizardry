package net.raguraccoon.bizarre_wizardry.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.raguraccoon.bizarre_wizardry.BizarreWizardry;
import net.raguraccoon.bizarre_wizardry.item.custom.WandItem;

public class ModItems {
    //Deferred Register to register mod items
    public static DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, BizarreWizardry.MOD_ID);

    //Below is all the items that are registered in the mod
    public static final RegistryObject<Item> WAND = ITEMS.register("wand",
            () -> new WandItem(new Item.Properties()
                    .stacksTo(1)
                    .setNoRepair()));


    //Called by main mod class to register everything
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
