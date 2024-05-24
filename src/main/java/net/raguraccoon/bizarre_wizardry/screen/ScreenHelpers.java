package net.raguraccoon.bizarre_wizardry.screen;


import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.resources.ResourceLocation;
import net.raguraccoon.bizarre_wizardry.client.ClientSpellData;
import net.raguraccoon.bizarre_wizardry.client.SpellHudOverlay;


public class ScreenHelpers {


    //Helper method to hide view spell buttons
    public static void hideViewButtons() {
        for (int i = 0 ; i < ScreenVariables.viewSpellButtons.length ; ++i) {
            ScreenVariables.viewSpellButtons[i].active = false;
            ScreenVariables.viewSpellButtons[i].visible = false;
        }
    }

    //Hide all unlock buttons
    public static void hideUnlockButtons() {
        for (int i = 0 ; i < ScreenVariables.unlockSpellButtons.length ; ++i) {
            ScreenVariables.unlockSpellButtons[i].active = false;
            ScreenVariables.unlockSpellButtons[i].visible = false;
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

        return ClientSpellData.availableSpells[spellIndex] == 1;

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
        for (int i = 0 ; i < ScreenVariables.selectionButtons.length ; ++i) {
            ScreenVariables.selectionButtons[i].active = false;
            ScreenVariables.selectionButtons[i].visible = false;
            ScreenVariables.selectedSelectionButtons.put(ScreenVariables.selectionButtons[i], false);

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


    //Places requirements into hashmap
    public static void fillRequirements() {
        ScreenVariables.viewSpellRequirements.put(ScreenVariables.viewSpellButtons[0], new int[]{-1});
        ScreenVariables.viewSpellRequirements.put(ScreenVariables.viewSpellButtons[1], new int[]{-1});
        ScreenVariables.viewSpellRequirements.put(ScreenVariables.viewSpellButtons[2], new int[]{1, 2});
    }

    //Fills spell positions into hashmap
    public static void fillPositions() {

        for (int i = 0 ; i < ScreenVariables.viewSpellButtons.length ; ++i)
            ScreenVariables.spellNumberFromViewButton.put(ScreenVariables.viewSpellButtons[i], i + 1);

    }


    //Returns true if a spell's dependencies are fulfilled,
    //false otherwise
    public static boolean checkDependencies(Button button) {

        //Get the spell's dependencies
        int[] dependencies = ScreenVariables.viewSpellRequirements.get(button);

        //Iterate thru to see if they are fulfilled
        for (int spell : dependencies) {

            if (spell == -1)
                return true;
            else if (ClientSpellData.availableSpells[spell] != 1)
                return false;

        }

        return true;

    }

    public static void setLock(ImageButton button, GuiGraphics graphics) {

        //Get the button's position
        int leftPos = button.getX();
        int topPos = button.getY();

        int spellNumber = ClientSpellData.availableSpells[ScreenVariables.spellNumberFromViewButton.get(button)];

        //If the button is hard-locked, render lock-icon and darkening effect
        if (!checkDependencies(button)) {

            RenderSystem.setShaderTexture(0, ScreenVariables.HARD_LOCK);
            graphics.blit(ScreenVariables.HARD_LOCK, leftPos + 14, topPos + 25,
                    0, 0, 12, 12, 12, 12);

        } else if (checkDependencies(button) && spellNumber != 1) {

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
