/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se.sem4.group2.player;

import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        System.out.println("Player module installed");
    }

    @Override
    public void uninstalled() {
        System.out.println("Player module uninstalled");
    }

}
