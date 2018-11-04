package com.smarthome.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class Device {

    private String _id;
    private String boardId;
    private String name;
    private String topic;

    /*public Device() {
    }*/

    /*public Device(String deviceId, String name) {
        this.deviceId = deviceId;
        this.name = name;
    }*/

    /*public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "Device{" +
                "_id='" + _id + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }*/
}
