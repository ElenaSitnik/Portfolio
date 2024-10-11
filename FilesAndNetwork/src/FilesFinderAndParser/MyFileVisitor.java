package FilesFinderAndParser;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class MyFileVisitor extends SimpleFileVisitor<Path> {

    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
        if (path.toString().endsWith(".csv") || path.toString().endsWith(".json")){
            System.out.println(path);
        }
        return FileVisitResult.CONTINUE;
    }
}
