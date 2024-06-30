package net.raguraccoon.bizarre_wizardry.client;

import net.raguraccoon.bizarre_wizardry.spell.BizarreSpell;
import net.raguraccoon.bizarre_wizardry.spell.BizarreSpells;


public class ClientSpellData {

    public static final int manaSpillCap = 1000;

    private static int magicalClass;
    private static int spellCapacity = 3;
    private static int manaCap;
    private static int manaLevel;
    private static int manaRate;
    private static int manaSpill;


    public static BizarreSpell[] SPELLS_LIBRARY = {BizarreSpells.NO_SPELL, BizarreSpells.STOMP,
            BizarreSpells.MAGICIANS_RED, BizarreSpells.BLOODLETTING, BizarreSpells.CRYSTALLINE_SHIELD,
            BizarreSpells.IMPACT
            };

    public static BizarreSpell[] SPELL_ARSENAL = new BizarreSpell[spellCapacity];


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


        for (int i = 1 ; i < SPELLS_LIBRARY.length ; ++i) {

            if (spells[i] == 0)
                SPELLS_LIBRARY[i].setAvailable(false);
            else
                SPELLS_LIBRARY[i].setAvailable(true);

        }

    }


    public static void setCurrentSpells(int[] spells) {
        for (int i = 0 ; i < spells.length ; ++i) {

            BizarreSpell currentSpell = BizarreSpell.spellFromNumber(spells[i]);
            SPELL_ARSENAL[i] = currentSpell;

        }
    }

    public static int getManaCap() {
        return manaCap;
    }

    public static void setManaCap(int manaCap) {
        ClientSpellData.manaCap = manaCap;
    }

    public static int getManaLevel() {
        return manaLevel;
    }

    public static void setManaLevel(int manaLevel) {
        ClientSpellData.manaLevel = manaLevel;
    }

    public static int getManaRate() {
        return manaRate;
    }

    public static void setManaRate(int manaRate) {
        ClientSpellData.manaRate = manaRate;
    }

    public static int getManaSpill() {
        return manaSpill;
    }

    public static void setManaSpill(int manaSpill) {
        ClientSpellData.manaSpill = manaSpill;
    }
}
