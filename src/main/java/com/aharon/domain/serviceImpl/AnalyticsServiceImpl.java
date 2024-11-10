package com.aharon.domain.serviceImpl;

import com.aharon.models.entities.HumidityHistory;
import com.aharon.models.entities.TemperatureHistory;
import com.aharon.sensors.repository.HumidityHistoryRepository;
import com.aharon.sensors.repository.TemperatureRegisterRepository;
import com.aharon.zones.dto.GraphData;
import com.aharon.zones.dto.SoilMoistureReport;
import com.aharon.zones.service.AnalyticsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private static final int DAYS_IN_YEAR = 365;
    private static final int DAYS_IN_90_DAYS = 90;
    private static final int DAYS_IN_30_DAYS = 30;

    private final TemperatureRegisterRepository temperatureRegisterRepository;
    private final HumidityHistoryRepository humidityHistoryRepository;

    @Override
    public SoilMoistureReport getSoilMoistureReport(Long zoneId) {

        Double lastYearAverage = calculateAverageForPeriod(DAYS_IN_YEAR, zoneId);
        Double last90DaysAverage = calculateAverageForPeriod(DAYS_IN_90_DAYS, zoneId);
        Double last30DaysAverage = calculateAverageForPeriod(DAYS_IN_30_DAYS, zoneId);

        List<GraphData> last30DaysData = getLast30DaysData(zoneId);

        SoilMoistureReport.Averages averages = SoilMoistureReport.Averages.builder()
                .lastYear(lastYearAverage)
                .last90Days(last90DaysAverage)
                .last30Days(last30DaysAverage)
                .build();

        return SoilMoistureReport.builder()
                .averages(averages)
                .last30Days(last30DaysData)
                .build();
    }

    private Double calculateAverageForPeriod(int days, Long zoneId ) {
        Date endDate = new Date();
        Date startDate = getDateDaysAgo(days);

        List<HumidityHistory> humidityHistoryList =
                humidityHistoryRepository.findHumidityRecordsWithinDateRangeAndZone(startDate, endDate, zoneId);

        return humidityHistoryList.stream()
                .mapToDouble(HumidityHistory::getValue)
                .average()
                .orElse(0.0);
    }

    private List<GraphData> getLast30DaysData(Long zoneId) {
        Date endDate = new Date();
        Date startDate = getDateDaysAgo(DAYS_IN_30_DAYS);

        List<HumidityHistory> humidityHistoryList =
                humidityHistoryRepository.findHumidityRecordsWithinDateRangeAndZone(startDate, endDate, zoneId);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Map<String, Optional<HumidityHistory>> maxByDate = humidityHistoryList.stream()
                .collect(Collectors.groupingBy(
                        history -> dateFormat.format(history.getRegisterDate()),
                        Collectors.maxBy(Comparator.comparing(HumidityHistory::getValue))
                ));

        return maxByDate.values().stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(history -> GraphData.builder()
                        .date(history.getRegisterDate())
                        .value(history.getValue())
                        .build())
                .collect(Collectors.toList());
    }

    private Date getDateDaysAgo(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -days);
        return calendar.getTime();
    }

}

