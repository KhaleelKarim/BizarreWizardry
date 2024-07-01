package net.raguraccoon.bizarre_wizardry.entity.magicians_red;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MagiciansRedRenderer extends GeoEntityRenderer<MagiciansRed> {

    public MagiciansRedRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MagiciansRedModel());
    }

}
