package com.example.DeviceManager.service;

import com.example.DeviceManager.entity.Device;
import com.example.DeviceManager.repo.DeviceRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public List<Device> saveDevices(String devicesData) {
        List<Map<String, Object>> listOfDevicesData = null;
        try {
            listOfDevicesData = new ObjectMapper().readValue(devicesData, new TypeReference<List<Map<String, Object>>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        List<Device> devices = new ArrayList<>();

        for(Map<String, Object> deviceDataMap : listOfDevicesData){
            Device d = new Device();
            String strId = (String) deviceDataMap.get("id");
            if(strId != null && !strId.isBlank()){
                d.setId( Long.parseLong(strId) );
            }
            d.setSfId( (String) deviceDataMap.get("sfId") );
            d.setManufacturer( (String) deviceDataMap.get("manufacturer") );
            d.setModel( (String) deviceDataMap.get("model") );
            d.setPrice( (Double) deviceDataMap.get("price") );
            devices.add(d);
        }

        return deviceRepository.saveAll(devices);
    }

}
