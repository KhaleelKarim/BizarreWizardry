package net.raguraccoon.bizarre_wizardry.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
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
import net.raguraccoon.bizarre_wizardry.networking.ModMessages;
import net.raguraccoon.bizarre_wizardry.networking.packet.available_spells.SetAvailableSpellsC2SPacket;
import net.raguraccoon.bizarre_wizardry.networking.packet.unlocks.ValidateUnlockC2SPacket;
import net.raguraccoon.bizarre_wizardry.spell.BizarreSpell;
import net.raguraccoon.bizarre_wizardry.spell.BizarreSpells;
import org.jetbrains.annotations.NotNull;


public class BizarreWizardryMainScreen extends Screen {

    //Useless title
    private static final Component TITLE =
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen");



    //Information about main screen and player
    private final int imageWidth, imageHeight;
    private Player player = Minecraft.getInstance().player;
    private int leftPos, topPos;



    //Menu Button objects
    private Button universalSpellsButton;
    private Button spellSelectionButton;
    private Button notorietyButton;


    //View spell unlocking buttons
    private ImageButton stompButton;
    private ImageButton magiciansRedButton;
    private ImageButton bloodlettingButton;
    private ImageButton crystallineShieldButton;
    private ImageButton impactButton;
    private ImageButton resetButton;


    //Unlocking spell buttons
    private Button unlockStompButton;
    private Button unlockMagiciansRedButton;
    private Button unlockBloodLettingButton;
    private Button unlockCrystallineShieldButton;
    private Button unlockImpactButton;


    //Buttons to go to a previous screen
    private Button backToUniversal;
    private Button backToSpellSelection;
    private Button backToNotoriety;



    //Buttons to select a spell
    private Button selectNoSpellButton;
    private Button selectStompButton;
    private Button selectMagiciansRedButton;
    private Button selectBloodlettingButton;
    private Button selectCrystallineShieldButton;
    private Button selectImpactButton;



