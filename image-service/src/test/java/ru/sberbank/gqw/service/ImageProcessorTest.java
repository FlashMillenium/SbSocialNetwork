package ru.sberbank.gqw.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.sberbank.gqw.ImageServiceApplication;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageProcessorTest {
    @Autowired
    ImageProcessor ip;

    @Test
    public void getStr() {
        System.out.println(ip.getStr());
    }
}