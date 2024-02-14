package org.example.dao.customerDAO;

import org.example.dao.CRUDInterface;
import org.example.model.Customer;

import java.util.List;

public interface CustomerDao extends CRUDInterface<Customer> {

    List<Customer> findCustomer(String fullName);
}
