package net.raguraccoon.bizarre_wizardry.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.raguraccoon.bizarre_wizardry.BizarreWizardry;
import net.raguraccoon.bizarre_wizardry.client.ClientSpellData;
import net.raguraccoon.bizarre_wizardry.client.SpellHudOverlay;
import net.raguraccoon.bizarre_wizardry.networking.ModMessages;
import net.raguraccoon.bizarre_wizardry.networking.packet.SetAvailableSpellsC2SPacket;
import net.raguraccoon.bizarre_wizardry.networking.packet.SetCurrentSpellsC2SPacket;
import net.raguraccoon.bizarre_wizardry.networking.packet.ValidateUnlockC2SPacket;
import net.raguraccoon.bizarre_wizardry.statistics.PlayerRequirementChecker;
import net.raguraccoon.bizarre_wizardry.statistics.PlayerStatsGetter;
import net.raguraccoon.bizarre_wizardry.util.SpellDescriptions;
import net.raguraccoon.bizarre_wizardry.util.SpellRequirements;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;


public class BizarreWizardryMainScreen extends Screen {

    //Useless title
    private static final Component TITLE =
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen");



    //Components to describe buttons
    private static final Component UNIVERSAL_BUTTON =
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.universal_button");
    private static final Component SPELL_SELECTION_BUTTON =
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.spell_selection_button");
    private static final Component NOTORIETY_BUTTON =
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.notoriety_button");


    private static final Component NO_SPELL_SELECTION_BUTTON =
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.no_spell_selection_button");
    private static final Component STOMP_SELECTION_BUTTON =
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.stomp_selection_button");
    private static final Component MAGICIANS_RED_SELECTION_BUTTON =
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.magicians_red_selection_button");
    private static final Component BLOODLETTING_SELECTION_BUTTON =
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.bloodletting_selection_button");


    private static final Component RESET_BUTTON =
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.reset_button");
    private static final Component UNLOCK =
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.unlock_button");
    private static final Component BACK =
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.back_button");



    private static final Component PUT_FIRST =
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.put_first_button");
    private static final Component PUT_SECOND =
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.put_second_button");
    private static final Component PUT_THIRD =
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.put_third_button");




    //Textures
    private static final ResourceLocation HOME_SCREEN =
            new ResourceLocation(BizarreWizardry.MOD_ID, "textures/gui/home_screen.png");
    private static final ResourceLocation FILLER =
            new ResourceLocation(BizarreWizardry.MOD_ID, "textures/gui/filler.png");
    private static final ResourceLocation STOMP =
            new ResourceLocation(BizarreWizardry.MOD_ID, "textures/gui/buttons/stomp_button.png");
    private static final ResourceLocation MAGICIANS_RED =
            new ResourceLocation(BizarreWizardry.MOD_ID, "textures/gui/buttons/magicians_red_button.png");
    private static final ResourceLocation BLOODLETTING =
            new ResourceLocation(BizarreWizardry.MOD_ID, "textures/gui/buttons/bloodletting_button.png");


    //Information about main screen and player
    private final int imageWidth, imageHeight;
    private Player player = Minecraft.getInstance().player;
    private int leftPos, topPos;


    //Stats getter
    //PlayerStatsGetter statsGetter = new PlayerStatsGetter((LocalPlayer) player);
    PlayerRequirementChecker requirementChecker = new PlayerRequirementChecker(player);


    //Menu Button objects
    private Button universalSpellsButton;
    private Button spellSelectionButton;
    private Button notorietyButton;


    //View spell unlocking buttons
    private Button stompButton;
    private Button magiciansRedButton;
    private Button bloodlettingButton;
    private Button resetButton;


    //Unlocking spell buttons
    private Button unlockStompButton;
    private Button unlockMagiciansRedButton;
    private Button unlockBloodLettingButton;


    //Buttons to go to a previous screen
    private Button backToUniversal;
    private Button backToSpellSelection;
    private Button backToNotoriety;



