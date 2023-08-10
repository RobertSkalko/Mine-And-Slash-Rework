package com.robertx22.age_of_exile.database.data.spells.summons.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ColorableAgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class ModWolfModel<T extends LivingEntity> extends ColorableAgeableListModel<T> {
    private static final String REAL_HEAD = "real_head";
    private static final String UPPER_BODY = "upper_body";
    private static final String REAL_TAIL = "real_tail";
    private final ModelPart head;
    /**
     * Added as a result/workaround for the loss of renderWithRotation
     */
    private final ModelPart realHead;
    private final ModelPart body;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;
    private final ModelPart tail;
    /**
     * Added as a result/workaround for the loss of renderWithRotation
     */
    private final ModelPart realTail;
    private final ModelPart upperBody;
    private static final int LEG_SIZE = 8;

    public ModWolfModel(ModelPart pRoot) {
        this.head = pRoot.getChild("head");
        this.realHead = this.head.getChild("real_head");
        this.body = pRoot.getChild("body");
        this.upperBody = pRoot.getChild("upper_body");
        this.rightHindLeg = pRoot.getChild("right_hind_leg");
        this.leftHindLeg = pRoot.getChild("left_hind_leg");
        this.rightFrontLeg = pRoot.getChild("right_front_leg");
        this.leftFrontLeg = pRoot.getChild("left_front_leg");
        this.tail = pRoot.getChild("tail");
        this.realTail = this.tail.getChild("real_tail");
    }


    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.head);
    }

    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body, this.rightHindLeg, this.leftHindLeg, this.rightFrontLeg, this.leftFrontLeg, this.tail, this.upperBody);
    }

    public void prepareMobModel(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick) {

        this.tail.yRot = 0.0F;


        this.body.setPos(0.0F, 14.0F, 2.0F);
        this.body.xRot = ((float) Math.PI / 2F);
        this.upperBody.setPos(-1.0F, 14.0F, -3.0F);
        this.upperBody.xRot = this.body.xRot;
        this.tail.setPos(-1.0F, 12.0F, 8.0F);
        this.rightHindLeg.setPos(-2.5F, 16.0F, 7.0F);
        this.leftHindLeg.setPos(0.5F, 16.0F, 7.0F);
        this.rightFrontLeg.setPos(-2.5F, 16.0F, -4.0F);
        this.leftFrontLeg.setPos(0.5F, 16.0F, -4.0F);
        this.rightHindLeg.xRot = Mth.cos(pLimbSwing * 0.6662F) * 1.4F * pLimbSwingAmount;
        this.leftHindLeg.xRot = Mth.cos(pLimbSwing * 0.6662F + (float) Math.PI) * 1.4F * pLimbSwingAmount;
        this.rightFrontLeg.xRot = Mth.cos(pLimbSwing * 0.6662F + (float) Math.PI) * 1.4F * pLimbSwingAmount;
        this.leftFrontLeg.xRot = Mth.cos(pLimbSwing * 0.6662F) * 1.4F * pLimbSwingAmount;


        /*
        this.realHead.zRot = pEntity.getHeadRollAngle(pPartialTick) + pEntity.getBodyRollAngle(pPartialTick, 0.0F);
        this.upperBody.zRot = pEntity.getBodyRollAngle(pPartialTick, -0.08F);
        this.body.zRot = pEntity.getBodyRollAngle(pPartialTick, -0.16F);
        this.realTail.zRot = pEntity.getBodyRollAngle(pPartialTick, -0.2F);

         */
    }

    /**
     * Sets this entity's model rotation angles
     */
    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        this.head.xRot = pHeadPitch * ((float) Math.PI / 180F);
        this.head.yRot = pNetHeadYaw * ((float) Math.PI / 180F);
        //this.tail.xRot = pAgeInTicks;
    }
}