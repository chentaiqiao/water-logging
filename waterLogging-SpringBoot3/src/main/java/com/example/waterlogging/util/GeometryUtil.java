package com.example.waterlogging.util;

import com.example.waterlogging.dto.PointDTO;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Point;


public class GeometryUtil {

    // SRID 4326 is standard for WGS84 GPS coordinates
    private static final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    public static Point createPoint(Double latitude, Double longitude) {
        if (latitude == null || longitude == null) {
            return null;
        }
        // IMPORTANT: JTS and PostGIS usually expect (longitude, latitude) order
        return geometryFactory.createPoint(new Coordinate(longitude, latitude));
    }

    public static PointDTO pointToDto(Point point) {
        if (point == null) {
            return null;
        }
        PointDTO dto = new PointDTO();
        dto.setLatitude(point.getY());
        dto.setLongitude(point.getX());
        return dto;
    }
}
