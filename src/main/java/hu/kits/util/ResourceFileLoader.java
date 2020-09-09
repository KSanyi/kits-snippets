package hu.kits.util;

import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ResourceFileLoader {

    public static Path loadPath(String fileName) {
        try {
            URI uri = ClassLoader.getSystemResource(fileName).toURI();
            
            boolean isInJar = uri.toString().contains("!");
            if(isInJar) {
                String[] array = uri.toString().split("!");
                Map<String, String> env = new HashMap<>();
                URI uriToUse = URI.create(array[0]);
                
                FileSystem fs;
                try {
                    fs = FileSystems.getFileSystem(uriToUse);   
                } catch (FileSystemNotFoundException ex) {
                    fs = FileSystems.newFileSystem(uriToUse, env);
                }
                return fs.getPath(array[1]);
            } else {
                return Paths.get(uri);
            }
        } catch(Exception ex) {
            throw new RuntimeException("Can not load resource " + fileName, ex);
        }
    }
    
}
