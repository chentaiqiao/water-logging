package com.example.waterlogging.dto;

import lombok.Data;

@Data
public class WaterLogInfoDTO {
    private Long id;
    private String type; // e.g., "EVENT", "ALERT"
    private String city;
    private String address; // 用于事件
    private PointDTO location;
    private String severity;
    private Integer floodDepthCm;
    private Double floodAreaSqm;
    private String timestamp; // ISO format string
    private String source; // For alerts
    private Boolean resolved; // For alerts

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PointDTO getLocation() {
        return location;
    }

    public void setLocation(PointDTO location) {
        this.location = location;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public Integer getFloodDepthCm() {
        return floodDepthCm;
    }

    public void setFloodDepthCm(Integer floodDepthCm) {
        this.floodDepthCm = floodDepthCm;
    }

    public Double getFloodAreaSqm() {
        return floodAreaSqm;
    }

    public void setFloodAreaSqm(Double floodAreaSqm) {
        this.floodAreaSqm = floodAreaSqm;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Boolean getResolved() {
        return resolved;
    }

    public void setResolved(Boolean resolved) {
        this.resolved = resolved;
    }
}

