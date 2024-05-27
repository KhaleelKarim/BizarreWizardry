package net.raguraccoon.bizarre_wizardry.spell;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.raguraccoon.bizarre_wizardry.BizarreWizardry;
import net.raguraccoon.bizarre_wizardry.statistics.PlayerStatsGetter;

public class BizarreSpells {

    public static BizarreSpell NO_SPELL = new BizarreSpell(0,
            new ResourceLocation(BizarreWizardry.MOD_ID, "textures/spell/no_spell.png"),
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.no_spell_selection_button"),
            null, null,
            "No Spell", false, new int[]{-1}, false, null, null, null,
            PlayerStatsGetter::unlockedNoSpell, SpellMethods::noSpell
            );

    public static BizarreSpell STOMP = new BizarreSpell(1,
            new ResourceLocation(BizarreWizardry.MOD_ID, "textures/spell/stomp_spell_1.png"),
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.stomp_selection_button"),
            new String[]{"stomp_description_line_one"}, new String[]{"stomp_requirements_line_one"},
            "Stomp", false, new int[]{-1}, false, null, null, null,
            PlayerStatsGetter::unlockedStomp, SpellMethods::stomp
    );

    public static BizarreSpell MAGICIANS_RED = new BizarreSpell(2,
            new ResourceLocation(BizarreWizardry.MOD_ID, "textures/spell/fire_spell.png"),
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.magicians_red_selection_button"),
            new String[]{"magicians_red_description_line_one"}, new String[]{"magicians_red_requirements_line_one"},
            "Magician's Red", false, new int[]{-1}, false, null, null, null,
            PlayerStatsGetter::unlockedMagiciansRed, SpellMethods::burn
    );

    public static BizarreSpell BLOODLETTING = new BizarreSpell(3,
            new ResourceLocation(BizarreWizardry.MOD_ID, "textures/spell/bloodletting_spell.png"),
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.bloodletting_selection_button"),
            new String[]{"bloodletting_description_line_one", "bloodletting_description_line_two"}, new String[]{"bloodletting_requirements_line_one"},
            "Bloodletting", false, new int[]{1, 2}, false, null, null, null,
            PlayerStatsGetter::unlockedBloodletting, SpellMethods::bloodletting
    );

    public static BizarreSpell CRYSTALLINE_SHIELD = new BizarreSpell(4,
            new ResourceLocation(BizarreWizardry.MOD_ID, "textures/spell/crystal_shield_spell_1.png"),
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.crystalline_shield_selection_button"),
            new String[]{"crystalline_shield_description_line_one", "crystalline_shield_description_line_two"}, new String[]{"crystalline_shield_requirements_line_one"},
            "Crystalline Shield", false, new int[]{3}, false, null, null, null,
            PlayerStatsGetter::unlockedCrystallineShield, SpellMethods::crystallineShield
    );

    public static BizarreSpell IMPACT = new BizarreSpell(5,
            new ResourceLocation(BizarreWizardry.MOD_ID, "textures/spell/impact_spell.png"),
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_home_screen.button.impact_selection_button"),
            new String[]{"impact_description_line_one"}, new String[]{"effect.bizarre_wizardry.crystalline_shield"},
            "Impact", false, new int[]{4}, false, null, null, null,
            PlayerStatsGetter::unlockedImpact, SpellMethods::impact
            );



}
