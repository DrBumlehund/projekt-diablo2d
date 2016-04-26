/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.common.services;

import com.se.sem4.group2.common.data.Collider;
import com.se.sem4.group2.common.data.Transform;

/**
 *
 * @author Simon
 */
public interface IColliderService {
        
    public void start(Transform transform, Collider collider);
    
    public void stop(Transform transform);
    
       
}
