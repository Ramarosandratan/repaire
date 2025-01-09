package com.rina.repaire.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.rina.repaire.services.ComputerService;
import com.rina.repaire.services.RepairService;

@Controller
public class HomeController {
    @Autowired
    private ComputerService computerService;

    @Autowired
    private RepairService repairService;

/**
 * Handles the root URL ("/") GET request and populates the model with
 * data required for the home page view, including the count of all
 * computers, active repairs, and completed repairs.
 *
 * @param model the model to which attributes are added for rendering the view
 * @return the name of the view to be rendered, "index"
 */

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("computerCount", computerService.getAllComputers().size());
        model.addAttribute("activeRepairs", repairService.getRepairsByStatus("PENDING").size());
        model.addAttribute("totalRepairs", repairService.getRepairsByStatus("COMPLETED").size());
        return "index";
    }
}
