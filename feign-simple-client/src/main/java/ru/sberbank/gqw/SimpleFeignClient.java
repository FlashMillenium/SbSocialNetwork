package ru.sberbank.gqw;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("feign-simple-server")
public interface SimpleFeignClient {

    @RequestMapping("get/{id}")
    SimpleDto getById(@PathVariable("id") Long id);
}
