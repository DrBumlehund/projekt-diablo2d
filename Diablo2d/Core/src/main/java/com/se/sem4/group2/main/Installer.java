/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.openide.modules.ModuleInstall;

/**
 *
 * @author ThomasLemqvist
 */

public class Installer extends ModuleInstall {
    
    @Override
    public void restored(){
        //TODO: tråd
//        Game game = new Game();
    LwjglApplicationConfiguration cfg =
			new LwjglApplicationConfiguration();
		cfg.title = "Diablo ii-D";
		cfg.width = 800;
		cfg.height = 640;
		cfg.useGL30 = false;
		cfg.resizable = false;
		
		new LwjglApplication(new Game(), cfg);
            
    }
    
}