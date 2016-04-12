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
import java.util.ArrayList;
import java.util.List;
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
    public void stop() {
        // TODO: Do we need to unload anything
    }
    
}
