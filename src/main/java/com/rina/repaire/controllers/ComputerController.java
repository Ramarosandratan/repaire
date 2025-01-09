package com.rina.repaire.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rina.repaire.models.Computer;
import com.rina.repaire.services.ComputerService;

@Controller
@RequestMapping("/computers")
public class ComputerController {
    @Autowired
    private ComputerService computerService;

    /**
     * List all computers
     * @param model the model to store the computers
     * @return the view name to render the list of computers
     */
    @GetMapping
    public String listComputers(Model model) {
        model.addAttribute("computers", computerService.getAllComputers());
        return "computers/list";
    }

    /**
     * Show the form to create a new computer
     * @param model the model to store the new computer
     * @return the view name to render the form
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("computer", new Computer());
        return "computers/form";
    }

    /**
     * Create a new computer
     * @param computer the computer to create
     * @return the view name to redirect to the list of computers
     */
    @PostMapping
    public String createComputer(@ModelAttribute Computer computer) {
        computerService.saveComputer(computer);
        return "redirect:/computers";
    }

    /**
     * Show the details of a specific computer
     * @param id the ID of the computer to retrieve
     * @param model the model to store the computer details
     * @return the view name to render the computer details
     */

    @GetMapping("/{id}")
    public String showComputerDetails(@PathVariable Long id, Model model) {
        Computer computer = computerService.getComputerById(id);
        model.addAttribute("computer", computer);
        return "computers/detail";
    }
}