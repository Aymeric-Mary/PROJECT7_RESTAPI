package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        List<Trade> trades = tradeService.findAll();
        model.addAttribute("trades", trades);
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addTradeForm(Trade trade) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "trade/add";
        }
        tradeService.create(trade);
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<Trade> optionalTrade = tradeService.findById(id);
        if (optionalTrade.isEmpty()) {
            return "redirect:/trade/list";
        }
        model.addAttribute("trade", optionalTrade.get());
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "trade/update";
        }
        tradeService.findById(id)
                .ifPresent(existingTrade -> tradeService.update(existingTrade, trade));
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id) {
        tradeService.deleteById(id);
        return "redirect:/trade/list";
    }
}
