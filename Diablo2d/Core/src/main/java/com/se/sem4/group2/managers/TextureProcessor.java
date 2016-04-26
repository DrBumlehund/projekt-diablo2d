

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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.openide.util.lookup.ServiceProvider;
import com.se.sem4.group2.common.services.IAssetServices.IAssetTextureService;

/**
 *
 * @author casperbeese
 */
@ServiceProvider(service = com.se.sem4.group2.common.services.IAssetServices.IAssetTextureService.class)
public class TextureProcessor extends AssetProcessor implements IAssetTextureService{
    
    private SpriteBatch batch;
    private Sprite sprite;

    @Override
    public void create(){
        batch = new SpriteBatch();
    }

    @Override
    public void render(String path, float x, float y, float radians){
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.
        batch.begin();
        //System.out.println(path);
        Texture tex = super.textures.get(path);
        sprite = new Sprite(tex);
        sprite.setCenter(x, y);
        sprite.setSize(32, 32);
        sprite.rotate((float)(radians*(180/Math.PI)));
        sprite.setPosition(x, y);

        sprite.draw(batch);
        
        //batch.draw(tex, x, y);
        // Drawing goes here!
        batch.end();
    }

    @Override
    public void draw(String path) {
    }

    
}
