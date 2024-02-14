package org.example.dao.dessertDAO;

import org.example.dao.CRUDInterface;
import org.example.model.Dessert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DessertDaoImpl implements DessertDao {
    private final Connection connection;

    public DessertDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Dessert dessert) {
        try {
            String query = "INSERT INTO Desserts (name_eng, name_ukr, price) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, dessert.getNameEng());
                preparedStatement.setString(2, dessert.getNameUkr());
                preparedStatement.setDouble(3, dessert.getPrice());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveMany(List<Dessert> desserts) {
        for (Dessert dessert : desserts) {
            save(dessert);
        }
    }

    @Override
    public void update(Dessert dessert) {
        try {
            String query = "UPDATE Desserts SET name_eng = ?, name_ukr = ?, price = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, dessert.getNameEng());
                preparedStatement.setString(2, dessert.getNameUkr());
                preparedStatement.setDouble(3, dessert.getPrice());
                preparedStatement.setInt(4, dessert.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Dessert dessert) {
        try {
            String query = "DELETE FROM Desserts WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, dessert.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Dessert> findAll() {
        List<Dessert> desserts = new ArrayList<>();
        try {
            String query = "SELECT * FROM Desserts";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Dessert dessert = new Dessert();
                    dessert.setId(resultSet.getInt("id"));
                    dessert.setNameEng(resultSet.getString("name_eng"));
                    dessert.setNameUkr(resultSet.getString("name_ukr"));
                    dessert.setPrice(resultSet.getDouble("price"));
                    desserts.add(dessert);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return desserts;
    }

    @Override
    public void deleteAll() {
        try {
            String query = "DELETE FROM Desserts";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Dessert> findDessert(String nameEng) {
        List<Dessert> desserts = new ArrayList<>();
        try {
            String query = "SELECT * FROM Desserts WHERE name_eng = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, nameEng);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Dessert dessert = new Dessert();
                        dessert.setId(resultSet.getInt("id"));
                        dessert.setNameEng(resultSet.getString("name_eng"));
                        dessert.setNameUkr(resultSet.getString("name_ukr"));
                        dessert.setPrice(resultSet.getDouble("price"));
                        desserts.add(dessert);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return desserts;
    }


    @Override
    public void deleteOrderItemsByDessertName(String nameEng) {
        try {
            String query = "DELETE FROM orderitems WHERE dessert_id IN (SELECT id FROM desserts WHERE name_eng = ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, nameEng);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}