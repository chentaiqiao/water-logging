package com.example.waterlogging.controller;

import com.example.waterlogging.dto.ApiResponse;
import com.example.waterlogging.dto.SocialMediaInputDTO;
import com.example.waterlogging.dto.WaterLogInfoDTO;
import com.example.waterlogging.service.WaterLoggingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; // Added for logging
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1") //此控制器中所有端点的基本路径
@RequiredArgsConstructor    // 通过构造函数注入依赖项（例如 WaterLoggingService）
@Slf4j                      // 使用 SLF4J 启用日志记录
public class WaterLoggingController {

    private final WaterLoggingService waterLoggingService; // Service 依赖注入

    // --- 摄取端点（社交媒体示例）---
    // Corresponds to: POST /api/v1/ingest/social
    @PostMapping("/ingest/social")
    public ResponseEntity<ApiResponse<Long>> ingestSocialMedia(@RequestBody SocialMediaInputDTO inputDTO) {
        log.info("收到了提取社交媒体帖子的请求: {}", inputDTO.getPlatform());
        try {
            Long id = waterLoggingService.processSocialMediaPost(inputDTO);
            log.info("成功处理社交媒体帖子，创建 ID 为 {}的实体", id);
            // 资源创建成功返回 201 Created 状态
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(id));
        } catch (Exception e) {
            log.error("处理社交媒体帖子时出错: {}", inputDTO, e);
            // 失败时返回 500 内部服务器错误
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("无法处理社交媒体帖子: " + e.getMessage()));
        }
    }

    // --- GIS / Query Endpoints ---
    //简化热图 - 仅返回最近的点，前端将呈现热图
    // Corresponds to: GET /api/v1/gis/recent_points
    @GetMapping("/gis/recent_points")
    public ResponseEntity<ApiResponse<List<WaterLogInfoDTO>>> getRecentPoints(
            // 请求参数“limit”，默认值为 20
            @RequestParam(defaultValue = "20") int limit) {
        log.info("收到获取最近有限制的供水点的请求: {}", limit);
        try {
            List<WaterLogInfoDTO> points = waterLoggingService.getRecentWaterPoints(limit);
            return ResponseEntity.ok(ApiResponse.success(points));
        } catch (Exception e) {
            log.error("获取最近的供水点时出错", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("无法获取最近的点: " + e.getMessage()));
        }
    }

    // Corresponds to: GET /api/v1/gis/heatmap
    @GetMapping("/gis/heatmap")
    public ResponseEntity<ApiResponse<List<WaterLogInfoDTO>>> getHeatmapData(
            @RequestParam(defaultValue = "100") int limit) {
        log.info("收到了有限制的热图数据请求: {}", limit);
        // 实际上，这可能涉及数据库中的空间聚合
        // 目前，使用与最近点相同的逻辑
        try {
            List<WaterLogInfoDTO> points = waterLoggingService.getRecentWaterPoints(limit);
            return ResponseEntity.ok(ApiResponse.success(points));
        } catch (Exception e) {
            log.error("Error fetching heatmap data", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to fetch heatmap data: " + e.getMessage()));
        }
    }

    // Corresponds to: GET /api/v1/query/history
    @GetMapping("/query/history")
    public ResponseEntity<ApiResponse<List<WaterLogInfoDTO>>> getHistory(
            // 期望 ISO 格式的日期时间（例如，2023-10-27T10:00:00）
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        log.info("收到了 {} 至 {} 之间的历史记录请求", start, end);
        try {
            List<WaterLogInfoDTO> history = waterLoggingService.getHistoricalWaterData(start, end);
            return ResponseEntity.ok(ApiResponse.success(history));
        } catch (Exception e) {
            log.error("获取历史数据时出错", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("无法获取历史记录: " + e.getMessage()));
        }
    }

    // Corresponds to: GET /api/v1/query/location
    @GetMapping("/query/location")
    public ResponseEntity<ApiResponse<List<WaterLogInfoDTO>>> queryByLocation(
            @RequestParam double lat,
            @RequestParam double lon,
            // 距离以度为单位，赤道处每 0.01 度约 1.1 公里
            @RequestParam(defaultValue = "0.01") double distance
    ) {
        log.info("收到查询纬度={}、经度={} 附近位置的请求，距离={}", lat, lon, distance);
        try {
            List<WaterLogInfoDTO> results = waterLoggingService.getWaterDataNearPoint(lat, lon, distance);
            return ResponseEntity.ok(ApiResponse.success(results));
        } catch (Exception e) {
            log.error("按位置查询时出错", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("按位置查询失败：" + e.getMessage()));
        }
    }

    // --- Placeholder Endpoints (Matching Documentation) ---

    // Corresponds to: POST /api/v1/nlp/analyze
    @PostMapping("/nlp/analyze")
    public ResponseEntity<ApiResponse<String>> analyzeText(@RequestBody(required = false) String text) {
        // In a real scenario, you'd likely take text input
        log.warn("<<< STUB Endpoint Hit: /api/v1/nlp/analyze >>>");
        System.out.println("<<< STUB: /api/v1/nlp/analyze called (Placeholder) >>>");
        // Delegate to service stub if needed or handle directly
        return ResponseEntity.ok(ApiResponse.success("NLP analysis placeholder triggered"));
    }

    // Corresponds to: POST /api/v1/cv/detect
    @PostMapping("/cv/detect")
    public ResponseEntity<ApiResponse<String>> detectCv(@RequestBody(required = false) Object imageData) {
        // In a real scenario, you'd likely take image data (e.g., MultipartFile)
        log.warn("<<< STUB Endpoint Hit: /api/v1/cv/detect >>>");
        System.out.println("<<< STUB: /api/v1/cv/detect called (Placeholder) >>>");
        return ResponseEntity.ok(ApiResponse.success("CV detection placeholder triggered"));
    }

    // Corresponds to: POST /api/v1/cv/video
    @PostMapping("/cv/video")
    public ResponseEntity<ApiResponse<String>> analyzeVideo(@RequestBody(required = false) Object videoData) {
        // In a real scenario, you'd likely take video data
        log.warn("<<< STUB Endpoint Hit: /api/v1/cv/video >>>");
        System.out.println("<<< STUB: /api/v1/cv/video called (Placeholder) >>>");
        return ResponseEntity.ok(ApiResponse.success("CV video analysis placeholder triggered"));
    }

    // Corresponds to: GET /api/v1/videoStream
    @GetMapping("/videoStream")
    public ResponseEntity<ApiResponse<String>> streamVideo(@RequestParam(required = false) String sourceId) {
        log.warn("<<< STUB Endpoint Hit: /api/v1/videoStream >>>");
        System.out.println("<<< STUB: /api/v1/videoStream called (Placeholder) >>>");
        // Actual streaming (e.g., RTSP to WebRTC/HLS) is complex and requires different handling
        return ResponseEntity.ok(ApiResponse.success("Video stream placeholder triggered"));
    }

    // Corresponds to: GET /api/v1/DB/waterDepth
    // Note: This functionality is likely better served by /query/location or a specific event query
    @GetMapping("/DB/waterDepth")
    public ResponseEntity<ApiResponse<String>> getWaterDepth(@RequestParam(required = false) Long locationId) {
        log.warn("<<< STUB Endpoint Hit: /api/v1/DB/waterDepth - Consider using /query/location >>>");
        System.out.println("<<< STUB: /api/v1/DB/waterDepth called (Placeholder) >>>");
        return ResponseEntity.ok(ApiResponse.success("Water depth query placeholder triggered (use /query/location?)"));
    }

    // Corresponds to: POST /api/v1/DB/alert
    // Note: Alert generation is handled internally by the ingestion process in this design
    @PostMapping("/DB/alert")
    public ResponseEntity<ApiResponse<String>> triggerAlert(@RequestBody(required = false) Object alertData) {
        log.warn("<<< STUB Endpoint Hit: /api/v1/DB/alert - Alerting handled by ingestion logic >>>");
        System.out.println("<<< STUB: /api/v1/DB/alert called (Placeholder) >>>");
        return ResponseEntity.ok(ApiResponse.success("Alert trigger placeholder triggered (handled by ingestion logic)"));
    }

    // Corresponds to: GET /api/v1/DB/history
    // Note: This functionality is served by /query/history
    @GetMapping("/DB/history")
    public ResponseEntity<ApiResponse<String>> getDbHistory(@RequestParam(required = false) String criteria) {
        log.warn("<<< STUB Endpoint Hit: /api/v1/DB/history - Use /query/history instead >>>");
        System.out.println("<<< STUB: /api/v1/DB/history called (Placeholder) >>>");
        return ResponseEntity.ok(ApiResponse.success("DB history placeholder triggered (use /query/history)"));
    }
}