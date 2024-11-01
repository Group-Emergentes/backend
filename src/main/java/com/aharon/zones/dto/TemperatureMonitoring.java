package com.aharon.zones.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Builder
@Data
public class TemperatureMonitoring {

    private Averages averages;
    private List<MoistureData> last30Days;

    @Data
    @Builder
    public static class Averages {
        private Double lastYear;
        private Double last90Days;
        private Double last30Days;
    }

    @Data
    @Builder
    public static class MoistureData {
        private Date date;
        private Double moisture;
    }
}
