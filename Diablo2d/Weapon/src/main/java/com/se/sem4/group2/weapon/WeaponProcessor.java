/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.weapon;

import com.se.sem4.group2.common.data.Collider;
import com.se.sem4.group2.common.data.Entity;
import com.se.sem4.group2.common.data.EntityType;
import static com.se.sem4.group2.common.data.EntityType.PLAYER;
import static com.se.sem4.group2.common.data.EntityType.PROJECTILE;
import com.se.sem4.group2.common.data.GameKeys;
import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.data.Transform;
import com.se.sem4.group2.common.data.util.SPILocator;
import com.se.sem4.group2.common.services.IAssetServices.IAssetTextureService;
import com.se.sem4.group2.common.services.IColliderService;
import com.se.sem4.group2.common.services.IEntityProcessingService;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Jesper
 */
@ServiceProvider(service = com.se.sem4.group2.common.services.IEntityProcessingService.class)
public class WeaponProcessor implements IEntityProcessingService {
    Collection<? extends IColliderService> colliderServices = SPILocator.locateAll(IColliderService.class);
    long timeStamp = 0;
    Entity player;
    
    @Override
    public void process(MetaData metaData, Map<String, Entity> world, Entity entity, IAssetTextureService assetManager) {

        
        if (metaData.getKeys().isDown(GameKeys.SPACE) && System.currentTimeMillis()-timeStamp > 200) {
            
            timeStamp = System.currentTimeMillis();
            for (Map.Entry<String, Entity> entrySet : world.entrySet()) {
                Entity value = entrySet.getValue();

                if (value.getType() == PLAYER) {
                    player = value;
                    fireProjectile(world, player);
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

    public void fireProjectile(Map<String, Entity> world, Entity player) {
        Entity newProjectile = new Entity();
        newProjectile.setType(PROJECTILE);
        newProjectile.setRadius(3);
        newProjectile.setRadians(player.getRadians());
        
        //x og y bliver sat på spidsen af den streg der viser hvor player kigger hen
        newProjectile.setX(player.getShapeX()[1]);
        newProjectile.setY(player.getShapeY()[1]);
        newProjectile.setMaxSpeed(350);
      
        
        
        newProjectile.setDx((float) (Math.cos(player.getRadians())*newProjectile.getMaxSpeed()));
        newProjectile.setDy((float) (Math.sin(player.getRadians())*newProjectile.getMaxSpeed()));
        newProjectile.setLifeTime(1);
        newProjectile.setLifeTimer(0);
        world.put(newProjectile.getId(), newProjectile);
    }
    
    
    //ved ikke lige hvad der skal gøres med det her
    public void collisionDetection(){
            for (IColliderService collider : colliderServices) {
            Transform transform = new Transform();
            transform = new Transform();
            transform.setX(5);
            transform.setName("nummer 2");
            collider.start(transform, new Collider(new Rectangle(10, 10, 200, 20), transform));
        }
    }
}