    //Buttons to select a spell
    private Button selectNoSpellButton;
    private Button selectStompButton;
    private Button selectMagiciansRedButton;
    private Button selectBloodlettingButton;



    //Buttons to put a spell in a specific slot
    private Button spell1;
    private Button spell2;
    private Button spell3;



    //List of menu buttons and hashmap to tell if it is selected
    Button[] menuButtons;
    HashMap<Button, Boolean> selectedMenuButtons = new HashMap<>();

    //List of buttons to view spell unlocking and hashmap
    Button[] viewSpellButtons;
    HashMap<Button, Boolean> selectedViewSpellButtons = new HashMap<>();

    //List of buttons to unlock spells and hashmap
    Button[] unlockSpellButtons;
    HashMap<Button, Boolean> selectedUnlockSpellButtons = new HashMap<>();
    HashMap<Button, Integer> unlockSpellButtonsPosition = new HashMap<>();

    //List of buttons to go back and hashmap
    Button[] backButtons;
    HashMap<Button, Boolean> selectedBackButtons = new HashMap<>();

    //List of selection buttons and 2 hashmaps, 1 if the button is selected
    //and 1 to determine which spell position to place it into
    Button[] selectionButtons;
    HashMap<Button, Integer> selectionButtonsPositions = new HashMap<>();
    HashMap<Button, Boolean> selectedSelectionButtons = new HashMap<>();


    //List of buttons to put spells in a specific position
    Button[] chooseButtons;
    HashMap<Button, Boolean> selectedChooseButtons = new HashMap<>();




    //List of spell requirements
    SpellRequirements[] spellRequirements = {SpellRequirements.STOMP, SpellRequirements.MAGICIANS_RED, SpellRequirements.BLOODLETTING};

    //List of spell descriptions
    SpellDescriptions[] spellDescriptions = {SpellDescriptions.STOMP, SpellDescriptions.MAGICIANS_RED, SpellDescriptions.BLOODLETTING};



    //Booleans to test when to render what
    boolean renderUniversalScreen = false;
    boolean renderSpellSelectionScreen = false;
    boolean renderNotorietyScreen = false;


    
    public BizarreWizardryMainScreen() {
        super(TITLE);

        this.imageWidth = 500;
        this.imageHeight = 250;

//        this.player = player;

    }


