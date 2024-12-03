public class DeviceDTO {

    public String id;
    public String sfId;
    public String manufacturer;
    public String model;
    public Decimal price;
    
    public DeviceDTO(Device__c device) {
        this.id = device.ExternalDeviceManagerAppId__c;
        this.sfId = device.Id;
        this.manufacturer = device.Manufacturer__c;
        this.model = device.Name;
        this.price = device.Price__c;
    }
}