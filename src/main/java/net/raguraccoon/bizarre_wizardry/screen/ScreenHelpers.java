package net.raguraccoon.bizarre_wizardry.screen;


import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.raguraccoon.bizarre_wizardry.client.ClientSpellData;
import net.raguraccoon.bizarre_wizardry.spell.BizarreSpell;


public class ScreenHelpers {


    //Helper method to hide view spell buttons
    public static void hideViewButtons() {
        for (int i = 1 ; i < ClientSpellData.SPELLS_LIBRARY.length ; ++i) {

            ImageButton currentButton = ClientSpellData.SPELLS_LIBRARY[i].viewButton;

            currentButton.active = false;
            currentButton.visible = false;

        }
    }

    //Hide all unlock buttons
    public static void hideUnlockButtons() {
        for (int i = 1 ; i < ClientSpellData.SPELLS_LIBRARY.length ; ++i) {

            Button currentButton = ClientSpellData.SPELLS_LIBRARY[i].unlockButton;

            currentButton.active = false;
            currentButton.visible = false;

        }
    }


    //Helper method to set all button's booleans to false
    public static void viewButtonsFalse() {

        for (Button key : ScreenVariables.selectedViewSpellButtons.keySet()) {
            ScreenVariables.selectedViewSpellButtons.put(key, false);
        }

    }




    //Helper method to see if a spell is unlocked
    public static boolean spellUnlocked(int spellIndex) {

        BizarreSpell spell = BizarreSpell.spellFromNumber(spellIndex);
        return spell.available;

    }

    //Hides all change buttons
    public static void hideChangeButtons() {
        for (int i = 0 ; i < ScreenVariables.chooseButtons.length ; ++i) {
            ScreenVariables.chooseButtons[i].active = false;
            ScreenVariables.chooseButtons[i].visible = false;
        }
    }

    //Hides all selection buttons
    public static void hideSelectionButtons() {
        for (int i = 0 ; i < ClientSpellData.SPELLS_LIBRARY.length ; ++i) {

            Button currentButton = ClientSpellData.SPELLS_LIBRARY[i].selectButton;

            currentButton.active = false;
            currentButton.visible = false;
            ScreenVariables.selectedSelectionButtons.put(currentButton, false);

        }
    }

    //Hides all back buttons
    public static void hideBackButtons() {
        for (int i = 0 ; i < ScreenVariables.backButtons.length ; ++i) {
            ScreenVariables.backButtons[i].active = false;
            ScreenVariables.backButtons[i].visible = false;
            ScreenVariables.selectedBackButtons.put(ScreenVariables.backButtons[i], false);
        }
    }




    //Returns true if a spell's dependencies are fulfilled,
    //false otherwise
    public static boolean checkDependencies(BizarreSpell spell) {

        //Get the spell's dependencies
        int[] dependencies = spell.dependencies;

        //Iterate thru to see if they are fulfilled
        for (int i : dependencies) {

            if (i == -1)
                return true;
            else if (!ClientSpellData.SPELLS_LIBRARY[i].available)
                return false;

        }

        return true;

    }

    public static void setLock(BizarreSpell spell, GuiGraphics graphics) {

        ImageButton button = spell.viewButton;

        //Get the button's position
        int leftPos = button.getX();
        int topPos = button.getY();

        int spellNumber = spell.spellNumber;

        //If the button is hard-locked, render lock-icon and darkening effect
        if (!checkDependencies(spell)) {

            RenderSystem.setShaderTexture(0, ScreenVariables.HARD_LOCK);
            graphics.blit(ScreenVariables.HARD_LOCK, leftPos + 14, topPos + 25,
                    0, 0, 12, 12, 12, 12);

        } else if (checkDependencies(spell) && !spell.available) {

            RenderSystem.setShaderTexture(0, ScreenVariables.SOFT_LOCK);
            graphics.blit(ScreenVariables.SOFT_LOCK, leftPos + 14, topPos + 25,
                    0, 0, 12, 12, 12, 12);

        } else {

            RenderSystem.setShaderTexture(0, ScreenVariables.NO_LOCK);
            graphics.blit(ScreenVariables.NO_LOCK, leftPos + 14, topPos + 25,
                    0, 0, 12, 12, 12, 12);

        }

    }


}
