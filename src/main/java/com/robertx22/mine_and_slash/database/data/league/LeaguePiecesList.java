package com.robertx22.mine_and_slash.database.data.league;

import java.util.ArrayList;
import java.util.List;

public class LeaguePiecesList {

    public List<LeagueStructurePieces> list = new ArrayList<>();

    public LeaguePiecesList(List<LeagueStructurePieces> list) {
        this.list = list;
    }

    public LeagueStructurePieces get(String id) {
        return list.stream().filter(x -> x.folder.equals(id)).findAny().get();
    }

}
