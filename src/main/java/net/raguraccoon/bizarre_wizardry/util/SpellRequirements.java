package net.raguraccoon.bizarre_wizardry.util;

import net.minecraft.network.chat.Component;

public enum SpellRequirements {

    STOMP("Must fall from 20 blocks"),
    MAGICIANS_RED("Must swim in lava for 5 seconds"),
    BLOODLETTING("Must eat a spider's eye");

    private final Component requirement;


    SpellRequirements(String requirement) {
        this.requirement = Component.translatable(requirement);
    }

    public Component getRequirement() {
        return requirement;
    }

}
