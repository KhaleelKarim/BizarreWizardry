package net.raguraccoon.bizarre_wizardry.client;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.raguraccoon.bizarre_wizardry.BizarreWizardry;
import net.raguraccoon.bizarre_wizardry.item.ModItems;

public class SpellHudOverlay {

    public static final ResourceLocation BURN = new ResourceLocation(BizarreWizardry.MOD_ID,
            "textures/spell/fire_spell.png");
    public static final ResourceLocation RHINO_STOMP = new ResourceLocation(BizarreWizardry.MOD_ID,
            "textures/spell/stomp_spell_1.png");
    public static final ResourceLocation BLOODLETTING = new ResourceLocation(BizarreWizardry.MOD_ID,
            "textures/spell/bloodletting_spell.png");
    public static final ResourceLocation CRYSTALLINE_SHIELD = new ResourceLocation(BizarreWizardry.MOD_ID,
            "textures/spell/crystal_shield_spell_1.png");
    public static final ResourceLocation OVERGROWTH = new ResourceLocation(BizarreWizardry.MOD_ID,
            "textures/spell/overgrowth_spell_1.png");
    public static final ResourceLocation NONE = new ResourceLocation(BizarreWizardry.MOD_ID,
            "textures/spell/none_spell.png");
    public static final ResourceLocation EMPTY_SPELL = new ResourceLocation(BizarreWizardry.MOD_ID,
            "textures/spell/no_spell.png");


    public static final ResourceLocation[] spellPictures = {EMPTY_SPELL, RHINO_STOMP, BURN, BLOODLETTING,
                                                            CRYSTALLINE_SHIELD};

    public static IGuiOverlay HUD_SPELLS = ((forgeGui, guiGraphics, partialTick, width, height) -> {
        int x = width / 2;
        int y = height;

        guiGraphics.blit(NONE, x - 135, y - 21,40, 20, 40, 20, 40, 20);

        Player player = Minecraft.getInstance().player;
        assert player != null;
        if (player.getMainHandItem().is(ModItems.WAND.get())) {
            ItemStack wand = player.getMainHandItem();

            if (!wand.hasTag()) {
                CompoundTag tag = new CompoundTag();
                tag.putInt("bizarre_wizardry.spell_number", 0);
                wand.setTag(tag);
            }

            CompoundTag tag = wand.getTag();
            int spellChoice = tag.getInt("bizarre_wizardry.spell_number");
            int activeSpell = ClientSpellData.SPELL_ARSENAL[spellChoice].spellNumber;

            switch(activeSpell) {
                case 0:
                    guiGraphics.blit(EMPTY_SPELL, x - 135, y - 21,40, 20, 40, 20, 40, 20);
                    break;
                case 1:
                    guiGraphics.blit(RHINO_STOMP, x - 135, y - 21,40, 20, 40, 20, 40, 20);
                    break;
                case 2:
                    guiGraphics.blit(BURN, x - 135, y - 21,40, 20, 40, 20, 40, 20);
                    break;
                case 3:
                    guiGraphics.blit(BLOODLETTING, x - 135, y - 21,40, 20, 40, 20, 40, 20);
                    break;
                case 4:
                    guiGraphics.blit(CRYSTALLINE_SHIELD, x - 135, y - 21,40, 20, 40, 20, 40, 20);

                default:
                    guiGraphics.blit(NONE, x - 135, y - 21,40, 20, 40, 20, 40, 20);
                    break;
            }
        }
    });
}
