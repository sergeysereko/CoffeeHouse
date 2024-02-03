package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.sql.Date;
import java.util.Scanner;

public class CafeManagementSystem {

    public static final String INSERTDRINK ="INSERT INTO drinks(name_eng, name_ukr, price)VALUES (?, ?, ?)";
    public static final String INSERTDESSERT ="INSERT INTO desserts(name_eng, name_ukr, price)VALUES (?, ?, ?)";
    public static final String INSERTBARISTA ="INSERT INTO staff(full_name, phone, email, position_id)VALUES (?, ?, ?, 1)";
    public static final String INSERTCONFECTIONER ="INSERT INTO staff(full_name, phone, email, position_id)VALUES (?, ?, ?, 3)";
    public static final String INSERTCUSTOMER ="INSERT INTO customers(full_name, date_of_birth, phone, email, discount)VALUES (?, ?, ?, ?, ?)";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5440/coffee_house",
                "sa", "admin")){

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Выберите, что вы хотите сделать?:");
                System.out.println("1. Добавить новый напиток");
                System.out.println("2. Добавить новый десерт");
                System.out.println("3. Добавить нового бариста");
                System.out.println("4. Добавить нового кондитера");
                System.out.println("5. Добавить нового клиента");
                System.out.println("0. Выход");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        addNewDrink(connection, scanner);
                        break;
                    case 2:
                        addNewDessert(connection, scanner);
                        break;
                    case 3:
                        addNewBarista(connection, scanner);
                        break;
                    case 4:
                        addNewConfectioner(connection, scanner);
                        break;
                    case 5:
                        addNewCustomer(connection, scanner);
                        break;
                    case 0:
                        System.out.println("Программа завершена.");
                        System.exit(0);
                    default:
                        System.out.println("Некорректный выбор. Пожалуйста, выберите снова.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addNewDrink(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Введите название нового напитка English:");
        String name_eng = scanner.next();
        System.out.println("Введите название нового напитка Украинский:");
        String name_ukr = scanner.next();
        System.out.println("Введите стоимость напитка:");
        double price = scanner.nextDouble();

        String query = INSERTDRINK;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name_eng);
            preparedStatement.setString(2, name_ukr);
            preparedStatement.setDouble(3, price);
            preparedStatement.executeUpdate();
            System.out.println("Новый напиток успешно добавлен!");
        }
    }

    private static void  addNewDessert(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Введите название нового десерта English:");
        String name_eng = scanner.next();
        System.out.println("Введите название нового десерта Украинский:");
        String name_ukr = scanner.next();
        System.out.println("Введите стоимость десерта:");
        double price = scanner.nextDouble();

        String query = INSERTDESSERT;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name_eng);
            preparedStatement.setString(2, name_ukr);
            preparedStatement.setDouble(3, price);
            preparedStatement.executeUpdate();
            System.out.println("Новый десерт успешно добавлен!");
        }

    }

    private static void addNewBarista(Connection connection, Scanner scanner) throws SQLException {
        scanner.nextLine();
        System.out.println("Введите ФИО нового Бариста:");
        String full_name = scanner.nextLine();
        System.out.println("Введите мобильный тел. сотрудника:");
        String phone = scanner.next();
        System.out.println("Введите email сотрудника:");
        String email = scanner.next();

        String query = INSERTBARISTA;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, full_name);
            preparedStatement.setString(2, phone);
            preparedStatement.setString(3, email);
            preparedStatement.executeUpdate();
            System.out.println("Новый Бариста успешно добавлен!");
        }

    }

    private static void  addNewConfectioner(Connection connection, Scanner scanner) throws SQLException {
        scanner.nextLine();
        System.out.println("Введите ФИО нового Кондитера:");
        String full_name = scanner.nextLine();
        System.out.println("Введите мобильный тел. сотрудника:");
        String phone = scanner.next();
        System.out.println("Введите email сотрудника:");
        String email = scanner.next();

        String query = INSERTCONFECTIONER;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, full_name);
            preparedStatement.setString(2, phone);
            preparedStatement.setString(3, email);
            preparedStatement.executeUpdate();
            System.out.println("Новый Кондитер успешно добавлен!");
        }

    }

    private static void addNewCustomer(Connection connection, Scanner scanner) throws SQLException {
        scanner.nextLine();
        System.out.println("Введите ФИО нового клиента:");
        String full_name = scanner.nextLine();
        System.out.println("Введите день рождения клиента (в формате ГГГГ-ММ-ДД):");
        String date_of_birthString = scanner.next();
        Date date_of_birth = Date.valueOf(date_of_birthString);
        System.out.println("Введите мобильный тел. клиента:");
        String phone = scanner.next();
        System.out.println("Введите email клиента:");
        String email = scanner.next();
        System.out.println("Введите скидку клиента:");
        Double discount = scanner.nextDouble();

        String query = INSERTCUSTOMER;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, full_name);
            preparedStatement.setDate(2, date_of_birth);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, email);
            preparedStatement.setDouble(5, discount);
            preparedStatement.executeUpdate();
            System.out.println("Новый Клиент успешно добавлен!");
        }

    }


}
