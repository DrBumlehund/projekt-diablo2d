/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.common.data;

import java.util.Random;
import java.util.UUID;

/**
 * Transform was intended to be a leight weight version of the Entity
 * only containing information about positions, but it exploded .. :-/
 * 
 * @author Simon
 */
//TODO: remove shit above...
public class Transform {

    protected float x;
    protected float y;
    protected final UUID id = UUID.randomUUID();
    protected String name;
    protected boolean colided;
    // XXX: skal dette flyttes til Entity?, hvis det skal skal vi gøre op 
    //      om transform er nødvendig...
    protected boolean dead;
    protected int maxHealth;
    protected int health;
    protected int minDamage;
    protected int maxDamage;
    // XXX: skal evt. flyttes tilbage til Entity...
    //      har flyttet ned for at kunne tilgå den i colideren, så npc'er
    //      ikke dræber hinanden. /thomas
    protected EntityType type;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getId() {
        return this.id.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Transform with id: " + id + " named: " + name;
    }

    public boolean isColided() {
        return colided;
    }

    public void setColided(boolean colided) {
        this.colided = colided;
    }

    public int getMinDamage() {
        return minDamage;
    }

    public void setMinDamage(int minDamage) {
        this.minDamage = minDamage;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public void setMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    public int getHealth() {
        return health;
    }

    public boolean isDead() {
        return dead;
    }

    /**
     * subtracts some damage from this transforms hp. sets the to true if health
     * turns negative.
     *
     * @param damage the amount of damage the transform takes...
     */
    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            dead = true;
        }
    }

    /**
     * called to get this transforms damage to other transforms.
     *
     * @return Random int between minDamage and maxDamage.
     */
    public int doDamage() {
        Random rng = new Random();
        if (maxDamage > 0) {
            return rng.nextInt(maxDamage) + minDamage;
        }
        return 0; // if this transform doesn't do colider damage.
    }

    /**
     * called to get the current health percentage from this transform.
     *
     * @return int health percentage.
     */
    public int getHealthPercentage() {
        if (health > 0) {
            return (health / maxHealth) * 100;
        }
        return 0;
    }

    public EntityType getType() {
        return type;
    }

    public void setType(EntityType type) {
        this.type = type;
    }
}
