package ua.masterjoi.sundial.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ua.masterjoi.sundial.models.Message;
import ua.masterjoi.sundial.models.User;
import ua.masterjoi.sundial.models.dto.MessageDto;
import ua.masterjoi.sundial.repositories.MessageRepository;
import ua.masterjoi.sundial.services.FileService;
import ua.masterjoi.sundial.services.MessageService;

import java.io.IOException;
import java.util.Set;

@Controller
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private MessageService messageService;

    @GetMapping("/user-messages/{author}")
    public String userMessages(
            /*Принимает текущего пользователя из сессии и запрашиваемый пользователь(из url запроса)*/
            @AuthenticationPrincipal User currentUser,
            @PathVariable User author,
            Model model,
            /*Для messageEditor, Spring берет автоматом из get запроса (но его может и небыть в запросе такчто зделали поле необязательным)*/
            @RequestParam(required = false) Message message,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<MessageDto> page = messageService.messageListForUser(pageable, author, currentUser);

        model.addAttribute("message", message);
        //Для отображения имени пользователя
        model.addAttribute("userChannel", author);
        //Отображает число подписок на странице
        model.addAttribute("subscriptionsCount", author.getSubscriptions().size());
        //Отображает число подписчиков на странице
        model.addAttribute("subscribersCount", author.getSubscribers().size());
        //Определяем являеться ли текущий пользователь подписанным на странице другого(у пользователя берем его список подпищиков и ищем там текцщего пользователя)
        model.addAttribute("isSubscriber", author.getSubscribers().contains(currentUser));

        //Заполняем поля для pageable
        model.addAttribute("url", "/user-messages/" + author.getId());
        model.addAttribute("page", page);

        model.addAttribute("isCurrentUser", currentUser.equals(author));
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
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
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

    /*Метод обработки ставки лайков*/
    @GetMapping("/messages/{message}/like")
    public String like(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Message message,
            /*Позволяет отправить параметры на тот метод, на который делаем redirect*/
            RedirectAttributes redirectAttributes,
            /*Отсюда мы получаем страницу с которой мы пришли с лайком чтобы на нее заредиректить сообщение(понимаем откуда пришли)*/
            @RequestHeader(required = false) String referer
    ) {
        /*Получаем количество лайков с сообщения*/
        Set<User> likes = message.getLikes();
        if(likes.contains(currentUser)) {
            /*Если лайк существует то снимаем*/
            likes.remove(currentUser);
        } else {
            /*Иначе добавляем*/
            likes.add(currentUser);
        }
        /*Сделали переменную с параметрами из запроса которые хотим получить*/
        UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();

        /*С нее получаем параметры*/
        components.getQueryParams()
                .entrySet()
                /*Для каждой пары из entrySet делаем добавление в  redirectAttributes ключа и значения параметра(передали в качестве параметров все что получили)
                * Для того чтобы передать пагинацию, количество записей на странице и тд.*/
                .forEach(pair -> redirectAttributes.addAttribute(pair.getKey(), pair.getValue()));
        return "redirect:" + components.getPath();
    }
}