    //This method is called before render
    @Override
    protected void init() {
        super.init();

        this.leftPos = (this.width - this.imageWidth) / 2;  //These coordinates refer to the upper left corner
        this.topPos = (this.height - this.imageHeight) / 2; //of the main texture, not the screen


        //Button to open menu for general spell tree
        this.universalSpellsButton = addRenderableWidget(Button.builder(
                UNIVERSAL_BUTTON,
                        this::handleUniversalButton)
                .bounds(this.leftPos, this.topPos, 165, 20)
                .tooltip(Tooltip.create(UNIVERSAL_BUTTON))
                .build());

        //Button to go back to general spell tree
        this.backToUniversal = addRenderableWidget(Button.builder(
                BACK, this::handleBackUniversalButton)
                .bounds(this.leftPos + 20, this.topPos + 160, 30, 20)
                .build());
        backToUniversal.active = false;
        backToUniversal.visible = false;


        //Button to open menu for class-specific spells
        this.spellSelectionButton = addRenderableWidget(Button.builder(
                SPELL_SELECTION_BUTTON,
                        this::handleUniqueButton)
                .bounds(this.leftPos + 165, this.topPos, 170, 20)
                .tooltip(Tooltip.create(SPELL_SELECTION_BUTTON))
                .build());

        this.backToSpellSelection = addRenderableWidget(Button.builder(
                        BACK, this::handleBackSpellSelectionButton)
                .bounds(this.leftPos + 20, this.topPos + 210, 30, 20)
                .build());
        backToSpellSelection.active = false;
        backToSpellSelection.visible = false;



        //Button to display info about your notoriety
        this.notorietyButton = addRenderableWidget(Button.builder(
                NOTORIETY_BUTTON,
                        this::handleNotorietyButton)
                .bounds(this.leftPos + 335, this.topPos, 165, 20)
                .tooltip(Tooltip.create(NOTORIETY_BUTTON))
                .build());

        this.backToNotoriety = addRenderableWidget(Button.builder(BACK, this::handleBackNotorietyButton).build());



        //This group of buttons should only appear in the universal screen
        this.stompButton = addRenderableWidget(new ImageButton(this.leftPos + 100, this.topPos + 100,
                25, 25, 0, 0,
                2, STOMP,25,25, this::handleStompButton));
        this.stompButton.active = false;
        this.stompButton.visible = false;


        this.unlockStompButton = addRenderableWidget(makeUnlockButton(this::handleUnlockStompButton));
        this.unlockStompButton.active = false;
        this.unlockStompButton.visible = false;


        this.magiciansRedButton = addRenderableWidget(new ImageButton(this.leftPos + 200, this.topPos + 100,
                25, 25, 0, 0,
                2, MAGICIANS_RED,25,25, this::handleMagiciansRedButton));
        this.magiciansRedButton.active = false;
        this.magiciansRedButton.visible = false;


        this.unlockMagiciansRedButton = addRenderableWidget(makeUnlockButton(this::handleUnlockMagiciansRedButton));
        this.unlockMagiciansRedButton.active = false;
        this.unlockMagiciansRedButton.visible = false;


        this.bloodlettingButton = addRenderableWidget(new ImageButton(this.leftPos + 300, this.topPos + 100,
                25, 25, 0, 0,
                2, BLOODLETTING,25,25, this::handleBloodlettingButton));
        this.bloodlettingButton.active = false;
        this.bloodlettingButton.visible = false;


        this.unlockBloodLettingButton = addRenderableWidget(makeUnlockButton(this::handleUnlockBloodlettingButton));
        this.unlockBloodLettingButton.active = false;
        this.unlockBloodLettingButton.visible = false;


        //Button to remove all spells from current spells list
//        this.resetButton = addRenderableWidget(Button.builder(
//                RESET_BUTTON,
//                    this::handleResetButton)
//                .bounds(this.leftPos + 50, this.topPos + 50, 50, 50)
//                .build());




        //Buttons to place a spell into current spells array
        this.selectNoSpellButton = addRenderableWidget(Button.builder(NO_SPELL_SELECTION_BUTTON, this::handleSelectNoSpellButton)
                .bounds(this.leftPos + 50, this.topPos + 50, 100, 20)
                .build());

        this.selectStompButton = addRenderableWidget(Button.builder(STOMP_SELECTION_BUTTON, this::handleSelectStompButton)
                .bounds(this.leftPos + 150, this.topPos + 50, 100, 20)
                .build());

        this.selectMagiciansRedButton = addRenderableWidget(Button.builder(MAGICIANS_RED_SELECTION_BUTTON, this::handleSelectMagiciansRedButton)
                .bounds(this.leftPos + 250, this.topPos + 50, 100, 20)
                .build());

        this.selectBloodlettingButton = addRenderableWidget(Button.builder(BLOODLETTING_SELECTION_BUTTON, this::handleSelectBloodlettingButton)
                .bounds(this.leftPos + 50, this.topPos + 70, 100, 20)
                .build());






        //Buttons to change what position a spell is going into
        this.spell1 = addRenderableWidget(Button.builder(PUT_FIRST, this::handleSpell1Button)
                .bounds(this.leftPos + 76, this.topPos + 175, 70, 30)
                .build());

        this.spell2 = addRenderableWidget(Button.builder(PUT_SECOND, this::handleSpell2Button)
                .bounds(this.leftPos + 215, this.topPos + 175, 70, 30)
                .build());

        this.spell3 = addRenderableWidget(Button.builder(PUT_THIRD, this::handleSpell3Button)
                .bounds(this.leftPos + 354, this.topPos + 175, 70, 30)
                .build());



        //Putting buttons into their respective arrays
        //Also take care of the boolean arrays
        this.menuButtons = new Button[]{universalSpellsButton, spellSelectionButton, notorietyButton};
        for (Button button : menuButtons)
            selectedMenuButtons.put(button, false);

        this.viewSpellButtons = new Button[]{stompButton, magiciansRedButton, bloodlettingButton};
        for (Button button : viewSpellButtons)
            selectedViewSpellButtons.put(button, false);

        this.unlockSpellButtons = new Button[]{unlockStompButton, unlockMagiciansRedButton, unlockBloodLettingButton};
        for (Button button : unlockSpellButtons)
            selectedUnlockSpellButtons.put(button, false);
        for (int i = 0 ; i < unlockSpellButtons.length ; ++i)
            unlockSpellButtonsPosition.put(unlockSpellButtons[i], i + 1);


        this.backButtons = new Button[]{backToUniversal, backToSpellSelection, backToNotoriety};
        for (Button button : backButtons)
            selectedBackButtons.put(button, false);
        
        this.selectionButtons = new Button[]{selectNoSpellButton, selectStompButton, selectMagiciansRedButton, selectBloodlettingButton};
        for (Button button : selectionButtons) {
            selectionButtonsPositions.put(button, 0);
            selectedSelectionButtons.put(button, false);
        }

        this.chooseButtons = new Button[]{spell1, spell2, spell3};
        for (Button button : chooseButtons)
            selectedChooseButtons.put(button, false);
        


        //Initializing the appropriate ones to invisible/inactive right away
        for (int i = 0 ; i < viewSpellButtons.length ; ++i) {
            viewSpellButtons[i].active = false;
            viewSpellButtons[i].visible = false;

            unlockSpellButtons[i].active = false;
            unlockSpellButtons[i].visible = false;
        }

        for (int i = 0 ; i < backButtons.length ; ++i) {
            backButtons[i].active = false;
            backButtons[i].visible = false;
        }

        for (int i = 0 ; i < selectionButtons.length ; ++i) {
            selectionButtons[i].active = false;
            selectionButtons[i].visible = false;
        }

        for (int i = 0 ; i < chooseButtons.length ; ++i) {
            chooseButtons[i].active = false;
            chooseButtons[i].visible = false;
        }


    }





