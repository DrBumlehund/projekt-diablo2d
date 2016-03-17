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
 * @author Jesper
 */

@ServiceProvider(service = com.se.sem4.group2.common.services.IEntityProcessingService.class)
public class ProjectileProcessor implements IEntityProcessingService{

    @Override
    public void process(MetaData metaData, Map<String, Entity> world, Entity entity) {
        if(entity.getType() == EntityType.PROJECTILE){
            
        float x = entity.getX();
        float y = entity.getY();
        float dt = metaData.getDelta();
        float dx = entity.getDx();
        float dy = entity.getDy();
        
        
        x = dx * dt;
             if (x > metaData.getDisplayWidth()) {
                x = 0;
            } else if (x < 0) {
                x = metaData.getDisplayWidth();
            }
        
        y = dy * dt;
            if (y > metaData.getDisplayHeight()) {
                y = 0;
            } else if (y < 0) {
                y = metaData.getDisplayHeight();
            }
            
            entity.setPos(x, y);
        
        }
    
    
}
}
