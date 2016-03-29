/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.common.data;

/**
 *
 * @author lhrbo
 */
public class Character extends Entity{
    
    private Weapon weapon;
    private boolean hostile;
    private byte[] sprite;

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public boolean isHostile() {
        return hostile;
    }

    public void setHostile(boolean hostile) {
        this.hostile = hostile;
    }

    public byte[] getSprite() {
        return sprite;
    }

    public void setSprite(byte[] sprite) {
        this.sprite = sprite;
    }
    
}
