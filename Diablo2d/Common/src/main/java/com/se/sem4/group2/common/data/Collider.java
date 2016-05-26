/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.common.data;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

/**
 *
 * @author Simon
 */
public class Collider {

    private final Shape shape;
    private final Transform transform;

    /**
     *
     * Returns the shape object from the given entity
     * 
     * @return Shape
     */
    public Shape getShape() {
        return shape;
    }

    /**
     *
     * Return the transform object from the given entity
     * 
     * @return Transform
     */
    public Transform getTransform() {
        return transform;
    }

    /**
     *
     * Collider Contructor
     * 
     * @param shape
     * @param transform
     */
    public Collider(Shape shape, Transform transform) {
        this.shape = shape;
        this.transform = transform;
    }

    /**
     *
     * 
     * 
     * @param otherCollider
     * @return Boolean which denotes whether the to entities are colliding or not  
     */
    public boolean checkCollision(Collider otherCollider) {
        //if (transform.getId() == otherCollider.getTransform().getId())
        //    return false; // Let's not check collision against ourselfes
        //XXX:  Gør at transforms af samme type ikke colider, 
        //      og dermed ikke sætter sig fast... /thomas
        if (otherCollider.getTransform().getType() == this.transform.getType()) {
            return false;
        }
        syncLocation(shape, transform);
        syncLocation(otherCollider.shape, otherCollider.getTransform());
        Area areaA = new Area(shape);
        areaA.intersect(new Area(otherCollider.getShape()));
        return !areaA.isEmpty();
    }

    /**
     *
     * @param otherColider
     */
    public void OnCollision(Collider otherColider) {
        transform.setColided(true);
        Transform ot = otherColider.getTransform();
        ot.takeDamage(transform.doDamage());
    }

    private void syncLocation(Shape shape, Transform transform) {
//        Omitted because it is impossible to create a rectangle shape with the current 
//        implementation 
//
//        if (shape instanceof Rectangle) {
//            Rectangle rect = (Rectangle) shape;
//            rect.setLocation(Math.round(transform.x) - (rect.width / 2), Math.round(transform.y) - (rect.height / 2));
//       } 
        if (shape instanceof Ellipse2D.Float) {
            Ellipse2D.Float ellipse = (Ellipse2D.Float) shape;
            ellipse.setFrame(transform.x - (ellipse.width / 2), transform.y - (ellipse.height / 2), ellipse.width, ellipse.height);
        } 
        else {
            System.out.println("Tried to process collider with an invalid shape: " + transform.getName() + " : " + transform.getId());
        }
    }
}
