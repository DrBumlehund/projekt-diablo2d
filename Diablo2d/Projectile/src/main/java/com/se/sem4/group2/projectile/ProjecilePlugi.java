/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.projectile;

import com.se.sem4.group2.common.data.Entity;
import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.services.IGamePluginService;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Thomas
 */
@ServiceProvider(service = com.se.sem4.group2.common.services.IGamePluginService.class)
public class ProjecilePlugi implements IGamePluginService{

    @Override
    public void start(MetaData metaData, Map<String, Entity> world) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void stop(MetaData metaData) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
