package com.eprana.backend.controller;

import com.eprana.backend.dto.response.DashboardRecentResponseDto;
import com.eprana.backend.dto.response.DashboardStatsResponseDto;
import com.eprana.backend.service.DashboardService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(
            DashboardService dashboardService
    ) {
        this.dashboardService = dashboardService;
    }

    @PreAuthorize(
            "hasAnyRole('ADMIN','DOCTOR')"
    )
    @GetMapping("/stats")
    public DashboardStatsResponseDto getStats() {

        return dashboardService.getStats();
    }

    @GetMapping("/recent")
    public List<DashboardRecentResponseDto>
    getRecentActivity() {

        return dashboardService
                .getRecentActivity();
    }

}