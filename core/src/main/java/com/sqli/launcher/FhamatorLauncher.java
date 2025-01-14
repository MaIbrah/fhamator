package com.sqli.launcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.sqli.chatUI.controllers.chat.ChatController;

@SpringBootApplication
@ComponentScan(basePackages = {"com.sqli.*"})
public class FhamatorLauncher {
    public static void main(String[] args) {
        SpringApplication.run(FhamatorLauncher.class, args);

    }
}
