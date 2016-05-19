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

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.openide.modules.ModuleInstall;

/**
 *
 * @author ThomasLemqvist
 */
public class Installer extends ModuleInstall {

    private final int N_GUI_THREADS = 8;

    ExecutorService guiPool = Executors.newFixedThreadPool(N_GUI_THREADS);

    @Override
    public void restored() {
        guiPool.execute(() -> {
            LwjglApplicationConfiguration cfg
                    = new LwjglApplicationConfiguration();
            cfg.title = "Diablo ii-D";
            cfg.width = 800;
            cfg.height = 600;
            cfg.useGL30 = false;
            cfg.resizable = false;
            cfg.foregroundFPS = 0;
            cfg.vSyncEnabled = false;

            new LwjglApplication(new Game(), cfg);
        });
    }
}
