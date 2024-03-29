package com.example.sbserver.domain.analysis.service;

import com.example.sbserver.domain.analysis.presentation.dto.response.QueryFocusGraphResponse;
import com.example.sbserver.domain.record.domain.repository.RecordRepository;
import com.example.sbserver.domain.user.domain.User;
import com.example.sbserver.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class QueryFocusGraphService {
    private final RecordRepository recordRepository;
    private final UserFacade userFacade;

    @Transactional
    public QueryFocusGraphResponse execute() {
        LocalDateTime today = LocalDateTime.now();
        User user = userFacade.getCurrentUser();

        Integer thisFocusedTime = recordRepository.findFocusedTimeByLocalDateAndUser(today, user);
        Integer lastFocusedTime = recordRepository.findFocusedTimeByLocalDateAndUser(today.minusDays(30), user);

        lastFocusedTime = lastFocusedTime == 0 ? 1 : lastFocusedTime;

        boolean isThisFocusedTimeBig = thisFocusedTime > lastFocusedTime;

        int growthPercent = isThisFocusedTimeBig ?
                (int) ((float) thisFocusedTime / (float) lastFocusedTime * 100) : 0;

        Integer increasedTime = isThisFocusedTimeBig ?
                (thisFocusedTime - lastFocusedTime) / 60 : 0;

        return QueryFocusGraphResponse.builder()
                .thisMonth(today.getMonth())
                .lastMonth(today.minusMonths(1).getMonth())
                .growthPercent(Math.min(growthPercent, 100)) //100%가 한도임
                .increasedTime(increasedTime)
                .build();
    }
}
