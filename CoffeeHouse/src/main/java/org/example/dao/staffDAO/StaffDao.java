package org.example.dao.staffDAO;

import org.example.dao.CRUDInterface;
import org.example.model.Dessert;
import org.example.model.Staff;

import java.sql.SQLException;
import java.util.List;

public interface StaffDao extends CRUDInterface<Staff> {
    List<Staff> findStaff(String fullName);
    public List<Staff> findStaffByPosition(int positionId);
}
