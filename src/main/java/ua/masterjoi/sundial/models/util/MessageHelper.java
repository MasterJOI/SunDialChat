package ua.masterjoi.sundial.models.util;

import ua.masterjoi.sundial.models.User;

//Переместили меиод getAuthorName что б его можно было переиспользовать в других класах
public abstract class MessageHelper {
    public static String getAuthorName(User author) {
        return author != null ? author.getUsername() : "<none>";
    }
}
