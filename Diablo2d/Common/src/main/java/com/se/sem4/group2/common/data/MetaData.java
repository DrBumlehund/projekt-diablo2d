/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.common.data;

/**
 *
 * @author ThomasLemqvist
 */
public class MetaData {

    private float delta;
    private int displayWidth;
    private int displayHeight;
    private final GameKeys keys = new GameKeys();

    public GameKeys getKeys() {
        return keys;
    }

    public void setDisplayWidth(int width) {
        this.displayWidth = width;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayHeight(int height) {
        this.displayHeight = height;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public void setDelta(float delta) {
        this.delta = delta;
    }

   


}
