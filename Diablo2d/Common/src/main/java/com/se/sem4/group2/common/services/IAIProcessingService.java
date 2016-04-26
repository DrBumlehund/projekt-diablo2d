/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.common.services;

import com.se.sem4.group2.common.data.Entity;
import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.data.WorldMap;
import java.util.Map;

/**
 *
 * @author lhrbo
 */
public interface IAIProcessingService {
    
    void process (MetaData metaData, Map<String, Entity> world, WorldMap worldMap);
    
}