    //Renders background and sets buttons to invisible when appropriate
    //This method is called after init
    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {

        renderBackground(graphics);

        RenderSystem.setShaderTexture(0, HOME_SCREEN);
        graphics.blit(HOME_SCREEN, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, 500, 250);


        //Check and see which screens we should be rendering

        //For the universal spell screen
        if (renderUniversalScreen) {

            //Iterate through all view spell buttons and set them visible/active
            for (int i = 0 ; i < viewSpellButtons.length ; ++i) {
                viewSpellButtons[i].visible = true;
                viewSpellButtons[i].active = true;
            }


            //After they are set as visible/active, check to see if they are selected in which case
            //the proper menu should be displayed!
            for (int i = 0 ; i < viewSpellButtons.length ; ++i) {

                if (selectedViewSpellButtons.get(viewSpellButtons[i])) {
                    renderSpellScreen(graphics, i);

                    hideViewButtons();

                    break;


                }

            }

        }


        //For the spell selection screen

        if (renderSpellSelectionScreen) {

            //Clean up other screens
            hideViewButtons();


            //Display each button to change spells
            //Display current spells
            displaySelectionScreenMenu(graphics);


            //Check each choose button to see if it is selected
            for (int i = 0 ; i < chooseButtons.length ; ++i) {

                //If the button is selected
                if (selectedChooseButtons.get(chooseButtons[i])) {

                    //Display the appropriate screen
                    displaySelectionSpellButtons(graphics, ClientSpellData.currentSpells[i]);

                    //Display back button
                    backToSpellSelection.active = true;
                    backToSpellSelection.visible = true;

                    break;

                }

            }



        } else {

            backToSpellSelection.visible = false;
            backToSpellSelection.active = false;


            hideChangeButtons();
            hideSelectionButtons();

        }



        super.render(graphics, mouseX, mouseY, partialTicks);
    }



