package main.java.org.jeycode.easymoney.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public interface EasyCopyHelpers
{

      String FILE_MSG_PREFIX = "El archivo <";
      String ERR_PREDICATE_MSG = "> no se pudo copiar en el destino --> ";
      String SUCCES_PREDICATE_MSG = "> se ha copiado con éxito. Destino --> ";
      String DIR_MSG_PREFIX = "El directorio <";
      char SEPARATOR_v2 = '\\';
      char SEPARATOR_v1 = '/';
      char SLASH = File.separatorChar;

      default String formatTargetPath(String target)
      {
            return target.charAt(target.length() - 1) == SLASH ? target : target + SLASH;
      }

      default boolean copyFile(Path source,Path target)
      {
            try
            {
                  Files.copy(source,target,StandardCopyOption.REPLACE_EXISTING);
                  return true;
            }
            catch (IOException ex)
            {
                  return false;
            }
      }

      default boolean copyDir(Path source,Path target,FileVisitor<? super Path> visitor)
      {
            try
            {
                  Files.walkFileTree(source,visitor);
                  return true;
            }
            catch (IOException ex)
            {
                  return false;
            }
      }
//
//      default void validateThatTargetNotAFile(Path target) throws IOException
//      {
//            if (target.toFile()
//                      .isFile())
//            {
//                  throw new IOException();
//            }
//      }
}
