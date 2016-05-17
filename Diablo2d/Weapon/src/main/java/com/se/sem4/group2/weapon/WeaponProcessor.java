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
import org.openide.util.lookup.ServiceProvider;
import static com.se.sem4.group2.common.data.EntityType.SPELL;
import java.io.File;
import java.util.List;

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
                    fireSpell(world, player);
                }
            }
        }

        if (entity.getType() == EntityType.SPELL) {

            // Removes the bullet if certain conditions is met.
            if (entity.getLifeTime() < entity.getLifeTimer() || entity.isColided() || entity.isDead()) {
                world.remove(entity.getId());
                for (IColliderService coliderService : getColliderServices()) {
                    coliderService.stop(entity);
                }

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
    
    public void fireSpell(Map<String, Entity> world, Entity player) {
        Entity spell = new Entity();
        spell.setType(SPELL);
        spell.setName("Fireball");
        spell.setRadius(RADIUS);
        spell.setRadians(player.getRadians());
        spell.setTexturePath("com/se/sem4/group2/weapon/fireball.png");

        float offset = player.getRadius() + 2 + RADIUS;
        spell.setX((float) (player.getX() + Math.cos(player.getRadians()) * offset));
        spell.setY((float) (player.getY() + Math.sin(player.getRadians()) * offset));

        

        String path = (new File("").getAbsolutePath() + "/target/diablo2d/diablo2d/modules/com-se-sem4-group2-Weapon.jar!/assets/images/");
        switch (player.getActiveSpell()) {
            default:
                // default fires a fireball... (no break;)
            case FIREBALL:
                path += "fireball.png";
                spell.setMaxSpeed(350);
                spell.setMaxDamage(250);
                spell.setMinDamage(150);
                break;
            case ICEBOLT:
                path += "icebolt.png";
                spell.setMaxSpeed(450);
                spell.setMaxDamage(150);
                spell.setMinDamage(75);
                break;
            case CHARGEDBOLT:
                path += "chargedbolt.png";
                spell.setMaxSpeed((float) (player.getMaxSpeed() * Math.random() + player.getMaxSpeed()));
                spell.setMaxDamage(1100);
                spell.setMinDamage(70);
                break;
        }
        
        spell.setDx((float) (Math.cos(player.getRadians()) * spell.getMaxSpeed()));
        spell.setDy((float) (Math.sin(player.getRadians()) * spell.getMaxSpeed()));
        
        spell.setMaxHealth(1); // spells needs to die in first hit.
        spell.setTexturePath(path);
        spell.setLifeTime(2);
        spell.setLifeTimer(0);
        world.put(spell.getId(), spell);

//        for (IColliderService colliderService : getColliderServices()) {
//            Ellipse2D shape = new java.awt.geom.Ellipse2D.Float(0, 0, RADIUS * 2, RADIUS * 2);
//            Collider collider = new Collider(shape, spell);
//            colliderService.start(spell, collider);
//        }
    }

    private List<IColliderService> getColliderServices() {
        return SPILocator.locateAll(IColliderService.class);
    }
}
