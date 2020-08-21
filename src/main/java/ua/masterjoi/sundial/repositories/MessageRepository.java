package ua.masterjoi.sundial.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ua.masterjoi.sundial.models.Message;
import ua.masterjoi.sundial.models.User;
import ua.masterjoi.sundial.models.dto.MessageDto;

public interface MessageRepository extends CrudRepository<Message, Long> {
    //Метод для поиску по тегу
    //Добавили Pageable для pagination(оптимального отображения длинных списков)
    @Query("select new ua.masterjoi.sundial.models.dto.MessageDto(" +
            "   m, " +
            "   count(ml), " +
            "   sum(case when ml = :user then 1 else 0 end) > 0" +
            ") " +
            "from Message m left join m.likes ml " +
            "group by m")
    Page<MessageDto> findAll(Pageable pageable, @Param("user") User user);
    @Query("select new ua.masterjoi.sundial.models.dto.MessageDto(" +
            "   m, " +
            "   count(ml), " +
            "   sum(case when ml = :user then 1 else 0 end) > 0" +
            ") " +
            "from Message m left join m.likes ml " +
            "where m.tag = :tag " +
            "group by m")
    Page<MessageDto> findByTag(@Param("tag") String tag, Pageable pageable, @Param("user") User user);


    //Делаем запрос на HQL
    @Query("select new ua.masterjoi.sundial.models.dto.MessageDto(" +
            "   m, " +
            "   count(ml), " +
            "   sum(case when ml = :user then 1 else 0 end) > 0" +
            ") " +
            "from Message m left join m.likes ml " +
            "where m.author = :author " +
            "group by m")

    Page<MessageDto> findByUser(Pageable pageable, @Param("author") User author, @Param("user") User user);
}
