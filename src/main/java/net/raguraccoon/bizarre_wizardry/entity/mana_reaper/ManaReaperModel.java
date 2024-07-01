package net.raguraccoon.bizarre_wizardry.entity.mana_reaper;


import net.minecraft.resources.ResourceLocation;
import net.raguraccoon.bizarre_wizardry.BizarreWizardry;
import software.bernie.geckolib.model.GeoModel;

public class ManaReaperModel extends GeoModel<ManaReaper> {

    //Geo model, texture, and animations
    private final ResourceLocation modelResource =
            new ResourceLocation(BizarreWizardry.MOD_ID, "geo/mana_reaper.geo.json");
    private final ResourceLocation textureResource =
            new ResourceLocation(BizarreWizardry.MOD_ID, "textures/entity/mana_reaper_texture.png");
    private final ResourceLocation animationResource =
            new ResourceLocation(BizarreWizardry.MOD_ID, "animations/mana_reaper_animations.json");


    @Override
    public ResourceLocation getModelResource(ManaReaper manaReaper) {
        return modelResource;
    }

    @Override
    public ResourceLocation getTextureResource(ManaReaper manaReaper) {
        return textureResource;
    }

    @Override
    public ResourceLocation getAnimationResource(ManaReaper manaReaper) {
        return animationResource;
    }
}
