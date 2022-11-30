package pro.sky.command.service;

import org.springframework.stereotype.Service;
import pro.sky.command.constants.BotMessageEnum;

import java.util.HashMap;
import java.util.Map;
@Service
public class ButtonModService {
    private final Map<Long, BotMessageEnum> buttonMod = new HashMap<>();

  public BotMessageEnum getButton(long chatId){
      return buttonMod.getOrDefault(chatId,BotMessageEnum.CALL_VOLUNTEER);
  }
  public void setButton(long chatId,BotMessageEnum button){
      buttonMod.put(chatId,button);
  }
}
