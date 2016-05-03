/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.common.services.IAssetServices;

import com.se.sem4.group2.common.data.Entity;
import com.se.sem4.group2.common.data.EntityType;
import com.se.sem4.group2.common.data.MetaData;


/**
 *
 * @author casperbeese
 */
public interface IAssetTextureService extends IAssetService{
    
    public void create();
    
    public void render(String path, Entity entity, MetaData metaData);
    
    public void draw(String path); 
}
