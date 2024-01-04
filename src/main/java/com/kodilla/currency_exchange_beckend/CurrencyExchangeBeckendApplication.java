package com.kodilla.currency_exchange_beckend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CurrencyExchangeBeckendApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyExchangeBeckendApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure (SpringApplicationBuilder application){
        return application.sources(CurrencyExchangeBeckendApplication.class);
    }
}
