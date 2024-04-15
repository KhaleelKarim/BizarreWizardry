package net.raguraccoon.bizarre_wizardry.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.raguraccoon.bizarre_wizardry.BizarreWizardry;
import net.raguraccoon.bizarre_wizardry.client.ClientSpellData;
import net.raguraccoon.bizarre_wizardry.client.SpellHudOverlay;
import net.raguraccoon.bizarre_wizardry.networking.ModMessages;
import net.raguraccoon.bizarre_wizardry.networking.packet.SetAvailableSpellsC2SPacket;
import net.raguraccoon.bizarre_wizardry.networking.packet.SetCurrentSpellsC2SPacket;
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
    private static final Component UNIQUE_BUTTON =
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.unique_button");
    private static final Component NOTORIETY_BUTTON =
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.notoriety_button");
    private static final Component RESET_BUTTON =
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.reset_button");
    private static final Component UNLOCK =
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.unlock_button");
    private static final Component BACK =
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.back_button");




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
    private Player player;
    private int leftPos, topPos;



    //Menu Button objects
    private Button universalSpellsButton;
    private Button uniqueSpellsButton;
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


    //List of menu buttons and hashmap to tell if it is selected
    Button[] menuButtons;
    HashMap<Button, Boolean> selectedMenuButtons = new HashMap<>();

    //List of buttons to view spell unlocking and hashmap
    Button[] viewSpellButtons;
    HashMap<Button, Boolean> selectedViewSpellButtons = new HashMap<>();

    //List of buttons to unlock spells and hashmap
    Button[] unlockSpellButtons;
    HashMap<Button, Boolean> selectedUnlockSpellButtons = new HashMap<>();

    //List of buttons to go back and hashmap
    Button[] backButtons;
    HashMap<Button, Boolean> selectedBackButtons = new HashMap<>();




    //List of spell requirements
    SpellRequirements[] spellRequirements = {SpellRequirements.STOMP, SpellRequirements.MAGICIANS_RED, SpellRequirements.BLOODLETTING};

    //List of spell descriptions
    SpellDescriptions[] spellDescriptions = {SpellDescriptions.STOMP, SpellDescriptions.MAGICIANS_RED, SpellDescriptions.BLOODLETTING};



    //Booleans to test when to render what
    boolean renderUniversalScreen = false;
    boolean renderUniqueScreen = false;
    boolean renderNotorietyScreen = false;


    
    public BizarreWizardryMainScreen(Player player) {
        super(TITLE);

        this.player = player;
        this.imageWidth = 500;
        this.imageHeight = 250;


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
        this.uniqueSpellsButton = addRenderableWidget(Button.builder(
                UNIQUE_BUTTON,
                        this::handleUniqueButton)
                .bounds(this.leftPos + 165, this.topPos, 170, 20)
                .tooltip(Tooltip.create(UNIQUE_BUTTON))
                .build());

        //Button to display info about your notoriety
        this.notorietyButton = addRenderableWidget(Button.builder(
                NOTORIETY_BUTTON,
                        this::handleNotorietyButton)
                .bounds(this.leftPos + 335, this.topPos, 165, 20)
                .tooltip(Tooltip.create(NOTORIETY_BUTTON))
                .build());



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



        //Putting buttons into their respective arrays
        //Also take care of the boolean arrays
        this.menuButtons = new Button[]{universalSpellsButton, uniqueSpellsButton, notorietyButton};
        for (Button button : menuButtons)
            selectedMenuButtons.put(button, false);

        this.viewSpellButtons = new Button[]{stompButton, magiciansRedButton, bloodlettingButton};
        for (Button button : viewSpellButtons)
            selectedViewSpellButtons.put(button, false);

        this.unlockSpellButtons = new Button[]{unlockStompButton, unlockMagiciansRedButton, unlockBloodLettingButton};
        for (Button button : unlockSpellButtons)
            selectedUnlockSpellButtons.put(button, false);

        this.backButtons = new Button[]{backToUniversal, backToSpellSelection, backToNotoriety};
        for (Button button : backButtons)
            selectedBackButtons.put(button, false);


        //Initializing the appropriate ones to invisible/inactive right away
        for (int i = 0 ; i < viewSpellButtons.length ; ++i) {
            viewSpellButtons[i].active = false;
            viewSpellButtons[i].visible = false;

            unlockSpellButtons[i].active = false;
            unlockSpellButtons[i].visible = false;
        }

//        for (int i = 0 ; i < backButtons.length ; ++i) {
//            backButtons[i].active = false;
//            backButtons[i].visible = false;
//        }


    }





    //Renders background and sets buttons to invisible when appropriate
    //This method is called after init
    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {

        renderBackground(graphics);

        RenderSystem.setShaderTexture(0, HOME_SCREEN);
        graphics.blit(HOME_SCREEN, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, 500, 250);


        //Check and see which screens we should be rendering
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
                    renderSpellScreen(graphics, spellDescriptions[i].getDescription(),
                            spellRequirements[i].getRequirement(), SpellHudOverlay.spellPictures[i], unlockSpellButtons[i]);

                    hideViewButtons();

                    break;


                }

            }

        } else {

            backToUniversal.active = false;
            backToUniversal.visible = false;

            hideViewButtons();
            hideUnlockButtons();

        }


        super.render(graphics, mouseX, mouseY, partialTicks);
    }



    //A bunch of event handlers
    private void handleUniversalButton(Button button) {
        selectedMenuButtons.put(button, !(selectedMenuButtons.get(button)));
        this.renderUniversalScreen = !this.renderUniversalScreen;
    }

    private void handleBackUniversalButton(Button button) {
        viewButtonsFalse();
        hideUnlockButtons();
        button.active = false;
        button.visible = false;


        this.renderUniversalScreen = true;
        selectedMenuButtons.put(button, true);
    }

    private void handleUniqueButton(Button button) {
        selectedMenuButtons.put(button, !(selectedMenuButtons.get(button)));
        this.renderUniqueScreen = !this.renderUniqueScreen;
    }

    private void handleNotorietyButton(Button button) {
        selectedMenuButtons.put(button, !(selectedMenuButtons.get(button)));
        this.renderNotorietyScreen = !this.renderNotorietyScreen;
    }

    private void handleStompButton(Button button) {

        selectedViewSpellButtons.put(button, !(selectedViewSpellButtons.get(button)));

        //1 is the spell to unlock, 0 is the position we place it in!
        int[] spellInfo = {2, 0};
        ModMessages.sendToServer(new SetAvailableSpellsC2SPacket(1));
        ModMessages.sendToServer(new SetCurrentSpellsC2SPacket(spellInfo));
    }
    private void handleUnlockStompButton(Button button) {
        selectedUnlockSpellButtons.put(button, !(selectedUnlockSpellButtons.get(button)));
    }

    private void handleMagiciansRedButton(Button button) {

        selectedViewSpellButtons.put(button, !(selectedViewSpellButtons.get(button)));

        int[] spellInfo = {1, 1};
        ModMessages.sendToServer(new SetAvailableSpellsC2SPacket(0));
        ModMessages.sendToServer(new SetCurrentSpellsC2SPacket(spellInfo));
    }
    private void handleUnlockMagiciansRedButton(Button button) {
        selectedUnlockSpellButtons.put(button, !(selectedUnlockSpellButtons.get(button)));
    }

    private void handleBloodlettingButton(Button button) {

        selectedViewSpellButtons.put(button, !(selectedViewSpellButtons.get(button)));

        int[] spellInfo = {3, 2};
        ModMessages.sendToServer(new SetAvailableSpellsC2SPacket(2));
        ModMessages.sendToServer(new SetCurrentSpellsC2SPacket(spellInfo));
    }
    private void handleUnlockBloodlettingButton(Button button) {
        selectedUnlockSpellButtons.put(button, !(selectedUnlockSpellButtons.get(button)));
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
    private void renderSpellScreen(GuiGraphics graphics, Component description, Component requirements, ResourceLocation spellImage, Button unlockButton) {

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
        unlockButton.active = true;

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


}
