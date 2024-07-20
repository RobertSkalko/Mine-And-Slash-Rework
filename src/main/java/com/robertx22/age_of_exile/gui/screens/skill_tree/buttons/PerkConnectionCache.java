package com.robertx22.age_of_exile.gui.screens.skill_tree.buttons;

import com.robertx22.age_of_exile.gui.screens.skill_tree.SkillTreeScreen;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.library_of_exile.utils.Watch;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.client.gui.components.events.GuiEventListener;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PerkConnectionCache {

    public static Int2ReferenceOpenHashMap<Int2ReferenceOpenHashMap<PerkConnectionRender2>> rendersCache = new Int2ReferenceOpenHashMap<>(5000);

    public static IntOpenHashSet typeHistory = new IntOpenHashSet(5);

    public static ConcurrentLinkedQueue<PerkButton> update = new ConcurrentLinkedQueue<>();

    public static boolean canUpdate = false;


    public static void addToUpdate(PerkButton button) {
        update.add(button);
    }

    @SuppressWarnings("unchecked")
    public static void updateRenders(SkillTreeScreen skillTreeScreen) {
        if (update.isEmpty()) return;
        if (!canUpdate) return;
        var data = Load.player(ClientOnly.getPlayer());
        while (!update.isEmpty()) {
            PerkButton poll = update.poll();
            int buttonSchoolTypeHash = poll.screen.schoolType.toString().hashCode();
            //System.out.println("take a point: " + poll);
            Set<PointData> connections = skillTreeScreen.school.calcData.connections.getOrDefault(poll.point, Collections.EMPTY_SET);

            //System.out.println("related points: " + connections);
            for (PointData connection : connections) {

                PerkButton sb = skillTreeScreen.pointPerkButtonMap.get(connection);

                if (sb == null) {
                    continue;
                }

                var con = data.talents.getConnection(skillTreeScreen.school, sb.point, poll.point);
                var result = new PerkConnectionRender2(new PerkPointPair(poll.point, sb.point), con);
                //System.out.println("update point connection: " + poll.point + "     " + sb.point);
                rendersCache.get(buttonSchoolTypeHash).merge(result.hashCode(), result, (oldOne, newOne) -> {
                    //System.out.println(oldOne.connection);
                    //System.out.println(newOne.connection);
                    return newOne;
                });
            }
        }

        canUpdate = false;
    }

    @SuppressWarnings("unchecked")
    private static Int2ReferenceOpenHashMap<PerkConnectionRender2> initAllConnection(SkillTreeScreen skillTreeScreen) {

        Int2ReferenceOpenHashMap<PerkConnectionRender2> map = new Int2ReferenceOpenHashMap<>();

        IntOpenHashSet integers = new IntOpenHashSet(2000);

        var data = Load.player(ClientOnly.getPlayer());
        List<? extends GuiEventListener> children = skillTreeScreen.children();
        if (children.size() > 1500) {
            //use parallel if too many renderer
            ConcurrentHashMap.KeySetView<PerkConnectionRender2, Boolean> objects = ConcurrentHashMap.newKeySet(2000);
            children.parallelStream().forEach(b -> {
                if (b instanceof PerkButton pb) {

                    Set<PointData> connections = skillTreeScreen.school.calcData.connections.getOrDefault(pb.point, Collections.EMPTY_SET);

                    for (PointData p : connections) {

                        PerkButton sb = skillTreeScreen.pointPerkButtonMap.get(p);
                        PerkPointPair pair = new PerkPointPair(pb.point, sb.point);
                        if (!integers.contains(pair.hashCode())) {
                            if (sb == null) {
                                continue;
                            }

                            var con = data.talents.getConnection(skillTreeScreen.school, sb.point, pb.point);
                            var result = new PerkConnectionRender2(pair, con);
                            objects.add(result);
                            integers.add(pair.hashCode());
                        }

                    }
                }

            });
            System.out.println("render count: " + objects.size());
            objects.forEach(x -> map.put(x.hashCode(), x));
        } else {
            children.forEach(b -> {
                if (b instanceof PerkButton pb) {

                    Set<PointData> connections = skillTreeScreen.school.calcData.connections.getOrDefault(pb.point, Collections.EMPTY_SET);

                    for (PointData p : connections) {

                        PerkButton sb = skillTreeScreen.pointPerkButtonMap.get(p);
                        PerkPointPair pair = new PerkPointPair(pb.point, sb.point);
                        if (!integers.contains(pair.hashCode())) {
                            if (sb == null) {
                                continue;
                            }

                            var con = data.talents.getConnection(skillTreeScreen.school, sb.point, pb.point);
                            var result = new PerkConnectionRender2(pair, con);
                            map.put(result.hashCode(), result);
                            integers.add(pair.hashCode());
                        }

                    }
                }

            });

        }
        return map;

    }

    public static void init(SkillTreeScreen skillTreeScreen) {
        int typeHash = skillTreeScreen.schoolType.toString().hashCode();
        if (rendersCache.isEmpty() || !typeHistory.contains(typeHash)) {
            Watch watch = new Watch();
            rendersCache.put(typeHash, initAllConnection(skillTreeScreen));
            typeHistory.add(typeHash);
            System.out.println("add all: " + watch.getPrint());
        }
    }


}
