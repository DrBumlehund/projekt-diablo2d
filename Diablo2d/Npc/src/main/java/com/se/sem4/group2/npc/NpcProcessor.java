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
import com.se.sem4.group2.common.services.IEntityProcessingService;
import com.se.sem4.group2.common.services.IGamePluginService;
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
        
        if (player == null) return;
        if (entity instanceof Entity) 
        if (entity.getType().equals(NPC)) {

            float theta = (float)Math.atan2(y-player.getY(), x-player.getX());
            theta += Math.PI;

            dx = maxSpeed * (float)Math.cos(theta) * dt;
            dy = maxSpeed * (float)Math.sin(theta) * dt;
            
            //set position
            x += dx;
            y += dy;


            // Update entity
            entity.setPos(x, y);
            entity.setDx(dx);
            entity.setDy(dy);
            entity.setRadians(theta);
            updateShape(entity);
        }

    }

    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        float x = entity.getX();
        float y = entity.getY();
        float radians = entity.getRadians();

        shapex[0] = (float) (x);
        shapey[0] = (float) (y);

        shapex[1] = (float) (x + Math.cos(radians) * entity.getRadius());
        shapey[1] = (float) (y + Math.sin(radians) * entity.getRadius());

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }
    
}
