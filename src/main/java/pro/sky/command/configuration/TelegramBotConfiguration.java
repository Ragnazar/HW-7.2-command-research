
package pro.sky.command.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Класс конфигурации для создания бота.
 * Содержит поля с названием бота и токеном.
 * @autor Шилова Наталья
 */
@Configuration
@PropertySource("application.properties")
@Data
public class TelegramBotConfiguration {
    @Value("${telegram.bot.name}")
    private String botName;
    @Value("${telegram.bot.token}")
    private String token;
    @Value("${report.avatar.dir.path}")
    private String reportPhotoPath;


    @Value("${service.file_storage.uri}")
    private String fileUri;

    public String getFileUri(String filePath) {
        return fileUri+token+"/"+filePath;
    }

}
