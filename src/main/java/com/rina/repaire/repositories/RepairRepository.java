package com.rina.repaire.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rina.repaire.models.Repair;

public interface RepairRepository extends JpaRepository<Repair, Long> {
    List<Repair> findByComputerId(Long computerId);

    List<Repair> findByStatus(String status);

    List<Repair> findByDateCreatedBetween(LocalDateTime start, LocalDateTime end);

    /**
     * Finds all repairs for the given computer with the given status.
     * 
     * @param computerId The id of the computer.
     * @param status     The status of the repairs to find.
     * @return A list of repairs for the given computer with the given status.
     */
    @Query("SELECT r FROM Repair r WHERE r.computer.id = :computerId AND r.status = :status")
    List<Repair> findByComputerIdAndStatus(@Param("computerId") Long computerId, @Param("status") String status);

    /**
     * Finds all repairs with a cost greater than the given minimum cost.
     * 
     * @param minCost The minimum cost of the repairs to find.
     * @return A list of repairs with a cost greater than the given minimum cost.
     */
    @Query("SELECT r FROM Repair r WHERE r.cost > :minCost")
    List<Repair> findByCostGreaterThan(@Param("minCost") double minCost);
}
