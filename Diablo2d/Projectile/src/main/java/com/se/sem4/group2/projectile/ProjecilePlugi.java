/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.projectile;

import com.se.sem4.group2.common.data.Entity;
import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.services.IGamePluginService;
import static com.se.sem4.group2.common.data.EntityType.PLAYER;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Thomas
 */
@ServiceProvider(service = com.se.sem4.group2.common.services.IGamePluginService.class)
public class ProjecilePlugi implements IGamePluginService{

    private Map<String, Entity> world;
     private MetaData metaData;
 
     @Override
     public void start(MetaData metaData, Map<String, Entity> world) {
         this.world = world;
         this.metaData = metaData;
         fireProjectile();
     }
 
     @Override
     public void stop(MetaData metaData) {
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
     }
 
     void fireProjectile () {
         // maybe we should get this value through a parameter?
         Entity player = null;
         for (Map.Entry<String, Entity> entrySet : world.entrySet()) {
             Entity value = entrySet.getValue();
             
             if (value.getType() == PLAYER) {
                 player = value;
             }
         }
         if (player == null) {
             return;
         }
         
         Entity projectile = new Entity();
         projectile.setX(player.getX());
         projectile.setY(player.getY());
         projectile.setWidth(2);
         projectile.setHeight(2);
         float speed = 350;
         projectile.setDx((float) Math.cos(player.getRadians() * speed));
         projectile.setDy((float) Math.sin(player.getRadians() * speed));
         
         world.put(projectile.getId(), projectile);
     }   
}
