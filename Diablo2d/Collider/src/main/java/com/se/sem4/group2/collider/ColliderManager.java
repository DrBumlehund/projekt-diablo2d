/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.collider;

import com.se.sem4.group2.common.data.Collider;
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
    private Map<Integer, Collider> colliders = new ConcurrentHashMap<>();
    
    public static ColliderManager getInstance() {
        if (colliderManager == null) {
            colliderManager = new ColliderManager();
        }
        return colliderManager;
    }
    
    public void AddCollider (Collider collider) {
        colliders.put(colliders.size(), collider);
    }
    
    public void RemoveCollider (Integer key) {
        colliders.remove(key);
    }
    
    public Map<Integer, Collider> GetColliders () {
        return colliders;
    }
}
