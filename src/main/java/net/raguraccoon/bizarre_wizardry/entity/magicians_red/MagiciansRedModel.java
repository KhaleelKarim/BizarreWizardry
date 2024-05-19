package net.raguraccoon.bizarre_wizardry.entity.magicians_red;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.raguraccoon.bizarre_wizardry.BizarreWizardry;
import net.raguraccoon.bizarre_wizardry.util.KMath;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class MagiciansRedModel extends GeoModel<MagiciansRed> {

    //Resource locations of the geo model, the texture, and the animation
    private final ResourceLocation modelResource = new ResourceLocation(BizarreWizardry.MOD_ID, "geo/magicians_red.geo.json");
    private final ResourceLocation textureResource = new ResourceLocation(BizarreWizardry.MOD_ID, "textures/entity/phoenix_texture.png");
    private final ResourceLocation animationResource = new ResourceLocation(BizarreWizardry.MOD_ID, "animations/phoenix_idle.json");

    @Override
    public ResourceLocation getModelResource(MagiciansRed magiciansRed) {
        return modelResource;
    }

    @Override
    public ResourceLocation getTextureResource(MagiciansRed magiciansRed) {
        return textureResource;
    }

    @Override
    public ResourceLocation getAnimationResource(MagiciansRed magiciansRed) {
        return animationResource;
    }


    @Override
    public void setCustomAnimations(MagiciansRed animatable, long instanceId, AnimationState<MagiciansRed> animationState) {
        CoreGeoBone phoenix = getAnimationProcessor().getBone("Phoenix");

        if (phoenix != null) {

            Vec3 travelVector = animatable.getDeltaMovement();           //Vector that phoenix travels along
            Vec2 stdVector = new Vec2(0, 1);             //Vector that phoenix looks along default
            double angleX;  //Angle to rotate phoenix on x-axis
            double angleY;  //Angle to rotate phoenix on y-axis


            //Get angleY
            Vec2 noYTravel = new Vec2((float) travelVector.x, (float) travelVector.z);
            angleY = KMath.vectorAngle(stdVector, noYTravel);

            //If the noYTravel vector lays in QI or QIV, we multiply by -1
            if (noYTravel.x > 0 && noYTravel.y > 0) {
                angleY *= -1;
            } else if (noYTravel.x > 0 && noYTravel.y < 0) {
                angleY *= -1;
            }



            //Get angleX
            Vec3 referenceVector = new Vec3(travelVector.x, 0, travelVector.z);
            angleX = KMath.vectorAngle3(travelVector, referenceVector);

            //The appropriate operations must be done on the angle depending
            //on the quadrant noXTravel lies in

            if (travelVector.y < 0)
                angleX *= -1;



            //Set rotations
            phoenix.setRotX((float) (angleX));
            phoenix.setRotY((float) (-1 * angleY));


        }

    }
}
