package org.example.dao.customerDAO;


import org.example.dao.CRUDInterface;
import org.example.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {

    private Connection connection;

    public CustomerDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Customer customer) {
        String query = "INSERT INTO customers (full_name, date_of_birth, phone, email, discount) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, customer.getFullName());
            preparedStatement.setDate(2, (Date) customer.getDateOfBirth());
            preparedStatement.setString(3, customer.getPhone());
            preparedStatement.setString(4, customer.getEmail());
            preparedStatement.setDouble(5, customer.getDiscount());
            preparedStatement.executeUpdate();
            System.out.println("Новый клиент успешно добавлен!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveMany(List<Customer> customers) {
        for (Customer customer : customers) {
            save(customer);
        }
    }

    @Override
    public void update(Customer customer) {
        String query = "UPDATE customers SET date_of_birth = ?, phone = ?, email = ?, discount = ? WHERE full_name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, (Date) customer.getDateOfBirth());
            preparedStatement.setString(2, customer.getPhone());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setDouble(4, customer.getDiscount());
            preparedStatement.setString(5, customer.getFullName());
            preparedStatement.executeUpdate();
            System.out.println("Информация о клиенте успешно обновлена!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Customer customer) {
        String query = "DELETE FROM customers WHERE full_name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, customer.getFullName());
            preparedStatement.executeUpdate();
            System.out.println("Информация о клиенте успешно удалена!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getInt("id"));
                customer.setFullName(resultSet.getString("full_name"));
                customer.setDateOfBirth(resultSet.getDate("date_of_birth"));
                customer.setPhone(resultSet.getString("phone"));
                customer.setEmail(resultSet.getString("email"));
                customer.setDiscount(resultSet.getDouble("discount"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public void deleteAll() {
        String query = "DELETE FROM customers";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
            System.out.println("Вся информация о клиентах успешно удалена!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Customer> findCustomer(String fullName) {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers WHERE full_name LIKE ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "%" + fullName + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Customer customer = new Customer();
                    customer.setId(resultSet.getInt("id"));
                    customer.setFullName(resultSet.getString("full_name"));
                    customer.setDateOfBirth(resultSet.getDate("date_of_birth"));
                    customer.setPhone(resultSet.getString("phone"));
                    customer.setEmail(resultSet.getString("email"));
                    customer.setDiscount(resultSet.getDouble("discount"));
                    customers.add(customer);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
}