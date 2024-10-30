package com.example.DeviceManager.service;

import com.example.DeviceManager.entity.Device;
import com.example.DeviceManager.repo.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    public List<Device> getAllDevices(){
        return deviceRepository.findAll();
    }

    public Device getDeviceById(String id){
        return deviceRepository.getReferenceById(id);
    }

    public Device saveDevice(Device device){
        return deviceRepository.save(device);
    }

}
