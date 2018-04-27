package ru.sberbank.front.controllers.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.sberbank.front.services.RestResponsePage;
import ru.sberbank.front.services.login.UserLogin;
import ru.sberbank.front.services.photo.UserPhoto;
import ru.sberbank.gqw.dto.AlbumDTO;
import ru.sberbank.gqw.dto.ImageDTO;
import ru.sberbank.gqw.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PhotoController {
    @Autowired
    private UserPhoto userPhoto;
    @Autowired
    private UserLogin userLogin;
    private AlbumDTO albumDTO;
    private ResponseEntity<UserDTO> user;

    @RequestMapping("users/photo")
    public ModelAndView photo() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users/photo");
        return modelAndView;
    }

    @RequestMapping(value = "users/photo", method = RequestMethod.GET)
    public ModelAndView photoAddAlbumGet(HttpSession session, HttpServletRequest request) {
        albumDTO = new AlbumDTO();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users/photo");
        user = userLogin.getByLogin((String) session.getAttribute("username"));
        RestResponsePage<AlbumDTO> albums = userPhoto.getUserAlbums(user.getBody().getId(), new PageRequest(0, 10));
        modelAndView.addObject("albums", albums.getContent());
        return modelAndView;
    }

    @RequestMapping(value = "users/photoaddalbum", method = RequestMethod.POST)
    public ModelAndView photoAddAlbumPost(HttpSession session, HttpServletRequest request) {
        albumDTO = new AlbumDTO();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:photo");
        user = userLogin.getByLogin((String) session.getAttribute("username"));
        String albumName = request.getParameter("Albumname");
        albumDTO.setName(albumName);
        albumDTO.setUserId(user.getBody().getId());
        userPhoto.saveAlbum(albumDTO);
        return modelAndView;
    }

    @RequestMapping(value = "users/photoalbum", method = RequestMethod.GET)
    public ModelAndView photoFromAlbumGet(@RequestParam("id") long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users/photoalbum");
        RestResponsePage<ImageDTO> photos = userPhoto.getImagesFromAlbum(id, new PageRequest(0, 10));
        modelAndView.addObject("album", photos.getContent());
        return modelAndView;
    }

    @RequestMapping(value = "users/photoalbum", method = RequestMethod.POST)
    public ModelAndView photoFromAlbumPost(@RequestParam("file") MultipartFile file) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users/photoalbum");
        byte[] bytes = null;
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(bytes.length);
//        System.out.println(id + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        userPhoto.addImage(1L, "aaa", bytes);
        return modelAndView;
    }


}
