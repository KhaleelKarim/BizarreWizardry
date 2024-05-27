package net.raguraccoon.bizarre_wizardry.spell;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.raguraccoon.bizarre_wizardry.client.ClientSpellData;
import net.raguraccoon.bizarre_wizardry.item.custom.WandItem;


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
available   : If the spell is available to be put into arsenal
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
    public final Validator validator;
    public final Caster caster;


    public BizarreSpell(int spellNumber, ResourceLocation image, Component buttonName, String[] description,
                        String[] requirements, String trueName, boolean unlockable, int[] dependencies,
                        boolean available, ImageButton viewButton, Button unlockButton, Button selectButton,
                        Validator validator, Caster caster)
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
        this.validator = validator;
        this.caster = caster;

    }

    public static BizarreSpell spellFromNumber(int spellNumber) {

        return ClientSpellData.SPELLS_LIBRARY[spellNumber];

    }

    public void setUnlockable(boolean unlockable) {
        this.unlockable = unlockable;
        ClientSpellData.SPELLS_LIBRARY[this.spellNumber].unlockable = unlockable;
    }

    public void setAvailable(boolean available) {
        this.available = available;
        ClientSpellData.SPELLS_LIBRARY[this.spellNumber].available = available;
    }

    public void setViewButton(ImageButton viewButton) {
        this.viewButton = viewButton;
        ClientSpellData.SPELLS_LIBRARY[this.spellNumber].viewButton = viewButton;
    }

    public void setUnlockButton(Button unlockButton) {
        this.unlockButton = unlockButton;
        ClientSpellData.SPELLS_LIBRARY[this.spellNumber].unlockButton = unlockButton;
    }

    public void setSelectButton(Button selectButton) {
        this.selectButton = selectButton;
        ClientSpellData.SPELLS_LIBRARY[this.spellNumber].selectButton = selectButton;
    }

    @Override
    public boolean equals(Object obj) {
        BizarreSpell spell = (BizarreSpell) obj;
        return this.spellNumber == spell.spellNumber;
    }


    //Methods that check whether the server player
    //has met the requirement for unlocking the spell
    public interface Validator {
        boolean validate(ServerPlayer serverPlayer);
    }

    //Methods that are called when the spell is cast
    public interface Caster {
        void cast(WandItem wand, Level level, Player player, LivingEntity entity, UseOnContext context);
    }

}
