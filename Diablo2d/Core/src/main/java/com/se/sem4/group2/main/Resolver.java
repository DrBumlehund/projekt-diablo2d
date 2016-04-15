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
    String pathToJars = (file.getAbsolutePath() + "/target/diablo2d/diablo2d/modules");
    File modulesFolder = new File(pathToJars);
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
                        
                        File file1 = writeTempFile(jar.getInputStream(zipEntry));
                        file1.deleteOnExit();
                        return file1;
                    }
                }
                jar.close();
            }
        }
        return null;
    }

    private File writeTempFile(InputStream inputStream) {
        
        try {
            file = File.createTempFile("Diablo2dMusic", ".tmp");
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
//        try {
//            // getResource("", new File())
//            //System.out.println("Resolve: " + arg0);
//
//            URL url = new URL("jar:file:" + pathToJars);
//
//            //URL url = getResource("/com/se/sem4/group2/core", new File(file.getAbsolutePath() + "/target/diablo2d/diablo2d/modules/com-se-sem4-group2-Map.jar"));
//            if (url == null) {
//                // error - missing folder
//            } else {
//                try {
//                    File dir = Utilities.toFile(url.toURI());
//                    for (File nextFile : dir.listFiles()) {
//                        // Do something with nextFile
//                        FileHandle music = new FileHandle(nextFile);
//                    }
//                } catch (URISyntaxException ex) {
//                    Exceptions.printStackTrace(ex);
//                }
//            }
//            return null;
//        } catch (MalformedURLException ex) {
//            Exceptions.printStackTrace(ex);
//        }
//        return null;
