package main.java.org.jeycode.easymoney;

import main.java.org.jeycode.easymoney.action.EasyCopyExecutor;
import main.java.org.jeycode.easymoney.action.EasyCopyManager;

public class EasyRunner
{

//      public static CLI cliFrame;

      public static void main(String[] args) throws Exception
      {
            EasyCopyManager.loadEasyEventWrapper()
                           .queueCommands("*a")
                           .executeAllAndGetOperationMessages(new EasyCopyExecutor())
                           .forEach(System.out::println);
      }

}