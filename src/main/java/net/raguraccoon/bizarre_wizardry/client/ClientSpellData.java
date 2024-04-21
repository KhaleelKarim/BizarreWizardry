package net.raguraccoon.bizarre_wizardry.client;

public class ClientSpellData {
    private static int magicalClass;
    private static int spellCapacity = 3;

    public static String[] spellsLibrary = {"No Spell", "Rhino Stomp", "Magician's Red", "Bloodletting", "Overgrowth"};
    public static int[] availableSpells = new int[spellsLibrary.length];
    public static int[] currentSpells = new int[spellCapacity];


    public static void setMagicalClass(int magicalClass) {
        ClientSpellData.magicalClass = magicalClass;
    }

    public static int getMagicalClass() {
        return ClientSpellData.magicalClass;
    }



    public static void setSpellCapacity(int spellCapacity) {
        if (spellCapacity < 3)
            ClientSpellData.spellCapacity = 3;
        else
            ClientSpellData.spellCapacity = spellCapacity;
    }

    public static int getSpellCapacity() {
        return ClientSpellData.spellCapacity;
    }




    public static void setAvailableSpells(int[] spells) {
        ClientSpellData.availableSpells = spells;
    }

    public static void setCurrentSpells(int[] spells) {
        ClientSpellData.currentSpells = spells;
    }

}
