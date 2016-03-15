/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.common.data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Simon
 */
public class Tile {
    private String source = "billede.jpg";
    private float speedModifier = 1f;
    private byte[] imageBytes = null;

    public Tile(String source, float speedModifier) {
        this.source = source;
        this.speedModifier = speedModifier;
    }
    
    public void injectSource (byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public byte[] getImage() {
        return imageBytes;
    }

    public String getSource() {
        return source;
    }
}
