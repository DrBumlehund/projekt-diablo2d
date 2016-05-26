/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.common.data;

import java.util.Random;
import java.util.UUID;

/**
 * Transform was intended to be a leight weight version of the Entity only
 * containing information about positions, but it exploded .. :-/
 *
 * @author Simon
 */
//TODO: remove shit above...
public class Transform {

    /**
     *
     */
    protected float x;

    /**
     *
     */
    protected float y;

    /**
     *
     */
    protected final UUID id = UUID.randomUUID();

    /**
     *
     */
    protected String name;

    /**
     *
     */
    protected boolean colided;
    // XXX: skal dette flyttes til Entity?, hvis det skal skal vi gøre op 
    //      om transform er nødvendig...

    /**
     *
     */
    protected boolean dead;

    /**
     *
     */
    protected int maxHealth;

    /**
     *
     */
    protected int health;

    /**
     *
     */
    protected int minDamage;

    /**
     *
     */
    protected int maxDamage;
    // XXX: skal evt. flyttes tilbage til Entity...
    //      har flyttet ned for at kunne tilgå den i colideren, så npc'er
    //      ikke dræber hinanden. /thomas

    /**
     *
     */
    protected EntityType type;

    /**
     *
     */
    protected boolean initialized;

    /**
     *
     * @return
     */
    public float getX() {
        return x;
    }

    /**
     *
     * @param x
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     *
     * @return
     */
    public float getY() {
        return y;
    }

    /**
     *
     * @param y
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     *
     * @return
     */
    public String getId() {
        return this.id.toString();
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Transform with id: " + id + " named: " + name;
    }

    /**
     *
     * @return
     */
    public boolean isColided() {
        return colided;
    }

    /**
     *
     * @param colided
     */
    public void setColided(boolean colided) {
        this.colided = colided;
    }

    /**
     *
     * @return
     */
    public int getMinDamage() {
        return minDamage;
    }

    /**
     *
     * @param minDamage
     */
    public void setMinDamage(int minDamage) {
        this.minDamage = minDamage;
    }

    /**
     *
     * @return
     */
    public int getMaxDamage() {
        return maxDamage;
    }

    /**
     *
     * @param maxDamage
     */
    public void setMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
    }

    /**
     *
     * @return
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     *
     * @param maxHealth
     */
    public void setMaxHealth(int maxHealth) {
        if (!initialized) {             // makes sure that maxhealth and 
            this.maxHealth = maxHealth; // health is only set once.
            this.health = maxHealth;
            initialized = true;
        }
    }

    /**
     *
     * @return
     */
    public int getHealth() {
        return health;
    }

    /**
     *
     * @return
     */
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
     * @param width of the health bar.
     * @return int health percentage.
     */
    public float getHealthPercentage(float width) {
        if (health > 0) {                       // checks if health is above 0 
            return health * width / maxHealth;  // to make sure that negative 
        }                                       // "health %" is not returned
        return 0f;
    }

    /**
     *
     * @return
     */
    public EntityType getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(EntityType type) {
        this.type = type;
    }
}
