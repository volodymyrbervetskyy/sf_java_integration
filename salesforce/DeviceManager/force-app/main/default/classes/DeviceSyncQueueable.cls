public with sharing class DeviceSyncQueueable implements Queueable, Database.AllowsCallouts {

    private Set<Id> deviceIds;

    public DeviceSyncQueueable(Set<Id> deviceIds){
        this.deviceIds = deviceIds;
    }

    public void execute(QueueableContext context) {
        List<Device__c> devicesToSync = [
            SELECT Name, ExternalDeviceManagerAppId__c, Manufacturer__c, Price__c
            FROM Device__c
            WHERE Id IN :deviceIds
        ];

        String requestBody = JSON.serialize(
            DeviceSyncService.convertDevicesToDTOs(devicesToSync)
        );

        HttpRequest req = new HttpRequest();
        req.setEndpoint('callout:DM_Named_Credential/api/device/saveDevices');
        req.setMethod('POST');
        req.setHeader('Content-Type', 'application/json');
        req.setBody(requestBody);

        HTTPResponse res = new Http().send(req);
        String body = res.getBody();

        DeviceSyncService.updateExternalDMAppId( 
            (List<DeviceDTO>) JSON.deserialize(body, List<DeviceDTO>.class),
            new Map<Id, Device__c>(devicesToSync)
        );
    }

}