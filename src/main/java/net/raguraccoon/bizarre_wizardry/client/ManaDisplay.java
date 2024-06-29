package net.raguraccoon.bizarre_wizardry.client;


import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.raguraccoon.bizarre_wizardry.BizarreWizardry;


public class ManaDisplay {

    //Bar of mana
    public static final ResourceLocation MANA_BAR = new ResourceLocation(BizarreWizardry.MOD_ID,
            "textures/mana_display/mana_bar.png");


    public static IGuiOverlay MANA_HUD = ((forgeGui, guiGraphics, partialTick, width, height) -> {

        int x = width / 2;
        int y = height;
        double barHeightPercent = (double) ClientSpellData.getManaLevel() / ClientSpellData.getManaCap();
        int barPortion = (int) (100 * barHeightPercent);

        guiGraphics.blit(MANA_BAR, x + 270, y - 120,
                30, barPortion, 30, barPortion, 30, 100 );



    });


}
