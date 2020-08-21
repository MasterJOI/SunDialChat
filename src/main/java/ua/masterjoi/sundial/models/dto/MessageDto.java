package ua.masterjoi.sundial.models.dto;

import lombok.Getter;
import ua.masterjoi.sundial.models.Message;
import ua.masterjoi.sundial.models.User;
import ua.masterjoi.sundial.models.util.MessageHelper;

//Делаем dto что б отпарвлять только те поля которые нам надо
@Getter
public class MessageDto {
    private Long id;
    private String text;
    private String tag;
    private User author;
    private String filename;
    //Отправляем только количество лайков(пользователей)
    private Long likes;
    // Флажок что текущий пользователь лайкнул сообщение
    private Boolean meLiked;

    public MessageDto(Message message, Long likes, Boolean meLiked) {
        this.id = message.getId();
        this.text = message.getText();
        this.tag = message.getTag();
        this.author = message.getAuthor();
        this.filename = message.getFilename();
        this.likes = likes;
        this.meLiked = meLiked;
    }

    public String getAuthorName() {
        return MessageHelper.getAuthorName(author);
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "id=" + id +
                ", author=" + author +
                ", likes=" + likes +
                ", meLiked=" + meLiked +
                '}';
    }
}
