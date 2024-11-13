package com.example.DeviceManager.service;

import com.example.DeviceManager.entity.Device;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DeviceSfIntegrationService {

    private final static String DEVICE_SOBJECT = "Device__c";
    private final static String DEVICE_SOBJECT_EXTERNAL_ID = "ExternalDeviceManagerAppId__c";

    private final SfIntegrationService sfIntegrationService;

    @Autowired
    public DeviceSfIntegrationService(SfIntegrationService sfIntegrationService){
        this.sfIntegrationService = sfIntegrationService;
    }

    public void upsertDevice(Device device) {
        String requestBody = createRequestBody(device);
        ResponseEntity<String> response = sfIntegrationService.upsertRecord(
            DEVICE_SOBJECT,
            DEVICE_SOBJECT_EXTERNAL_ID,
            device.getId().toString(),
            requestBody
        );
    }

    private String createRequestBody(Device device) {
        Map<String, Object> bodyDataMap = new HashMap<>();
        bodyDataMap.put("Name", device.getModel());
        bodyDataMap.put("Manufacturer__c", device.getManufacturer());
        bodyDataMap.put("Price__c", device.getPrice());

        String requestBody;
        try {
            requestBody = new ObjectMapper().writeValueAsString(bodyDataMap);
        } catch (JsonProcessingException jsonProcessingException){
            jsonProcessingException.printStackTrace();
            throw new RuntimeException("Error during creation of request body. Please contact the administrator.");
        }

        return requestBody;
    }

}
