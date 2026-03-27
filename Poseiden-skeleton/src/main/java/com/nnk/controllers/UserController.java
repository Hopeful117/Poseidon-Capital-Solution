package com.nnk.controllers;

import com.nnk.domain.User;
import com.nnk.exceptions.EntityNotFoundException;
import com.nnk.repositories.UserRepository;
import com.nnk.services.CrudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {


   private final  CrudService<User> service;

    @RequestMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", service.findAll());
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(User bid) {
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            redirectAttributes.addFlashAttribute("errorMessage", errors);
            return "redirect:/user/add";
        }
        try {

            service.create(user);
            model.addAttribute("users", service.findAll());
            return "redirect:/user/list";
        }catch(Exception e){
            model.addAttribute("errors",e.getMessage());
            return "user/add";
        }


    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
       User user = service.findById(id);
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model,RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            redirectAttributes.addFlashAttribute("errorMessage", errors);
            return "user/update";
        }

        try {

            user.setId(id);
            service.update(user);
            model.addAttribute("users", service.findAll());
            return "redirect:/user/list";
        } catch (Exception e) {
            List<String> errors = result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            redirectAttributes.addFlashAttribute("errorMessage", errors);
            return "user/update";
        }
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        try {
            service.deleteById(id);
            model.addAttribute("users", service.findAll());

        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());

        }
        return "/user/list";
    }
}
