package org.example.dao.scheduleDAO;

import org.example.dao.CRUDInterface;
import org.example.model.Schedule;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDaoImpl implements ScheduleDao {

    private Connection connection;

    public ScheduleDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Schedule schedule) throws SQLException {
        String query = "INSERT INTO schedule (staff_id, day_of_week, start_time, end_time) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, schedule.getStaffId());
            preparedStatement.setString(2, schedule.getDayOfWeek());
            preparedStatement.setTime(3, schedule.getStartTime());
            preparedStatement.setTime(4, schedule.getEndTime());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void saveMany(List<Schedule> schedules) {
        String query = "INSERT INTO schedule (staff_id, day_of_week, start_time, end_time) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (Schedule schedule : schedules) {
                preparedStatement.setInt(1, schedule.getStaffId());
                preparedStatement.setString(2, schedule.getDayOfWeek());
                preparedStatement.setTime(3, schedule.getStartTime());
                preparedStatement.setTime(4, schedule.getEndTime());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Schedule schedule) {
        String query = "UPDATE schedule SET staff_id = ?, day_of_week = ?, start_time = ?, end_time = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, schedule.getStaffId());
            preparedStatement.setString(2, schedule.getDayOfWeek());
            preparedStatement.setTime(3, schedule.getStartTime());
            preparedStatement.setTime(4, schedule.getEndTime());
            preparedStatement.setInt(5, schedule.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Schedule schedule) {
        String query = "DELETE FROM schedule WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, schedule.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Schedule> findAll() {
        List<Schedule> schedules = new ArrayList<>();
        String query = "SELECT * FROM schedule";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Schedule schedule = new Schedule();
                schedule.setId(resultSet.getInt("id"));
                schedule.setStaffId(resultSet.getInt("staff_id"));
                schedule.setDayOfWeek(resultSet.getString("day_of_week"));
                schedule.setStartTime(resultSet.getTime("start_time"));
                schedule.setEndTime(resultSet.getTime("end_time"));
                schedules.add(schedule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
    }

    @Override
    public void deleteAll() {
        String query = "DELETE FROM schedule";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Schedule> findScheduleByDay(String dayOfWeek) {
        List<Schedule> schedules = new ArrayList<>();
        String query = "SELECT * FROM schedule WHERE day_of_week = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, dayOfWeek);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Schedule schedule = new Schedule();
                    schedule.setId(resultSet.getInt("id"));
                    schedule.setStaffId(resultSet.getInt("staff_id"));
                    schedule.setDayOfWeek(resultSet.getString("day_of_week"));
                    schedule.setStartTime(resultSet.getTime("start_time"));
                    schedule.setEndTime(resultSet.getTime("end_time"));
                    schedules.add(schedule);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
    }

}
