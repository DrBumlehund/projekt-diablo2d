/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.core;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;



public class Game extends SimpleApplication{
    
    private RenderManager rm;

    public Game() {
        
    }
    
    @Override
    public void simpleInitApp() {
        Box b = new Box(1, 2, 1);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

        rootNode.attachChild(geom);
    }
    
    @Override
    public void simpleUpdate(float tpf){
        
    }
    
    @Override
    public void simpleRender(RenderManager rm){
        
    }
    
}