    //A bunch of event handlers
    private void handleUniversalButton(Button button) {
        selectedMenuButtons.put(button, !(selectedMenuButtons.get(button)));
        this.renderUniversalScreen = !this.renderUniversalScreen;

        renderSpellSelectionScreen = false;
        renderNotorietyScreen = false;

        hideBackButtons();
        hideUnlockButtons();

    }

    private void handleBackUniversalButton(Button button) {
        viewButtonsFalse();
        hideUnlockButtons();
        button.active = false;
        button.visible = false;


        this.renderUniversalScreen = true;
        selectedMenuButtons.put(universalSpellsButton, true);
    }

    private void handleUniqueButton(Button button) {
        selectedMenuButtons.put(button, !(selectedMenuButtons.get(button)));
        this.renderSpellSelectionScreen = !this.renderSpellSelectionScreen;

        renderUniversalScreen = false;
        renderNotorietyScreen = false;

        hideBackButtons();
        hideUnlockButtons();

    }

    private void handleBackSpellSelectionButton(Button button) {
        button.active = false;
        button.visible = false;

        for (Button chooseButton : selectedChooseButtons.keySet())
            selectedChooseButtons.put(chooseButton, false);



        this.renderSpellSelectionScreen = true;
        //selectedMenuButtons.put(button, !selectedMenuButtons.get(button));
    }


    private void handleNotorietyButton(Button button) {
        selectedMenuButtons.put(button, !(selectedMenuButtons.get(button)));
        this.renderNotorietyScreen = !this.renderNotorietyScreen;

        renderUniversalScreen = false;
        renderSpellSelectionScreen = false;

    }

    private void handleBackNotorietyButton(Button button) {

    }
    
    
    

    private void handleStompButton(Button button) {

        selectedViewSpellButtons.put(button, !(selectedViewSpellButtons.get(button)));

    }


    private void handleUnlockStompButton(Button button) {
        selectedUnlockSpellButtons.put(button, !(selectedUnlockSpellButtons.get(button)));

        ModMessages.sendToServer(new SetAvailableSpellsC2SPacket(1));

        button.active = false;

    }
    
    

    private void handleMagiciansRedButton(Button button) {

        selectedViewSpellButtons.put(button, !(selectedViewSpellButtons.get(button)));

    }
    private void handleUnlockMagiciansRedButton(Button button) {
        selectedUnlockSpellButtons.put(button, !(selectedUnlockSpellButtons.get(button)));

        ModMessages.sendToServer(new SetAvailableSpellsC2SPacket(2));

        button.active = false;

    }
    
    

    private void handleBloodlettingButton(Button button) {

        selectedViewSpellButtons.put(button, !(selectedViewSpellButtons.get(button)));

    }
    private void handleUnlockBloodlettingButton(Button button) {
        selectedUnlockSpellButtons.put(button, !(selectedUnlockSpellButtons.get(button)));

        ModMessages.sendToServer(new SetAvailableSpellsC2SPacket(3));

        button.active = false;

    }
    
    
    
    
    //For handling buttons that put spells into your current spells list
    private void handleSelectStompButton(Button button) {
        
        int position = selectionButtonsPositions.get(button);
        selectedSelectionButtons.put(button, !selectedSelectionButtons.get(button));
        int[] spellInfo = {1, position};
        
        ModMessages.sendToServer(new SetCurrentSpellsC2SPacket(spellInfo));
        
    }

    private void handleSelectMagiciansRedButton(Button button) {

        int position = selectionButtonsPositions.get(button);
        selectedSelectionButtons.put(button, !selectedSelectionButtons.get(button));
        int[] spellInfo = {2, position};

        ModMessages.sendToServer(new SetCurrentSpellsC2SPacket(spellInfo));

    }

