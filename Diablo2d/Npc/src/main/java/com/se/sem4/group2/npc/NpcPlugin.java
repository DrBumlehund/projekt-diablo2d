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
import com.se.sem4.group2.common.services.IAssetServices.IAssetTextureService;
import com.se.sem4.group2.common.services.IColliderProcessingService;
import com.se.sem4.group2.common.services.IColliderService;
import com.se.sem4.group2.common.services.IGamePluginService;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
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
    public void start(MetaData metaData, Map<String, Entity> world, IAssetTextureService assetmanager) {
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
        if (player == null) return null;
        
        Entity n = new Entity();
        
        double r = Math.sqrt(Math.pow(metaData.getDisplayWidth()/2, 2) + Math.pow(metaData.getDisplayHeight()/2, 2));
        double x = player.getX() + r * Math.cos(random.nextFloat() * 2 * Math.PI);
        double y = player.getY() + r * Math.sin(random.nextFloat() * 2 * Math.PI);
        
        n.setType(NPC);
        n.setPos((float) x, (float) y);
        n.setRadians((float) Math.PI / 2);
        n.setMaxSpeed(100);
        n.setAcceleration(600);
        n.setDeacceleration(400);

        n.setShapeX(new float[2]);
        n.setShapeY(new float[2]);
        n.setRadius(10f);
        
//        n.setHostile(true);
        //Set Sprite, Weapon, Color
        world.put(n.getId(), n);

        List<IColliderService> colliderServices = SPILocator.locateAll(IColliderService.class);
        for (IColliderService colliderService : colliderServices) {
            Ellipse2D shape = new java.awt.geom.Ellipse2D.Float(0, 0, 20, 20);
            Collider collider = new Collider(shape, n);
            colliderService.start(player, collider);
        }

        return n;
    }

    @Override
    public void stop(MetaData metaData) {
        for (Entity entity : npcs) {
            if (world.containsKey(entity)) {
                world.remove(entity.getId());
            }
        }
    }

}
