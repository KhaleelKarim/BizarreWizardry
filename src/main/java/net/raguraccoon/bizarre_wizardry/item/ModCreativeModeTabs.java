package net.raguraccoon.bizarre_wizardry.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.raguraccoon.bizarre_wizardry.BizarreWizardry;

public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BizarreWizardry.MOD_ID);

    public static final RegistryObject<CreativeModeTab> BIZARRE_TAB = CREATIVE_MODE_TABS.register("bizarre_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.WAND.get()))
                    .title(Component.translatable("creativetab.bizarre_tab"))
                    .displayItems((pParameters, pOutput) -> {

                        pOutput.accept(ModItems.WAND.get());
                        pOutput.accept(ModItems.STONE_OF_THE_SELF.get());

                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
