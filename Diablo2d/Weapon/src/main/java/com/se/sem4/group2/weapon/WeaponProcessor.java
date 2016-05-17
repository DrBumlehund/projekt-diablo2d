/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.weapon;

import com.se.sem4.group2.common.data.Entity;
import com.se.sem4.group2.common.data.EntityType;
import static com.se.sem4.group2.common.data.EntityType.PLAYER;
import com.se.sem4.group2.common.data.GameKeys;
import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.data.SpellType;
import com.se.sem4.group2.common.data.util.SPILocator;
import com.se.sem4.group2.common.services.IColliderService;
import com.se.sem4.group2.common.services.IEntityProcessingService;
import java.util.Collection;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;
import static com.se.sem4.group2.common.data.EntityType.SPELL;
import com.se.sem4.group2.common.data.GameEvent;
import java.io.File;
import java.util.ArrayList;
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
                    fireSpell(world, player, metaData);
                }
            }
        }

        if (entity.getType() == EntityType.SPELL) {

            // Removes the bullet if certain conditions is met.
            if (entity.getLifeTime() < entity.getLifeTimer() || entity.isColided() || entity.isDead()) {
                // TODO: propperly dispose Entity in Weapon.
                world.remove(entity.getId());
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

    String soundPath = (new File("").getAbsolutePath() + "/target/diablo2d/diablo2d/modules/com-se-sem4-group2-Weapon.jar!/assets/sounds/");
    String[] fireballSounds = {
        soundPath + "fireball1.wav",
        soundPath + "fireball2.wav",
        soundPath + "fireball3.wav"
    };
    String[] iceboltSounds = {
        soundPath + "icebolt1.wav",
        soundPath + "icebolt2.wav",
        soundPath + "icebolt3.wav"
    };
    String[] chargedboltSounds = {
        soundPath + "chargedbolt1.wav",
        soundPath + "chargedbolt2.wav",
        soundPath + "chargedbolt3.wav"
    };

    public void fireSpell(Map<String, Entity> world, Entity player, MetaData metaData) {
        Entity spell = new Entity();
        spell.setType(SPELL);
        spell.setName("Fireball");
        spell.setRadius(RADIUS);
        spell.setRadians(player.getRadians());

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
                spell.setSoundPaths(fireballSounds);
                metaData.getGameEvents().add(GameEvent.FIREBALL_SHOT);
                break;
            case ICEBOLT:
                path += "icebolt.png";
                spell.setMaxSpeed(450);
                spell.setMaxDamage(150);
                spell.setMinDamage(75);
                spell.setSoundPaths(iceboltSounds);
                metaData.getGameEvents().add(GameEvent.ICEBOLT_SHOT);
                break;
            case CHARGEDBOLT:
                path += "chargedbolt.png";
                spell.setMaxSpeed((float) (player.getMaxSpeed() * Math.random() + player.getMaxSpeed()));
                spell.setMaxDamage(1100);
                spell.setMinDamage(70);
                spell.setSoundPaths(chargedboltSounds);
                metaData.getGameEvents().add(GameEvent.CHARGEDBOLT_SHOT);
                break;
        }

        spell.setDx((float) (Math.cos(player.getRadians()) * spell.getMaxSpeed()));
        spell.setDy((float) (Math.sin(player.getRadians()) * spell.getMaxSpeed()));

        spell.setMaxHealth(1); // spells needs to die in first hit.
        spell.setTexturePath(path);
        spell.setLifeTime(2);
        spell.setLifeTimer(0);
        world.put(spell.getId(), spell);
    }
}
