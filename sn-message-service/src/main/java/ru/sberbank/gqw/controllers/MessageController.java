package ru.sberbank.gqw.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import ru.sberbank.gqw.entity.Message;
import ru.sberbank.gqw.services.MessageServiceImpl;

import javax.validation.Valid;


@Controller
public class MessageController {

    private MessageServiceImpl messageDAOImpl;
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    public void setMessageDAOImpl(MessageServiceImpl messageDaoImpl) {
        this.messageDAOImpl = messageDaoImpl;
    }

    @RequestMapping("/index")
    public String getIndexPage() {
        logger.info("Opening index page");
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
        //Need auth user id
        //Need address id
        Message message = new Message(100, 99);
        model.addAttribute("message", message);
        logger.info("created new Message: ", message.toString());
        return "message.create";
    }

    @PostMapping("/message/send")
    public String sendMessage(@Valid @ModelAttribute("message") Message message,
                              BindingResult result, ModelMap model) {
        logger.info("Sending message: ", message.toString());
        messageDAOImpl.sendMessage(message);
        model.addAttribute("message", message);
        return "message.show";
    }

    @RequestMapping(value = "/messages/{userId}")
    public String showMessagesByUserId(@PathVariable(value = "userId") Integer userId, Model model){
        logger.info("getting user's messages from DB");
        model.addAttribute("messages", messageDAOImpl.getAllMessagesForUser(userId));
        return "message.index";
    }

    @RequestMapping(value = "/messages/{userId}/from/{senderId}")
    public String showMessagesBetweenTwoUsers(@PathVariable(value = "userId") Integer userId,
                                              @PathVariable(value = "senderId") Integer senderId,
                                              Model model){
        logger.info("getting user's messages from specific user from DB");
        model.addAttribute("messages", messageDAOImpl.getAllMessagesFromUserToUser(senderId, userId));
        return "message.index";
    }

    @RequestMapping(value = "/messages/{userId}/unread")
    public String showUnreadMessages(@PathVariable(value = "userId") Integer userId, Model model){
        logger.info("getting unread user's messages from DB");
        model.addAttribute("messages", messageDAOImpl.getUnreadMessagesForUser(userId));
        return "message.index";
    }



}
