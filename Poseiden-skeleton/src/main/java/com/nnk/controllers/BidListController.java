package com.nnk.controllers;

import com.nnk.domain.BidList;
import com.nnk.services.CrudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BidListController {


    private final  CrudService<BidList> service;

    @RequestMapping("/bidList/list")
    public String home(Model model) {
        // TODO: call service find all bids to show to the view
        List<BidList> bidLists = service.findAll();
        model.addAttribute("bidLists",bidLists);
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return bid list
        if (result.hasErrors()) {

            return "bidList/add";
        }
        try{
            service.create(bid);
            return "redirect:/bidList/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage",e.getMessage());
            return "bidList/add";
        }

    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        // TODO: get Bid by Id and to model then show to the form
        try {
            BidList bidList = service.findById(id);
            model.addAttribute("bidList", bidList);
            return "bidList/update";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/bidList/list";
        }
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid
        if (result.hasErrors()) {

            return "bidList/update";
        }
        try {
            bidList.setBidListId(id);
            service.update(bidList);
            return "redirect:/bidList/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "bidList/update";
        }
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        try {
            service.deleteById(id);

        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "bidList/list";
    }
}
