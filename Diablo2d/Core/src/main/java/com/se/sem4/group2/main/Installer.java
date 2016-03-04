/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.main;

import org.openide.modules.ModuleInstall;

/**
 *
 * @author ThomasLemqvist
 */

public class Installer extends ModuleInstall {
    
    @Override
    public void restored(){
        
        Game game = new Game();
//        game.simpleInitApp();
        
    }
    
}
