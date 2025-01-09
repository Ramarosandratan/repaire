package com.rina.repaire.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rina.repaire.models.Repair;
import com.rina.repaire.services.ComponentService;
import com.rina.repaire.services.ComputerService;
import com.rina.repaire.services.RepairService;

@Controller
@RequestMapping("/repairs")
public class RepairController {
    @Autowired
    private RepairService repairService;

    @Autowired
    private ComputerService computerService;

    @Autowired
    private ComponentService componentService;

    /**
     * Shows a list of repairs for the given computer.
     * 
     * @param computerId The id of the computer.
     * @param model      The model to add the list of repairs and the computer to.
     * @return The name of the view to render.
     */
    @GetMapping("/computer/{computerId}")
    public String listRepairs(@PathVariable Long computerId, Model model) {
        model.addAttribute("repairs", repairService.getRepairsByComputer(computerId));
        model.addAttribute("computer", computerService.getComputerById(computerId));
        return "repairs/list";
    }

    /**
     * Shows the form to create a new repair for the given computer.
     * 
     * @param computerId The id of the computer.
     * @param model      The model to add the computer and the list of components to.
     * @return The name of the view to render.
     */
     @GetMapping("/list")
    public String showCreateForm( Model model) {
       model.addAttribute("repairs", repairService.getAllRepairs());
        return "repairs/listAll";
        
    }

    @GetMapping("/new")
    public String showCreateForm(@RequestParam Long computerId, Model model) {
        model.addAttribute("repair", new Repair());
        model.addAttribute("computer", computerService.getComputerById(computerId));
        model.addAttribute("components", componentService.getComponentsByComputer(computerId)) ;//listComponents(computerId, model)
        return "repairs/form";
    }

    /**
     * Creates a new repair for the given computer and list of components.
     * 
     * @param repair       The repair to create.
     * @param computerId   The id of the computer.
     * @param componentIds The ids of the components to repair.
     * @return The redirect to the list of repairs for the given computer.
     */
    @PostMapping
    public String createRepair(
            @ModelAttribute Repair repair,
            @RequestParam Long computerId,
            @RequestParam List<Long> componentIds,
               @RequestParam String type) {
        repairService.creatRepair(repair, computerId, componentIds,type);
        return "redirect:/repairs/computer/" + computerId;
    }

    /**
     * Completes a repair for the given id.
     * 
     * @param id The id of the repair to complete.
     * @return The redirect to the list of repairs for the computer of the completed
     *         repair.
     */
    @PostMapping("/{id}/complete")
    public String completeRepair(@PathVariable Long id) {
        Repair repair = repairService.completeRepair(id);
        return "redirect:/repairs/computer/" + repair.getComputer().getId();
    }
}
