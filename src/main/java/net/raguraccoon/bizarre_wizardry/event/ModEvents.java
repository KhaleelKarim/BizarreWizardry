package net.raguraccoon.bizarre_wizardry.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.raguraccoon.bizarre_wizardry.BizarreWizardry;
import net.raguraccoon.bizarre_wizardry.capability.available_spells.AvailableSpells;
import net.raguraccoon.bizarre_wizardry.capability.available_spells.AvailableSpellsProvider;
import net.raguraccoon.bizarre_wizardry.capability.current_spells.CurrentSpells;
import net.raguraccoon.bizarre_wizardry.capability.current_spells.CurrentSpellsProvider;
import net.raguraccoon.bizarre_wizardry.capability.magical_class.MagicalClass;
import net.raguraccoon.bizarre_wizardry.capability.magical_class.MagicalClassProvider;
import net.raguraccoon.bizarre_wizardry.capability.mana.Mana;
import net.raguraccoon.bizarre_wizardry.capability.mana.ManaProvider;
import net.raguraccoon.bizarre_wizardry.capability.mana_spill.ManaSpill;
import net.raguraccoon.bizarre_wizardry.capability.mana_spill.ManaSpillProvider;
import net.raguraccoon.bizarre_wizardry.client.ClientSpellData;
import net.raguraccoon.bizarre_wizardry.entity.ModEntities;
import net.raguraccoon.bizarre_wizardry.entity.mana_reaper.ManaReaper;
import net.raguraccoon.bizarre_wizardry.networking.ModMessages;
import net.raguraccoon.bizarre_wizardry.networking.packet.available_spells.GetAvailableSpellsS2CPacket;
import net.raguraccoon.bizarre_wizardry.networking.packet.current_spells.GetCurrentSpellsS2CPacket;
import net.raguraccoon.bizarre_wizardry.networking.packet.mana.ModifyManaLevelC2SPacket;
import net.raguraccoon.bizarre_wizardry.networking.packet.mana.UpdateManaInfoS2CPacket;
import net.raguraccoon.bizarre_wizardry.networking.packet.mana_spill.UpdateManaSpillS2CPacket;
import net.raguraccoon.bizarre_wizardry.networking.packet.spell_capacity.GetSpellCapacityS2CPacket;
import net.raguraccoon.bizarre_wizardry.networking.packet.magical_class.SetClassSyncS2CPacket;
import net.raguraccoon.bizarre_wizardry.spell_capacity.SpellCapacity;
import net.raguraccoon.bizarre_wizardry.spell_capacity.SpellCapacityProvider;


public class ModEvents {
    @Mod.EventBusSubscriber(modid = BizarreWizardry.MOD_ID)
    public static class ForgeEvents {
        @SubscribeEvent
        public static void addCustomTrades(VillagerTradesEvent event) {
            
        }

