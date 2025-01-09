package com.rina.repaire.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rina.repaire.models.Component;

public interface ComponentRepository extends JpaRepository<Component, Long> {
    List<Component> findByComputerId(Long computerId);

    List<Component> findByType(String type);

    List<Component> findByStatus(String status);

    /**
     * Finds components by computer ID and status.
     *
     * @param computerId the computer ID
     * @param status the status of the components to find
     * @return the list of components
     */
    @Query("SELECT c FROM Component c WHERE c.computer.id = :computerId AND c.status = :status")
    List<Component> findByComputerIdAndStatus(@Param("computerId") Long computerId, @Param("status") String status);
}