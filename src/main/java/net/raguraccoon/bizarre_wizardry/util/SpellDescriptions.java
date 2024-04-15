package net.raguraccoon.bizarre_wizardry.util;

import net.minecraft.network.chat.Component;

public enum SpellDescriptions {

    STOMP("In a wide area around the player, send mobs flying upwards"),
    MAGICIANS_RED("Shoot out a fireball that incinerates and pierces mobs"),
    BLOODLETTING("Sacrifice three hearts for speed and strength boost\n" +
                 "The spell will NOT prevent you from killing yourself");

    private final Component description;


    SpellDescriptions(String description) {
        this.description = Component.translatable(description);
    }

    public Component getDescription() {
        return description;
    }

}
