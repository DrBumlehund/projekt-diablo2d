/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.npc;

import com.se.sem4.group2.common.data.Entity;
import com.se.sem4.group2.common.data.EntityType;
import static com.se.sem4.group2.common.data.EntityType.NPC;
import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.services.IEntityProcessingService;
import java.awt.Point;
import java.util.List;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Thomas
 */
@ServiceProvider(service = com.se.sem4.group2.common.services.IEntityProcessingService.class)
public class NpcProcessor implements IEntityProcessingService {

    private int divisor = 64;

    @Override
    public void process(MetaData metaData, Map<String, Entity> world, Entity entity) {
        if (entity instanceof Entity) {
            if (entity.getType().equals(NPC)) {

                //removes dead npc's
                if (entity.isDead()) {
                    world.remove(entity.getId());
                }
                
                float x = entity.getX();
                float y = entity.getY();
                float dt = metaData.getDelta();
                float dx = entity.getDx();
                float dy = entity.getDy();
                float maxSpeed = entity.getMaxSpeed();
                Entity player = null;

                for (Map.Entry<String, Entity> entry : world.entrySet()) {
                    String key = entry.getKey();
                    Entity value = entry.getValue();
                    if (value.getType() == EntityType.PLAYER) {
                        player = value;
                    }
                }

                if (player == null) {
                    return;
                }
                if (entity instanceof Entity) {
                    if (entity.getType().equals(NPC)) {
                        List<Point> path = entity.getPath();
                        float theta = (float) Math.atan2(y - player.getY(), x - player.getX());
                        theta += Math.PI;

                        if (path.size() > 0) {
                            Point target = path.get(0);
                            //target = new Point(64,64);
                            float direction = (float) Math.atan2(target.getY() - y , target.getX() - x);
                            direction += Math.PI * 2;

                            dx = maxSpeed * (float) Math.cos(direction) * dt;
                            dy = maxSpeed * (float) Math.sin(direction) * dt;

                            //set position
                            x += dx;
                            y += dy;
                            
                            if (almostEqual(x, target.x, 30) && almostEqual(y, target.y, 30)) {
                                path.remove(0);
                            }

                            entity.setPos(x, y);
                            entity.setDx(dx);
                            entity.setDy(dy);
                        }

                        // Update entity
                        entity.setRadians(theta);
                    }
                }
            }
        }
    }

    private float calcDist(Entity me, Entity other) {
        return (float) Math.sqrt(Math.pow((other.getX() - me.getX()), 2) + Math.pow((other.getY() - me.getY()), 2));
    }
    
    public static boolean almostEqual(double a, double b, double eps){
        return Math.abs(a-b)<eps;
    }
}
