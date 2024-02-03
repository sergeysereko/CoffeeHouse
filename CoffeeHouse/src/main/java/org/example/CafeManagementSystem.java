package org.example;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.Scanner;

public class CafeManagementSystem {

    public static final String INSERT_DRINK = "INSERT INTO drinks(name_eng, name_ukr, price)VALUES (?, ?, ?)";
    public static final String INSERT_DESSERT = "INSERT INTO desserts(name_eng, name_ukr, price)VALUES (?, ?, ?)";
    public static final String INSERT_BARISTA = "INSERT INTO staff(full_name, phone, email, position_id)VALUES (?, ?, ?, 1)";
    public static final String INSERT_CONFECTIONER = "INSERT INTO staff(full_name, phone, email, position_id)VALUES (?, ?, ?, 3)";
    public static final String INSERT_CUSTOMER = "INSERT INTO customers(full_name, date_of_birth, phone, email, discount)VALUES (?, ?, ?, ?, ?)";
    public static final String UPDATE_DRINK_PRICE = "UPDATE drinks SET price = ? WHERE name_eng = ?";
    public static final String UPDATE_CONFECTIONER_CONTACTS = "UPDATE staff SET email = ? WHERE position_id = 3 AND full_name = ?";
    public static final String UPDATE_BARISTA_PHONE = "UPDATE staff SET phone = ? WHERE position_id = 1 AND full_name = ?";
    public static final String UPDATE_CUSTOMER_DISCOUNT = "UPDATE customers SET discount = ? WHERE full_name = ?";
    public static final String DELETE_DESSERT = "DELETE FROM desserts WHERE name_eng = ?";
    public static final String DEACTIVATE_WAITER = "UPDATE staff SET is_active = false, description = ? WHERE position_id = 2 AND full_name = ?";
    public static final String DEACTIVATE_BARISTA = "UPDATE staff SET is_active = false, description = ? WHERE position_id = 1 AND full_name = ?";
    public static final String DELETE_CUSTOMER = "DELETE FROM customers WHERE full_name = ?";
    public static final String SELECT_ALL_DRINKS = "SELECT * FROM drinks";
    public static final String SELECT_ALL_DESSERTS = "SELECT * FROM desserts";
    public static final String SELECT_ALL_BARISTAS = "SELECT * FROM staff WHERE position_id = 1";
    public static final String SELECT_ALL_WAITERS = "SELECT * FROM staff WHERE position_id = 2";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5440/coffee_house",
                "sa", "admin")) {

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Выберите, что вы хотите сделать?:");
                System.out.println("1. Добавить новый напиток");
                System.out.println("2. Добавить новый десерт");
                System.out.println("3. Добавить нового бариста");
                System.out.println("4. Добавить нового кондитера");
                System.out.println("5. Добавить нового клиента");
                System.out.println("6. Изменить цену на определенный вид кофе/напитка");
                System.out.println("7. Изменить почтовый адрес кондитеру");
                System.out.println("8. Изменить контактный телефон бариста");
                System.out.println("9. Изменить процент скидки клиента.");
                System.out.println("10. Удалить информацию о десерте.");
                System.out.println("11. Деактивировать официанта по причине увольнения.");
                System.out.println("12. Деактивировать бариста по причине увольнения.");
                System.out.println("13. Удалить информацию о клиенте.");
                System.out.println("14. Показать все напитки");
                System.out.println("15. Показать все десерты");
                System.out.println("16. Показать всех бариста");
                System.out.println("17. Показать всех официантов");
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
                    case 6:
                        updateDrinkPrice(connection, scanner);
                        break;
                    case 7:
                        updateEmailConfectioner(connection, scanner);
                        break;
                    case 8:
                        updateBaristaPhone(connection, scanner);
                        break;
                    case 9:
                        updateCustomerDiscount(connection, scanner);
                        break;
                    case 10:
                        deleteDessert(connection, scanner);
                        break;
                    case 11:
                        deactivateWaiter(connection, scanner);
                        break;
                    case 12:
                        deactivateBarista(connection, scanner);
                        break;
                    case 13:
                        deleteCustomer(connection, scanner);
                        break;
                    case 14:
                        showAllDrinks(connection);
                        break;
                    case 15:
                        showAllDesserts(connection);
                        break;
                    case 16:
                        showAllBaristas(connection);
                        break;
                    case 17:
                        showAllWaiters(connection);
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

        String query = INSERT_DRINK;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name_eng);
            preparedStatement.setString(2, name_ukr);
            preparedStatement.setDouble(3, price);
            preparedStatement.executeUpdate();
            System.out.println("Новый напиток успешно добавлен!");
        }
    }

    private static void addNewDessert(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Введите название нового десерта English:");
        String name_eng = scanner.next();
        System.out.println("Введите название нового десерта Украинский:");
        String name_ukr = scanner.next();
        System.out.println("Введите стоимость десерта:");
        double price = scanner.nextDouble();

        String query = INSERT_DESSERT;
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

        String query = INSERT_BARISTA;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, full_name);
            preparedStatement.setString(2, phone);
            preparedStatement.setString(3, email);
            preparedStatement.executeUpdate();
            System.out.println("Новый Бариста успешно добавлен!");
        }

    }

    private static void addNewConfectioner(Connection connection, Scanner scanner) throws SQLException {
        scanner.nextLine();
        System.out.println("Введите ФИО нового Кондитера:");
        String full_name = scanner.nextLine();
        System.out.println("Введите мобильный тел. сотрудника:");
        String phone = scanner.next();
        System.out.println("Введите email сотрудника:");
        String email = scanner.next();

        String query = INSERT_CONFECTIONER;
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

        String query = INSERT_CUSTOMER;
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

    private static void updateDrinkPrice(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Введите название напитка для изменения цены (English):");
        String name_eng = scanner.next();
        System.out.println("Введите новую цену для напитка:");
        double newPrice = scanner.nextDouble();

        String query = UPDATE_DRINK_PRICE;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, newPrice);
            preparedStatement.setString(2, name_eng);
            preparedStatement.executeUpdate();
            System.out.println("Цена напитка успешно изменена!");
        }
    }

    private static void updateEmailConfectioner(Connection connection, Scanner scanner) throws SQLException {
        scanner.nextLine();
        System.out.println("Введите ФИО кондитера для изменения контактов:");
        String full_name = scanner.nextLine();
        System.out.println("Введите новый email кондитера:");
        String newEmail = scanner.next();

        String query = UPDATE_CONFECTIONER_CONTACTS;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newEmail);
            preparedStatement.setString(2, full_name);
            preparedStatement.executeUpdate();
            System.out.println("Контакты кондитера успешно изменены!");
        }
    }

    private static void updateBaristaPhone(Connection connection, Scanner scanner) throws SQLException {
        scanner.nextLine();
        System.out.println("Введите ФИО бариста для изменения контактного телефона:");
        String full_name = scanner.nextLine();
        System.out.println("Введите новый мобильный телефон бариста:");
        String newPhone = scanner.next();

        String query = UPDATE_BARISTA_PHONE;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newPhone);
            preparedStatement.setString(2, full_name);
            preparedStatement.executeUpdate();
            System.out.println("Контактный телефон бариста успешно изменен!");
        }
    }

    private static void updateCustomerDiscount(Connection connection, Scanner scanner) throws SQLException {
        scanner.nextLine();
        System.out.println("Введите ФИО клиента для изменения процента скидки:");
        String full_name = scanner.nextLine();
        System.out.println("Введите новый процент скидки для клиента:");
        double newDiscount = scanner.nextDouble();

        String query = UPDATE_CUSTOMER_DISCOUNT;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, newDiscount);
            preparedStatement.setString(2, full_name);
            preparedStatement.executeUpdate();
            System.out.println("Процент скидки клиента успешно изменен!");
        }
    }


    private static void deleteDessert(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Введите название десерта для удаления:");
        String name_eng = scanner.next();


        String deleteOrderItems = "DELETE FROM orderitems WHERE dessert_id IN (SELECT id FROM desserts WHERE name_eng = ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteOrderItems)) {
            preparedStatement.setString(1, name_eng);
            preparedStatement.executeUpdate();
        }


        String query = DELETE_DESSERT;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name_eng);
            preparedStatement.executeUpdate();
            System.out.println("Информация о десерте успешно удалена!");
        }
    }

    private static void deactivateWaiter(Connection connection, Scanner scanner) throws SQLException {
        scanner.nextLine();
        System.out.println("Введите ФИО официанта для увольнения:");
        String full_name = scanner.nextLine();
        System.out.println("Введите причину увольнения:");
        String reason = scanner.nextLine();

        String query = DEACTIVATE_WAITER;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, reason);
            preparedStatement.setString(2, full_name);
            preparedStatement.executeUpdate();
            System.out.println("Информация об официанте успешно удалена!");
        }
    }

    private static void deactivateBarista(Connection connection, Scanner scanner) throws SQLException {
        scanner.nextLine();
        System.out.println("Введите ФИО бариста для увольнения:");
        String full_name = scanner.nextLine();
        System.out.println("Введите причину увольнения:");
        String reason = scanner.nextLine();

        String query = DEACTIVATE_BARISTA;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, reason);
            preparedStatement.setString(2, full_name);
            preparedStatement.executeUpdate();
            System.out.println("Информация о баристе успешно удалена!");
        }
    }


    private static void deleteCustomer(Connection connection, Scanner scanner) throws SQLException {
        scanner.nextLine();
        System.out.println("Введите ФИО клиента для удаления:");
        String customerFullName = scanner.nextLine();

        //  удаляем все связанные записи в таблице orderitems
        String deleteOrderItems = "DELETE FROM orderitems WHERE order_id IN (SELECT id FROM orders WHERE customer_id IN (SELECT id FROM customers WHERE full_name = ?))";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteOrderItems)) {
            preparedStatement.setString(1, customerFullName);
            preparedStatement.executeUpdate();
        }

        //  удаляем все связанные заказы
        String deleteOrders = "DELETE FROM orders WHERE customer_id IN (SELECT id FROM customers WHERE full_name = ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteOrders)) {
            preparedStatement.setString(1, customerFullName);
            preparedStatement.executeUpdate();
        }


        String query = DELETE_CUSTOMER;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, customerFullName);
            preparedStatement.executeUpdate();
            System.out.println("Информация о клиенте успешно удалена!");
        }
    }

    private static void showAllDrinks(Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_DRINKS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nameEng = resultSet.getString("name_eng");
                String nameUkr = resultSet.getString("name_ukr");
                double price = resultSet.getDouble("price");

                System.out.println("ID: " + id + ", Name (English): " + nameEng + ", Name (Ukrainian): " + nameUkr + ", Price: " + price);
            }
        }
    }

    private static void showAllDesserts(Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_DESSERTS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nameEng = resultSet.getString("name_eng");
                String nameUkr = resultSet.getString("name_ukr");
                double price = resultSet.getDouble("price");

                System.out.println("ID: " + id + ", Name (English): " + nameEng + ", Name (Ukrainian): " + nameUkr + ", Price: " + price);
            }
        }
    }

    private static void showAllBaristas(Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BARISTAS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String fullName = resultSet.getString("full_name");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                Boolean is_active = resultSet.getBoolean("is_active");
                String description = resultSet.getString("description");

                System.out.println("ID: " + id + ", Full Name: " + fullName + ", Phone: " + phone + ", Email: " + email + ", Is_Active: " + is_active + ", Description: " + description);
            }
        }
    }

    private static void showAllWaiters(Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_WAITERS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String fullName = resultSet.getString("full_name");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                Boolean is_active = resultSet.getBoolean("is_active");
                String description = resultSet.getString("description");

                System.out.println("ID: " + id + ", Full Name: " + fullName + ", Phone: " + phone + ", Email: " + email + ", Is_Active: " + is_active + ", Description: " + description);
            }
        }

    }
}
