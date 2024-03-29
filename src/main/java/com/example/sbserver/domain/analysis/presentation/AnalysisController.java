package com.example.sbserver.domain.analysis.presentation;

import com.example.sbserver.domain.analysis.presentation.dto.response.QueryFocusGraphResponse;
import com.example.sbserver.domain.analysis.presentation.dto.response.QueryFocusTimeListResponse;
import com.example.sbserver.domain.analysis.service.QueryFocusGraphService;
import com.example.sbserver.domain.analysis.service.QueryFocusTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;

@RequiredArgsConstructor
@RestController
@RequestMapping("/analysis")
public class AnalysisController {
    private final QueryFocusTimeService queryFocusTimeService;
    private final QueryFocusGraphService queryFocusGraphService;

    @GetMapping
    public QueryFocusTimeListResponse queryFocusTimeList(@RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth) {
        return queryFocusTimeService.execute(yearMonth);
    }

    @GetMapping("/graph")
    public QueryFocusGraphResponse queryFocusGraph() {
        return queryFocusGraphService.execute();
    }
}