        @SubscribeEvent
        public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player) {
                if (!event.getObject().getCapability(MagicalClassProvider.MAGICAL_CLASS).isPresent()) {
                    event.addCapability(new ResourceLocation(BizarreWizardry.MOD_ID, "bizarre_wizardry.magical_class_property"), new MagicalClassProvider());
                    event.addCapability(new ResourceLocation(BizarreWizardry.MOD_ID, "bizarre_wizardry.spell_capacity_property"), new SpellCapacityProvider());
                    event.addCapability(new ResourceLocation(BizarreWizardry.MOD_ID, "bizarre_wizardry.available_spells_property"), new AvailableSpellsProvider());
                    event.addCapability(new ResourceLocation(BizarreWizardry.MOD_ID, "bizarre_wizardry.current_spells_property"), new CurrentSpellsProvider());
                    event.addCapability(new ResourceLocation(BizarreWizardry.MOD_ID, "bizarre_wizardry.mana_property"), new ManaProvider());
                    event.addCapability(new ResourceLocation(BizarreWizardry.MOD_ID, "bizarre_wizardry.mana_spill_property"), new ManaSpillProvider());

                }
            }
        }

        @SubscribeEvent
        public static void onPlayerCloned(PlayerEvent.Clone event) {
            if (event.isWasDeath()) {

                event.getOriginal().reviveCaps();
                //Restores magical class
                event.getOriginal().getCapability(MagicalClassProvider.MAGICAL_CLASS).ifPresent(oldStore -> {
                    event.getEntity().getCapability(MagicalClassProvider.MAGICAL_CLASS).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });

                //Restores spell capacity
                event.getOriginal().getCapability(SpellCapacityProvider.SPELL_CAPACITY).ifPresent(oldStore -> {
                    event.getEntity().getCapability(SpellCapacityProvider.SPELL_CAPACITY).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });

                //Restores available spells
                event.getOriginal().getCapability(AvailableSpellsProvider.AVAILABLE_SPELLS).ifPresent(oldStore -> {
                    event.getEntity().getCapability(AvailableSpellsProvider.AVAILABLE_SPELLS).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });

                //Restores current spells
                event.getOriginal().getCapability(CurrentSpellsProvider.CURRENT_SPELLS).ifPresent(oldStore -> {
                    event.getEntity().getCapability(CurrentSpellsProvider.CURRENT_SPELLS).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });

                //Restores mana
                event.getOriginal().getCapability(ManaProvider.MANA).ifPresent(oldStore -> {
                    event.getEntity().getCapability(ManaProvider.MANA).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });

                //Restores mana spill
                event.getOriginal().getCapability(ManaSpillProvider.MANA_SPILL).ifPresent(oldStore -> {
                    event.getEntity().getCapability(ManaSpillProvider.MANA_SPILL).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });

                event.getOriginal().invalidateCaps();
            }
        }

        @SubscribeEvent
        public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
            event.register(MagicalClass.class);
            event.register(SpellCapacity.class);
            event.register(AvailableSpells.class);
            event.register(CurrentSpells.class);
            event.register(Mana.class);
            event.register(ManaSpill.class);
        }

        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {

            if (ClientSpellData.getManaLevel() < ClientSpellData.getManaCap())
                ModMessages.sendToServer(new ModifyManaLevelC2SPacket("add", ClientSpellData.getManaRate()));

        }

        @SubscribeEvent
        public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
            if (!event.getLevel().isClientSide()) {
                if (event.getEntity() instanceof ServerPlayer player) {
                    //For magic class
                    player.getCapability(MagicalClassProvider.MAGICAL_CLASS).ifPresent(magicalClass -> {
                        ModMessages.sendToPlayer(new SetClassSyncS2CPacket(magicalClass.getMagicalClass()), player);
                    });

                    //For spell capacity
                    player.getCapability(SpellCapacityProvider.SPELL_CAPACITY).ifPresent(spellCapacity -> {
                        ModMessages.sendToPlayer(new GetSpellCapacityS2CPacket(spellCapacity.getSpellCapacity()), player);
                    });

                    //For available spells
                    player.getCapability(AvailableSpellsProvider.AVAILABLE_SPELLS).ifPresent(availableSpells -> {
                        ModMessages.sendToPlayer(new GetAvailableSpellsS2CPacket(availableSpells.getAvailableSpells()), player);
                    });

                    //For current spells
                    player.getCapability(CurrentSpellsProvider.CURRENT_SPELLS).ifPresent(currentSpells -> {
                        ModMessages.sendToPlayer(new GetCurrentSpellsS2CPacket(currentSpells.getCurrentSpells()), player);
                    });

                    //For mana
                    player.getCapability(ManaProvider.MANA).ifPresent(mana -> {
                        ModMessages.sendToPlayer(new UpdateManaInfoS2CPacket(new int[]{mana.getManaCapacity(),
                                mana.getManaLevel(), mana.getManaRegenRate()}), player);
                    });

                    //For mana spill
                    player.getCapability(ManaSpillProvider.MANA_SPILL).ifPresent(manaSpill -> {
                        ModMessages.sendToPlayer(new UpdateManaSpillS2CPacket(manaSpill.getManaSpill()), player);
                    });

                }
            }
        }

        @SubscribeEvent
        public static void onEntityDeath(LivingDeathEvent event) {

        }

    }

    @Mod.EventBusSubscriber(modid = BizarreWizardry.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {

        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
            event.put(ModEntities.MANA_REAPER.get(), ManaReaper.setAttributes());
        }
    }
}
