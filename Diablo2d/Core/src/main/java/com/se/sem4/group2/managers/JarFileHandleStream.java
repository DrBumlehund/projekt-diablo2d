/*
 * Copyright (C) 2016 thomaslemqvist
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

import com.badlogic.gdx.files.FileHandleStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thomaslemqvist
 */
public class JarFileHandleStream extends FileHandleStream {

    private JarFile jarFile = null;
    private String jarRelResDir;

    public JarFileHandleStream(String path) {
        super(path);
        try {
            String[] args = path.split("!");
            jarRelResDir = args[1].substring(1);

            String jarFilePath = args[0];
            jarFile = new JarFile(jarFilePath);
            System.out.println("JAR path= " + jarFile + " Asset path= " + jarRelResDir);
        } catch (IOException ex) {
            Logger.getLogger(JarFileHandleStream.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public InputStream read() {

        InputStream is = null;
        try {
            is = jarFile.getInputStream(jarFile.getEntry(jarRelResDir));
        } catch (IOException ex) {
            Logger.getLogger(JarFileHandleStream.class.getName()).log(Level.SEVERE, null, ex);
        }

        return is;
    }

    @Override
    public OutputStream write(boolean overwrite) {
        return super.write(overwrite);
    }

}