    private void handleSelectBloodlettingButton(Button button) {

        int position = selectionButtonsPositions.get(button);
        selectedSelectionButtons.put(button, !selectedSelectionButtons.get(button));
        int[] spellInfo = {3, position};

        ModMessages.sendToServer(new SetCurrentSpellsC2SPacket(spellInfo));

    }

    private void handleSelectNoSpellButton(Button button) {

        int position = selectionButtonsPositions.get(button);
        selectedSelectionButtons.put(button, !selectedSelectionButtons.get(button));
        int[] spellInfo = {0, position};

        ModMessages.sendToServer(new SetCurrentSpellsC2SPacket(spellInfo));

    }




    //For handling choose buttons
    private void handleSpell1Button(Button button) {

        selectedChooseButtons.put(button, !selectedChooseButtons.get(button));

        for (Button selectionButton : selectionButtonsPositions.keySet()) {
            selectionButtonsPositions.put(selectionButton, 0);
        }

    }

    private void handleSpell2Button(Button button) {

        selectedChooseButtons.put(button, !selectedChooseButtons.get(button));

        for (Button selectionButton : selectionButtonsPositions.keySet()) {
            selectionButtonsPositions.put(selectionButton, 1);
        }

    }

    private void handleSpell3Button(Button button) {

        selectedChooseButtons.put(button, !selectedChooseButtons.get(button));

        for (Button selectionButton : selectionButtonsPositions.keySet()) {
            selectionButtonsPositions.put(selectionButton, 2);
        }

    }

    


    private void handleResetButton(Button button) {
        for (int i = 0 ; i < ClientSpellData.getSpellCapacity() ; ++i) {
            int[] spellInfo = {0, i};
            ModMessages.sendToServer(new SetCurrentSpellsC2SPacket(spellInfo));
        }
    }


    //We ain't pausing the screen here
    @Override
    public boolean isPauseScreen() {
        return false;
    }


    //Helper method to render screens for unlocking spells
    private void renderSpellScreen(GuiGraphics graphics, int spellIndex) {

        //Get some basic info
        Component description = spellDescriptions[spellIndex].getDescription();
        Component requirements = spellRequirements[spellIndex].getRequirement();
        ResourceLocation spellImage = SpellHudOverlay.spellPictures[spellIndex + 1];
        Button unlockButton = unlockSpellButtons[spellIndex];
        String currentSpell = ClientSpellData.spellsLibrary[spellIndex + 1];


        //Begin by rendering background
        RenderSystem.setShaderTexture(0, FILLER);
        graphics.blit(FILLER, this.leftPos + 7, this.topPos + 20, 0, 0, 486, 223, 486, 223);

        //Write the description of the spell
        graphics.drawString(this.font, description, this.leftPos + 20, this.topPos + 20, 0x404040, false);

        //Write requirements
        graphics.drawString(this.font, requirements, this.leftPos + 20, this.topPos + 60, 0x404040, false);

        //Draw image
        RenderSystem.setShaderTexture(0, spellImage);
        graphics.blit(spellImage, this.leftPos + 200, this.topPos + 200, 0, 0, 40, 20, 40, 20);

        //Make back button visible
        backToUniversal.visible = true;
        backToUniversal.active = true;

        //Make unlocking button visible
        unlockButton.visible = true;
        unlockButton.active = false;

        //If the spell is unlocked, don't let the player click it anymore
        if (ClientSpellData.availableSpells[unlockSpellButtonsPosition.get(unlockButton)] == 1) {
            unlockButton.active = false;
        } else {

            //Check if the spell is unlockable
            ModMessages.sendToServer(new ValidateUnlockC2SPacket(currentSpell));

            //If it is, let the user click the button
            if (ClientSpellData.spellBooleans[spellIndex])
                unlockButton.active = true;
            else //Set it false otherwise
                unlockButton.active = false;

        }

    }


    //Helper method to create a button that unlocks a spell
    private Button makeUnlockButton(Button.OnPress handler) {

        Button button = Button.builder(UNLOCK, handler)
                .bounds(this.leftPos + 20, this.topPos + 200, 50, 20)
                .tooltip(Tooltip.create(UNLOCK))
                .build();

        return button;

    }

