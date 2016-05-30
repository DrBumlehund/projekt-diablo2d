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

import java.awt.Point;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author ThomasLemqvist
 */
public class MetaData {

    private WorldMap worldMap;
    private float delta;
    private int displayWidth;
    private int displayHeight;
    private final GameKeys keys = new GameKeys();
    private Point mousePos = new Point();
    private List<GameEvent> gameEvents = new CopyOnWriteArrayList<>();
    
    /**
     *
     * @return
     */
    public Point getMousePos() {
        return mousePos;
    }

    /**
     *
     * @param x
     * @param y
     */
    public void setMousePos(int x, int y) {
        this.mousePos = new Point(x, y);
    }

    /**
     *
     * @return
     */
    public GameKeys getKeys() {
        return keys;
    }

    /**
     *
     * @param width
     */
    public void setDisplayWidth(int width) {
        this.displayWidth = width;
    }

    /**
     *
     * @return
     */
    public int getDisplayWidth() {
        return displayWidth;
    }

    /**
     *
     * @param height
     */
    public void setDisplayHeight(int height) {
        this.displayHeight = height;
    }

    /**
     *
     * @return
     */
    public int getDisplayHeight() {
        return displayHeight;
    }

    /**
     *
     * @param delta
     */
    public void setDelta(float delta) {
        this.delta = delta;
    }

    /**
     *
     * @return
     */
    public float getDelta() {
        return delta;
    }

    /**
     *
     * @return
     */
    public WorldMap getWorldMap() {
        return worldMap;
    }

    /**
     *
     * @param worldMap
     */
    public void setWorldMap(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    /**
     *
     * @return
     */
    public List<GameEvent> getGameEvents() {
        return gameEvents;
    }
}
