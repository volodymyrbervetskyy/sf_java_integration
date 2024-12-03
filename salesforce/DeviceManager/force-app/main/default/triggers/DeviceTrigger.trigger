trigger DeviceTrigger on Device__c (before insert, before update, after insert, after update) {
    if(Trigger.isBefore){
        if(Trigger.isInsert || Trigger.isUpdate){
            DeviceSyncService.checkIfItIsDmJavaAppIntegrationContext(Trigger.new);
        }
    } else {
        if(Trigger.isInsert || Trigger.isUpdate){
            if(!DeviceSyncService.isItDmJavaAppIntegrationContext ){
                DeviceSyncService.syncDevicesToExternalDMApp(
                    DeviceSyncService.filterDevicesToSync(Trigger.new, Trigger.oldMap)
                );
            }
        }
    }
}