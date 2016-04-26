/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.collider;

import com.se.sem4.group2.common.data.Collider;
import com.se.sem4.group2.common.data.Transform;
import com.se.sem4.group2.common.services.IColliderService;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Simon
 */
@ServiceProvider(service = com.se.sem4.group2.common.services.IColliderService.class)
public class ColliderService implements IColliderService {

    @Override
    public void start(Transform transform, Collider collider) {
        ColliderManager.getInstance().AddCollider(collider);
    }

    @Override
    public void stop(Transform transform) {
        ColliderManager.getInstance().RemoveCollider(transform.getId());
    }
    
}
