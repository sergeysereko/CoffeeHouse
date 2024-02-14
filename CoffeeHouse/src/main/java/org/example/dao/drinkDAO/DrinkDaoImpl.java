package org.example.dao.drinkDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import org.example.model.Drink;
public class DrinkDaoImpl implements DrinkDao {
    private final Connection connection;

    public DrinkDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Drink drink) {
        try {
            String query = "INSERT INTO Drinks (name_eng, name_ukr, price) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, drink.getNameEng());
                preparedStatement.setString(2, drink.getNameUkr());
                preparedStatement.setDouble(3, drink.getPrice());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveMany(List<Drink> drinks) {
        for (Drink drink : drinks) {
            save(drink);
        }
    }

    @Override
    public void update(Drink drink) {
        try {
            String query = "UPDATE Drinks SET name_eng = ?, name_ukr = ?, price = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, drink.getNameEng());
                preparedStatement.setString(2, drink.getNameUkr());
                preparedStatement.setDouble(3, drink.getPrice());
                preparedStatement.setInt(4, drink.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Drink drink) {
        try {
            String query = "DELETE FROM Drinks WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, drink.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Drink> findAll() {
        List<Drink> drinks = new ArrayList<>();
        try {
            String query = "SELECT * FROM Drinks";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Drink drink = new Drink();
                    drink.setId(resultSet.getInt("id"));
                    drink.setNameEng(resultSet.getString("name_eng"));
                    drink.setNameUkr(resultSet.getString("name_ukr"));
                    drink.setPrice(resultSet.getDouble("price"));
                    drinks.add(drink);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drinks;
    }

    @Override
    public void deleteAll() {
        try {
            String query = "DELETE FROM Drinks";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Drink> findDrink(String nameEng) {
        List<Drink> drinks = new ArrayList<>();
        try {
            String query = "SELECT * FROM Drinks WHERE name_eng = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, nameEng);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Drink drink = new Drink();
                        drink.setId(resultSet.getInt("id"));
                        drink.setNameEng(resultSet.getString("name_eng"));
                        drink.setNameUkr(resultSet.getString("name_ukr"));
                        drink.setPrice(resultSet.getDouble("price"));
                        drinks.add(drink);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drinks;
    }
}
