package com.robertx22.mine_and_slash.maps.room_adders;

import com.robertx22.mine_and_slash.maps.generator.RoomType;

public class Sewer2RoomAdder extends BaseRoomAdder {

    public Sewer2RoomAdder() {
        super();
    }

    @Override
    public void addAllRooms() {

        add("sewers_corner_01vcf", RoomType.CURVED_HALLWAY);
        add("sewers_corner_01vf", RoomType.CURVED_HALLWAY);
        add("sewers_corner_02vcf", RoomType.CURVED_HALLWAY);
        add("sewers_corner_02vf", RoomType.CURVED_HALLWAY);
        add("sewers_corner_03vcf", RoomType.CURVED_HALLWAY);
        add("sewers_corner_03vf", RoomType.CURVED_HALLWAY);

        add("sewers_bossroom_01", RoomType.END);
        add("sewers_bossroom_02", RoomType.END);
        add("sewers_bossroom_03", RoomType.END);
        add("sewers_bossroom_04", RoomType.END);
        add("sewers_bossroom_05", RoomType.END);
        add("sewers_bossroom_06", RoomType.END);

        add("sewers_beginroom_base", RoomType.ENTRANCE);

        add("sewers_cross_01vcf", RoomType.FOUR_WAY);
        add("sewers_cross_01vf", RoomType.FOUR_WAY);
        add("sewers_cross_02vcf", RoomType.FOUR_WAY);
        add("sewers_cross_02vf", RoomType.FOUR_WAY);
        add("sewers_cross_03vcf", RoomType.FOUR_WAY);

        add("sewers_straight_01vcf", RoomType.STRAIGHT_HALLWAY);
        add("sewers_straight_01vf", RoomType.STRAIGHT_HALLWAY);
        add("sewers_straight_02vf", RoomType.STRAIGHT_HALLWAY);
        add("sewers_straight_02vcf", RoomType.STRAIGHT_HALLWAY);
        add("sewers_straight_03vcf", RoomType.STRAIGHT_HALLWAY);
        add("sewers_straight_03vf", RoomType.STRAIGHT_HALLWAY);
        add("sewers_straight_04vcf", RoomType.STRAIGHT_HALLWAY);

        add("sewers_tsection_01vcf", RoomType.TRIPLE_HALLWAY);
        add("sewers_tsection_01vf", RoomType.TRIPLE_HALLWAY);
        add("sewers_tsection_02vcf", RoomType.TRIPLE_HALLWAY);
        add("sewers_tsection_02vf", RoomType.TRIPLE_HALLWAY);
        add("sewers_tsection_03vcf", RoomType.TRIPLE_HALLWAY);
        add("sewers_tsection_03vf", RoomType.TRIPLE_HALLWAY);

    }
}


