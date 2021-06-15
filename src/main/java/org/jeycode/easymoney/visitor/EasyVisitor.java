package main.java.org.jeycode.easymoney.visitor;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public final class EasyVisitor extends SimpleFileVisitor<Path>
{

      private final Path sourceDir;
      private final Path targetDir;

      public EasyVisitor(Path sourceDir,Path targetDir)
      {
            super();
            this.sourceDir = sourceDir;
            this.targetDir = targetDir;
      }

      @Override
      public FileVisitResult visitFile(Path file,BasicFileAttributes attrs) throws IOException
      {
            Path targetFile = targetDir.resolve(sourceDir.relativize(file));
            if (!targetFile.toFile()
                           .exists())
            {

                  Files.copy(file,targetFile);
            }
            return FileVisitResult.CONTINUE;
      }

      @Override
      public FileVisitResult preVisitDirectory(Path dir,BasicFileAttributes attrs) throws IOException
      {
            Path newDirectory = targetDir.resolve(sourceDir.relativize(dir));
            if (!newDirectory.toFile()
                             .exists())
            {
                  Files.createDirectory(newDirectory);
            }
            return FileVisitResult.CONTINUE;

      }

}
