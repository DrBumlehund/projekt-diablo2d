/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.collider;
import com.se.sem4.group2.common.data.Collider;
import com.se.sem4.group2.common.data.Entity;
import com.se.sem4.group2.common.services.IColliderProcessingService;
import java.util.List;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;
/**
 *
 * @author Simon
 */
@ServiceProvider(service = com.se.sem4.group2.common.services.IColliderProcessingService.class)
public class ColliderProcessor implements IColliderProcessingService {
    
    @Override
    public void process() {
        List<Collider> colliders = ColliderManager.getInstance().GetColliders();
        for (Collider collider : colliders) {
            Entity entity = null;
            if (collider.getTransform() instanceof Entity) {
                entity = (Entity) collider.getTransform();
            } else {
                continue;
            }
            if (Math.abs(entity.getDx()) < 0.001f && Math.abs(entity.getDy()) < 0.001f) 
                continue;
            for (Collider otherCollider : colliders) {
                if (!collider.getTransform().getId().equals(otherCollider.getTransform().getId())) {
                    if (collider.checkCollision(otherCollider)) {
                        
                        entity.setPos(entity.getX() - entity.getDx(), entity.getY() - entity.getDy());
                            
                        collider.OnCollision();
                        //System.out.println("Collison between " + collider.getTransform() + " and " + otherCollider.getTransform());
                    }
                }
            }
        }
    }
    
}
