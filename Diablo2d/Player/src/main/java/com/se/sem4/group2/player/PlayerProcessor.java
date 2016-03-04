/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.player;


import com.se.sem4.group2.common.data.Entity;
import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.services.IEntityProcessingService;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author ThomasLemqvist
 */
@ServiceProvider  (service = com.se.sem4.group2.common.services.IEntityProcessingService.class )
public class PlayerProcessor implements IEntityProcessingService {

    @Override
    public void Process(MetaData md, Map<String, Entity> w, Entity e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
