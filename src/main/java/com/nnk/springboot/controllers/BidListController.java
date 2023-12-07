package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class BidListController {

    private final BidService bidService;

    @GetMapping("/bidList/list")
    public String home(Model model)
    {
        List<BidList> bidLists = bidService.findAll();
        model.addAttribute("bidLists", bidLists);
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result) {
        if (result.hasErrors()) {
            return "bidList/add";
        }
        bidService.create(bid);
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<BidList> optionalBidList = bidService.findById(id);
        if (optionalBidList.isEmpty()) {
            return "redirect:/bidList/list";
        }
        model.addAttribute("bidList", optionalBidList.get());
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "bidList/update";
        }
        bidService.findById(id)
                .ifPresent(existingBidList -> bidService.update(existingBidList, bidList));
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id) {
        bidService.deleteById(id);
        return "redirect:/bidList/list";
    }
}
