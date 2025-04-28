package com.example.waterlogging.repository;

import com.example.waterlogging.entity.AlertInfo;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AlertInfoRepository extends JpaRepository<AlertInfo, Long> {

    List<AlertInfo> findByTriggerTimeBetween(LocalDateTime start, LocalDateTime end);

    List<AlertInfo> findByResolvedFalseOrderByTriggerTimeDesc();

    // PostGIS 查询示例：查找一定距离内未解决的警报
    @Query(value = "SELECT * FROM alert_info WHERE resolved = false AND ST_DWithin(location, :point, :distance)", nativeQuery = true)
    List<AlertInfo> findNearbyUnresolvedAlerts(@Param("point") Point point, @Param("distance") double distance);

    // 查找最新的 N 个警报
    List<AlertInfo> findTop10ByOrderByTriggerTimeDesc();

}