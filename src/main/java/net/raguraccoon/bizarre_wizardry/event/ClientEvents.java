package net.raguraccoon.bizarre_wizardry.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.raguraccoon.bizarre_wizardry.BizarreWizardry;
import net.raguraccoon.bizarre_wizardry.client.ManaDisplay;
import net.raguraccoon.bizarre_wizardry.client.SpellHudOverlay;
import net.raguraccoon.bizarre_wizardry.effect.ModEffects;
import net.raguraccoon.bizarre_wizardry.entity.ModEntities;
import net.raguraccoon.bizarre_wizardry.entity.magicians_red.MagiciansRedRenderer;
import net.raguraccoon.bizarre_wizardry.networking.ModMessages;
import net.raguraccoon.bizarre_wizardry.networking.packet.SwitchSpellC2SPacket;
import net.raguraccoon.bizarre_wizardry.screen.BizarreWizardryMainScreen;
import net.raguraccoon.bizarre_wizardry.util.KeyBinding;



public class ClientEvents {
    @Mod.EventBusSubscriber(modid = BizarreWizardry.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
        public static void onKeyInput(InputEvent.Key event) {
            if (KeyBinding.SWITCH_SPELL_KEY.consumeClick()) {
                ModMessages.sendToServer(new SwitchSpellC2SPacket());
            }

            if (KeyBinding.OPEN_GUI_KEY.consumeClick()) {
                Player player = Minecraft.getInstance().player;
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> Minecraft.getInstance().setScreen(new BizarreWizardryMainScreen()));
            }
        }


        @SubscribeEvent
        public static void onPlayerHurt(LivingHurtEvent event) {

            if (event.getEntity() instanceof Player) {

                Player player = (Player) event.getEntity();

                //Check if player has the effect
                if (player.hasEffect(ModEffects.CRYSTALLINE_SHIELD.get())) {

                    event.setCanceled(true);
                    player.removeEffect(ModEffects.CRYSTALLINE_SHIELD.get());
                    Level level = Minecraft.getInstance().level;
                    Player player1 = Minecraft.getInstance().player;

                    level.playSound(player1, player.blockPosition(), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS);


                }

            }

        }



    }
    @Mod.EventBusSubscriber(modid = BizarreWizardry.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.SWITCH_SPELL_KEY);
            event.register(KeyBinding.OPEN_GUI_KEY);
        }

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerBelowAll("spell", SpellHudOverlay.HUD_SPELLS);
            event.registerAboveAll("mana", ManaDisplay.MANA_HUD);
        }

        @SubscribeEvent
        public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(ModEntities.MAGICIANS_RED.get(), MagiciansRedRenderer::new);
        }

        @SubscribeEvent
        public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {

        }
    }

    @Mod.EventBusSubscriber(modid = BizarreWizardry.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.MAGICIANS_RED.get(), MagiciansRedRenderer::new);
        }
    }

}