    //Buttons to put a spell in a specific slot
    private Button spell1;
    private Button spell2;
    private Button spell3;



    
    public BizarreWizardryMainScreen() {
        super(TITLE);

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
                ScreenVariables.UNIVERSAL_BUTTON,
                        ScreenButtonHandlers::handleMenuButton)
                .bounds(this.leftPos, this.topPos, 165, 20)
                .tooltip(Tooltip.create(ScreenVariables.UNIVERSAL_BUTTON))
                .build());

        //Button to go back to general spell tree
        this.backToUniversal = addRenderableWidget(Button.builder(
                        ScreenVariables.BACK, ScreenButtonHandlers::handleBackButton)
                .bounds(this.leftPos + 20, this.topPos + 160, 30, 20)
                .build());
        backToUniversal.active = false;
        backToUniversal.visible = false;



        //Button to open menu for class-specific spells
        this.spellSelectionButton = addRenderableWidget(Button.builder(
                        ScreenVariables.SPELL_SELECTION_BUTTON,
                        ScreenButtonHandlers::handleMenuButton)
                .bounds(this.leftPos + 165, this.topPos, 170, 20)
                .tooltip(Tooltip.create(ScreenVariables.SPELL_SELECTION_BUTTON))
                .build());

        this.backToSpellSelection = addRenderableWidget(Button.builder(
                        ScreenVariables.BACK, ScreenButtonHandlers::handleBackButton)
                .bounds(this.leftPos + 20, this.topPos + 210, 30, 20)
                .build());
        backToSpellSelection.active = false;
        backToSpellSelection.visible = false;



        //Button to display info about your notoriety
        this.notorietyButton = addRenderableWidget(Button.builder(
                        ScreenVariables.NOTORIETY_BUTTON,
                        ScreenButtonHandlers::handleMenuButton)
                .bounds(this.leftPos + 335, this.topPos, 165, 20)
                .tooltip(Tooltip.create(ScreenVariables.NOTORIETY_BUTTON))
                .build());

        this.backToNotoriety = addRenderableWidget(Button
                .builder(ScreenVariables.BACK, ScreenButtonHandlers::handleBackButton).build());



        //This group of buttons should only appear in the universal screen
        this.stompButton = addRenderableWidget(new ImageButton(this.leftPos + 30, this.topPos + 81,
                40, 20, 0, 0,
                1, BizarreSpells.STOMP.image, 40,20, ScreenButtonHandlers::handleViewSpellButton));
        this.stompButton.active = false;
        this.stompButton.visible = false;
        BizarreSpells.STOMP.setViewButton(this.stompButton);


        this.unlockStompButton = addRenderableWidget(makeUnlockButton(ScreenButtonHandlers::handleUnlockButton));
        this.unlockStompButton.active = false;
        this.unlockStompButton.visible = false;
        BizarreSpells.STOMP.setUnlockButton(this.unlockStompButton);



        this.magiciansRedButton = addRenderableWidget(new ImageButton(this.leftPos + 30, this.topPos + 162,
                40, 20, 0, 0,
                1, BizarreSpells.MAGICIANS_RED.image, 40,20, ScreenButtonHandlers::handleViewSpellButton));
        this.magiciansRedButton.active = false;
        this.magiciansRedButton.visible = false;
        BizarreSpells.MAGICIANS_RED.setViewButton(this.magiciansRedButton);


        this.unlockMagiciansRedButton = addRenderableWidget(makeUnlockButton(ScreenButtonHandlers::handleUnlockButton));
        this.unlockMagiciansRedButton.active = false;
        this.unlockMagiciansRedButton.visible = false;
        BizarreSpells.MAGICIANS_RED.setUnlockButton(this.unlockMagiciansRedButton);



        this.bloodlettingButton = addRenderableWidget(new ImageButton(this.leftPos + 110, this.topPos + 121,
                40, 20, 0, 0,
                1, BizarreSpells.BLOODLETTING.image, 40,20, ScreenButtonHandlers::handleViewSpellButton));
        this.bloodlettingButton.active = false;
        this.bloodlettingButton.visible = false;
        BizarreSpells.BLOODLETTING.setViewButton(this.bloodlettingButton);


        this.unlockBloodLettingButton = addRenderableWidget(makeUnlockButton(ScreenButtonHandlers::handleUnlockButton));
        this.unlockBloodLettingButton.active = false;
        this.unlockBloodLettingButton.visible = false;
        BizarreSpells.BLOODLETTING.setUnlockButton(this.unlockBloodLettingButton);



        this.crystallineShieldButton = addRenderableWidget(new ImageButton(this.leftPos + 190, this.topPos + 121,
                40, 20, 0, 0,
                1, BizarreSpells.CRYSTALLINE_SHIELD.image, 40, 20, ScreenButtonHandlers::handleViewSpellButton));
        this.crystallineShieldButton.active = false;
        this.crystallineShieldButton.visible = false;
        BizarreSpells.CRYSTALLINE_SHIELD.setViewButton(this.crystallineShieldButton);


        this.unlockCrystallineShieldButton = addRenderableWidget(makeUnlockButton(ScreenButtonHandlers::handleUnlockButton));
        this.unlockCrystallineShieldButton.active = false;
        this.unlockCrystallineShieldButton.visible = false;
        BizarreSpells.CRYSTALLINE_SHIELD.setUnlockButton(this.unlockCrystallineShieldButton);



        this.impactButton = addRenderableWidget(new ImageButton(this.leftPos + 260, this.topPos + 121,
                40, 20, 0, 0,
                1, BizarreSpells.IMPACT.image, 40, 20, ScreenButtonHandlers::handleViewSpellButton));
        this.impactButton.active = false;
        this.impactButton.visible = false;
        BizarreSpells.IMPACT.setViewButton(this.impactButton);


        this.unlockImpactButton = addRenderableWidget(makeUnlockButton(ScreenButtonHandlers::handleUnlockButton));
        this.unlockImpactButton.active = false;
        this.unlockImpactButton.visible = false;
        BizarreSpells.IMPACT.setUnlockButton(this.unlockImpactButton);


        //Button to remove all spells from current spells list
