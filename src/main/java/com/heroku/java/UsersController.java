package com.heroku.java;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;

@Controller
public class UsersController {
  private DataSource dataSource;

  @Autowired
  public void AccountController(DataSource dataSource) {
    this.dataSource = dataSource;
    }

   @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @PostMapping("/registerAcc")
  public String registerAcc(HttpSession session,@ModelAttribute("register") Users user, Model model){
    try{
      Connection connection = dataSource.getConnection();
      String sql = "INSERT INTO Users(username, email, password,address) VALUES (?,?,?,?)";
      final var statement = connection.prepareStatement(sql);
      statement.setString(1, user.getName());
      statement.setString(2, passwordEncoder.encode(user.getPassword()));
      statement.setString(3, user.getEmail());
      statement.setString(4, user.getAddress());
      
      statement.executeUpdate();

      connection.close();

      return "redirect:/index";
    }
    catch (SQLException sqe) {
      System.out.println("Error Code = " + sqe.getErrorCode());
      System.out.println("SQL state = " + sqe.getSQLState());
      System.out.println("Message = " + sqe.getMessage());
      System.out.println("printTrace /n");
      sqe.printStackTrace();

      return "redirect:/";
    } catch (Exception e) {
      System.out.println("E message : " + e.getMessage());
      return "redirect:/";
    }

    }
  }



