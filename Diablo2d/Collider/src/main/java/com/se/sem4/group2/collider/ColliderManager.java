/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.collider;

import com.se.sem4.group2.common.data.Collider;
import com.se.sem4.group2.common.data.Transform;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Simon
 */
public class ColliderManager {

    private static ColliderManager colliderManager;
    private Map<String, Collider> colliders = new ConcurrentHashMap<>();

    /**
     * Singletonpatern for the ColiderManager
     *
     * @return the ColiderManager instance
     */
    public static ColliderManager getInstance() {
        if (colliderManager == null) {
            colliderManager = new ColliderManager();
        }
        return colliderManager;
    }

    public void AddCollider(Collider collider) {
        colliders.put(collider.getTransform().getId(), collider);
    }

    public void RemoveCollider(String key) {
        if (!colliders.containsKey(key)) {
            return;
        }
        colliders.remove(key);
    }

    public Map<String, Collider> GetColliders() {
        return colliders;
    }
}
