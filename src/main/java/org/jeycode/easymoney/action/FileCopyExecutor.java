package main.java.org.jeycode.easymoney.action;

import java.util.List;

import main.java.org.jeycode.easymoney.pojo.EasyEvent;

public interface FileCopyExecutor
{

      void tryThis(EasyEvent event);

      void copyDirectoryTo(String source,String target);

      void copyFileTo(String source,String target);

      List<String> getOperationMessages();

}
