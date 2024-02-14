package org.example.dao.orderDAO;

import org.example.dao.CRUDInterface;
import org.example.model.Order;

import java.util.List;

public interface OrderDao extends CRUDInterface<Order> {
    public List<Order> findOrdersByCustomerId(int customerId);
    public Order findById(int orderId);
    public int getLastOrderId();
    public List<Order> findAllOrdersByWaiterId(int waiterId);
}
