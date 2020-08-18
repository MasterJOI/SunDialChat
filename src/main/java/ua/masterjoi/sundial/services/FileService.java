package ua.masterjoi.sundial.services;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.masterjoi.sundial.models.Message;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {

    //Value - указіваем Spring что хотим получить переменную upload.path (будет искать в properies)
    @Value("${upload.path}")
    private String uploadPath;

    /*Вынесли сохранение файла в отдельный метод для переиспользования в редакторе сообщения*/
    public void saveFile(Message message, MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            //Проверка если нету дериктории для загрузки, то мы ее создадим
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            //Обезопасимя от колизий и создадим для каждого файла уникальное имя
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            //Загружаем файл
            file.transferTo(new File(uploadPath + "/" + resultFilename));

            message.setFilename(resultFilename);
        }
    }
}
