package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Time;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Schedule {
    private int id;
    private int staffId;
    private String dayOfWeek;
    private Time startTime;
    private Time endTime;

}
