package main.java.org.jeycode.easymoney.action;

import java.io.File;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;

import main.java.org.jeycode.easymoney.pojo.EasyEvent;
import main.java.org.jeycode.easymoney.utils.EasyCopyHelpers;
import main.java.org.jeycode.easymoney.visitor.EasyVisitor;

public class EasyCopyExecutor implements FileCopyExecutor,EasyCopyHelpers
{

      private final List<String> operationMessages = new LinkedList<>();

      private final BiConsumer<String,String> copyDirConsumer = this::copyDirectoryTo;
      private final BiConsumer<String,String> copyFileConsumer = this::copyFileTo;

      @Override
      public void tryThis(EasyEvent event)
      {
            event.source.stream()
                        .filter(path-> new File(path).exists())
                        .forEach(srcPath->
                              {
                                    boolean isDirectory = new File(srcPath).isDirectory();
                                    BiConsumer<String,String> copyConsumer = isDirectory ? copyDirConsumer : copyFileConsumer;

                                    copyInAllTargets(srcPath,event.target,copyConsumer);
                              });
      }

      private void copyInAllTargets(String srcPath,List<String> targets,BiConsumer<String,String> copyConsumer)
      {
            targets.stream()
                   .filter(path-> new File(path).exists())
                   .forEach(target-> copyConsumer.accept(srcPath,target));
      }

      @Override
      public void copyDirectoryTo(String source,String target)
      {
            Path srcPath = Path.of(source);
            String fileName = srcPath.getFileName()
                                     .toString();
            Path targetPath = Path.of(formatTargetPath(target) + fileName);
            operationMessages.add(copyDir(srcPath,targetPath,new EasyVisitor(srcPath,targetPath)) ?
                  DIR_MSG_PREFIX + fileName + SUCCES_PREDICATE_MSG + target : DIR_MSG_PREFIX + fileName + ERR_PREDICATE_MSG + target);
      }

      @Override
      public void copyFileTo(String source,String target)
      {
            Path srcPath = Path.of(source);
            String fileName = srcPath.getFileName()
                                     .toString();
            Path targetPath = Path.of(formatTargetPath(target) + fileName);
            operationMessages.add(copyFile(srcPath,targetPath) ? FILE_MSG_PREFIX + fileName + SUCCES_PREDICATE_MSG + target :
                  FILE_MSG_PREFIX + fileName + ERR_PREDICATE_MSG + target);
      }

      @Override
      public List<String> getOperationMessages()
      {
            return operationMessages;
      }
}
