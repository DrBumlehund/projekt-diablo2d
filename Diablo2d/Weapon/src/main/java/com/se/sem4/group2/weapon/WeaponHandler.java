/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.weapon;

import com.se.sem4.group2.common.data.Entity;
import static com.se.sem4.group2.common.data.EntityType.SPELL;
import com.se.sem4.group2.common.data.GameEvent;
import com.se.sem4.group2.common.data.MetaData;
import java.io.File;
import java.util.Map;

/**
 *
 * @author thomaslemqvist
 */
public class WeaponHandler {

    private static final int SPELL_RADIUS = 10;

    String path;

    String[] fireballSounds;
    String[] iceboltSounds;
    String[] chargedboltSounds;

    private WeaponHandler() {
        if (System.getProperty("os.name").startsWith("Windows")) {
            path = (new File("").getAbsolutePath() + "/diablo2d/modules/com-se-sem4-group2-Weapon.jar!/assets/");
            path = path.substring(2);
            path = path.replaceAll("\\\\", "/");
        } else {
            path = (new File("").getAbsolutePath() + "/target/diablo2d/diablo2d/modules/com-se-sem4-group2-Weapon.jar!/assets/");
        }
        fireballSounds = new String[]{
            path + "sounds/fireball1.wav",
            path + "sounds/fireball2.wav",
            path + "sounds/fireball3.wav"};

        iceboltSounds = new String[]{
            path + "sounds/icebolt1.wav",
            path + "sounds/icebolt2.wav",
            path + "sounds/icebolt3.wav"};

        chargedboltSounds = new String[]{
            path + "sounds/chargedbolt1.wav",
            path + "sounds/chargedbolt2.wav",
            path + "sounds/chargedbolt3.wav"};
    }

    public static WeaponHandler getInstance() {
        return WeaponHandlerHolder.INSTANCE;
    }

    private static class WeaponHandlerHolder {

        private static final WeaponHandler INSTANCE = new WeaponHandler();
    }

    public void fireSpell(Map<String, Entity> world, Entity shooter, MetaData metaData) {
        Entity spell = new Entity();
        spell.setType(SPELL);
        spell.setName("Fireball");
        spell.setRadius(SPELL_RADIUS);
        spell.setRadians(shooter.getRadians());

        float offset = shooter.getRadius() + 2 + SPELL_RADIUS;
        spell.setX((float) (shooter.getX() + Math.cos(shooter.getRadians()) * offset));
        spell.setY((float) (shooter.getY() + Math.sin(shooter.getRadians()) * offset));

        switch (shooter.getActiveSpell()) {
            default:
            // default fires a fireball... (no break;)
            case FIREBALL:
                spell.setTexturePath(path + "images/fireball.png");
                spell.setMaxSpeed(350);
                spell.setMaxDamage(250);
                spell.setMinDamage(150);
                spell.setSoundPaths(fireballSounds);
                metaData.getGameEvents().add(GameEvent.FIREBALL_SHOT);
                break;
            case ICEBOLT:
                spell.setTexturePath(path + "images/icebolt.png");
                spell.setMaxSpeed(450);
                spell.setMaxDamage(150);
                spell.setMinDamage(75);
                spell.setSoundPaths(iceboltSounds);
                metaData.getGameEvents().add(GameEvent.ICEBOLT_SHOT);
                break;
            case CHARGEDBOLT:
                spell.setTexturePath(path + "images/chargedbolt.png");
                spell.setMaxSpeed((float) (shooter.getMaxSpeed() * Math.random() + shooter.getMaxSpeed()));
                spell.setMaxDamage(1100);
                spell.setMinDamage(70);
                spell.setSoundPaths(chargedboltSounds);
                metaData.getGameEvents().add(GameEvent.CHARGEDBOLT_SHOT);
                break;
        }

        spell.setDx((float) (Math.cos(shooter.getRadians()) * spell.getMaxSpeed()));
        spell.setDy((float) (Math.sin(shooter.getRadians()) * spell.getMaxSpeed()));

        spell.setMaxHealth(1); // spells needs to die in first hit.
        spell.setLifeTime(2);
        spell.setLifeTimer(0);
        world.put(spell.getId(), spell);
    }
}
