package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminContoller {
    @Autowired
    private UserService service;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/admin/new-user")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);
        return "user_form";
    }

    @PostMapping("/admin/update-user")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "roleIds", required = false) List<Long> roleIds) {
        if (roleIds == null) {
            roleIds = new ArrayList<>();
        }
        userService.updateUser(user, roleIds);
        return "redirect:/admin/all-users";
    }

    @PostMapping("/admin/save-new-user")
    public String saveUser(@ModelAttribute("user")User user) {

    userService.saveUser(user);
    return "redirect:/admin/all-users";
    }


    @GetMapping("/admin/edit{id}")
    public String editUser(@RequestParam(name = "id") Long id, Model model) {
        User user = service.findUserById(id);
        model.addAttribute("user", user);
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);
        return "user_form_edit";
    }

    @GetMapping("/admin/all-users")
    public String ShowUsers(Model model) {
        List<User> users = userService.allUsers();
        List<Role> roles = (List<Role>) roleRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);
        return "ShowUsers";
    }
    @PostMapping("/admin/delete")
    public String deleteUser(@RequestParam("id") Long id) {
    userService.deleteUser(id);
    return "redirect:/admin/all-users";
    }



}
