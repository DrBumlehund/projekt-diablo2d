/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.common.data;

import java.util.UUID;

/**
 *
 * @author ThomasLemqvist
 */
public class Entity {

    //TODO: Tilføj sprite/billede/form...
    private float x, y, dx, dy, radians, speed;
    private EntityType type;
    private final UUID id = UUID.randomUUID();

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

    public float getDx() {
        return dx;
    }

    public void setDx(float dx) {
        this.dx = dx;
    }

    public float getDy() {
        return dy;
    }

    public void setDy(float dy) {
        this.dy = dy;
    }

    public float getRadians() {
        return radians;
    }

    public void setRadians(float radians) {
        this.radians = radians;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public EntityType getType() {
        return type;
    }

    public void setType(EntityType type) {
        this.type = type;
    }

    public void setPos(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public void setDeltaPos(float dx, float dy) {
        //ved ikke om det er nødvendigt ... men wth ... :D
        this.dx = dx;
        this.dy = dy;
    }

}
