package com.aharon.sensors.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class SoilMoistureReport {

    private Averages averages;
    private List<GraphData> last30Records;

    @Data
    @Builder
    public static class Averages {
        private Double lastYear;
        private Double last90Days;
        private Double last30Days;
    }

}
