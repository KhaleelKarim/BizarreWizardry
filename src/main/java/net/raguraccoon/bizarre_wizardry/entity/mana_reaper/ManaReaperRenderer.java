package net.raguraccoon.bizarre_wizardry.entity.mana_reaper;


import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ManaReaperRenderer extends GeoEntityRenderer<ManaReaper> {

    public ManaReaperRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ManaReaperModel());
    }

}
