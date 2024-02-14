package org.example.dao.orderDAO;

import org.example.dao.CRUDInterface;
import org.example.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {

    private Connection connection;

    public OrderDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Order order) throws SQLException {
        String query = "INSERT INTO orders (customer_id, waiter_id, order_date, total_amount) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, order.getCustomerId());
            preparedStatement.setInt(2, order.getWaiterId());
            preparedStatement.setDate(3, new java.sql.Date(order.getOrderDate().getTime()));
            preparedStatement.setDouble(4, order.getTotalAmount());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void saveMany(List<Order> orders) {
        String query = "INSERT INTO orders (customer_id, waiter_id, order_date, total_amount) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (Order order : orders) {
                preparedStatement.setInt(1, order.getCustomerId());
                preparedStatement.setInt(2, order.getWaiterId());
                preparedStatement.setDate(3, new java.sql.Date(order.getOrderDate().getTime()));
                preparedStatement.setDouble(4, order.getTotalAmount());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Order order) {
        String query = "UPDATE orders SET customer_id = ?, waiter_id = ?, order_date = ?, total_amount = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, order.getCustomerId());
            preparedStatement.setInt(2, order.getWaiterId());
            preparedStatement.setDate(3, new java.sql.Date(order.getOrderDate().getTime()));
            preparedStatement.setDouble(4, order.getTotalAmount());
            preparedStatement.setInt(5, order.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Order order) {
        String query = "DELETE FROM orders WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, order.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt("id"));
                order.setCustomerId(resultSet.getInt("customer_id"));
                order.setWaiterId(resultSet.getInt("waiter_id"));
                order.setOrderDate(resultSet.getDate("order_date"));
                order.setTotalAmount(resultSet.getDouble("total_amount"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public void deleteAll() {
        String query = "DELETE FROM orders";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Order> findOrdersByCustomerId(int customerId) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE customer_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customerId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = new Order();
                    order.setId(resultSet.getInt("id"));
                    order.setCustomerId(resultSet.getInt("customer_id"));
                    order.setWaiterId(resultSet.getInt("waiter_id"));
                    order.setOrderDate(resultSet.getDate("order_date"));
                    order.setTotalAmount(resultSet.getDouble("total_amount"));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public Order findById(int orderId) {
        String query = "SELECT * FROM orders WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, orderId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Order order = new Order();
                    order.setId(resultSet.getInt("id"));
                    order.setCustomerId(resultSet.getInt("customer_id"));
                    order.setWaiterId(resultSet.getInt("waiter_id"));
                    order.setOrderDate(resultSet.getDate("order_date"));
                    order.setTotalAmount(resultSet.getDouble("total_amount"));
                    return order;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getLastOrderId() {
        int lastOrderId = -1;
        String query = "SELECT id FROM orders ORDER BY id DESC LIMIT 1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                lastOrderId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastOrderId;
    }


    @Override
    public List<Order> findAllOrdersByWaiterId(int waiterId) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE waiter_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, waiterId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = new Order();
                    order.setId(resultSet.getInt("id"));
                    order.setCustomerId(resultSet.getInt("customer_id"));
                    order.setWaiterId(resultSet.getInt("waiter_id"));
                    order.setOrderDate(resultSet.getDate("order_date"));
                    order.setTotalAmount(resultSet.getDouble("total_amount"));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

}
