package com.example.waterlogging.repository;

import com.example.waterlogging.entity.WaterEvent;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WaterEventRepository extends JpaRepository<WaterEvent, Long> {

    List<WaterEvent> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

    // PostGIS 查询示例：查找距离某个点一定距离（以米为单位）内的事件
    // 注意：SRID 4326 使用度，ST_DWithin 可以使用相应的单位。
    // 对于米，您可能需要使用地理类型，或者如果数据是平面的，则需要转换 SRID。
    // 为简单起见，本示例假设距离基于度，请根据需要调整单位/函数。
    @Query(value = "SELECT * FROM water_event WHERE ST_DWithin(location, :point, :distance)", nativeQuery = true)
    List<WaterEvent> findNearbyEvents(@Param("point") Point point, @Param("distance") double distance);

    // Find latest N events
    List<WaterEvent> findTop10ByOrderByTimestampDesc();
}