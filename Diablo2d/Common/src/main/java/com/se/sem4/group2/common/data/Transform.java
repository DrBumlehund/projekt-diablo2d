/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.common.data;

import java.util.UUID;

/**
 *
 * @author Simon
 */
public class Transform {
    protected float x;
    protected float y;
    protected final UUID id = UUID.randomUUID();

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
    
    
}
