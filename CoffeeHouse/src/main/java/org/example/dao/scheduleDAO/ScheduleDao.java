package org.example.dao.scheduleDAO;

import org.example.dao.CRUDInterface;
import org.example.model.Schedule;

import java.util.List;

public interface ScheduleDao extends CRUDInterface<Schedule> {
    public List<Schedule> findScheduleByDay(String dayOfWeek);
}
