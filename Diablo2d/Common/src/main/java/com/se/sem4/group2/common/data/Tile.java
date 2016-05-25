/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.common.data;

/**
 *
 * @author Simon
 */
public class Tile {
    private String source = "billede.jpg";
    private float speedModifier = 1f;
    private byte[] imageBytes = null;

    /**
     *
     * @param source
     * @param speedModifier
     */
    public Tile(String source, float speedModifier) {
        this.source = source;
        this.speedModifier = speedModifier;
    }
    
    /**
     *
     * @param imageBytes
     */
    public void injectSource (byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    /**
     *
     * @return
     */
    public byte[] getImage() {
        return imageBytes;
    }

    /**
     *
     * @return
     */
    public String getSource() {
        return source;
    }

    /**
     *
     * @return
     */
    public float getSpeedModifier() {
        return speedModifier;
    }
    
}
