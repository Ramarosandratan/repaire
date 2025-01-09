package com.rina.repaire.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rina.repaire.models.Component;
import com.rina.repaire.models.Computer;
import com.rina.repaire.models.Repair;
import com.rina.repaire.repositories.RepairRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RepairService {
    @Autowired
    private RepairRepository repairRepository;

    @Autowired
    private ComputerService computerService;

    @Autowired
    private ComponentService componentService;

    /**
     * Retrieves a repair by its ID.
     *
     * @param id The ID of the repair to retrieve.
     * @return The repair object associated with the given ID.
     * @throws EntityNotFoundException if no repair is found with the given ID.
     */

    @Transactional
    public Repair getRepairById(Long id) {
        return repairRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Repair not found with id: " + id));
    }

    /**
     * Creates a new repair for the specified computer and components.
     *
     * @param repair       The repair object containing repair details.
     * @param computerId   The ID of the computer to associate with the repair.
     * @param componentIds The list of component IDs to be repaired.
     * @return The saved repair object.
     */

    @Transactional
    public Repair creatRepair(Repair repair, Long computerId, List<Long> componentIds,String type) {
        Computer computer = computerService.getComputerById(computerId);
        repair.setComputer(computer);
        repair.setDateCreated(LocalDateTime.now());
        repair.setStatus("PENDING");
        repair.setType(type);

        List<Component> components = componentIds.stream()
                .map(componentService::getComponentById)
                .toList();
        repair.setRepairedComponents(components);

        return repairRepository.save(repair);
    }

    /**
     * Retrieves all repairs associated with the given computer ID.
     *
     * @param computerId The ID of the computer to retrieve repairs for.
     * @return A list of all repairs associated with the given computer ID.
     */
    public List<Repair> getRepairsByComputer(Long computerId) {
        return repairRepository.findByComputerId(computerId);
    }

    /**
     * Completes a repair for the given ID.
     * 
     * @param id The ID of the repair to complete.
     * @return The completed repair object.
     */
    @Transactional
    public Repair completeRepair(Long id) {
        Repair repair = getRepairById(id);
        repair.setStatus("COMPLETED");
        repair.setDateCompleted(LocalDateTime.now());

        // update component status of repaired components
        repair.getRepairedComponents()
                .forEach(component -> componentService.updateComponentStatus(component.getId(), "REPAIRED"));

        return repairRepository.save(repair);
    }
    public List<Repair> getAllRepairs() {
        return repairRepository.findAllRepair();
    }

    /**
     * Retrieves all repairs created within the specified date range.
     *
     * @param start The start date and time of the range.
     * @param end   The end date and time of the range.
     * @return A list of repairs created between the specified start and end dates.
     */

    public List<Repair> getRepairsByDateRange(LocalDateTime start, LocalDateTime end) {
        return repairRepository.findByDateCreatedBetween(start, end);
    }

    /**
     * Retrieves all repairs with a cost greater than the specified minimum cost.
     *
     * @param minCost The minimum cost threshold for repairs to retrieve.
     * @return A list of repairs with a cost exceeding the specified minimum cost.
     */

    public List<Repair> getRepairsAboveCost(Double minCost) {
        return repairRepository.findByCostGreaterThan(minCost);
    }

    /**
     * Retrieves all repairs with the given status.
     * 
     * @param string The status of the repairs to retrieve.
     * @return A list of all repairs with the given status.
     */
    public List<Repair> getRepairsByStatus(String string) {
        return repairRepository.findByStatus(string);
    }
}
