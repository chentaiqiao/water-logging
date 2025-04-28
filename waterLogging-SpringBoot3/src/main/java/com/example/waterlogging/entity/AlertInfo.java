package com.example.waterlogging.entity;

import com.example.waterlogging.enums.Severity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;
@Entity
@Table(name = "alert_info")
@Data
@NoArgsConstructor
public class AlertInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_type")
    private String eventType = "WATERLOGGING"; // 如果存在更多类型，则可以是枚举

    @Column(name = "trigger_time")
    private LocalDateTime triggerTime = LocalDateTime.now();

    private boolean resolved = false;

    @Column(name = "resolution_time")
    private LocalDateTime resolutionTime;

    private String city;

    @Column(columnDefinition = "geometry(Point,4326)")
    private Point location;

    @Column(name = "source_description")
    private String source; // 来源的文字描述

    @Column(name = "related_entity_type") // 例如，“SocialMediaInfo”，“WaterEvent”
    private String relatedEntityType;

    @Column(name = "related_entity_id") // 相关实体的ID
    private Long relatedEntityId;

    @Enumerated(EnumType.STRING)
    private Severity severity = Severity.UNKNOWN;

    @Column(name = "flood_depth_cm")
    private Integer floodDepth; // In centimeters

    @Column(name = "flood_area_sqm")
    private Double floodArea; // In square meters

    // 如果点位置使用得当，GISCoordinates 可能会冗余
    // private String gisCoordinates;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public LocalDateTime getTriggerTime() {
        return triggerTime;
    }

    public void setTriggerTime(LocalDateTime triggerTime) {
        this.triggerTime = triggerTime;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public LocalDateTime getResolutionTime() {
        return resolutionTime;
    }

    public void setResolutionTime(LocalDateTime resolutionTime) {
        this.resolutionTime = resolutionTime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRelatedEntityType() {
        return relatedEntityType;
    }

    public void setRelatedEntityType(String relatedEntityType) {
        this.relatedEntityType = relatedEntityType;
    }

    public Long getRelatedEntityId() {
        return relatedEntityId;
    }

    public void setRelatedEntityId(Long relatedEntityId) {
        this.relatedEntityId = relatedEntityId;
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
}
