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
public interface IAssetAudioService extends IAssetService{
    
    public void playMusic(String path);
    
    public void playSound(String path);
    
    public void pause(String path);
    
    public void stop(String path);
    
    public void setVolume(String path, Boolean isLooping);
    
}