//        this.resetButton = addRenderableWidget(Button.builder(
//                RESET_BUTTON,
//                    this::handleResetButton)
//                .bounds(this.leftPos + 50, this.topPos + 50, 50, 50)
//                .build());




        //Buttons to place a spell into current spells array
        this.selectNoSpellButton =
                addRenderableWidget(Button
                        .builder(BizarreSpells.NO_SPELL.buttonName, ScreenButtonHandlers::handleSelectNoSpellButton)
                .bounds(this.leftPos + 50, this.topPos + 50, 100, 20)
                .build());
        BizarreSpells.NO_SPELL.setSelectButton(this.selectNoSpellButton);


        this.selectStompButton =
                addRenderableWidget(Button
                        .builder(BizarreSpells.STOMP.buttonName, ScreenButtonHandlers::handleSelectButton)
                .bounds(this.leftPos + 150, this.topPos + 50, 100, 20)
                .build());
        BizarreSpells.STOMP.setSelectButton(this.selectStompButton);


        this.selectMagiciansRedButton =
                addRenderableWidget(Button
                        .builder(BizarreSpells.MAGICIANS_RED.buttonName, ScreenButtonHandlers::handleSelectButton)
                .bounds(this.leftPos + 250, this.topPos + 50, 100, 20)
                .build());
        BizarreSpells.MAGICIANS_RED.setSelectButton(this.selectMagiciansRedButton);


        this.selectBloodlettingButton =
                addRenderableWidget(Button
                        .builder(BizarreSpells.BLOODLETTING.buttonName, ScreenButtonHandlers::handleSelectButton)
                .bounds(this.leftPos + 50, this.topPos + 70, 100, 20)
                .build());
        BizarreSpells.BLOODLETTING.setSelectButton(this.selectBloodlettingButton);


        this.selectCrystallineShieldButton =
                addRenderableWidget(Button
                        .builder(BizarreSpells.CRYSTALLINE_SHIELD.buttonName, ScreenButtonHandlers::handleSelectButton)
                        .bounds(this.leftPos + 150, this.topPos + 70, 100, 20)
                        .build());
        BizarreSpells.CRYSTALLINE_SHIELD.setSelectButton(this.selectCrystallineShieldButton);


        this.selectImpactButton =
                addRenderableWidget(Button
                        .builder(BizarreSpells.IMPACT.buttonName, ScreenButtonHandlers::handleSelectButton)
                        .bounds(this.leftPos + 250, this.topPos + 70, 100, 20)
                        .build());
        BizarreSpells.IMPACT.setSelectButton(this.selectImpactButton);



        //Buttons to change what position a spell is going into
        this.spell1 = addRenderableWidget(Button.builder(ScreenVariables.PUT_FIRST, ScreenButtonHandlers::handleSpell1Button)
                .bounds(this.leftPos + 76, this.topPos + 175, 70, 30)
                .build());

        this.spell2 = addRenderableWidget(Button.builder(ScreenVariables.PUT_SECOND, ScreenButtonHandlers::handleSpell2Button)
                .bounds(this.leftPos + 215, this.topPos + 175, 70, 30)
                .build());

        this.spell3 = addRenderableWidget(Button.builder(ScreenVariables.PUT_THIRD, ScreenButtonHandlers::handleSpell3Button)
                .bounds(this.leftPos + 354, this.topPos + 175, 70, 30)
                .build());



        //Putting buttons into their respective arrays
        //Also take care of the boolean arrays
        ScreenVariables.menuButtons = new Button[]{universalSpellsButton, spellSelectionButton, notorietyButton};
        for (Button button : ScreenVariables.menuButtons)
            ScreenVariables.selectedMenuButtons.put(button, false);


        ScreenVariables.selectedSelectionButtons.put(BizarreSpells.NO_SPELL.selectButton, false);
        ScreenVariables.spellNumberSelectionButtons.put(BizarreSpells.NO_SPELL.selectButton, 0);

        BizarreSpells.NO_SPELL.selectButton.active = false;
        BizarreSpells.NO_SPELL.selectButton.visible = false;


        for (int i = 1 ; i < ClientSpellData.SPELLS_LIBRARY.length ; ++i) {

            BizarreSpell currentSpell = ClientSpellData.SPELLS_LIBRARY[i];

            ScreenVariables.selectedViewSpellButtons.put(currentSpell.viewButton, false);

            ScreenVariables.selectedUnlockSpellButtons.put(currentSpell.unlockButton, false);
            ScreenVariables.unlockSpellButtonsPosition.put(currentSpell.unlockButton, i);

            ScreenVariables.selectedSelectionButtons.put(currentSpell.selectButton, false);
            ScreenVariables.spellNumberSelectionButtons.put(currentSpell.selectButton, i);



            currentSpell.viewButton.active = false;
            currentSpell.viewButton.visible = false;

            currentSpell.unlockButton.active = false;
            currentSpell.unlockButton.visible = false;

            currentSpell.selectButton.active = false;
            currentSpell.selectButton.visible = false;

        }





        ScreenVariables.backButtons = new Button[]{backToUniversal, backToSpellSelection, backToNotoriety};
        for (Button button : ScreenVariables.backButtons)
            ScreenVariables.selectedBackButtons.put(button, false);


        ScreenVariables.positionToPlaceSpell = 0;



        ScreenVariables.chooseButtons = new Button[]{spell1, spell2, spell3};
        for (Button button : ScreenVariables.chooseButtons)
            ScreenVariables.selectedChooseButtons.put(button, false);
        


        //Initializing the appropriate ones to invisible/inactive right away

        for (int i = 0 ; i < ScreenVariables.backButtons.length ; ++i) {
            ScreenVariables.backButtons[i].active = false;
            ScreenVariables.backButtons[i].visible = false;
        }

        for (int i = 0 ; i < ScreenVariables.chooseButtons.length ; ++i) {
            ScreenVariables.chooseButtons[i].active = false;
            ScreenVariables.chooseButtons[i].visible = false;
        }


    }



    //Renders background and sets buttons to invisible when appropriate
    //This method is called after init
    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {

        //Necessary call
        renderBackground(graphics);

        //Put a nice background up
        RenderSystem.setShaderTexture(0, ScreenVariables.HOME_SCREEN);
        graphics.blit(ScreenVariables.HOME_SCREEN, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, 500, 250);


        //Check and see which screens we should be rendering
        boolean renderUniversalScreen = ScreenVariables.selectedMenuButtons.get(universalSpellsButton);
        boolean renderSpellSelectionScreen = ScreenVariables.selectedMenuButtons.get(spellSelectionButton);
        boolean renderNotorietyScreen = ScreenVariables.selectedMenuButtons.get(notorietyButton);



        //For the universal spell screen
        if (renderUniversalScreen) {

            //Clean up spell selection and notoriety screens
            ScreenHelpers.hideSpellSelectionScreen();
            ScreenHelpers.hideNotorietyScreen();


            //Update info on available spells
            ModMessages.sendToServer(new SetAvailableSpellsC2SPacket(0));

            //Set background
            RenderSystem.setShaderTexture(0, ScreenVariables.BACKGROUND);
            graphics.blit(ScreenVariables.BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, 500, 250);


            //Iterate through all view spell buttons and set them visible/active
            for (int i = 1 ; i < ClientSpellData.SPELLS_LIBRARY.length ; ++i) {

                BizarreSpell currentSpell = ClientSpellData.SPELLS_LIBRARY[i];
                ImageButton currentViewButton = currentSpell.viewButton;
                currentViewButton.visible = true;
                currentViewButton.active = ScreenHelpers.checkDependencies(currentSpell);

                ScreenHelpers.setLock(currentSpell, graphics);

            }




            //After they are set as visible/active, check to see if they are selected in which case
            //the proper menu should be displayed!
            for (int i = 1 ; i < ClientSpellData.SPELLS_LIBRARY.length ; ++i) {

                BizarreSpell currentSpell = ClientSpellData.SPELLS_LIBRARY[i];

                if (ScreenVariables.selectedViewSpellButtons.get(currentSpell.viewButton)) {

                    renderSpellScreen(graphics, currentSpell);
                    ScreenHelpers.hideViewButtons();

                    break;

                }

            }

        } else if (renderSpellSelectionScreen) {

            //Clean up other screens
            ScreenHelpers.hideUniversalScreen();
            ScreenHelpers.hideNotorietyScreen();

            //Display each button to change spells
            //Display current spells
            displaySelectionScreenMenu(graphics);


            //Check each choose button to see if it is selected
            for (int i = 0 ; i < ScreenVariables.chooseButtons.length ; ++i) {

                //If the button is selected
                if (ScreenVariables.selectedChooseButtons.get(ScreenVariables.chooseButtons[i])) {

                    //Display the appropriate screen
                    displaySelectionSpellButtons(graphics, ClientSpellData.SPELL_ARSENAL[i]);

                    //Display back button
                    backToSpellSelection.active = true;
                    backToSpellSelection.visible = true;

                    break;

                }

            }



        } else if (renderNotorietyScreen) {

            //Clean up
            ScreenHelpers.hideUniversalScreen();
            ScreenHelpers.hideSpellSelectionScreen();


            displayManaSpillScreen(graphics);


        } else {

            ScreenHelpers.hideUniversalScreen();
            ScreenHelpers.hideSpellSelectionScreen();
            ScreenHelpers.hideNotorietyScreen();

        }



        super.render(graphics, mouseX, mouseY, partialTicks);
    }



    //We ain't pausing the screen here
    @Override
    public boolean isPauseScreen() {
        return false;
    }


    //Helper method to render screens for unlocking spells
    private void renderSpellScreen(GuiGraphics graphics, BizarreSpell spell) {

        //Get some basic info
        String[] description = spell.description;
        String[] requirements = spell.requirements;
        ResourceLocation spellImage = spell.image;
        Button unlockButton = spell.unlockButton;
        String currentSpell = spell.trueName;


        //Begin by rendering background
        RenderSystem.setShaderTexture(0, ScreenVariables.BACKGROUND);
        graphics.blit(ScreenVariables.BACKGROUND, this.leftPos, this.topPos, 0, 0, 500, 250, 500, 250);

        //Write the description of the spell
        int descriptionOffset = 23;
        for (int i = 0 ; i < description.length ; ++i) {

            Component tempDescription = Component.translatable(description[i]);
            graphics.drawString(this.font, tempDescription,
                    this.leftPos + 20, this.topPos + descriptionOffset,
                    0x404040, false);
            descriptionOffset += 13;

        }
        
        //Write requirements
        int requirementsOffset = 65;
        for (int i = 0 ; i < requirements.length ; ++i) {

            Component tempRequirement = Component.translatable(requirements[i]);
            graphics.drawString(this.font, tempRequirement,
                    this.leftPos + 20, this.topPos + requirementsOffset,
                    0x404040, false);
            requirementsOffset += 13;

        }

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
        if (spell.available) {
            unlockButton.active = false;
        } else {

            //Check if the spell is unlockable
            ModMessages.sendToServer(new ValidateUnlockC2SPacket(spell.spellNumber));

            //If it is, let the user click the button
            if (spell.unlockable)
                unlockButton.active = true;
            else //Set it false otherwise
                unlockButton.active = false;

        }

    }


    //Helper method to create a button that unlocks a spell
    private Button makeUnlockButton(Button.OnPress handler) {

        Button button = Button.builder(ScreenVariables.UNLOCK, handler)
                .bounds(this.leftPos + 20, this.topPos + 200, 50, 20)
                .tooltip(Tooltip.create(ScreenVariables.UNLOCK))
                .build();

        return button;

    }



    //Helper method to display the main spell selection screen
    private void displaySelectionScreenMenu(GuiGraphics graphics) {

        //Hide selection buttons and display background
        RenderSystem.setShaderTexture(0, ScreenVariables.BACKGROUND);
        graphics.blit(ScreenVariables.BACKGROUND, this.leftPos, this.topPos, 0, 0, 500, 250, 500, 250);

        backToSpellSelection.active = false;
        backToSpellSelection.visible = false;


        ScreenHelpers.hideSelectionButtons();

        for (int i = 0 ; i < ScreenVariables.chooseButtons.length ; ++i) {
            ScreenVariables.chooseButtons[i].active = true;
            ScreenVariables.chooseButtons[i].visible = true;
        }


        int offset = 94;

        for (int i = 0 ; i < ClientSpellData.SPELL_ARSENAL.length ; ++i) {

            BizarreSpell currentSpell = ClientSpellData.SPELL_ARSENAL[i];
            ResourceLocation texture = currentSpell.image;

            RenderSystem.setShaderTexture(0, texture);
            graphics.blit(texture, this.leftPos + offset, this.topPos + 130, 0, 0, 40, 20, 40, 20);

            offset += 138;

            ScreenVariables.chooseButtons[i].active = true;
            ScreenVariables.chooseButtons[i].visible = true;

        }

    }

    //Helper method to view spell selection buttons
    private void displaySelectionSpellButtons(GuiGraphics graphics, BizarreSpell bizarreSpell) {

        //Hide change spell buttons
        ScreenHelpers.hideChangeButtons();

        //Make filler background
        RenderSystem.setShaderTexture(0, ScreenVariables.BACKGROUND);
        graphics.blit(ScreenVariables.BACKGROUND, this.leftPos, this.topPos, 0, 0, 500, 250, 500, 250);

        for (int i = 0 ; i < ClientSpellData.SPELLS_LIBRARY.length ; ++i) {

            Button currentSelectionButton = ClientSpellData.SPELLS_LIBRARY[i].selectButton;

            //Make all visible
            currentSelectionButton.visible = true;

            //Only unlocked ones should be clickable
            if (ScreenHelpers.spellUnlocked(i))
                currentSelectionButton.active = true;
            else
                currentSelectionButton.active = false;

        }

        //The no spell should always be clickable
        BizarreSpells.NO_SPELL.selectButton.active = true;


        //Display image of spell we are going to change

        ResourceLocation spell = bizarreSpell.image;

        graphics.blit(spell, this.leftPos + 400, this.topPos + 100, 0, 0, 40, 20, 40, 20);


    }

    //Helper method to display mana spill screen
    private void displayManaSpillScreen(GuiGraphics graphics) {

        double barPercent = (double) ClientSpellData.getManaSpill() / ClientSpellData.manaSpillCap;
        int barPortion = (int) (440 * barPercent);

        //Display background
        RenderSystem.setShaderTexture(0, ScreenVariables.BACKGROUND);
        graphics.blit(ScreenVariables.BACKGROUND, this.leftPos, this.topPos, 0, 0, 500, 250, 500, 250);


        //Render border and bar
        RenderSystem.setShaderTexture(0, ScreenVariables.MANA_SPILL_BAR_BORDER);
        graphics.blit(ScreenVariables.MANA_SPILL_BAR_BORDER, leftPos + 28, topPos + 50,
                0, 0,
                444, 19, 444, 19);

        RenderSystem.setShaderTexture(0, ScreenVariables.MANA_SPILL_BAR);
        graphics.blit(ScreenVariables.MANA_SPILL_BAR, leftPos + 30, topPos + 52,
                0, 0,
                barPortion, 15, 440, 15);

    }

}
