package com.rina.repaire.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rina.repaire.models.Computer;

@Repository
public interface ComputerRepository extends JpaRepository<Computer, Long> {
    List<Computer> findByBrand(String brand);

    List<Computer> findByModel(String model);

    Computer findBySerialNumber(String serialNumber);
}
