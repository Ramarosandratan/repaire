package com.rina.repaire.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rina.repaire.models.Component;
import com.rina.repaire.models.Computer;
import com.rina.repaire.repositories.ComponentRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ComponentService {
    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private ComputerService computerService;

    /**
     * Saves the given component and associates it with the given computer id.
     * 
     * @param component The component to save.
     * @param computerId The id of the computer to associate with the component.
     * @return The saved component.
     */
    public Component saveComponent(Component component, Long computerId) {
        Computer computer = computerService.getComputerById(computerId);
        component.setComputer(computer);
        return componentRepository.save(component);
    }

    /**
     * Retrieves a component by its id.
     * 
     * @param id The id of the component to retrieve.
     * @return The component with the given id, or throws an
     *         {@link EntityNotFoundException} if no component is found with the
     *         given id.
     */
    public Component getComponentById(Long id) {
        return componentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Component not found with id: " + id));
    }

    /**
     * Retrieves all components associated with the given computer id.
     * 
     * @param computerId The id of the computer to retrieve components for.
     * @return A list of all components associated with the given computer id.
     */
    public List<Component> getComponentsByComputer(Long computerId) {
        return componentRepository.findByComputerId(computerId);
    }

    /**
     * Retrieves all components with the given status.
     * 
     * @param status The status of the components to retrieve.
     * @return A list of all components with the given status.
     */
    public List<Component> getComponentsByStatus(String status) {
        return componentRepository.findByStatus(status);
    }

    /**
     * Updates the status of a component with the given id.
     * 
     * @param id       The id of the component to update.
     * @param newStatus The new status of the component.
     */
    public void updateComponentStatus(Long id, String newStatus) {
        Component component = getComponentById(id);
        component.setStatus(newStatus);
        componentRepository.save(component);
    }
}
