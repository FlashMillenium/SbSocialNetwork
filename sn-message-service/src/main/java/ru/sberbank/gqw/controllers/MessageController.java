package ru.sberbank.gqw.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.sberbank.gqw.MessageServiceApplication;
import org.springframework.ui.Model;
import ru.sberbank.gqw.dao.MessageDAOImpl;
import ru.sberbank.gqw.entity.Message;

import javax.validation.Valid;


@Controller
public class MessageController {

    private MessageDAOImpl messageDAOImpl;
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    public void setMessageDAOImpl(MessageDAOImpl messageDaoImpl) {
        this.messageDAOImpl = messageDaoImpl;
    }

    @RequestMapping("/index")
    public String getIndexPage() {
        logger.info("Opening index page");
        System.out.println("Opening index page");
        return "index";
    }

    @RequestMapping("/messages")
    public String getAllFromDB(Model model){
        logger.info("getting all from DB");
        model.addAttribute("messages", messageDAOImpl.getAll());
        return "message.index";
    }

    @RequestMapping("/message/create")
    public String newMessage(Model model) {
        //Auth user id
        //Address id
        Message message = new Message(100, 99);
        model.addAttribute("message", message);
        logger.info("created new Message: ", message.toString());
        System.out.println("created new Message: " + message.toString());
        return "message.create";
    }

    //todo: принимает норм модель, возвращает обнуленную со всеми полями, кроме месседж
    @PostMapping("/message/send")
    public String sendMessage(@Valid @ModelAttribute("message") Message message,
                              BindingResult result, ModelMap model) {
        logger.info("Sending message: ", message.toString());
        System.out.println("Sending message: " + message.toString());
        messageDAOImpl.sendMessage(message);
        model.addAttribute("message", message);
        return "message.show";
    }

}
