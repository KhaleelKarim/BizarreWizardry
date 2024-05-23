package net.raguraccoon.bizarre_wizardry.screen;


import net.minecraft.client.gui.components.Button;
import net.raguraccoon.bizarre_wizardry.client.ClientSpellData;


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

}
