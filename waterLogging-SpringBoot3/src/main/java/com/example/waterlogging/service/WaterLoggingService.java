package com.example.waterlogging.service;

import com.example.waterlogging.dto.SocialMediaInputDTO;
import com.example.waterlogging.dto.WaterLogInfoDTO;
import com.example.waterlogging.entity.*;
import com.example.waterlogging.enums.Severity;
import com.example.waterlogging.repository.*;
import com.example.waterlogging.util.GeometryUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor // Constructor injection via Lombok
@Slf4j // Logging
public class WaterLoggingService {

    private final SocialMediaInfoRepository socialMediaInfoRepository;
    private final WaterEventRepository waterEventRepository;
    private final AlertInfoRepository alertInfoRepository;



    // ---数据提取和处理 ---

    @Transactional // 确保原子性
    public Long processSocialMediaPost(SocialMediaInputDTO dto) {
        log.info("Processing social media post from platform: {}", dto.getPlatform());

        // 1. --- STUB: Crawler ---
        // 假定数据已经被抓取并通过 DTO 传递。
        System.out.println("<<< STUB: Assuming data is already crawled >>>");

        // 2. --- STUB: NLP Analysis ---
        System.out.println("<<< STUB: Calling DeepSeek NLP API for content: " + dto.getContent() + " >>>");
        String nlpAnalysisResult = "{ \"sentiment\": \"negative\", \"keywords\": [\"积水\", \"严重\"], \"estimated_location\": \"" + dto.getCity() + "\"}"; // Simulated result
        System.out.println("<<< STUB: NLP Result: " + nlpAnalysisResult + " >>>");

        // 3. --- STUB: CV Analysis (if image/video exists) ---
        String cvAnalysisResult = null;
        Integer estimatedDepth = null; // From CV
        Double estimatedArea = null; // From CV
        if (dto.getPictureUrl() != null || dto.getVideoUrl() != null) {
            System.out.println("<<< STUB: Calling CV Model for media: " + (dto.getPictureUrl() != null ? dto.getPictureUrl() : dto.getVideoUrl()) + " >>>");
            cvAnalysisResult = "{ \"water_detected\": true, \"estimated_depth_cm\": 35, \"estimated_area_sqm\": 15.5 }"; // Simulated result
            estimatedDepth = 35; // Simulate extracted value
            estimatedArea = 15.5; // Simulate extracted value
            System.out.println("<<< STUB: CV Result: " + cvAnalysisResult + " >>>");
        } else {
            System.out.println("<<< STUB: No media provided for CV analysis >>>");
            // Maybe try to infer depth/severity from text? (Simple example)
            if (dto.getContent() != null && dto.getContent().contains("膝盖")) {
                estimatedDepth = 40; // Rough guess
            } else if (dto.getContent() != null && dto.getContent().contains("脚踝")) {
                estimatedDepth = 15;
            }
        }


        // 4. 创建并保存 SocialMediaInfo 实体
        SocialMediaInfo info = new SocialMediaInfo();
        info.setPlatform(dto.getPlatform());
        info.setUserId(dto.getUserId());
        info.setIp(dto.getIp());
        info.setTopic(dto.getTopic());
        info.setContent(dto.getContent());
        info.setPictureUrl(dto.getPictureUrl());
        info.setVideoUrl(dto.getVideoUrl());
        info.setMediaUrl(dto.getMediaUrl());
        info.setTimestamp(LocalDateTime.now()); // Capture time
        info.setCity(dto.getCity());
        info.setLocation(GeometryUtil.createPoint(dto.getLatitude(), dto.getLongitude()));
        info.setKeywords(dto.getKeywords()); // Pass through or extract via NLP stub
        info.setNlpResult(nlpAnalysisResult);
        info.setCvResult(cvAnalysisResult);

        SocialMediaInfo savedInfo = socialMediaInfoRepository.save(info);
        log.info("Saved SocialMediaInfo with ID: {}", savedInfo.getId());


        // 5. --- STUB：多模态融合与警报检查 ---
        // 结合自然语言处理、计算机视觉和位置数据，判断是否为有效的内涝事件
        // 目前，主要使用预估深度
        Severity severity = determineSeverity(estimatedDepth);
        Point location = savedInfo.getLocation();

        if (severity != Severity.UNKNOWN && location != null) {
            System.out.println("<<< STUB: Potential waterlogging event detected. Severity: " + severity + " Depth: "+ estimatedDepth +" >>>");
            // 如果严重程度为“中”或“高”，则创建警报
            if (severity == Severity.MEDIUM || severity == Severity.HIGH) {
                generateAlert(savedInfo, location, severity, estimatedDepth, estimatedArea);
            }

        } else {
            System.out.println("<<< STUB: 数据未显示发生严重涝灾或缺乏位置/深度. >>>");
        }

        return savedInfo.getId();
    }

    // --- Alert Generation & Notification ---

    private Severity determineSeverity(Integer depthCm) {
        if (depthCm == null) return Severity.UNKNOWN;
        if (depthCm > 50) return Severity.HIGH;
        if (depthCm >= 30) return Severity.MEDIUM; // Doc says 30-50
        if (depthCm >= 10) return Severity.LOW; // Doc says 10-30
        return Severity.UNKNOWN; // Below 10cm
    }

