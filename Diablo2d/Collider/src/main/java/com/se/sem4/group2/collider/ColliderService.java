/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.collider;

import com.se.sem4.group2.common.data.Collider;
import com.se.sem4.group2.common.data.Entity;
import com.se.sem4.group2.common.data.EntityType;
import com.se.sem4.group2.common.data.Transform;
import com.se.sem4.group2.common.services.IColliderService;
import java.util.List;

/**
 *
 * @author Simon
 */
public class ColliderService implements IColliderService {

    private Collider collider;

    @Override
    public void start(Transform transform, Collider collider) {
        this.collider = collider;
    }

    @Override
    public void stop() {
        // TODO: Do we need to unload anything
    }

    @Override
    public boolean checkCollision(Collider theirCollider) {
        // Don't collide with our own transform
        if (collider.getTransform().getId().equals(theirCollider.getTransform().getId()))
            return false;
        return collider.checkCollision(theirCollider);
    }

    @Override
    public Collider getCollider() {
        return collider;
    }
    
}
