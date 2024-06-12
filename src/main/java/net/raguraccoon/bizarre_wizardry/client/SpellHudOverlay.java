package net.raguraccoon.bizarre_wizardry.client;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.raguraccoon.bizarre_wizardry.BizarreWizardry;
import net.raguraccoon.bizarre_wizardry.item.ModItems;
import net.raguraccoon.bizarre_wizardry.spell.BizarreSpell;

public class SpellHudOverlay {

    public static final ResourceLocation NONE = new ResourceLocation(BizarreWizardry.MOD_ID,
            "textures/spell/none_spell.png");


    public static IGuiOverlay HUD_SPELLS = ((forgeGui, guiGraphics, partialTick, width, height) -> {
        int x = width / 2;
        int y = height;

        guiGraphics.blit(NONE, x - 135, y - 44,40, 20, 40, 20, 40, 20);

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
            BizarreSpell bizarreSpell = ClientSpellData.SPELL_ARSENAL[spellChoice];
            ResourceLocation texture = bizarreSpell.image;

            guiGraphics.blit(texture, x - 135, y - 44,40, 20, 40, 20, 40, 20);

        } else {
            guiGraphics.blit(NONE, x - 135, y - 44,40, 20, 40, 20, 40, 20);

        }
    });
}
