/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.weapon;

import com.se.sem4.group2.common.data.Entity;
import com.se.sem4.group2.common.data.EntityType;
import static com.se.sem4.group2.common.data.EntityType.PLAYER;
import static com.se.sem4.group2.common.data.EntityType.PROJECTILE;
import com.se.sem4.group2.common.data.GameKeys;
import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.services.IEntityProcessingService;
import java.awt.Point;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Jesper
 */
@ServiceProvider(service = com.se.sem4.group2.common.services.IEntityProcessingService.class)
public class WeaponProcessor implements IEntityProcessingService {
    long timeStamp = 0;
    Entity player;
    
    @Override
    public void process(MetaData metaData, Map<String, Entity> world, Entity entity) {

        
        if (metaData.getKeys().isDown(GameKeys.SPACE) && System.currentTimeMillis()-timeStamp > 200) {
            
            timeStamp = System.currentTimeMillis();
            for (Map.Entry<String, Entity> entrySet : world.entrySet()) {
                Entity value = entrySet.getValue();

                if (value.getType() == PLAYER) {
                    player = value;
                    fireProjectile(world, player, metaData);
                }
            }
        }

        if (entity.getType() == EntityType.PROJECTILE) {

            
            
            
            float x = entity.getX();
            float y = entity.getY();
            float dt = metaData.getDelta();
            float dx = entity.getDx();
            float dy = entity.getDy();
            
            x += dx*dt;
            y += dy*dt;
            
            entity.setPos(x, y);
            
            entity.setLifeTimer(entity.getLifeTimer()+metaData.getDelta());
            if(entity.getLifeTime()<entity.getLifeTimer()){
                world.remove(entity.getId());
            }

            //XXX: bruger colider entitys dx/dy, eller dem ovenfor?

          

        }

    }

    public void fireProjectile(Map<String, Entity> world, Entity player, MetaData metaData) {
        Entity newProjectile = new Entity();
        newProjectile.setType(PROJECTILE);
        newProjectile.setX(player.getX());
        newProjectile.setY(player.getY());
        newProjectile.setRadius(2);
        newProjectile.setShapeX(new float[0]);
        newProjectile.setShapeY(new float[0]);
        newProjectile.setMaxSpeed(350);
       
        
       
          Point mousePos = metaData.getMousePos();
          float tmpX = mousePos.x - player.getX();
           float tmpY = mousePos.y - player.getY();
        float radians = (float) -(Math.atan2(tmpX, tmpY) - 0.5 * Math.PI);
        if (radians < 0) {
                radians += (float) (2 * Math.PI);
            }
        newProjectile.setRadians(radians);
        
        newProjectile.setDx((float) (Math.cos(radians)*newProjectile.getMaxSpeed()));
        newProjectile.setDy((float) (Math.sin(radians)*newProjectile.getMaxSpeed()));
        newProjectile.setLifeTime(1);
        newProjectile.setLifeTimer(0);
        newProjectile.setWidth(2);
        newProjectile.setHeight(2);
        world.put(newProjectile.getId(), newProjectile);
    }
}
