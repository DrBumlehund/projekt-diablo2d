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
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author ThomasLemqvist
 */
public class Entity extends Transform {


    private float dx;
    private float dy;
    private float radians;
    private float maxSpeed;
    private float acceleration;
    private float deacceleration;
    private float radius;
    private Color color;
    private String texturePath;
    private float lifeTime;
    private float lifeTimer;
    private SpellType activeSpell;
    private List<String> soundPaths = new CopyOnWriteArrayList<>();

    private List<Point> path = new CopyOnWriteArrayList<>();

    /**
     *
     * @return
     */
    public String getTexturePath() {
        return texturePath;
    }

    /**
     *
     * @param texturePath
     */
    public void setTexturePath(String texturePath) {
        this.texturePath = texturePath;
    }

    /**
     *
     * @return
     */
    public float getLifeTimer() {
        return lifeTimer;
    }

    /**
     *
     * @return
     */
    public List<Point> getPath() {
        return path;
    }

    /**
     *
     * @param path
     */
    public void setPath(List<Point> path) {
        this.path = path;
    }

    /**
     *
     * @return
     */
    public float getLifeTime() {
        return lifeTime;
    }

    /**
     *
     * @param lifeTime
     */
    public void setLifeTime(float lifeTime) {
        this.lifeTime = lifeTime;
    }

    /**
     *
     * @param lifeTimer
     */
    public void setLifeTimer(float lifeTimer) {
        this.lifeTimer = lifeTimer;
    }

    /**
     *
     * @return
     */
    public Color getColor() {
        return color;
    }

    /**
     *
     * @param color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     *
     * @return
     */
    public float getAcceleration() {
        return acceleration;
    }

    /**
     *
     * @param acceleration
     */
    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    /**
     *
     * @return
     */
    public float getDeacceleration() {
        return deacceleration;
    }

    /**
     *
     * @param deacceleration
     */
    public void setDeacceleration(float deacceleration) {
        this.deacceleration = deacceleration;
    }

    /**
     *
     * @return
     */
    public float getRadius() {
        return radius;
    }

    /**
     *
     * @param radius
     */
    public void setRadius(float radius) {
        this.radius = radius;
    }

    /**
     *
     * @return
     */
    public float getDx() {
        return dx;
    }

    /**
     *
     * @param dx
     */
    public void setDx(float dx) {
        this.dx = dx;
    }

    /**
     *
     * @return
     */
    public float getDy() {
        return dy;
    }

    /**
     *
     * @param dy
     */
    public void setDy(float dy) {
        this.dy = dy;
    }

    /**
     *
     * @return
     */
    public float getRadians() {
        return radians;
    }

    /**
     *
     * @param radians
     */
    public void setRadians(float radians) {
        this.radians = radians % (float) (2 * Math.PI);
        this.radians = radians;
    }

    /**
     *
     * @return
     */
    public float getMaxSpeed() {
        return maxSpeed;
    }

    /**
     *
     * @param speed
     */
    public void setMaxSpeed(float speed) {
        this.maxSpeed = speed;
    }

    /**
     *
     * @param x
     * @param y
     */
    public void setPos(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @param dx
     * @param dy
     */
    public void setDeltaPos(float dx, float dy) {
        //ved ikke om det er nødvendigt ... men wth ... :D
        this.dx = dx;
        this.dy = dy;
    }

    /**
     *
     * @return
     */
    public SpellType getActiveSpell() {
        return activeSpell;
    }

    /**
     *
     * @param activeSpell
     */
    public void setActiveSpell(SpellType activeSpell) {
        this.activeSpell = activeSpell;
    }

    /**
     *
     * @return
     */
    public List<String> getSoundPaths() {
        return soundPaths;
    }

    /**
     *
     * @param soundPaths
     */
    public void setSoundPaths(String[] soundPaths) {
        this.soundPaths = new ArrayList<>();
        this.soundPaths.addAll(Arrays.asList(soundPaths));
    }
}
