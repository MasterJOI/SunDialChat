package ua.masterjoi.sundial.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ua.masterjoi.sundial.models.util.MessageHelper;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Please, fill the message.")
    @Length(max = 2048, message = "Insert message too long(< 2kB).")
    private String text;
    @Length(max = 25, message = "Insert tag too long(<255 symb).")
    private String tag;

    @ManyToOne(fetch = FetchType.EAGER) //Много сообщений у одного пользователя
    @JoinColumn(name = "user_id") //По умолчанию дало бы название author_id
    private User author; //Чье сообшение

    private String filename;

    //Делаем поле лайков где будет список пользователей которые нажали лайк
    @ManyToMany
    @JoinTable(
            //Название таблицы
            name = "message_likes",
            //Где id сообщения связанно с id пользователя
            joinColumns = { @JoinColumn(name = "message_id")},
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private Set<User> likes = new HashSet<>();

    public Message(String text, String tag, User user) {
        this.text = text;
        this.tag = tag;
        this.author = user;
    }

    public String getAuthorName() {
        return MessageHelper.getAuthorName(author);
    }
}
