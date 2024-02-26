package net.raguraccoon.bizarre_wizardry.client;

public class ClientSpellData {
    private static int magicalClass;

    public static void setMagicalClass(int magicalClass) {
        ClientSpellData.magicalClass = magicalClass;
    }

    public static int getMagicalClass() {
        return ClientSpellData.magicalClass;
    }
}
