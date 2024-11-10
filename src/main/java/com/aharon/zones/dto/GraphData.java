package com.aharon.zones.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class GraphData {
    private Date date;
    private Double moisture;
}
