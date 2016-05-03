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
package com.se.sem4.group2.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import org.openide.util.Exceptions;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import java.net.MalformedURLException;
import org.openide.util.Utilities;

/**
 *
 * @author casperbeese
 */
public class Resolver implements FileHandleResolver {

    File file = new File("");

    String pathToJars;
    File modulesFolder;

    public Resolver() {
        if (System.getProperty("os.name").startsWith("Windows")) {
            pathToJars = (file.getAbsolutePath() + "/diablo2d/modules");
        } else {
            pathToJars = (file.getAbsolutePath() + "/target/diablo2d/diablo2d/modules");
        }
        modulesFolder = new File(pathToJars);

    }

    public File getResources(File modulesFolder, String path) throws IOException, URISyntaxException {
        for (File jarFile : modulesFolder.listFiles()) {
            if (jarFile.getName().contains(".jar")) {  // Run with JAR file
                JarFile jar = new JarFile(jarFile);

                Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
                while (entries.hasMoreElements()) {

                    final String name = entries.nextElement().getName();

                    ZipEntry zipEntry = jar.getEntry(name);
                    if (name.equals(path)) { //filter according to the path
                        System.out.println(name);

                        String[] extension = name.split("/");
                        File file1 = writeTempFile(jar.getInputStream(zipEntry), extension);
                        file1.deleteOnExit();
                        return file1;
                    }
                }
                jar.close();
            }
        }
        return null;
    }

    private File writeTempFile(InputStream inputStream, String[] extension) {

        try {
            file = File.createTempFile("Diablo2dTemp", "." + extension[extension.length - 1]);
            System.out.println(file.getPath());
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            inputStream.close();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return file;
    }

    @Override
    public FileHandle resolve(String arg0) {
        try {
            FileHandle file = new FileHandle(getResources(modulesFolder, arg0));
            return file;
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        } catch (URISyntaxException ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }

    //TODO: remove
    @Override
    public String toString() {
        return "Custom Resolver";
    }

}
