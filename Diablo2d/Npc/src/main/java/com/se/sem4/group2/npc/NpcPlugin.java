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
 * @author Thomas
 */
@ServiceProvider(service = com.se.sem4.group2.common.services.IGamePluginService.class)
public class NpcPlugin implements IGamePluginService {

    Map<String, Entity> world;
    Entity npc;
    Random rng = new Random();

    @Override
    public void start(MetaData metaData, Map<String, Entity> world) {

        this.world = world;
        this.npc = createNpc(metaData);
        world.put(npc.getId(), npc);
    }

    private Entity createNpc(MetaData metaData) {
        Entity n = new Entity();
        n.setType(NPC);
        n.setPos(rng.nextFloat() * metaData.getDisplayWidth(),
                rng.nextFloat() * metaData.getDisplayHeight());
        n.setRadians((float) Math.PI / 2);
        n.setMaxSpeed(100);
        n.setAcceleration(600);
        n.setDeacceleration(400);

        n.setShapeX(new float[2]);
        n.setShapeY(new float[2]);
        n.setRadius(10f);

        return n;
    }

    @Override
    public void stop(MetaData metaData) {
        world.remove(npc.getId());
    }

}
