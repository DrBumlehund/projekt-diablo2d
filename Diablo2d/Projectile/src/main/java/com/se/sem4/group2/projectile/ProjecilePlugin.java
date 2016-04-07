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
public class ProjecilePlugin implements IGamePluginService{

    private Map<String, Entity> world;
    private Entity projectile;
     
     @Override
     public void start(MetaData metaData, Map<String, Entity> world) {
         this.world = world;
         this.projectile = fireProjectile();
         world.put(projectile.getId(), projectile);
     }
 
     @Override
     public void stop(MetaData metaData) {
         world.remove(projectile.getId());
     }
     
     private Entity fireProjectile () {
         // XXX: maybe we should get this value through a parameter?
         // evt. sin egen processor, på nuværende tidspunkt er det kun 
         // PLAYER-entities der kan skyde...
         Entity player = null;
         for (Map.Entry<String, Entity> entrySet : world.entrySet()) {
             Entity value = entrySet.getValue();
             
             if (value.getType() == PLAYER) {
                 player = value;
             }
         }
         if (player == null) {
             return null;
         }
         
         Entity newProjectile = new Entity();
         newProjectile.setX(player.getX());
         newProjectile.setY(player.getY());
         newProjectile.setRadius(2);
         newProjectile.setShapeX(new float[0]);
         newProjectile.setShapeY(new float[0]);
         
         newProjectile.setMaxSpeed(350f);
         newProjectile.setRadians(player.getRadians());
         
         return newProjectile;
     }   
}
