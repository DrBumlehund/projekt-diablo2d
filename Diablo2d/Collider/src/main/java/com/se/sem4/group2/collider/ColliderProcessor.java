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
        Map<Integer, Collider> colliders = ColliderManager.getInstance().GetColliders();
        for (Map.Entry<Integer, Collider> collider : colliders.entrySet()){
            Entity entity = null;
            if (collider.getValue().getTransform() instanceof Entity) {
                entity = (Entity) collider.getValue().getTransform();
            } else {
                continue;
            }
            if (Math.abs(entity.getDx()) < 0.001f && Math.abs(entity.getDy()) < 0.001f) 
                continue;
            for (Map.Entry<Integer, Collider> otherCollider : colliders.entrySet()) {
                if (!collider.getValue().getTransform().getId().equals(otherCollider.getValue().getTransform().getId())) {
                    if (collider.getValue().checkCollision(otherCollider.getValue())) {
                        
                        entity.setPos(entity.getX() - entity.getDx(), entity.getY() - entity.getDy());
                            
                        collider.getValue().OnCollision();
                        System.out.println("Collison between " + collider.getValue().getTransform() + " and " + otherCollider.getValue().getTransform());
                    }
                }
            }
        }
    }
    
}
