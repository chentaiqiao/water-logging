package com.example.waterlogging.entity;


import com.example.waterlogging.enums.Severity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


import org.locationtech.jts.geom.Point;



@Entity
@Table(name = "water_event")
@Data
@NoArgsConstructor
public class WaterEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_timestamp") // 事件记录/发生的时间
    private LocalDateTime timestamp;

    @Column(name = "event_duration_minutes")
    private Integer duration; // 持续时间（分钟）

    private String city;
    private String address; // 更具体的位置文本

    @Column(columnDefinition = "geometry(Point,4326)")
    private Point location;

    @Enumerated(EnumType.STRING)
    private Severity severity = Severity.UNKNOWN;

    @Column(name = "flood_depth_cm")
    private Integer floodDepth; // 以厘米为单位

    @Column(name = "flood_area_sqm")
    private Double floodArea; // 平方米

    @Column(name = "picture_url")
    private String pictureUrl;

    @Column(name = "video_url")
    private String videoUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
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

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public Integer getFloodDepth() {
        return floodDepth;
    }

    public void setFloodDepth(Integer floodDepth) {
        this.floodDepth = floodDepth;
    }

    public Double getFloodArea() {
        return floodArea;
    }

    public void setFloodArea(Double floodArea) {
        this.floodArea = floodArea;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
