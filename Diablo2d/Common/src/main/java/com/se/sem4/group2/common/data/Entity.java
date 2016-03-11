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
    private float x;
    private float y;
    private float dx;
    private float dy;
    private float radians;
    private float maxSpeed;
    private float acceleration;
    private float deacceleration;
    private float[] shapeX;
    private float[] shapeY;
    private float radius;
    private EntityType type;
    private final UUID id = UUID.randomUUID();

    public float getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public float getDeacceleration() {
        return deacceleration;
    }

    public void setDeacceleration(float deacceleration) {
        this.deacceleration = deacceleration;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getX() {
        return x;
    }

    public float[] getShapeX() {
        return shapeX;
    }

    public void setShapeX(float[] shapeX) {
        this.shapeX = shapeX;
    }

    public float[] getShapeY() {
        return shapeY;
    }

    public void setShapeY(float[] shapeY) {
        this.shapeY = shapeY;
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
        this.radians = radians % (float) (2 * Math.PI);
        this.radians = radians;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(float speed) {
        this.maxSpeed = speed;
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

    public String getId() {
        return this.id.toString();
    }
    //TODO: MAKE SHIT FOR SHAPEX,Y

}
