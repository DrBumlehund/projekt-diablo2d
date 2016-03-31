/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.common.data;

import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Simon
 */
public class Collider {
    private Shape shape;
    private Transform transform;

    public Shape getShape() {
        return shape; 
    }

    public Transform getTransform() {
        return transform;
    }
    
    public Collider(Shape shape, Transform transform) {
        this.shape = shape;
        this.transform = transform;
    }
    
    public boolean checkCollision (Collider collider) {
        if (collider == this)
            return false; // Let's not check collision against ourselfes
        Area areaA = new Area(shape);
        areaA.intersect(new Area(collider.getShape()));
        return !areaA.isEmpty();
    }
}
