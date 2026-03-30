package com.nnk.controllers;

import com.nnk.domain.Rating;
import com.nnk.services.CrudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
public class RatingController {


    private final CrudService<Rating> service;

    @RequestMapping("/rating/list")
    public String home(Model model) {
        List<Rating> ratings = service.findAll();
        model.addAttribute("ratings", ratings);

        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        // TODO: check data valid and save to db, after saving return Rating list
        if (result.hasErrors()) {

            return "rating/add";
        }
        try {

            service.create(rating);
            return "redirect:/rating/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "rating/add";
        }

    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        // TODO: get Rating by Id and to model then show to the form
        try {
            Rating rating = service.findById(id);
            model.addAttribute("rating", rating);
            return "rating/update";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/rating/list";
        }
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                               BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        // TODO: check required fields, if valid call service to update Rating and return Rating list
        if (result.hasErrors()) {

            return "rating/update";
        }
        try {

            rating.setId(id);
            service.update(rating);
            return "redirect:/rating/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "rating/update";
        }
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model,RedirectAttributes redirectAttributes) {
        // TODO: Find Rating by Id and delete the Rating, return to Rating list
        try {
            service.deleteById(id);

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/rating/list";
    }
}
