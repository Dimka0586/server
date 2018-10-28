package com.smarthome.server.controller;

import com.smarthome.server.model.Device;
import com.smarthome.server.repository.DeviceRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController(value = "/device")
@CrossOrigin(origins = "http://localhost:4200")
//@NoArgsConstructor
public class DeviceController {


    @Autowired
    private DeviceRepository deviceRepository;

    @GetMapping
    public String getAllDevices() {
        return "All Devices";
    }


    @PostMapping()
    public Device createDevice(@RequestBody Device device) {
        System.out.println("createDevice");
        return this.deviceRepository.save(device);
    }
}
