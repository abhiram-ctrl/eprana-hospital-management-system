package com.eprana.backend.dto.response;

import lombok.Data;

@Data
public class DashboardStatsResponseDto {

    private long totalPatients;

    private long totalDoctors;

    private long totalAppointments;

    private long totalPrescriptions;

    private long totalReminders;
}