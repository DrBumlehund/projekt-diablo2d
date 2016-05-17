/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.collider;

import com.se.sem4.group2.common.data.Collider;
import com.se.sem4.group2.common.data.Entity;
import java.awt.geom.Ellipse2D;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Simon
 */
public class ColliderHandler {

    private static ColliderHandler colliderManager;
    private final Map<String, Collider> colliders = new ConcurrentHashMap<>();

    /**
     * Singletonpatern for the ColiderManager
     *
     * @return the ColiderManager instance
     */
    public static ColliderHandler getInstance() {
        if (colliderManager == null) {
            colliderManager = new ColliderHandler();
        }
        return colliderManager;
    }

    protected void AddCollider(Collider collider) {
        colliders.put(collider.getTransform().getId(), collider);
    }

    protected void removeCollider(String key) {
        if (!colliders.containsKey(key)) {
            return;
        }
        colliders.remove(key);
    }

    protected Map<String, Collider> GetColliders() {
        return colliders;
    }

    private boolean needsCollider(Entity entity) {
        for (Collider col : colliders.values()) {
            if (col.getTransform().getId().equals(entity.getId())) {
                return false;
            }
        }
        return true;
    }

    protected void removeExcessColliders(Map<String, Entity> world) {
        for (Collider col : colliders.values()) {
            if(!world.containsKey(col.getTransform().getId())){
                removeCollider(col.getTransform().getId());
            }
        }
    }

    protected void addCollidersToEntities(Map<String, Entity> world) {
        for (Entity e : world.values()) {
            if (needsCollider(e)) {
                Ellipse2D shape = new java.awt.geom.Ellipse2D.Float(0, 0, 2 * e.getRadius(), 2 * e.getRadius());
                Collider collider = new Collider(shape, e);
                AddCollider(collider);
            }
        }
    }

}
