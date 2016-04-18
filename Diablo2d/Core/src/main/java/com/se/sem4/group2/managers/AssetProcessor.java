/*
 * Copyright (C) 2016 casperbeese
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.se.sem4.group2.managers;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import com.se.sem4.group2.main.Resolver;
import java.io.File;
import java.util.HashMap;
import com.se.sem4.group2.common.services.IAssetServices.IAssetService;
import java.util.ArrayList;
import org.openide.util.Exceptions;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author casperbeese
 */

public abstract class AssetProcessor implements IAssetService {

    private Resolver resolver = new Resolver();

    private AssetManager aM = new AssetManager(resolver);

    public HashMap<String, Music> music;
    public HashMap<String, Sound> sounds;
    public HashMap<String, Texture> textures;

    public AssetProcessor() {
        music = new HashMap<>();
        sounds = new HashMap<>();
        textures = new HashMap<>();
    }

    @Override
    public void load(String path, String loaderType) {
        switch (loaderType) {
            case "Music":
                aM.setLoader(Music.class, new MusicLoader(resolver));
                aM.load(path, Music.class);
                aM.update();
                aM.finishLoading();
                
                Music mus = aM.get(path);
               
                if(aM.isLoaded(path)){
                music.put(path, mus);
                }
                break;
            case "Sound":
                aM.setLoader(Sound.class, new SoundLoader(resolver));
                aM.load(path, Sound.class);
                aM.update();
                aM.finishLoading();
                
                if(aM.isLoaded(path)){
                sounds.put(path, Gdx.audio.newSound(Gdx.files.getFileHandle(path, Files.FileType.Local)));
                }
                break;
            case "Texture":
                TextureLoader texL = new TextureLoader(resolver);
                aM.setLoader(Texture.class, texL);
                aM.load(path, Texture.class);
                aM.update();
                aM.finishLoading();
                
                if(aM.isLoaded(path)){
                    Texture tex = aM.get(path);
                //FileHandle awd = Gdx.files.getFileHandle(path, Files.FileType.Local);
                //Texture tex = new Texture(awd);
                textures.put(path, tex);
                }
                break;
            default:
                System.out.println("The choosen classtype is not availible");
                break;
        }   
        
//        textures.get("dkdks");
    }

}
