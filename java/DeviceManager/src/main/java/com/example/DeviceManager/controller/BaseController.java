package com.example.DeviceManager.controller;

import com.example.DeviceManager.entity.Device;
import com.example.DeviceManager.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BaseController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("data", "Hello World !!!");
        return "home";
    }

    @GetMapping("/allDevices")
    public String allDevices(Model model){

        List<Device> devices = deviceService.getAllDevices();

        model.addAttribute("devices", devices);
        return "allDevices";
    }

    @GetMapping("/device/{id}")
    public String device(Model model, @PathVariable String id){

        Device device = deviceService.getDeviceById(id);

        model.addAttribute("device", device);

        return "device";
    }

    @PostMapping("/updateDevice")
    public String updateDevice(@ModelAttribute Device device){
        deviceService.saveDevice(device);
        return "redirect:/device/" + device.getId();
    }

}
