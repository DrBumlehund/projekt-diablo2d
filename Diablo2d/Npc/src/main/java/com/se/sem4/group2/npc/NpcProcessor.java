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
import com.se.sem4.group2.common.data.util.SPILocator;
import com.se.sem4.group2.common.services.IColliderService;
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

    @Override
    public void process(MetaData metaData, Map<String, Entity> world, Entity entity) {
        if (entity instanceof Entity) {
            if (entity.getType().equals(NPC)) {

                //removes dead npc's
                if (entity.isDead()) {
                    world.remove(entity.getId());
                    getColliderService().stop(entity);
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
                            Point target = path.get(2);

                            float direction = (float) Math.atan2(y - target.getY(), x - target.getX());
                            direction += Math.PI;

                            dx = maxSpeed * (float) Math.cos(direction) * dt;
                            dy = maxSpeed * (float) Math.sin(direction) * dt;

                            //set position
                            x += dx;
                            y += dy;

                            /*
                if ((int)(x*100) == (int)(target.x*100)) {
                    if ((int)(y*100) == (int)(target.y*100)) {
                        path.remove(0);
                    }
                }
                             */
                            if ((int) x == target.x && (int) y == target.y) {
                                path.remove(0);
                                path.remove(1);
                                path.remove(2);
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

    private IColliderService getColliderService() {
        return SPILocator.locateFirst(IColliderService.class);
    }

    private float calcDist(Entity me, Entity other) {
        return (float) Math.sqrt(Math.pow((other.getX() - me.getX()), 2) + Math.pow((other.getY() - me.getY()), 2));
    }

}
