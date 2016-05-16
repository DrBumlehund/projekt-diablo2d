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
import com.se.sem4.group2.common.data.GameKeys;
import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.data.SpellType;
import com.se.sem4.group2.common.data.util.SPILocator;

import com.se.sem4.group2.common.services.IColliderService;
import com.se.sem4.group2.common.services.IEntityProcessingService;
import java.awt.geom.Ellipse2D;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;
import static com.se.sem4.group2.common.data.EntityType.SPELL;

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
    private SpellType activeSpell;
    private Map<String, Entity> world;

    @Override
    public void process(MetaData metaData, Map<String, Entity> world, Entity entity) {

        this.world = world;

        if (metaData.getKeys().isDown(GameKeys.SPACE) && System.currentTimeMillis() - timeStamp > 200) {

            timeStamp = System.currentTimeMillis();
            for (Map.Entry<String, Entity> entrySet : world.entrySet()) {
                Entity value = entrySet.getValue();

                if (value.getType() == PLAYER) {
                    player = value;
                    activeSpell = player.getActiveSpell();
                    fireSpell(activeSpell);
                }
            }
        }

        if (entity.getType() == EntityType.SPELL) {

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

//    private void loadSounds(IAssetAudioService soundManager) {
//        soundManager.load("com/se/sem4/group2/weapon/fireball1.wav", "Sound");
//        soundManager.load("com/se/sem4/group2/weapon/fireball2.wav", "Sound");
//        soundManager.load("com/se/sem4/group2/weapon/fireball3.wav", "Sound");
//
//        soundManager.load("com/se/sem4/group2/weapon/icebolt1.wav", "Sound");
//        soundManager.load("com/se/sem4/group2/weapon/icebolt2.wav", "Sound");
//        soundManager.load("com/se/sem4/group2/weapon/icebolt3.wav", "Sound");
//
//        soundManager.load("com/se/sem4/group2/weapon/chargedbolt1.wav", "Sound");
//        soundManager.load("com/se/sem4/group2/weapon/chargedbolt2.wav", "Sound");
//        soundManager.load("com/se/sem4/group2/weapon/chargedbolt3.wav", "Sound");
//    }
//
//    private void loadTextures(IAssetTextureService textureManager) {
//        textureManager.load("com/se/sem4/group2/weapon/fireball.png", "Texture");
//        textureManager.load("com/se/sem4/group2/weapon/icebolt.png", "Texture");
//        textureManager.load("com/se/sem4/group2/weapon/chargedbolt.png", "Texture");
//
//    }

//    private void randomSoundPlayer(IAssetAudioService soundManager, SpellType activeSpell) {
//
//        Random rng = new Random();
//        int rnd = rng.nextInt(3);
//        String path;
//
//        switch (activeSpell) {
//            default:
//                switch (rnd) {
//                    case 0:
//                        path = "com/se/sem4/group2/weapon/fireball1.wav";
//                        break;
//                    case 1:
//                        path = "com/se/sem4/group2/weapon/fireball2.wav";
//                        break;
//                    default:
//                        path = "com/se/sem4/group2/weapon/fireball3.wav";
//                        break;
//                }
//                break;
//            case ICEBOLT:
//                switch (rnd) {
//                    case 0:
//                        path = "com/se/sem4/group2/weapon/icebolt1.wav";
//                        break;
//                    case 1:
//                        path = "com/se/sem4/group2/weapon/icebolt2.wav";
//                        break;
//                    default:
//                        path = "com/se/sem4/group2/weapon/icebolt3.wav";
//                        break;
//                }
//                break;
//            case CHARGEDBOLT:
//                switch (rnd) {
//                    case 0:
//                        path = "com/se/sem4/group2/weapon/chargedbolt1.wav";
//                        break;
//                    case 1:
//                        path = "com/se/sem4/group2/weapon/chargedbolt2.wav";
//                        break;
//                    default:
//                        path = "com/se/sem4/group2/weapon/chargedbolt3.wav";
//                        break;
//                }
//                break;
//        }
//        soundManager.playSound(path);
//    }

    private void fireSpell(SpellType activeSpell) {
        switch (activeSpell) {
            case FIREBALL:
                createFireball(world, player);
                break;
            case ICEBOLT:
                createIcebolt(world, player);
                break;
            case CHARGEDBOLT:
                createChargedbolt(world, player);
                break;
            default:
                break;
        }
    }

    public void createFireball(Map<String, Entity> world, Entity player) {
        Entity fireball = new Entity();
        fireball.setType(SPELL);
        fireball.setName("Fireball");
        fireball.setRadius(RADIUS);
        fireball.setRadians(player.getRadians());
        fireball.setSpritePath("com/se/sem4/group2/weapon/fireball.png");

        float offset = player.getRadius() + 2 + RADIUS;
        fireball.setX((float) (player.getX() + Math.cos(player.getRadians()) * offset));
        fireball.setY((float) (player.getY() + Math.sin(player.getRadians()) * offset));
        fireball.setMaxSpeed(350);

        fireball.setDx((float) (Math.cos(player.getRadians()) * fireball.getMaxSpeed()));
        fireball.setDy((float) (Math.sin(player.getRadians()) * fireball.getMaxSpeed()));

        fireball.setMaxHealth(1); // Fireballs needs to die in first hit.
        fireball.setMaxDamage(250);
        fireball.setMinDamage(150);

        fireball.setLifeTime(2);
        fireball.setLifeTimer(0);
        world.put(fireball.getId(), fireball);

        Ellipse2D shape = new java.awt.geom.Ellipse2D.Float(0, 0, RADIUS * 2, RADIUS * 2);
        Collider collider = new Collider(shape, fireball);

        getColliderService().start(fireball, collider);
    }

    public void createIcebolt(Map<String, Entity> world, Entity player) {
        Entity fireball = new Entity();
        fireball.setType(SPELL);
        fireball.setName("Frostbolt");
        fireball.setRadius(RADIUS);
        fireball.setRadians(player.getRadians());
        fireball.setSpritePath("com/se/sem4/group2/weapon/icebolt.png");

        float offset = player.getRadius() + 2 + RADIUS;
        fireball.setX((float) (player.getX() + Math.cos(player.getRadians()) * offset));
        fireball.setY((float) (player.getY() + Math.sin(player.getRadians()) * offset));
        fireball.setMaxSpeed(450);

        fireball.setDx((float) (Math.cos(player.getRadians()) * fireball.getMaxSpeed()));
        fireball.setDy((float) (Math.sin(player.getRadians()) * fireball.getMaxSpeed()));

        fireball.setMaxHealth(1); // Fireballs needs to die in first hit.
        fireball.setMaxDamage(150);
        fireball.setMinDamage(75);

        fireball.setLifeTime(2);
        fireball.setLifeTimer(0);
        world.put(fireball.getId(), fireball);

        Ellipse2D shape = new java.awt.geom.Ellipse2D.Float(0, 0, RADIUS * 2, RADIUS * 2);
        Collider collider = new Collider(shape, fireball);

        getColliderService().start(fireball, collider);

    }

    public void createChargedbolt(Map<String, Entity> world, Entity player) {

        Entity fireball = new Entity();
        fireball.setType(SPELL);
        fireball.setName("Arcanebolt");
        fireball.setRadius(RADIUS);
        fireball.setRadians(player.getRadians());
        fireball.setSpritePath("com/se/sem4/group2/weapon/chargedbolt.png");

        float offset = player.getRadius() + 2 + RADIUS;
        fireball.setX((float) (player.getX() + Math.cos(player.getRadians()) * offset));
        fireball.setY((float) (player.getY() + Math.sin(player.getRadians()) * offset));

        fireball.setMaxSpeed((float) (player.getMaxSpeed() * Math.random() + player.getMaxSpeed()));

        fireball.setDx((float) (Math.cos(player.getRadians()) * fireball.getMaxSpeed()));
        fireball.setDy((float) (Math.sin(player.getRadians()) * fireball.getMaxSpeed()));

        fireball.setMaxHealth(1); // Fireballs needs to die in first hit.
        fireball.setMaxDamage(1100);
        fireball.setMinDamage(70);

        fireball.setLifeTime(2);
        fireball.setLifeTimer(0);
        world.put(fireball.getId(), fireball);

        Ellipse2D shape = new java.awt.geom.Ellipse2D.Float(0, 0, RADIUS * 2, RADIUS * 2);
        Collider collider = new Collider(shape, fireball);

        getColliderService().start(fireball, collider);
    }

    private IColliderService getColliderService() {
        return SPILocator.locateFirst(IColliderService.class);
    }
}
