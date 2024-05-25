package net.raguraccoon.bizarre_wizardry.spell;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;


/*
spellNumber : The number corresponding to the spell
image       : The 40x20 image associated with the spell
buttonName  : The name displayed on the selection button
description : Description of spell, each entry is a line
requirements: In-game task(s) to be able to unlock spell, each
              entry is a line
trueName    : How the spell is referred to in-game (e.g. Bloodletting)
unlockable  : Whether the spell's requirement has been fulfilled
dependencies: A list of integers that contain spellNumbers. Each
              spellNumber refers to a spell that must be unlocked
              before this one can be unlocked
viewButton  : ImageButton object to view the spell in main screen
unlockButton: Button object to unlock spell
selectButton: Button object to place spell in current arsenal
 */
public class BizarreSpell {

    public final int spellNumber;
    public final ResourceLocation image;
    public final Component buttonName;
    public final String[] description;
    public final String[] requirements;
    public final String trueName;
    public boolean unlockable;
    public final int[] dependencies;
    public boolean available;
    public ImageButton viewButton;
    public Button unlockButton;
    public Button selectButton;

    public BizarreSpell(int spellNumber, ResourceLocation image, Component buttonName, String[] description,
                        String[] requirements, String trueName, boolean unlockable, int[] dependencies,
                        boolean available, ImageButton viewButton, Button unlockButton, Button selectButton)
    {

        this.spellNumber = spellNumber;
        this.image = image;
        this.buttonName = buttonName;
        this.description = description;
        this.requirements = requirements;
        this.trueName = trueName;
        this.unlockable = unlockable;
        this.dependencies = dependencies;
        this.available = available;
        this.viewButton = viewButton;
        this.unlockButton = unlockButton;
        this.selectButton = selectButton;

    }

}
