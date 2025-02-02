package com.heroku.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Map;

@SpringBootApplication
@Controller
public class GettingStartedApplication {
    private final DataSource dataSource;

    @Autowired
    public GettingStartedApplication(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @PostMapping("/login")
    String homepage(HttpSession session, @ModelAttribute("user") Users user, Model model){
        try (Connection connection = dataSource.getConnection()) {
            final var statement = connection.createStatement();
            final var resultSet = statement.executeQuery("SELECT userID, name, password,email,address FROM user");
            String returnPage = "";
            while (resultSet.next()) {
                String username = resultSet.getString("name");
                String pwd = resultSet.getString("password");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                int userID = resultSet.getInt("userID");
                
                if (username.equals(user.getEmail()) && bCryptPasswordEncoder().matches(user.getPassword(), pwd)) {
        
                  session.setAttribute("name", user.getName());
                  session.setAttribute("email", email);
                  session.setAttribute("userID", userID);
                  returnPage = "redirect:/";
                  break;
                }else{
                    returnPage = "index";
                }
            }
            return "/"; 
    }catch (Throwable t) {
        System.out.println("message : " + t.getMessage());
        return "index";
      }
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

    @GetMapping("/signUp")
    public String signUp() {
        return "signUp";
    }   

    @GetMapping("/database")
    String database(Map<String, Object> model) {
        try (Connection connection = dataSource.getConnection()) {
            final var statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
            statement.executeUpdate("INSERT INTO ticks VALUES (now())");

            final var resultSet = statement.executeQuery("SELECT tick FROM ticks");
            final var output = new ArrayList<>();
            while (resultSet.next()) {
                output.add("Read from DB: " + resultSet.getTimestamp("tick"));
            }

            model.put("records", output);
            return "database";

        } catch (Throwable t) {
            model.put("message", t.getMessage());
            return "error";
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(GettingStartedApplication.class, args);
    }
}
