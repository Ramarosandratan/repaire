package com.rina.repaire.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rina.repaire.models.Computer;
import com.rina.repaire.repositories.ComputerRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ComputerService {
    @Autowired
    private ComputerRepository computerRepository;

/**
 * Saves the specified computer to the repository.
 *
 * @param computer The computer entity to be saved.
 * @return The saved computer entity.
 */

    /**
     * Saves the specified computer to the repository.
     * 
     * @param computer The computer entity to be saved.
     * @return The saved computer entity.
     */
    public Computer saveComputer(Computer computer) {
        return computerRepository.save(computer);
    }

    /**
     * Retrieves a computer by its id.
     * 
     * @param id The id of the computer to retrieve.
     * @return The computer with the given id, or throws an
     *         {@link EntityNotFoundException} if no computer is found with the
     *         given id.
     */
    public Computer getComputerById(Long id) {
        return computerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Computer not found with id: " + id));
    }

    /**
     * Retrieves all computers from the repository.
     * 
     * @return A list of all computers in the repository.
     */
    public List<Computer> getAllComputers() {
        return computerRepository.findAll();
    }

    /**
     * Deletes a computer from the repository by its id.
     * 
     * @param id The id of the computer to delete.
     */
    public void deleteComputer(Long id) {
        computerRepository.deleteById(id);
    }
}
