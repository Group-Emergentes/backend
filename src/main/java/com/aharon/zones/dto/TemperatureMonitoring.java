package com.aharon.zones.dto;

import com.aharon.zones.model.GraphData;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class TemperatureMonitoring {

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
