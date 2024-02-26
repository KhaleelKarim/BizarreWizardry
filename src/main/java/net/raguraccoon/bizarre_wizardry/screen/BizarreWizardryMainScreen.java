package net.raguraccoon.bizarre_wizardry.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.raguraccoon.bizarre_wizardry.BizarreWizardry;
import org.jetbrains.annotations.NotNull;

public class BizarreWizardryMainScreen extends Screen {

    private static final Component TITLE =
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_main_screen");
    private static final Component EXAMPLE_BUTTON =
            Component.translatable("gui." + BizarreWizardry.MOD_ID + ".bizarre_wizardry_main_screen.button.test_button");
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(BizarreWizardry.MOD_ID, "textures/gui/main_screen.png");

    private final int imageWidth, imageHeight;
    private Player player;
    private int leftPos, topPos;

    private Button button;

    public BizarreWizardryMainScreen(Player player) {
        super(TITLE);

        this.player = player;
        this.imageWidth = 256;
        this.imageHeight = 256;
    }

    @Override
    protected void init() {
        super.init();

        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        this.button = addRenderableWidget(Button.builder(
                EXAMPLE_BUTTON,
                this::handleExampleButton)
                .bounds(this.leftPos + 8, this.topPos + 20, 80, 20)
                .tooltip(Tooltip.create(EXAMPLE_BUTTON))
                .build());
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        renderBackground(graphics);

        RenderSystem.setShaderTexture(0, TEXTURE);
        graphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        graphics.drawString(this.font, TITLE, this.leftPos + 8, this.topPos + 8, 0x404040, false);

        super.render(graphics, mouseX, mouseY, partialTicks);
    }

    private void handleExampleButton(Button button) {
        player.sendSystemMessage(Component.literal("Check Check"));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
