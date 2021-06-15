package main.java.org.jeycode.easymoney.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import main.java.org.jeycode.easymoney.pojo.EasyEventWrapper;

public interface EasyExecutorHelper
{

      String CMND_SEPARATOR = ",";
      String CMND_GROUP_PREFIX = "*";
      String CMND_GROUP_EXCLUDE_PREFIX = "-";
      String EMPTY_STR = "";
      Class<EasyEventWrapper> EVENT_CLASS = EasyEventWrapper.class;
      Gson gson = new Gson();
      String CONFIG_FILE = "easy-config.json";
      File configFile = new File(CONFIG_FILE);

      default EasyEventWrapper deserializeConfigFile() throws Exception
      {
            try (FileReader reader = new FileReader(configFile))
            {
                  return gson.fromJson(reader,EVENT_CLASS);
            }
            catch (IOException ex)
            {
                  throw new Exception("Problemas al leer/mapear el fichero - " + CONFIG_FILE
                                          + " - Revise que se encuentra con el formato adecuado y ubicado en el mismo directorio que el jar ejecutable.");
            }
      }

      default List<String> lstOfCommands(String inputTxt)
      {
            return List.of(inputTxt.split(CMND_SEPARATOR));
      }

      default String getCommandGroup(String txt)
      {
            int indexOf = txt.contains(CMND_GROUP_EXCLUDE_PREFIX) ? txt.indexOf(CMND_GROUP_EXCLUDE_PREFIX) : txt.length();
            return txt.subSequence(1,indexOf) + EMPTY_STR;
      }

      default List<String> getCommandToExclude(String txt,String subSequence)
      {
            return subSequence.length() + 1 == txt.length() ? List.of() : List.of(txt.substring(subSequence.length() + 2)
                                                                                     .split(CMND_GROUP_EXCLUDE_PREFIX));
      }

      default Map<CMND,List<String>> loadMapOfCommands(List<String> lstOfCommands)
      {
            Function<String,CMND> groupingFunction = str-> str.startsWith(CMND_GROUP_PREFIX) ? CMND.COMMANDGROUP : CMND.COMMAND;
            return lstOfCommands.stream()
                                .collect(Collectors.groupingBy(groupingFunction));
      }

      enum CMND
      {
       COMMAND,
       COMMANDGROUP
      }
}
