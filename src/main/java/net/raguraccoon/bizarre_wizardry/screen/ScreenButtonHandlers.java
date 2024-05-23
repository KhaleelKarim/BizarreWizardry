package net.raguraccoon.bizarre_wizardry.screen;

import net.minecraft.client.gui.components.Button;
import net.raguraccoon.bizarre_wizardry.client.ClientSpellData;
import net.raguraccoon.bizarre_wizardry.networking.ModMessages;
import net.raguraccoon.bizarre_wizardry.networking.packet.SetAvailableSpellsC2SPacket;
import net.raguraccoon.bizarre_wizardry.networking.packet.SetCurrentSpellsC2SPacket;

public class ScreenButtonHandlers {

    //For any menu button
    public static void handleMenuButton(Button button) {

        //Set all to false and then only universal to its opposite
        boolean currentValue = ScreenVariables.selectedMenuButtons.get(button);
        ScreenVariables.selectedMenuButtons.replaceAll((b, v) -> false);
        ScreenVariables.selectedMenuButtons.put(button, !(currentValue));

    }


    //For any back button
    public static void handleBackButton(Button button) {

        //Back button should always hide itself
        button.active = false;
        button.visible = false;

        //Set certain bools to false
        ScreenVariables.selectedViewSpellButtons.replaceAll((b, v) -> false);
        ScreenVariables.selectedSelectionButtons.replaceAll((b, v) -> false);
        ScreenVariables.selectedChooseButtons.replaceAll((b, v) -> false);

        //Hide unlock buttons
        ScreenHelpers.hideUnlockButtons();

    }





    //For any view spell button
    public static void handleViewSpellButton(Button button) {


        //Set all to false and then only universal to its opposite
        boolean currentValue = ScreenVariables.selectedViewSpellButtons.get(button);
        ScreenVariables.selectedViewSpellButtons.replaceAll((b, v) -> false);
        ScreenVariables.selectedViewSpellButtons.put(button, !(currentValue));


    }


    //For any unlock button
    public static void handleUnlockButton(Button button) {

        //Sets button clicked to true,
        //Unlocks the button,
        //and sets the button to inactive

        int spellToUnlock = ScreenVariables.unlockSpellButtonsPosition.get(button);

        boolean currentValue = ScreenVariables.selectedUnlockSpellButtons.get(button);
        ScreenVariables.selectedUnlockSpellButtons.replaceAll((b, v) -> false);
        ScreenVariables.selectedUnlockSpellButtons.put(button, !(currentValue));

        ModMessages.sendToServer(new SetAvailableSpellsC2SPacket(spellToUnlock));

        button.active = false;

    }




    //For handling buttons that put spells into your current spells list
    public static void handleSelectButton(Button button) {

        int position = ScreenVariables.positionToPlaceSpell;
        int spellToUnlock = ScreenVariables.spellNumberSelectionButtons.get(button);
        int[] spellInfo = {spellToUnlock, position};


        boolean currentValue = ScreenVariables.selectedSelectionButtons.get(button);
        ScreenVariables.selectedSelectionButtons.replaceAll((b, v) -> false);
        ScreenVariables.selectedSelectionButtons.put(button, !(currentValue));


        ModMessages.sendToServer(new SetCurrentSpellsC2SPacket(spellInfo));

    }


    //Special method for the null spell
    public static void handleSelectNoSpellButton(Button button) {

        int position = ScreenVariables.positionToPlaceSpell;
        ScreenVariables.selectedSelectionButtons.put(button, !ScreenVariables.selectedSelectionButtons.get(button));
        int[] spellInfo = {0, position};

        ModMessages.sendToServer(new SetCurrentSpellsC2SPacket(spellInfo));

    }




    //For handling choose buttons
    public static void handleSpell1Button(Button button) {

        boolean currentValue = ScreenVariables.selectedChooseButtons.get(button);
        ScreenVariables.selectedChooseButtons.replaceAll((b, v) -> false);
        ScreenVariables.selectedChooseButtons.put(button, !(currentValue));

        ScreenVariables.positionToPlaceSpell = 0;

    }

    public static void handleSpell2Button(Button button) {

        boolean currentValue = ScreenVariables.selectedChooseButtons.get(button);
        ScreenVariables.selectedChooseButtons.replaceAll((b, v) -> false);
        ScreenVariables.selectedChooseButtons.put(button, !(currentValue));

        ScreenVariables.positionToPlaceSpell = 1;

    }

    public static void handleSpell3Button(Button button) {

        boolean currentValue = ScreenVariables.selectedChooseButtons.get(button);
        ScreenVariables.selectedChooseButtons.replaceAll((b, v) -> false);
        ScreenVariables.selectedChooseButtons.put(button, !(currentValue));

        ScreenVariables.positionToPlaceSpell = 2;

    }




    public static void handleResetButton(Button button) {
        for (int i = 0; i < ClientSpellData.getSpellCapacity() ; ++i) {
            int[] spellInfo = {0, i};
            ModMessages.sendToServer(new SetCurrentSpellsC2SPacket(spellInfo));
        }
    }

}
