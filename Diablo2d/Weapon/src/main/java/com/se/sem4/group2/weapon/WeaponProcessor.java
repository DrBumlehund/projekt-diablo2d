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
import com.se.sem4.group2.common.services.IAssetServices.IAssetAudioService;
import com.se.sem4.group2.common.services.IAssetServices.IAssetTextureService;
import com.se.sem4.group2.common.services.IColliderService;
import com.se.sem4.group2.common.services.IEntityProcessingService;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Jesper
 */
@ServiceProvider(service = com.se.sem4.group2.common.services.IEntityProcessingService.class)
public class WeaponProcessor implements IEntityProcessingService {

    private static int RADIUS = 10;
    private Collection<? extends IColliderService> colliderServices = SPILocator.locateAll(IColliderService.class);
    private long timeStamp = 0;
    private Entity player;
    private Boolean first = true;

    @Override
    public void process(MetaData metaData, Map<String, Entity> world, Entity entity, IAssetTextureService textureManager, IAssetAudioService soundManager) {

        if (first) {
            loadSounds(soundManager);
            loadTextures(textureManager);
            first = false;
        }

        if (metaData.getKeys().isDown(GameKeys.SPACE) && System.currentTimeMillis() - timeStamp > 200) {

            timeStamp = System.currentTimeMillis();
            for (Map.Entry<String, Entity> entrySet : world.entrySet()) {
                Entity value = entrySet.getValue();

                if (value.getType() == PLAYER) {
                    player = value;
                    fireProjectile(world, player);
                    randomSoundPlayer(soundManager);
                }
            }
        }

        if (entity.getType() == EntityType.PROJECTILE) {

            // Removes the bullet if certain conditions is met.
            if (entity.getLifeTime() < entity.getLifeTimer() || entity.isColided() || entity.isDead()) {
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

    private void loadSounds(IAssetAudioService soundManager) {
        soundManager.load("com/se/sem4/group2/weapon/fireball1.wav", "Sound");
        soundManager.load("com/se/sem4/group2/weapon/fireball2.wav", "Sound");
        soundManager.load("com/se/sem4/group2/weapon/fireball3.wav", "Sound");
    }
    
    private void loadTextures(IAssetTextureService textureManager){
        textureManager.load("com/se/sem4/group2/weapon/firebolt.png", "Texture");
        
    }

    private void randomSoundPlayer(IAssetAudioService soundManager) {

        Random rng = new Random();
        int rnd = rng.nextInt(3);
        String path;
        if (rnd == 0) {
            path = "com/se/sem4/group2/weapon/fireball1.wav";
        } else if (rnd == 1) {
            path = "com/se/sem4/group2/weapon/fireball2.wav";
        } else {
            path = "com/se/sem4/group2/weapon/fireball3.wav";
        }
        soundManager.playSound(path);
        return;
    }

    public void fireProjectile(Map<String, Entity> world, Entity player) {
        Entity bullet = new Entity();
        bullet.setType(PROJECTILE);
        bullet.setName("BULLET");
        bullet.setRadius(RADIUS);
        bullet.setRadians(player.getRadians());
        bullet.setSpritePath("com/se/sem4/group2/weapon/firebolt.png");

        float offset = player.getRadius() + 4;
        bullet.setX((float) (player.getX() + Math.sin(player.getRadians()) * offset));
        bullet.setY((float) (player.getY() + Math.cos(player.getRadians()) * offset));
        bullet.setMaxSpeed(350);

        fireball.setMaxHealth(1); // Fireballs needs to die in first hit.
        fireball.setMaxDamage(250);
        fireball.setMinDamage(150);


        Ellipse2D shape = new java.awt.geom.Ellipse2D.Float(0, 0, RADIUS * 2, RADIUS * 2);
        Collider collider = new Collider(shape, fireball);

        getColliderService().start(fireball, collider);

    }

    private IColliderService getColliderService() {
        return SPILocator.locateFirst(IColliderService.class);
    }
}
