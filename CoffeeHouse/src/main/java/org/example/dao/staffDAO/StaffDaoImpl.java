package org.example.dao.staffDAO;


import org.example.model.Staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffDaoImpl implements StaffDao {

    private Connection connection;

    public StaffDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Staff staff) {
        String query = "INSERT INTO staff (full_name, phone, email, position_id, is_active) VALUES (?, ?, ?, ?, true)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, staff.getFullName());
            statement.setString(2, staff.getPhone());
            statement.setString(3, staff.getEmail());
            statement.setInt(4, staff.getPositionId());
            statement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveMany(List<Staff> staffList) {
        String query = "INSERT INTO staff (full_name, phone, email, position_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (Staff staff : staffList) {
                statement.setString(1, staff.getFullName());
                statement.setString(2, staff.getPhone());
                statement.setString(3, staff.getEmail());
                statement.setInt(4, staff.getPositionId());
                statement.addBatch();
            }
            statement.executeBatch();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Staff staff) {
        String query = "UPDATE staff SET full_name = ?, phone = ?, email = ?, position_id = ?, is_active = ?, description = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, staff.getFullName());
            statement.setString(2, staff.getPhone());
            statement.setString(3, staff.getEmail());
            statement.setInt(4, staff.getPositionId());
            statement.setBoolean(5, staff.getActive());
            statement.setString(6, staff.getDescription());
            statement.setInt(7, staff.getId());
            statement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Staff staff) {
        String query = "DELETE FROM staff WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, staff.getId());
            statement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Staff> findAll(){
        List<Staff> staffList = new ArrayList<>();
        String query = "SELECT * FROM staff";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Staff staff = extractStaffFromResultSet(resultSet);
                staffList.add(staff);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return staffList;
    }

    @Override
    public void deleteAll() {
        String query = "DELETE FROM staff";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Staff extractStaffFromResultSet(ResultSet resultSet) throws SQLException {
        Staff staff = new Staff();
        staff.setId(resultSet.getInt("id"));
        staff.setFullName(resultSet.getString("full_name"));
        staff.setPhone(resultSet.getString("phone"));
        staff.setEmail(resultSet.getString("email"));
        staff.setPositionId(resultSet.getInt("position_id"));
        staff.setActive(resultSet.getBoolean("is_active"));
        staff.setDescription(resultSet.getString("description"));
        return staff;
    }


    @Override
    public List<Staff> findStaff(String fullName) {
        List<Staff> staffList = new ArrayList<>();
        String query = "SELECT * FROM staff WHERE full_name LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + fullName + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Staff staff = extractStaffFromResultSet(resultSet);
                    staffList.add(staff);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffList;
    }

    @Override
    public List<Staff> findStaffByPosition(int positionId) {
        List<Staff> staffList = new ArrayList<>();
        String query = "SELECT * FROM staff WHERE position_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, positionId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Staff staff = extractStaffFromResultSet(resultSet);
                    staffList.add(staff);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffList;
    }
}
