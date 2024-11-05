package com.aharon.zones.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

public class IrrigationFrequency {

    @Data
    @Builder
    public static class WaterConsumed {
        private Double lastYear;
        private Double last90Days;
        private Double last30Days;
    }

    @Data
    @Builder
    public static class ConsumedData {
        private Date date;
        private Double moisture;
    }
}
