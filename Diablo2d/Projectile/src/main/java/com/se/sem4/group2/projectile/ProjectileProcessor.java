/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.projectile;

import com.se.sem4.group2.common.data.Entity;
import com.se.sem4.group2.common.data.EntityType;
import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.services.IEntityProcessingService;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Thomas
 */
@ServiceProvider(service = com.se.sem4.group2.common.services.IEntityProcessingService.class)
public class ProjectileProcessor implements IEntityProcessingService {

    @Override
    public void process(MetaData metaData, Map<String, Entity> world, Entity entity) {
        if (entity.getType() == EntityType.PROJECTILE) {

            float x = entity.getX();
            float y = entity.getY();
            float dt = metaData.getDelta();
            float dx;
            float dy;
            float speed = entity.getMaxSpeed();
            float radians = entity.getRadians();

            dx = ((float) Math.cos(radians * speed)) * dt;
            dy = ((float) Math.sin(radians * speed)) * dt;
            
            //XXX: bruger colider entitys dx/dy, eller dem ovenfor?
            entity.setDx(dx);
            entity.setDy(dy);
            
            entity.setPos(x + dx, y + dy);

        }

    }
}
