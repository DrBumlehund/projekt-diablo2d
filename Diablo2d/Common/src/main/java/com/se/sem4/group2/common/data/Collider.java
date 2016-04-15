/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.common.data;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
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
        syncLocation(shape, transform);
        syncLocation(otherCollider.shape, otherCollider.getTransform());
        Area areaA = new Area(shape);
        areaA.intersect(new Area(otherCollider.getShape()));
        return !areaA.isEmpty();
    }

    public void OnCollision() {
        // TODO: Call whatever callback we might want here
    }

    private void syncLocation(Shape shape, Transform transform) {
        if (shape instanceof Rectangle) {
            Rectangle rect = (Rectangle) shape;
            rect.setLocation(Math.round(transform.x), Math.round(transform.y));
        } 
        else if (shape instanceof Ellipse2D.Float) {
            Ellipse2D.Float ellipse = (Ellipse2D.Float) shape;
            ellipse.setFrame(transform.x, transform.y, ellipse.width, ellipse.height);
        } else {
            System.out.println("Tried to process collider with an invalid shape: " + transform.getName() + " : " + transform.getId());
        }
    }
}
