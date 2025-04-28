package com.example.waterlogging.repository;

import com.example.waterlogging.entity.AlertInfo;
import com.example.waterlogging.entity.SocialMediaInfo;
import com.example.waterlogging.entity.WaterEvent;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SocialMediaInfoRepository extends JpaRepository<SocialMediaInfo, Long> {
}



