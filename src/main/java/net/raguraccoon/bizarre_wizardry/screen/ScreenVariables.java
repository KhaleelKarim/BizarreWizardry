package net.raguraccoon.bizarre_wizardry.screen;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.raguraccoon.bizarre_wizardry.BizarreWizardry;
import net.raguraccoon.bizarre_wizardry.client.SpellHudOverlay;
import net.raguraccoon.bizarre_wizardry.util.SpellInformation;

import java.util.HashMap;

public class ScreenVariables {

    //Components to describe buttons
    public static final Component UNIVERSAL_BUTTON =
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.universal_button");
    public static final Component SPELL_SELECTION_BUTTON =
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.spell_selection_button");
    public static final Component NOTORIETY_BUTTON =
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.notoriety_button");


    public static final Component NO_SPELL_SELECTION_BUTTON =
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.no_spell_selection_button");
    public static final Component STOMP_SELECTION_BUTTON =
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.stomp_selection_button");
    public static final Component MAGICIANS_RED_SELECTION_BUTTON =
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.magicians_red_selection_button");
    public static final Component BLOODLETTING_SELECTION_BUTTON =
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.bloodletting_selection_button");


    public static final Component RESET_BUTTON =
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.reset_button");
    public static final Component UNLOCK =
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.unlock_button");
    public static final Component BACK =
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.back_button");



    public static final Component PUT_FIRST =
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.put_first_button");
    public static final Component PUT_SECOND =
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.put_second_button");
    public static final Component PUT_THIRD =
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.put_third_button");




    //Textures
    public static final ResourceLocation HOME_SCREEN =
            new ResourceLocation(BizarreWizardry.MOD_ID, "textures/gui/home_screen.png");
    public static final ResourceLocation FILLER =
            new ResourceLocation(BizarreWizardry.MOD_ID, "textures/gui/filler.png");
    public static final ResourceLocation NOTORIETY_SCREEN =
            new ResourceLocation(BizarreWizardry.MOD_ID, "textures/gui/notoriety_screen.png");


    public static final ResourceLocation STOMP =
            SpellHudOverlay.spellPictures[1];
    public static final ResourceLocation MAGICIANS_RED =
            SpellHudOverlay.spellPictures[2];
    public static final ResourceLocation BLOODLETTING =
            SpellHudOverlay.spellPictures[3];



    //List of menu buttons and hashmap to tell if it is selected
    public static Button[] menuButtons;
    public static HashMap<Button, Boolean> selectedMenuButtons = new HashMap<>();

    //List of buttons to view spell unlocking and hashmap
    public static Button[] viewSpellButtons;
    public static HashMap<Button, Boolean> selectedViewSpellButtons = new HashMap<>();

    //List of buttons to unlock spells and hashmap
    public static Button[] unlockSpellButtons;
    public static HashMap<Button, Boolean> selectedUnlockSpellButtons = new HashMap<>();
    public static HashMap<Button, Integer> unlockSpellButtonsPosition = new HashMap<>();

    //List of buttons to go back and hashmap
    public static Button[] backButtons;
    public static HashMap<Button, Boolean> selectedBackButtons = new HashMap<>();

    //List of selection buttons, a hashmap,
    //and an integer to determine which position to place spells into
    //and a hashmap to map a button to the spell to unlock
    public static Button[] selectionButtons;
    public static int positionToPlaceSpell;
    public static HashMap<Button, Boolean> selectedSelectionButtons = new HashMap<>();
    public static HashMap<Button, Integer> spellNumberSelectionButtons = new HashMap<>();


    //List of buttons to put spells in a specific position
    public static Button[] chooseButtons;
    public static HashMap<Button, Boolean> selectedChooseButtons = new HashMap<>();







    //List of spell requirements
    public static String[][] spellRequirements =
            {SpellInformation.STOMP_REQUIREMENTS, SpellInformation.MAGICIANS_RED_REQUIREMENTS,
                    SpellInformation.BLOODLETTING_REQUIREMENTS};

    //List of spell descriptions
    public static String[][] spellDescriptions =
            {SpellInformation.STOMP_DESCRIPTION, SpellInformation.MAGICIANS_RED_DESCRIPTION,
                    SpellInformation.BLOODLETTING_DESCRIPTION};



}