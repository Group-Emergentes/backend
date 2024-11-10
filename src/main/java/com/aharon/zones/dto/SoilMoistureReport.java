package com.aharon.zones.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class SoilMoistureReport {

    private Averages averages;
    private List<GraphData> last30Days;

    @Data
    @Builder
    public static class Averages {
        private Double lastYear;
        private Double last90Days;
        private Double last30Days;
    }

}
