package com.example.waterlogging.entity;


import com.example.waterlogging.enums.SourcePlatform;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import org.locationtech.jts.geom.Point; // Use JTS Point


@Entity
@Table(name = "social_media_info")
@Data
@NoArgsConstructor
public class SocialMediaInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SourcePlatform platform;

    @Column(name = "user_id")
    private String userId;

    private String ip;
    private String topic;

    @Column(columnDefinition = "TEXT") // For longer content
    private String content;

    @Column(name = "picture_url")
    private String pictureUrl;

    @Column(name = "video_url")
    private String videoUrl;

    @Column(name = "media_url")
    private String mediaUrl; // Combined or original source URL

    @Column(name = "capture_timestamp")
    private LocalDateTime timestamp = LocalDateTime.now(); // When we captured it

    private String city;

    // --- PostGIS Point ---
    @Column(columnDefinition = "geometry(Point,4326)") // SRID 4326 is common for GPS
    private Point location;
    // --- End PostGIS Point ---

    private String keywords; // e.g., "积水,暴雨"

    @Column(name = "nlp_result", columnDefinition = "TEXT")
    private String nlpResult; // Store NLP analysis summary/JSON

    @Column(name = "cv_result", columnDefinition = "TEXT")
    private String cvResult; // Store CV analysis summary/JSON

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SourcePlatform getPlatform() {
        return platform;
    }

    public void setPlatform(SourcePlatform platform) {
        this.platform = platform;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
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

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getNlpResult() {
        return nlpResult;
    }

    public void setNlpResult(String nlpResult) {
        this.nlpResult = nlpResult;
    }

    public String getCvResult() {
        return cvResult;
    }

    public void setCvResult(String cvResult) {
        this.cvResult = cvResult;
    }
}
