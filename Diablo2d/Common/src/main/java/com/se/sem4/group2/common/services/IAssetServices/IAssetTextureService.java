/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.common.services.IAssetServices;

/**
 *
 * @author casperbeese
 */
public interface IAssetTextureService extends IAssetService{
    
    public void create();
    
    public void render(String path, float x, float y, float radians);
    
    public void draw(String path); 
}
