package com.rina.repaire.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rina.repaire.models.Component;
import com.rina.repaire.services.ComponentService;
import com.rina.repaire.services.ComputerService;

@Controller
@RequestMapping("/components")
public class ComponentController {
    @Autowired
    private ComponentService componentService;

    @Autowired
    private ComputerService computerService;

    /**
     * Shows a list of components for the given computer.
     * 
     * @param computerId The id of the computer.
     * @param model      The model to add the components and computer to.
     * @return The name of the template to render.
     */
    @GetMapping("/computer/{computerId}")
    public String listComponents(@PathVariable Long computerId, Model model) {
        model.addAttribute("components", componentService.getComponentsByComputer(computerId));
        model.addAttribute("computer", computerService.getComputerById(computerId));
        return "components/list";
    }

    /**
     * Displays the form for creating a new component for the specified computer.
     *
     * @param computerId The ID of the computer for which the component is being added.
     * @param model The model to add attributes used for rendering the form.
     * @return The name of the template to render the form.
     */

    @GetMapping("/new")
    public String showCreateForm(@RequestParam Long computerId, Model model) {
        model.addAttribute("component", new Component());
        model.addAttribute("computerId", computerId);
        return "components/form";
    }

    /**
     * Handles the form submission for creating a new component.
     *
     * @param component The component to be saved.
     * @param computerId The ID of the computer for which the component is being added.
     * @return The redirect URL to the components list page after the component is saved.
     */
    @PostMapping
    public String createComponent(@ModelAttribute Component component, @RequestParam Long computerId) {
        componentService.saveComponent(component, computerId);
        return "redirect:/components/computer/" + computerId;
    }
}
