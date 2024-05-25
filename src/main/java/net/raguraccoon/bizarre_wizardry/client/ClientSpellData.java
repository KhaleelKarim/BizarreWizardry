package net.raguraccoon.bizarre_wizardry.client;

public class ClientSpellData {

    //Booleans determining whether a spell is unlockable
    public static boolean stomp;
    public static boolean magiciansRed;
    public static boolean bloodletting;
    public static boolean crystallineShield;

    private static int magicalClass;
    private static int spellCapacity = 3;

    public static String[] spellsLibrary = {"No Spell", "Stomp", "Magician's Red", "Bloodletting", "Crystalline Shield"};
    public static int[] availableSpells = new int[spellsLibrary.length];
    public static int[] currentSpells = new int[spellCapacity];

    public static boolean[] spellBooleans = new boolean[spellsLibrary.length - 1];




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



    public static void setStomp(boolean b) {
        ClientSpellData.spellBooleans = new boolean[]{b, magiciansRed, bloodletting, crystallineShield};
    }

    public static void setMagiciansRed(boolean b) {
        ClientSpellData.spellBooleans = new boolean[]{stomp, b, bloodletting, crystallineShield};    }

    public static void setBloodletting(boolean b) {
        ClientSpellData.spellBooleans = new boolean[]{stomp, magiciansRed, b, crystallineShield};    }

    public static void setCrystallineShield(boolean b) {
        ClientSpellData.spellBooleans = new boolean[]{stomp, magiciansRed, bloodletting, b};    }

}
