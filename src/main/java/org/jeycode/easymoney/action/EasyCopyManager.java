package main.java.org.jeycode.easymoney.action;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import main.java.org.jeycode.easymoney.pojo.EasyEvent;
import main.java.org.jeycode.easymoney.utils.EasyExecutorHelper;

public final class EasyCopyManager implements EasyExecutorHelper
{

      private List<EasyEvent> events;
      private Map<CMND,List<String>> mapOfCommandsToExecute;
      private FileCopyExecutor easyCopyExecutor;

      private EasyCopyManager()
      {}

      public static EasyCopyManager loadEasyEventWrapper() throws Exception
      {
            EasyCopyManager builderInstance = new EasyCopyManager();
            builderInstance.events = builderInstance.deserializeConfigFile().easyevents;
            return builderInstance;
      }

      public EasyCopyManager queueCommands(String inputTxt)
      {
            mapOfCommandsToExecute = loadMapOfCommands(lstOfCommands(inputTxt));
            return this;
      }

      public List<String> executeAllAndGetOperationMessages(FileCopyExecutor easyCopyExecutor)
      {
            Objects.requireNonNull(mapOfCommandsToExecute,
                                   "Se debe llamar al método ${queueCommands} antes que ${executeAllAndGetOperationMessages}");
            this.easyCopyExecutor = Objects.requireNonNull(easyCopyExecutor,"El executor de los copy-files no puede ser null");
            executeCommandsIfNotEmpty();
            executeCommandsGroupIfNotEmpty();
            return easyCopyExecutor.getOperationMessages();
      }

      private void execute(EasyEvent event)
      {
            easyCopyExecutor.tryThis(event);
      }

      private void executeIf(Predicate<? super EasyEvent> isValidToExecute)
      {
            events.stream()
                  .filter(isValidToExecute)
                  .forEach(this::execute);
      }

      private Map<String,List<String>> loadMapOfCommandGroupCommandsToExclude(List<String> listOfCommandsGroup)
      {

            return listOfCommandsGroup.stream()
                                      .collect(Collectors.toMap(this::getCommandGroup,str-> getCommandToExclude(str,getCommandGroup(str))));
      }

      private void executeCommandsGroupIfNotEmpty()
      {
            List<String> listOfCommandsGroup = mapOfCommandsToExecute.get(CMND.COMMANDGROUP);
            if (listOfCommandsGroup != null && !listOfCommandsGroup.isEmpty())
            {
                  Map<String,List<String>> mapOfCommandGroup = loadMapOfCommandGroupCommandsToExclude(listOfCommandsGroup);
                  Predicate<EasyEvent> isValidToExecute = event->
                        {
                              String commandgroupKey = event.commandgroup;
                              return mapOfCommandGroup.containsKey(commandgroupKey) && !mapOfCommandGroup.get(commandgroupKey)
                                                                                                         .contains(event.command);
                        };
                  executeIf(isValidToExecute);
            }
      }

      private void executeCommandsIfNotEmpty()
      {
            List<String> listOfCommands = mapOfCommandsToExecute.get(CMND.COMMAND);
            if (listOfCommands != null && !listOfCommands.isEmpty())
            {
                  Predicate<EasyEvent> isValidToExecute = event-> listOfCommands.contains(event.command);
                  executeIf(isValidToExecute);
            }
      }

}
