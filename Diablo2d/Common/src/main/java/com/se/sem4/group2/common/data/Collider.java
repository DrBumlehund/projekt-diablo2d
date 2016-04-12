/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.common.data;

import java.awt.Point;
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
    
    public boolean checkCollision (Collider otherCollider) {
        //if (transform.getId() == otherCollider.getTransform().getId())
        //    return false; // Let's not check collision against ourselfes
        shape.getBounds().setLocation(Math.round(transform.x), Math.round(transform.y));
        otherCollider.getShape().getBounds().setLocation(Math.round(otherCollider.getTransform().x), Math.round(otherCollider.getTransform().y));
        Area areaA = new Area(shape);
        areaA.intersect(new Area(otherCollider.getShape()));
        return !areaA.isEmpty();
    }

    public void OnCollision() {
        // TODO: Call whatever callback we might want here
    }
}
