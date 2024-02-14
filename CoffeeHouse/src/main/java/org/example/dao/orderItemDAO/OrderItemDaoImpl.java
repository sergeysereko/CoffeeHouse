package org.example.dao.orderItemDAO;

import org.example.model.OrderItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDaoImpl implements OrderItemDao {

    private Connection connection;

    public OrderItemDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(OrderItem orderItem) throws SQLException {
        String query = "INSERT INTO orderitems (order_id, item_type, item_id, quantity) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, orderItem.getOrderId());
            preparedStatement.setString(2, orderItem.getItemType());
            preparedStatement.setInt(3, orderItem.getItemtId());
            preparedStatement.setInt(4, orderItem.getQuantity());
            preparedStatement.executeUpdate();
        }
    }


    @Override
    public void saveMany(List<OrderItem> orderItems) {
        String query = "INSERT INTO orderitems (order_id, item_type, item_id, quantity) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (OrderItem orderItem : orderItems) {
                preparedStatement.setInt(1, orderItem.getOrderId());
                preparedStatement.setString(2, orderItem.getItemType());
                preparedStatement.setInt(3, orderItem.getItemtId());
                preparedStatement.setInt(4, orderItem.getQuantity());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(OrderItem orderItem) {
        String query = "UPDATE orderitems SET order_id = ?, item_type = ?, item_id = ?, quantity = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, orderItem.getOrderId());
            preparedStatement.setString(2, orderItem.getItemType());
            preparedStatement.setInt(3, orderItem.getItemtId());
            preparedStatement.setInt(4, orderItem.getQuantity());
            preparedStatement.setInt(5, orderItem.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(OrderItem orderItem) {
        String query = "DELETE FROM orderitems WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, orderItem.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OrderItem> findAll() {
        List<OrderItem> orderItems = new ArrayList<>();
        String query = "SELECT * FROM orderitems";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setId(resultSet.getInt("id"));
                orderItem.setOrderId(resultSet.getInt("order_id"));
                orderItem.setItemType(resultSet.getString("item_type"));
                orderItem.setItemtId(resultSet.getInt("item_id"));
                orderItem.setQuantity(resultSet.getInt("quantity"));
                orderItems.add(orderItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItems;
    }

    @Override
    public void deleteAll() {
        String query = "DELETE FROM orderitems";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OrderItem> findOrderItemsByDessertId(int dessertId) {
        List<OrderItem> orderItems = new ArrayList<>();
        String query = "SELECT * FROM orderitems WHERE item_type = 'dessert' AND item_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, dessertId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setId(resultSet.getInt("id"));
                    orderItem.setOrderId(resultSet.getInt("order_id"));
                    orderItem.setItemType(resultSet.getString("item_type"));
                    orderItem.setItemtId(resultSet.getInt("item_id"));
                    orderItem.setQuantity(resultSet.getInt("quantity"));
                    orderItems.add(orderItem);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItems;
    }

    @Override
    public List<OrderItem> findOrderItemsByOrderId(int orderId) {
        List<OrderItem> orderItems = new ArrayList<>();
        String query = "SELECT * FROM orderitems WHERE order_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, orderId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setId(resultSet.getInt("id"));
                    orderItem.setOrderId(resultSet.getInt("order_id"));
                    orderItem.setItemType(resultSet.getString("item_type"));
                    orderItem.setItemtId(resultSet.getInt("item_id"));
                    orderItem.setQuantity(resultSet.getInt("quantity"));
                    orderItems.add(orderItem);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItems;
    }

    @Override
    public void deleteOrderItemsByOrderId(int orderId) {
        String query = "DELETE FROM orderitems WHERE order_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, orderId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
