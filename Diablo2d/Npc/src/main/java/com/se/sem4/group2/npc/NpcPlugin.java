/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.npc;

import com.se.sem4.group2.common.data.Entity;
import static com.se.sem4.group2.common.data.EntityType.NPC;
import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.services.IGamePluginService;
import java.util.Map;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author ThomasLemqvist
 */
@ServiceProvider(service = com.se.sem4.group2.common.services.IGamePluginService.class)
public class NpcPlugin implements IGamePluginService {

    private Map<String, Entity> world;
    private Entity npc;

    Random rng = new Random();

    @Override
    public void start(MetaData metaData, Map<String, Entity> world) {
        this.world = world;
        npc = createNpc(metaData);
        world.put(npc.getId(), npc);
    }

    private Entity createNpc(MetaData metaData) {
        Entity newNpc = new Entity();
        newNpc.setType(NPC);
        newNpc.setPos(rng.nextInt(metaData.getDisplayWidth()), rng.nextInt(metaData.getDisplayHeight()));

        newNpc.setRadians((float) Math.PI / 2);
        newNpc.setMaxSpeed(100);
        newNpc.setAcceleration(600);
        newNpc.setDeacceleration(400);

        newNpc.setShapeX(new float[2]);
        newNpc.setShapeY(new float[2]);
        newNpc.setRadius(10f);

        return newNpc;
    }

    @Override
    public void stop(MetaData metaData) {
        world.remove(npc.getId());
    }

}
