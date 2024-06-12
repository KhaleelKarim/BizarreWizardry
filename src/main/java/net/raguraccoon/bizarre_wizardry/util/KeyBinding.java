package net.raguraccoon.bizarre_wizardry.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_BIZARRE_WIZARDRY = "key.category.bizarre_wizardry.bizarre_category";
    public static final String KEY_SWITCH_SPELL = "key.bizarre_wizardry.switch_spell";
    public static final String KEY_OPEN_GUI = "key.bizarre_wizardry.open";

    public static final KeyMapping SWITCH_SPELL_KEY = new KeyMapping(KEY_SWITCH_SPELL, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, KEY_CATEGORY_BIZARRE_WIZARDRY);
    public static final KeyMapping OPEN_GUI_KEY = new KeyMapping(KEY_OPEN_GUI, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_C, KEY_CATEGORY_BIZARRE_WIZARDRY);
}
