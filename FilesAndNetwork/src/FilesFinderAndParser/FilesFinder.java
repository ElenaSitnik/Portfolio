package FilesFinderAndParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FilesFinder {

    public static void getTree(String path){
        try {
            Files.walkFileTree(Paths.get(path), new MyFileVisitor());
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
