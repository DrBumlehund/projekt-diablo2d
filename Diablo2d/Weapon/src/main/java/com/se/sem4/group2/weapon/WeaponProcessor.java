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
import java.awt.geom.Ellipse2D;
import java.util.Collection;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Jesper
 */
@ServiceProvider(service = com.se.sem4.group2.common.services.IEntityProcessingService.class)
public class WeaponProcessor implements IEntityProcessingService {

    static int RADIUS = 3;
    Collection<? extends IColliderService> colliderServices = SPILocator.locateAll(IColliderService.class);
    long timeStamp = 0;
    Entity player;

    @Override
    public void process(MetaData metaData, Map<String, Entity> world, Entity entity, IAssetTextureService assetManager) {

        if (metaData.getKeys().isDown(GameKeys.SPACE) && System.currentTimeMillis() - timeStamp > 200) {

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

            // Removes the bullet if certain conditions is met.
            if (entity.getLifeTime() < entity.getLifeTimer() || entity.isColided()) {
                world.remove(entity.getId());
                getColliderService().stop(entity);
            }
            
            float x = entity.getX();
            float y = entity.getY();
            float dt = metaData.getDelta();
            float dx = entity.getDx();
            float dy = entity.getDy();

            x += dx * dt;
            y += dy * dt;

            entity.setPos(x, y);

            entity.setLifeTimer(entity.getLifeTimer() + metaData.getDelta());      
        }

    }

    public void fireProjectile(Map<String, Entity> world, Entity player) {
        Entity bullet = new Entity();
        bullet.setType(PROJECTILE);
        bullet.setName("BULLET");
        bullet.setRadius(RADIUS);
        bullet.setRadians(player.getRadians());

        float offset = player.getRadius() + RADIUS + 1;
        bullet.setX((float) (player.getX() + Math.cos(player.getRadians()) * offset));
        bullet.setY((float) (player.getY() + Math.sin(player.getRadians()) * offset));
        bullet.setMaxSpeed(350);

        bullet.setDx((float) (Math.cos(player.getRadians()) * bullet.getMaxSpeed()));
        bullet.setDy((float) (Math.sin(player.getRadians()) * bullet.getMaxSpeed()));

        bullet.setLifeTime(1);
        bullet.setLifeTimer(0);
        world.put(bullet.getId(), bullet);

        Ellipse2D shape = new java.awt.geom.Ellipse2D.Float(0, 0, RADIUS * 2, RADIUS * 2);
        Collider collider = new Collider(shape, bullet);

        getColliderService().start(bullet, collider);

    }

    private IColliderService getColliderService() {
        return SPILocator.locateFirst(IColliderService.class);
    }
}
