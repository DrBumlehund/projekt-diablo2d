/* 
 * Copyright (C) 2016 Casper Beese Nielsen, Jakob Tøttrup, Jesper Bager Rasmussen, Oliver Vestergaard, Simon Hjortshøj Larsen & Thomas August Lemqvist
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.se.sem4.group2.common.data;

import java.awt.Color;

/**
 *
 * @author ThomasLemqvist
 */
public class Entity extends Transform {

    //TODO: Tilføj sprite/billede/form...
    private float dx;
    private float dy;
    private float radians;
    private float maxSpeed;
    private float acceleration;
    private float deacceleration;
    // TODO: Don't use shapex and shapey
    private float[] shapeX;
    private float[] shapeY;
    private float radius;
    private EntityType type;
    private Color color;
    private float lifeTime;
    private float lifeTimer;

    public float getLifeTimer() {
        return lifeTimer;
    }

    public void setLifeTimer(float lifeTimer) {
        this.lifeTimer = lifeTimer;
    }
    

    public float getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(float lifeTime) {
        this.lifeTime = lifeTime;
    }
    

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

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
}
