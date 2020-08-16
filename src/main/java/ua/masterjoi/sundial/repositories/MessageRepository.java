package ua.masterjoi.sundial.repositories;

import org.springframework.data.repository.CrudRepository;
import ua.masterjoi.sundial.models.Message;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {
    //Метод для поиску по тегу
    List<Message> findByTag(String tag);
}