    //Helper method to hide view spell buttons
    private void hideViewButtons() {
        for (int i = 0 ; i < viewSpellButtons.length ; ++i) {
            viewSpellButtons[i].active = false;
            viewSpellButtons[i].visible = false;
        }
    }

    //Hide all unlock buttons
    private void hideUnlockButtons() {
        for (int i = 0 ; i < unlockSpellButtons.length ; ++i) {
            unlockSpellButtons[i].active = false;
            unlockSpellButtons[i].visible = false;
        }
    }


    //Helper method to set all button's booleans to false
    private void viewButtonsFalse() {

        for (Button key : selectedViewSpellButtons.keySet()) {
            selectedViewSpellButtons.put(key, false);
        }

    }


    //Helper method to display the main spell selection screen
    private void displaySelectionScreenMenu(GuiGraphics graphics) {

        //Hide selection buttons and display background
        RenderSystem.setShaderTexture(0, FILLER);
        graphics.blit(FILLER, this.leftPos + 7, this.topPos + 20, 0, 0, 486, 223, 486, 223);

        backToSpellSelection.active = false;
        backToSpellSelection.visible = false;


        hideSelectionButtons();

        for (int i = 0 ; i < chooseButtons.length ; ++i) {
            chooseButtons[i].active = true;
            chooseButtons[i].visible = true;
        }


        int offset = 94;

        for (int i = 0 ; i < ClientSpellData.currentSpells.length ; ++i) {

            ResourceLocation texture = SpellHudOverlay.spellPictures[ClientSpellData.currentSpells[i]];

            RenderSystem.setShaderTexture(0, texture);
            graphics.blit(texture, this.leftPos + offset, this.topPos + 130, 0, 0, 40, 20, 40, 20);

            offset += 138;

            chooseButtons[i].active = true;
            chooseButtons[i].visible = true;

        }

    }

    //Helper method to view spell selection buttons
    private void displaySelectionSpellButtons(GuiGraphics graphics, int spellToChange) {

        //Hide change spell buttons
        hideChangeButtons();

        //Make filler background
        RenderSystem.setShaderTexture(0, FILLER);
        graphics.blit(FILLER, this.leftPos + 7, this.topPos + 20, 0, 0, 486, 223, 486, 223);

        for (int i = 0 ; i < selectionButtons.length ; ++i) {

            //Make all visible
            selectionButtons[i].visible = true;

            //Only unlocked ones should be clickable
            if (spellUnlocked(i))
                selectionButtons[i].active = true;

        }

        //The no spell should always be clickable
        selectionButtons[0].active = true;


        //Display image of spell we are going to change

        ResourceLocation spell = SpellHudOverlay.spellPictures[spellToChange];

        graphics.blit(spell, this.leftPos + 400, this.topPos + 100, 0, 0, 40, 20, 40, 20);


    }


    //Helper method to see if a spell is unlocked
    private boolean spellUnlocked(int spellIndex) {

        if (ClientSpellData.availableSpells[spellIndex] == 1)
            return true;

        return false;

    }

    //Hides all change buttons
    private void hideChangeButtons() {
        for (int i = 0 ; i < chooseButtons.length ; ++i) {
            chooseButtons[i].active = false;
            chooseButtons[i].visible = false;
        }
    }

    //Hides all selection buttons
    private void hideSelectionButtons() {
        for (int i = 0 ; i < selectionButtons.length ; ++i) {
            selectionButtons[i].active = false;
            selectionButtons[i].visible = false;
            selectedSelectionButtons.put(selectionButtons[i], false);

        }
    }

    //Hides all back buttons
    private void hideBackButtons() {
        for (int i = 0 ; i < backButtons.length ; ++i) {
            backButtons[i].active = false;
            backButtons[i].visible = false;
            selectedBackButtons.put(backButtons[i], false);
        }
    }


}
