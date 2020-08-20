package ua.masterjoi.sundial.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ua.masterjoi.sundial.models.Message;
import ua.masterjoi.sundial.models.User;
import ua.masterjoi.sundial.repositories.MessageRepository;
import ua.masterjoi.sundial.services.FileService;

import java.io.IOException;
import java.util.Set;

@Controller
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private FileService fileService;

    @GetMapping("/user-messages/{user}")
    public String userMessages(
            /*Принимает текущего пользователя из сессии и запрашиваемый пользователь(из url запроса)*/
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model,
            /*Для messageEditor, Spring берет автоматом из get запроса (но его может и небыть в запросе такчто зделали поле необязательным)*/
            @RequestParam(required = false) Message message
    ) {
        Set<Message> messages = user.getMessages();

        model.addAttribute("messages", messages);
        model.addAttribute("message", message);
        //Для отображения имени пользователя
        model.addAttribute("userChannel", user);
        //Отображает число подписок на странице
        model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
        //Отображает число подписчиков на странице
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        //Определяем являеться ли текущий пользователь подписанным на странице другого(у пользователя берем его список подпищиков и ищем там текцщего пользователя)
        model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));

        model.addAttribute("isCurrentUser", currentUser.equals(user));
        return "userMessages";
    }

    /*Обработка сохранения отредактированного сообщения*/
    @PostMapping("/user-messages/{user}")
    public String updateMessage(
            @AuthenticationPrincipal User currentUser,
            //Получаем Id пользователя
            @PathVariable Long user,
            @RequestParam("id") Message message,
            @RequestParam("text") String text,
            @RequestParam("tag") String tag,
            @RequestParam("file") MultipartFile file,
            Model model
    ) throws IOException {
        /*Проверка что пользователь может менять только свои сообщения*/
        if (message.getAuthor().equals(currentUser)) {
            //Обновляем данные
            if (!StringUtils.isEmpty(text)) {
                message.setText(text);
            } else {
               //model.addAttribute("editError", "Please, fill the message!");

            }
            if (!StringUtils.isEmpty(tag)) {
                message.setTag(tag);
            }
            
            fileService.saveFile(message, file);
            messageRepository.save(message);
        }
        return "redirect:/user-messages/" + user;
    }
}
