package net.raguraccoon.bizarre_wizardry.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.raguraccoon.bizarre_wizardry.BizarreWizardry;
import net.raguraccoon.bizarre_wizardry.client.ClientSpellData;
import net.raguraccoon.bizarre_wizardry.client.SpellHudOverlay;
import net.raguraccoon.bizarre_wizardry.networking.ModMessages;
import net.raguraccoon.bizarre_wizardry.networking.packet.ChooseClassC2SPacket;
import net.raguraccoon.bizarre_wizardry.networking.packet.SwitchSpellC2SPacket;
import net.raguraccoon.bizarre_wizardry.util.KeyBinding;


public class ClientEvents {
    @Mod.EventBusSubscriber(modid = BizarreWizardry.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
        public static void onKeyInput(InputEvent.Key event) {
            if (KeyBinding.SWITCH_SPELL_KEY.consumeClick()) {
                ModMessages.sendToServer(new SwitchSpellC2SPacket());
            }

            if (KeyBinding.DEBUG_KEY.consumeClick()) {
                int magicalClass = ClientSpellData.getMagicalClass();
                magicalClass++;
                ModMessages.sendToServer(new ChooseClassC2SPacket(magicalClass));
            }
        }
    }
    @Mod.EventBusSubscriber(modid = BizarreWizardry.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.SWITCH_SPELL_KEY);
        }

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerBelowAll("spell", SpellHudOverlay.HUD_SPELLS);
        }

        @SubscribeEvent
        public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {

        }

        @SubscribeEvent
        public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {

        }
    }

    @Mod.EventBusSubscriber(modid = BizarreWizardry.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }

}
