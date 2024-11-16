package com.example.DeviceManager.service;

import com.example.DeviceManager.entity.Device;
import com.example.DeviceManager.repo.DeviceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceSfIntegrationService deviceSfIntegrationService;

    public List<Device> getAllDevices(){
        return deviceRepository.findAll();
    }

    public Device getDeviceById(String id){
        return deviceRepository.getReferenceById(id);
    }

    @Transactional
    public Device saveDevice(Device device){
        Device savedDevice = deviceRepository.save(device);
        deviceSfIntegrationService.upsertDevice(savedDevice);
        return savedDevice;
    }

}
