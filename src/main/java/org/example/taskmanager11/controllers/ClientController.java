package org.example.taskmanager11.controllers;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import org.example.taskmanager11.model.Client;
import org.example.taskmanager11.services.ClientService;
import org.example.taskmanager11.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("register")
    public String register() {
        return "register";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @PostMapping("register")
    public String register(@RequestParam String login,
                           @RequestParam String password) {
        String salt = Utils.generateRandomString(10);
        String hash = Utils.passwordHash(salt, password);

        clientService.addClient(login, salt, hash);
        return "redirect:/login";
    }

    @PostMapping("login")
    public String login(@RequestParam String login,
                        @RequestParam String password,
                        HttpSession session) {
        if (clientService.checkClient(login, password)) {
            session.setAttribute("login", login);
            return "redirect:/";
        } else
            return "redirect:/login";
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.removeAttribute("login");
        return "redirect:/";
    }

    @GetMapping("changePassword")
    public String changePassword(HttpSession session, Model model) {
        Object error = session.getAttribute("error");
        Object message = session.getAttribute("message");

        if (error != null) {
            model.addAttribute("error", error);
            session.removeAttribute("error");
        }

        if (message != null) {
            model.addAttribute("message", message);
            session.removeAttribute("message");
        }

        return "changePassword";
    }

    @PostMapping("changePassword")
    public String changePassword(@RequestParam("oldPassword") String password,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 HttpSession session) {
        String login = (String) session.getAttribute("login");

        if (login == null) {
            return "redirect:/login";
        }

        if (!clientService.checkClient(login, password)) {
            session.setAttribute("error", "Invalid old password");
            return "redirect:/changePassword";
        }

        if (!newPassword.equals(confirmPassword)) {
            session.setAttribute("error", "Passwords do not match");
            return "redirect:/changePassword";
        }

        System.out.println();

        String newSalt = Utils.generateRandomString(10);
        String newHash = Utils.passwordHash(newSalt, newPassword);
        clientService.changePassword(login, newSalt, newHash);

        session.setAttribute("message", "Password changed successfully");
        return "redirect:/changePassword";
    }
}
