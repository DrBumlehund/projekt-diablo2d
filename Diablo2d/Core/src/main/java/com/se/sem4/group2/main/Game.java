/* 
 * Copyright (C) 2016 Casper Beese Nielsen, Jakob Tøttrup, Jesper Bager Rasmussen, Oliver Vestergaard, Simon Hjortshøj Larsen & Thomas August Lemqvist
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
package com.se.sem4.group2.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.se.sem4.group2.common.data.Entity;
import com.se.sem4.group2.common.data.MetaData;
import com.se.sem4.group2.common.data.util.SPILocator;
import com.se.sem4.group2.common.services.IEntityProcessingService;
import com.se.sem4.group2.common.services.IGamePluginService;
import com.se.sem4.group2.managers.GameInputProcessor;
import com.se.sem4.group2.player.PlayerPlugin;
import com.se.sem4.group2.player.PlayerProcessor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Game implements ApplicationListener {

    private static OrthographicCamera cam;

    private ShapeRenderer sr;

    private IGamePluginService playerPlugin;
    private IEntityProcessingService playerProcessor;

    private final MetaData metaData = new MetaData();
//    private final GameData md = new GameData();
    private List<IEntityProcessingService> entityProcessors = new ArrayList<>();
    private Map<String, Entity> world = new ConcurrentHashMap<>();

    @Override
    public void create() {

        metaData.setDisplayWidth(Gdx.graphics.getWidth());
        metaData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(metaData.getDisplayWidth(), metaData.getDisplayHeight());
        cam.translate(metaData.getDisplayWidth() / 2, metaData.getDisplayHeight() / 2);
        cam.update();

        sr = new ShapeRenderer();

        Gdx.input.setInputProcessor(
                new GameInputProcessor(metaData)
        );

        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : getPluginServices()) {
            iGamePlugin.start(metaData, world);
        }

        playerPlugin = new PlayerPlugin();
        playerPlugin.start(metaData, world);
        playerProcessor = new PlayerProcessor();
    }

    @Override
    public void render() {

        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        metaData.setDelta(Gdx.graphics.getDeltaTime());

        update();

        draw();

        metaData.getKeys().update();
    }

    private void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
            for (Entity e : world.values()) {
                entityProcessorService.process(metaData, world, e);
            }
        }
    }

    private void draw() {
        for (Entity entity : world.values()) {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            float[] shapeX = entity.getShapeX();
            float[] shapeY = entity.getShapeY();
            
            sr.begin(ShapeRenderer.ShapeType.Line);
            
            sr.line(shapeX[0], shapeY[0], shapeX[1], shapeY[1]);
            sr.circle(entity.getX(), entity.getY(), entity.getRadius());
            
            sr.end();

        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    private Collection<? extends IGamePluginService> getPluginServices() {
        return SPILocator.locateAll(IGamePluginService.class);
    }

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return SPILocator.locateAll(IEntityProcessingService.class);
    }
}