    @Transactional
    public void generateAlert(SocialMediaInfo sourceInfo, Point location, Severity severity, Integer depthCm, Double areaSqm) {
        log.warn("Generating Alert! Severity: {}, Depth: {}cm", severity, depthCm);

        AlertInfo alert = new AlertInfo();
        alert.setEventType("WATERLOGGING");
        alert.setTriggerTime(LocalDateTime.now());
        alert.setResolved(false);
        alert.setCity(sourceInfo.getCity());
        alert.setLocation(location);
        alert.setSource("Social Media: " + sourceInfo.getPlatform() + " (ID: " + sourceInfo.getId() + ")");
        alert.setRelatedEntityType("SocialMediaInfo");
        alert.setRelatedEntityId(sourceInfo.getId());
        alert.setSeverity(severity);
        alert.setFloodDepth(depthCm);
        alert.setFloodArea(areaSqm);

        alertInfoRepository.save(alert);
        log.info("Saved AlertInfo with ID: {}", alert.getId());

        // --- STUB：通知分发 ---
        System.out.println("<<< STUB：正在发送警报 ID 为：" + alert.getId() + " >>> 的通知（短信、Webhook、API、GIS 推送）>>>");
        System.out.println("<<< STUB：严重程度：" + severity + "，位置：" + location.toString() + " >>>");
        // --- STUB：GIS 的 WebSocket 推送 ---
        System.out.println("<<< STUB：正在通过 WebSocket 推送警报 ID 为：" + alert.getId() + " >>>");
    }

    // --- Query Services ---

    public List<WaterLogInfoDTO> getRecentWaterPoints(int limit) {
        // 结合最近的事件和警报，优先处理未解决的警报
        List<AlertInfo> recentAlerts = alertInfoRepository.findByResolvedFalseOrderByTriggerTimeDesc();
        List<WaterEvent> recentEvents = waterEventRepository.findTop10ByOrderByTimestampDesc(); // Example limit

        Stream<WaterLogInfoDTO> alertDtos = recentAlerts.stream().map(this::alertToDto);
        Stream<WaterLogInfoDTO> eventDtos = recentEvents.stream().map(this::eventToDto);

        // 简单的组合和限制
        return Stream.concat(alertDtos, eventDtos)
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<WaterLogInfoDTO> getHistoricalWaterData(LocalDateTime start, LocalDateTime end) {
        List<AlertInfo> alerts = alertInfoRepository.findByTriggerTimeBetween(start, end);
        List<WaterEvent> events = waterEventRepository.findByTimestampBetween(start, end);

        Stream<WaterLogInfoDTO> alertDtos = alerts.stream().map(this::alertToDto);
        Stream<WaterLogInfoDTO> eventDtos = events.stream().map(this::eventToDto);

        return Stream.concat(alertDtos, eventDtos)
                .sorted((d1, d2) -> d2.getTimestamp().compareTo(d1.getTimestamp())) // Sort descending
                .collect(Collectors.toList());
    }

    public List<WaterLogInfoDTO> getWaterDataNearPoint(double latitude, double longitude, double distanceDegrees) {
        Point searchPoint = GeometryUtil.createPoint(latitude, longitude);
        if (searchPoint == null) {
            return List.of();
        }
        List<AlertInfo> alerts = alertInfoRepository.findNearbyUnresolvedAlerts(searchPoint, distanceDegrees);
        List<WaterEvent> events = waterEventRepository.findNearbyEvents(searchPoint, distanceDegrees);

        Stream<WaterLogInfoDTO> alertDtos = alerts.stream().map(this::alertToDto);
        Stream<WaterLogInfoDTO> eventDtos = events.stream().map(this::eventToDto);

        return Stream.concat(alertDtos, eventDtos)
                .sorted((d1, d2) -> d2.getTimestamp().compareTo(d1.getTimestamp())) // Sort descending
                .collect(Collectors.toList());
    }


    // --- DTO Mappers ---
    private WaterLogInfoDTO alertToDto(AlertInfo alert) {
        WaterLogInfoDTO dto = new WaterLogInfoDTO();
        dto.setId(alert.getId());
        dto.setType("ALERT");
        dto.setCity(alert.getCity());
        dto.setLocation(GeometryUtil.pointToDto(alert.getLocation()));
        dto.setSeverity(alert.getSeverity().name());
        dto.setFloodDepthCm(alert.getFloodDepth());
        dto.setFloodAreaSqm(alert.getFloodArea());
        dto.setTimestamp(alert.getTriggerTime().format(DateTimeFormatter.ISO_DATE_TIME));
        dto.setSource(alert.getSource());
        dto.setResolved(alert.isResolved());
        return dto;
    }

    private WaterLogInfoDTO eventToDto(WaterEvent event) {
        WaterLogInfoDTO dto = new WaterLogInfoDTO();
        dto.setId(event.getId());
        dto.setType("EVENT");
        dto.setCity(event.getCity());
        dto.setAddress(event.getAddress());
        dto.setLocation(GeometryUtil.pointToDto(event.getLocation()));
        dto.setSeverity(event.getSeverity().name());
        dto.setFloodDepthCm(event.getFloodDepth());
        dto.setFloodAreaSqm(event.getFloodArea());
        dto.setTimestamp(event.getTimestamp().format(DateTimeFormatter.ISO_DATE_TIME));
        return dto;
    }
}