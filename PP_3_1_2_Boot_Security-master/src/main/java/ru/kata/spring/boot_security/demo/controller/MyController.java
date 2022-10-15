package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class MyController {

    private UserService userService;

    private RoleService roleService;

    public MyController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/")
    public String showAllUsers(Model model, Principal principal) {
        List<User> allUsers = userService.getAllUsers();
        User admin = userService.getUserByLogin(principal.getName());
        model.addAttribute("allusers", allUsers);
        model.addAttribute("name", admin.getName());
        model.addAttribute("role", admin.getAllRolesString());
        model.addAttribute("roles",roleService.getAllRoles());
        model.addAttribute("user", new User());
        model.addAttribute("userData", userService.getUserByLogin(principal.getName()));
       // model.addAttribute("user", userService.getUserByLogin(principal.getName()));
        return "all-users";
    }

    @GetMapping("/addUser")
    public String addUser(@ModelAttribute("user") User user, Model model) {
        //User user = new User();
        model.addAttribute("listRoles", roleService.getAllRoles());
        return "user-info";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
//        user.setRoles(roleService.findByIdRoles());

        userService.saveUser(user);
        return "redirect:/admin/";
    }

    @PostMapping("/userDelete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
    userService.deleteById(id);
    return "redirect:/admin/";
    }

    @GetMapping("/update/{id}")
    public String updateUserFrom(@PathVariable("id") Long id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        return "user-update";
    }

    @PostMapping("/update")
    public String updateUser(User user) {
        userService.updateUser(user);
        return "redirect:/admin/";
    }
}
