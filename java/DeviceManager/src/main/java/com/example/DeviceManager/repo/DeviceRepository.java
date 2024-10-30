package com.example.DeviceManager.repo;

import com.example.DeviceManager.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, String> {
}
