package ua.masterjoi.sundial.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ua.masterjoi.sundial.models.Message;
import ua.masterjoi.sundial.models.User;
import ua.masterjoi.sundial.models.dto.MessageDto;
import ua.masterjoi.sundial.repositories.MessageRepository;
import ua.masterjoi.sundial.services.FileService;
import ua.masterjoi.sundial.services.MessageService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private MessageService messageService;


    @Value("${upload.path}")
    private String uploadPath;



    @GetMapping("/")
    public String greeting(Map<String, Long> model ) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter,
                       Model model,
                       //Добавили атрибут для отображения длинного списка(указали как сортировать сообщения и в каком порядке выводить)
                       @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable,
                       /*Добавляем пользователя в контекст чтоб проверять лайкнул он сообщение или нет*/
                       @AuthenticationPrincipal User user
                       ) {
        Page<MessageDto> page = messageService.messageList(pageable, filter, user);

        model.addAttribute("page", page);
        model.addAttribute("url", "/main");
        model.addAttribute("filter", filter);
        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @Valid Message message,
            //Включает список аргументов и сообщения ошибок валидации всегда пишеться перед Model(иначе ошибки будет писать прямо на сайт)
            BindingResult bindingResult,
            Model model,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam("file") MultipartFile file) throws IOException {

        //Для page
        Page<MessageDto> page;

        message.setAuthor(user);

        if(bindingResult.hasErrors()) {
            //Теперь если сообщения не подойдут требованиям, то мы получим все ошибки в ErrorsMap и они попадут на страницу
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            //Ложим ошибки в модель
            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);
        } else {
            fileService.saveFile(message, file);
            model.addAttribute("message", null);
            messageRepository.save(message);
        }

        page = messageRepository.findAll(pageable, user);


        //Заполняем поля для pageable
        model.addAttribute("url", "/main");
        model.addAttribute("page", page);

        return "main";
    }


    /*  Не нужно, так как фильтруем все на одной странице main
    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        //Используем Iterable, потому что в findByTag нужно вернуть List, а в findAll - Iterable
        //Но List - наследник Iterable, поэтому обобщаем messages до Iterable.

        Iterable<Message> messages;

        //Если был задан фильтр, выводим найденные
        if(filter != null && !filter.isEmpty()) {
            messages = messageRepository.findByTag(filter);
        }
        else {
            messages = messageRepository.findAll();
        }

        model.put("messages", messages);
        return "main";
    }
     */
}
