/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.npc;

import com.se.sem4.group2.common.data.Entity;
import com.se.sem4.group2.common.data.Collider;
import com.se.sem4.group2.common.data.EntityType;
import static com.se.sem4.group2.common.data.EntityType.NPC;
import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.data.util.SPILocator;
import com.se.sem4.group2.common.services.IColliderService;
import com.se.sem4.group2.common.services.IGamePluginService;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Thomas
 */
@ServiceProvider(service = com.se.sem4.group2.common.services.IGamePluginService.class)
public class NpcPlugin implements IGamePluginService {

    private Random random;
    private long spawnDelay = 20000;
    private Map<String, Entity> world;
    private List<Entity> npcs = new ArrayList<Entity>();
    private MetaData metaData;

    @Override
    public void start(MetaData metaData, Map<String, Entity> world) {
        random = new Random();
        this.metaData = metaData;
        this.world = world;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                createNpc();
            }
        }, 0, spawnDelay);
    }

    private Entity createNpc() {

        Entity player = null;

        for (Map.Entry<String, Entity> entry : world.entrySet()) {
            String key = entry.getKey();
            Entity value = entry.getValue();
            if (value.getType() == EntityType.PLAYER) {
                player = value;
            }
        }
        if (player == null) {
            return null;
        }

        Entity n = new Entity();

        double r = Math.sqrt(Math.pow(metaData.getDisplayWidth() / 2, 2) + Math.pow(metaData.getDisplayHeight() / 2, 2));
        double x = player.getX() + r * Math.cos(random.nextFloat() * 2 * Math.PI);
        double y = player.getY() + r * Math.sin(random.nextFloat() * 2 * Math.PI);

        n.setType(NPC);
        n.setPos((float) x, (float) y);
        n.setRadians((float) Math.PI / 2);
        n.setMaxSpeed(100);
        n.setAcceleration(600);
        n.setDeacceleration(400);

        n.setRadius(10f);

        n.setMaxHealth(1000); // npc's have a lot of health,
        n.setMaxDamage(10);   // but they don't do a lot og damage.
        n.setMinDamage(5);

        String path = (new File("").getAbsolutePath() + "/target/diablo2d/diablo2d/modules/com-se-sem4-group2-Npc.jar!/assets/images/snowman.png");

        n.setTexturePath(path);
        //n.setHostile(true);

        world.put(n.getId(), n);

        return n;
    }

    @Override
    public void stop(MetaData metaData) {
        for (Entity entity : npcs) {
            if (world.containsKey(entity.getId())) {
                // TODO: TEST IF THIS WORKS (stop: npc)

                world.remove(entity.getId());
            }
        }
    }

}
