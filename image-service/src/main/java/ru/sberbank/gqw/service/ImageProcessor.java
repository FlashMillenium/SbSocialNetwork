package ru.sberbank.gqw.service;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageProcessor {

    @Value( "${gqw.image.root-folder}")
    private String rootPath;


    public String getStr(){
        String pathToFile = rootPath + "\\image\\35\\1\\";
        File file = FileUtils.getFile(pathToFile, "nginx.png");


        try  {
            byte[] bytes = FileUtils.readFileToByteArray(file);
            System.out.println("!!!!!!!!!");
            System.out.println(bytes.length);
            FileUtils.writeByteArrayToFile(new File(rootPath + "\\image\\35\\2\\test.png"), bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
