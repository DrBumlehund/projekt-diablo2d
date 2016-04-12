/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.collider;

import com.se.sem4.group2.common.data.Collider;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Simon
 */
public class ColliderManager {

    private static ColliderManager colliderManager;
    private List<Collider> colliders = new ArrayList<>();
    
    public static ColliderManager getInstance() {
        if (colliderManager == null) {
            colliderManager = new ColliderManager();
        }
        return colliderManager;
    }
    
    public void AddCollider (Collider collider) {
        colliders.add(collider);
    }
    
    public void RemoveCollider (Collider collider) {
        colliders.remove(collider);
    }
    
    public List<Collider> GetColliders () {
        return colliders;
    }
}
