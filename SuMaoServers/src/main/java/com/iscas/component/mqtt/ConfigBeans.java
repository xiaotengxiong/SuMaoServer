package com.iscas.component.mqtt;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by IntelliJ IDEA.
 * User: adams
 * Date:  2017/12/4
 * Time: 16:24
 */
@Configuration
public class ConfigBeans {

    @Bean
    public Gson gson() {
        return new Gson();
    }
}
