public with sharing class DeviceSyncService {

    public static Boolean isItDmJavaAppIntegrationContext = false;

    public static void checkIfItIsDmJavaAppIntegrationContext(List<Device__c> newDevices){
        isItDmJavaAppIntegrationContext = newDevices[0].LastlyModifiedByDMJavaApp__c == true;
        if(isItDmJavaAppIntegrationContext){
            for(Device__c device: newDevices){
                device.LastlyModifiedByDMJavaApp__c = false;
            }
        }
    }
    
    public static List<DeviceDTO> convertDevicesToDTOs(List<Device__c> devices){
        List<DeviceDTO> dtos = new List<DeviceDTO>();
        for(Device__c device: devices){
            dtos.add(new DeviceDTO(device));
        }
        return dtos;
    }

    public static void updateExternalDMAppId(List<DeviceDTO> resDeviceDtos, Map<Id, Device__c> devicesMap){
        List<Device__c> devicesToUpdate = new List<Device__c>();
        for(DeviceDTO deviceDto: resDeviceDtos){
            Device__c device = devicesMap.get(deviceDto.sfId);
            if(device.ExternalDeviceManagerAppId__c != deviceDto.id){
                devicesToUpdate.add(
                    new Device__c(
                        Id = deviceDto.sfId,
                        ExternalDeviceManagerAppId__c = deviceDto.id
                    )
                );
            }
        }
        update devicesToUpdate;
    }

    public static List<Device__c> filterDevicesToSync(List<Device__c> newDevices, Map<Id, Device__c> oldDevicesMap){
        if(oldDevicesMap == null || oldDevicesMap.isEmpty()){
            return newDevices;
        }
        
        List<Device__c> devicesToSync = new List<Device__c>();
        for(Device__c newDevice: newDevices){
            Device__c oldDevice = oldDevicesMap.get(newDevice.Id);
            if(newDevice.Name != oldDevice.Name
                || newDevice.Manufacturer__c != oldDevice.Manufacturer__c
                || newDevice.Price__c != oldDevice.Price__c)
            {
                devicesToSync.add(newDevice);
            }
        }

        return devicesToSync;
    }

    public static void syncDevicesToExternalDMApp(List<Device__c> devicesToSync){
        if(devicesToSync != null && !devicesToSync.isEmpty()){
            DeviceSyncQueueable dsq = new DeviceSyncQueueable(
                new Map<Id, Device__c>(devicesToSync).keySet()
            );

            System.enqueueJob(dsq);
        }
    }



}