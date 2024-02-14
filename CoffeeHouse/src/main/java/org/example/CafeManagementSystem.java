package org.example;

import org.example.dao.DatabaseManager;
import org.example.dao.customerDAO.CustomerDaoImpl;
import org.example.dao.dessertDAO.DessertDaoImpl;
import org.example.dao.drinkDAO.DrinkDaoImpl;
import org.example.dao.orderDAO.OrderDaoImpl;
import org.example.dao.orderItemDAO.OrderItemDaoImpl;
import org.example.dao.scheduleDAO.ScheduleDaoImpl;
import org.example.dao.staffDAO.StaffDaoImpl;
import org.example.model.*;

import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CafeManagementSystem {
    public static void main(String[] args) {

        try (Connection connection = DatabaseManager.getConnection()) {

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
                System.out.println("18. Добавить новый заказ");
                System.out.println("19. Добавление графика работы для понедельника.");
                System.out.println("20. Изменить название уже существующего кофе.");
                System.out.println("21. Изменить информацию в существующем заказе.");
                System.out.println("22. Изменить название уже существующего десерта.");
                System.out.println("23. Удалить конкретный заказ.");
                System.out.println("24. Удалить из расписания работы конкретный день");
                System.out.println("25. Показать все заказы конкретного десерта");
                System.out.println("26. Показать расписание работы на конкретный день");
                System.out.println("27. Показать все заказы конкретного официанта");
                System.out.println("28. Показать все заказы конкретного клиента");
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
                    case 18:
                        addNewOrder(connection, scanner);
                        break;
                    case 19:
                        addNewSchedule(connection, scanner);
                        break;
                    case 20:
                        changeCoffeeName(connection, scanner);
                        break;
                    case 21:
                        updateOrder(connection, scanner);
                        break;
                    case 22:
                        changeDessertName(connection, scanner);
                        break;
                    case 23:
                        deleteOrder(connection, scanner);
                        break;
                    case 24:
                        deleteScheduleByDay(connection, scanner);
                        break;
                    case 25:
                        showOrdersByDessert(connection, scanner);
                        break;
                    case 26:
                        showScheduleForDay(connection, scanner);
                        break;
                    case 27:
                        showOrdersByWaiter(connection, scanner);
                        break;
                    case 28:
                        showOrdersByCustomer(connection, scanner);
                        break;
                    case 0:
                        System.out.println("Программа завершена.");
                        System.exit(0);
                    default:
                        System.out.println("Некорректный выбор. Пожалуйста, выберите снова.");
                }
            }
        }  catch (SQLException e) {
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

        Drink drink = new Drink();
        drink.setNameEng(name_eng);
        drink.setNameUkr(name_ukr);
        drink.setPrice(price);

        DrinkDaoImpl drinkDao = new DrinkDaoImpl(connection);
        drinkDao.save(drink);

        System.out.println("Новый напиток успешно добавлен!");
    }

    private static void addNewDessert(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Введите название нового десерта English:");
        String name_eng = scanner.next();
        System.out.println("Введите название нового десерта Украинский:");
        String name_ukr = scanner.next();
        System.out.println("Введите стоимость десерта:");
        double price = scanner.nextDouble();

        Dessert dessert = new Dessert();
        dessert.setNameEng(name_eng);
        dessert.setNameUkr(name_ukr);
        dessert.setPrice(price);

        DessertDaoImpl dessertDao = new DessertDaoImpl(connection);
        List<Dessert> desserts = dessertDao.findDessert(name_eng);

        if (!desserts.isEmpty()) {
            System.out.println("Десерт с таким английским названием уже существует в базе данных.");
        } else {
            dessertDao.save(dessert);
            System.out.println("Новый десерт успешно добавлен!");
        }
    }

    private static void addNewBarista(Connection connection, Scanner scanner) throws SQLException {
        scanner.nextLine();
        System.out.println("Введите ФИО нового Бариста:");
        String fullName = scanner.nextLine();
        System.out.println("Введите мобильный тел. сотрудника:");
        String phone = scanner.next();
        System.out.println("Введите email сотрудника:");
        String email = scanner.next();

        Staff barista = new Staff();
        barista.setFullName(fullName);
        barista.setPhone(phone);
        barista.setEmail(email);
        barista.setPositionId(1);
        barista.setActive(true);
        StaffDaoImpl staffDao = new StaffDaoImpl(connection);
        staffDao.save(barista);

        System.out.println("Новый Бариста успешно добавлен!");
    }

    private static void addNewConfectioner(Connection connection, Scanner scanner) throws SQLException {
        scanner.nextLine();
        System.out.println("Введите ФИО нового Кондитера:");
        String fullName = scanner.nextLine();
        System.out.println("Введите мобильный тел. сотрудника:");
        String phone = scanner.next();
        System.out.println("Введите email сотрудника:");
        String email = scanner.next();

        Staff confectioner = new Staff();
        confectioner.setFullName(fullName);
        confectioner.setPhone(phone);
        confectioner.setEmail(email);
        confectioner.setPositionId(3);
        confectioner.setActive(true);
        StaffDaoImpl staffDao = new StaffDaoImpl(connection);
        staffDao.save(confectioner);

        System.out.println("Новый Кондитер успешно добавлен!");
    }

    private static void addNewCustomer(Connection connection, Scanner scanner) throws SQLException {
        scanner.nextLine();
        System.out.println("Введите ФИО нового клиента:");
        String fullName = scanner.nextLine();
        System.out.println("Введите день рождения клиента (в формате ГГГГ-ММ-ДД):");
        String dateOfBirthString = scanner.next();
        Date dateOfBirth = Date.valueOf(dateOfBirthString);
        System.out.println("Введите мобильный телефон клиента:");
        String phone = scanner.next();
        System.out.println("Введите email клиента:");
        String email = scanner.next();
        System.out.println("Введите скидку клиента:");
        Double discount = scanner.nextDouble();

        Customer customer = new Customer();
        customer.setFullName(fullName);
        customer.setDateOfBirth(dateOfBirth);
        customer.setPhone(phone);
        customer.setEmail(email);
        customer.setDiscount(discount);

        CustomerDaoImpl customerDao = new CustomerDaoImpl(connection);
        customerDao.save(customer);

        System.out.println("Новый клиент успешно добавлен!");
    }

    private static void updateDrinkPrice(Connection connection, Scanner scanner) throws SQLException {
        scanner.nextLine();
        System.out.println("Введите название напитка для изменения цены (English):");
        String nameEng = scanner.nextLine();
        System.out.println("Введите новую цену для напитка:");
        double newPrice = scanner.nextDouble();
        DrinkDaoImpl drinkDao = new DrinkDaoImpl(connection);

        List<Drink> drinks = drinkDao.findDrink(nameEng);

        if (!drinks.isEmpty()) {
            Drink drinkToUpdate = drinks.get(0);
            drinkToUpdate.setPrice(newPrice);
            drinkDao.update(drinkToUpdate);
            System.out.println("Цена напитка успешно изменена!");
        } else {
            System.out.println("Напиток с указанным названием не найден.");
        }
    }

    private static void updateEmailConfectioner(Connection connection, Scanner scanner) throws SQLException {
        scanner.nextLine();
        System.out.println("Введите ФИО кондитера для изменения контактов:");
        String fullName = scanner.nextLine();
        System.out.println("Введите новый email кондитера:");
        String newEmail = scanner.next();

        StaffDaoImpl staffDao = new StaffDaoImpl(connection);
        List<Staff> confectioners = staffDao.findStaff(fullName);

        if (!confectioners.isEmpty()) {
            Staff confectionerToUpdate = confectioners.get(0);
            confectionerToUpdate.setEmail(newEmail);
            staffDao.update(confectionerToUpdate);
            System.out.println("Контакты кондитера успешно изменены!");
        } else {
            System.out.println("Кондитер с указанным ФИО не найден.");
        }
    }

    private static void updateBaristaPhone(Connection connection, Scanner scanner) throws SQLException {
        scanner.nextLine();
        System.out.println("Введите ФИО бариста для изменения контактного телефона:");
        String fullName = scanner.nextLine();
        System.out.println("Введите новый мобильный телефон бариста:");
        String newPhone = scanner.next();
        StaffDaoImpl staffDao = new StaffDaoImpl(connection);
        List<Staff> baristas = staffDao.findStaff(fullName);

        if (!baristas.isEmpty()) {
            Staff baristaToUpdate = baristas.get(0);
            baristaToUpdate.setPhone(newPhone);
            staffDao.update(baristaToUpdate);
            System.out.println("Контактный телефон бариста успешно изменен!");
        } else {
            System.out.println("Бариста с указанным ФИО не найден.");
        }
    }
    private static void updateCustomerDiscount(Connection connection, Scanner scanner) throws SQLException {
        scanner.nextLine();
        System.out.println("Введите ФИО клиента для изменения процента скидки:");
        String fullName = scanner.nextLine();
        System.out.println("Введите новый процент скидки для клиента:");
        double newDiscount = scanner.nextDouble();
        CustomerDaoImpl customerDao = new CustomerDaoImpl(connection);
        List<Customer> customers = customerDao.findCustomer(fullName);

        if (!customers.isEmpty()) {
            Customer customerToUpdate = customers.get(0);
            customerToUpdate.setDiscount(newDiscount);
            customerDao.update(customerToUpdate);
            System.out.println("Процент скидки клиента успешно изменен!");
        } else {
            System.out.println("Клиент с указанным ФИО не найден.");
        }
    }


    private static void deleteDessert(Connection connection, Scanner scanner) throws SQLException {
        scanner.nextLine();
        System.out.println("Введите название десерта для удаления:");
        String nameEng = scanner.nextLine();

        DessertDaoImpl dessertDao = new DessertDaoImpl(connection);
        List<Dessert> desserts = dessertDao.findDessert(nameEng);

        if (desserts.isEmpty()) {
            System.out.println("Десерт с указанным названием не найден.");
            return;
        }
        dessertDao.deleteOrderItemsByDessertName(nameEng);
        dessertDao.delete(desserts.get(0));
        System.out.println("Информация о десерте успешно удалена!");
    }

    private static void deactivateWaiter(Connection connection, Scanner scanner) throws SQLException {
        scanner.nextLine();
        System.out.println("Введите ФИО официанта для увольнения:");
        String fullName = scanner.nextLine();
        System.out.println("Введите причину увольнения:");
        String reason = scanner.nextLine();

        StaffDaoImpl staffDao = new StaffDaoImpl(connection);
        List<Staff> waiters = staffDao.findStaff(fullName);

        if (waiters.isEmpty()) {
            System.out.println("Официант с указанным ФИО не найден.");
            return;
        }

        for (Staff waiter : waiters) {
            waiter.setActive(false);
            waiter.setDescription(reason);
            staffDao.update(waiter);
        }
        System.out.println("Официант деактивирован!");
    }

    private static void deactivateBarista(Connection connection, Scanner scanner) throws SQLException {
        scanner.nextLine();
        System.out.println("Введите ФИО бариста для увольнения:");
        String fullName = scanner.nextLine();
        System.out.println("Введите причину увольнения:");
        String reason = scanner.nextLine();

        StaffDaoImpl staffDao = new StaffDaoImpl(connection);
        List<Staff> baristas = staffDao.findStaff(fullName);

        if (baristas.isEmpty()) {
            System.out.println("Бариста с указанным ФИО не найден.");
            return;
        }

        for (Staff barista : baristas) {
            barista.setActive(false);
            barista.setDescription(reason);
            staffDao.update(barista);
        }
        System.out.println("Бариста деактивирован!");
    }


    private static void deleteCustomer(Connection connection, Scanner scanner) throws SQLException {
        scanner.nextLine();
        System.out.println("Введите ФИО клиента для удаления:");
        String customerFullName = scanner.nextLine();

        CustomerDaoImpl customerDao = new CustomerDaoImpl(connection);
        List<Customer> customers = customerDao.findCustomer(customerFullName);
        for (Customer customer : customers) {

            OrderDaoImpl orderDao = new OrderDaoImpl(connection);
            List<Order> orders = orderDao.findOrdersByCustomerId(customer.getId());
            for (Order order : orders) {
                orderDao.delete(order);
            }
            customerDao.delete(customer);
        }
        System.out.println("Информация о клиенте успешно удалена!");
    }

    private static void showAllDrinks(Connection connection) throws SQLException {
        DrinkDaoImpl drinkDao = new DrinkDaoImpl(connection);
        List<Drink> drinks = drinkDao.findAll();
        for (Drink drink : drinks) {
            System.out.println("ID: " + drink.getId() + ", Name (English): " + drink.getNameEng() + ", Name (Ukrainian): " + drink.getNameUkr() + ", Price: " + drink.getPrice());
        }
    }

    private static void showAllDesserts(Connection connection) throws SQLException {
        DessertDaoImpl dessertDao = new DessertDaoImpl(connection);
        List<Dessert> desserts = dessertDao.findAll();
        for (Dessert dessert : desserts) {
            System.out.println("ID: " + dessert.getId() + ", Name (English): " + dessert.getNameEng() + ", Name (Ukrainian): " + dessert.getNameUkr() + ", Price: " + dessert.getPrice());
        }
    }

    private static void showAllBaristas(Connection connection) throws SQLException {
        StaffDaoImpl staffDao = new StaffDaoImpl(connection);
        List<Staff> baristas = staffDao.findStaffByPosition(1);
        for (Staff barista : baristas) {
            System.out.println("ID: " + barista.getId() + ", Full Name: " + barista.getFullName() + ", Phone: " + barista.getPhone() + ", Email: " + barista.getEmail() + ", Is_Active: " + barista.getActive() + ", Description: " + barista.getDescription());
        }
    }

    private static void showAllWaiters(Connection connection) throws SQLException {
        StaffDaoImpl staffDao = new StaffDaoImpl(connection);
        List<Staff> waiters = staffDao.findStaffByPosition(2);
        for (Staff waiter : waiters) {
            System.out.println("ID: " + waiter.getId() + ", Full Name: " + waiter.getFullName() + ", Phone: " + waiter.getPhone() + ", Email: " + waiter.getEmail() + ", Is_Active: " + waiter.getActive() + ", Description: " + waiter.getDescription());
        }
    }


    private static void addNewOrder(Connection connection, Scanner scanner) throws SQLException {
        scanner.nextLine();
        System.out.println("Введите ФИО клиента:");
        String customerFullName = scanner.nextLine();

        CustomerDaoImpl customerDao = new CustomerDaoImpl(connection);
        List<Customer> customers = customerDao.findCustomer(customerFullName);

        if (customers.isEmpty()) {
            System.out.println("Клиент с таким ФИО не найден.");
            return;
        }

        Customer customer;
        if (customers.size() > 1) {
            System.out.println("Найдено несколько клиентов с указанным ФИО. Пожалуйста, выберите конкретного клиента из списка:");
            for (int i = 0; i < customers.size(); i++) {
                System.out.println((i + 1) + ". " + customers.get(i).getFullName());
            }
            System.out.println("Введите номер клиента:");
            int choice = scanner.nextInt();
            if (choice < 1 || choice > customers.size()) {
                System.out.println("Некорректный номер клиента.");
                return;
            }
            customer = customers.get(choice - 1);
        } else {
            customer = customers.get(0);
        }

        System.out.println("Введите дату заказа (в формате ГГГГ-ММ-ДД):");
        String orderDate = scanner.next();
        System.out.println("Введите общую сумму заказа:");
        double totalAmount = scanner.nextDouble();

        System.out.println("Введите ID официанта:");
        int waiterId = scanner.nextInt();

        OrderDaoImpl orderDao = new OrderDaoImpl(connection);

        int orderId = orderDao.getLastOrderId() + 1;
        if (orderId == 0) {
            System.out.println("Ошибка при получении ID последнего заказа.");
            return;
        }

        Order order = new Order();
        order.setCustomerId(customer.getId());
        order.setWaiterId(waiterId);
        order.setOrderDate(java.sql.Date.valueOf(orderDate));
        order.setTotalAmount(totalAmount);

        orderDao.save(order);

        System.out.println("Новый заказ успешно добавлен.");

        System.out.println("Добавление заказанных элементов. Введите '0', чтобы завершить.");
        List<OrderItem> orderItems = new ArrayList<>();
        boolean addingItems = true;
        while (addingItems) {
            System.out.println("Введите ID товара (или 0, чтобы завершить):");
            int itemId = scanner.nextInt();
            if (itemId == 0) {
                addingItems = false;
                continue;
            }
            System.out.println("Введите тип товара (1 - напиток, 2 - десерт):");
            int itemType = scanner.nextInt();
            System.out.println("Введите количество:");
            int quantity = scanner.nextInt();

            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderId);
            orderItem.setItemType(itemType == 1 ? "drink" : "dessert");
            orderItem.setItemtId(itemId);
            orderItem.setQuantity(quantity);

            orderItems.add(orderItem);
        }

        OrderItemDaoImpl orderItemDao = new OrderItemDaoImpl(connection);
        orderItemDao.saveMany(orderItems);

        System.out.println("Заказанные элементы успешно добавлены.");
    }


    private static void addNewSchedule(Connection connection, Scanner scanner) throws SQLException {
        scanner.nextLine();
        System.out.println("Введите ФИО сотрудника:");
        String fullName = scanner.nextLine();

        StaffDaoImpl staffDao = new StaffDaoImpl(connection);
        List<Staff> staffList = staffDao.findStaff(fullName);

        if (staffList.isEmpty()) {
            System.out.println("Сотрудник с таким ФИО не найден.");
            return;
        }

        Staff staff = staffList.get(0);

        System.out.println("Введите время начала работы (в формате HH:mm):");
        String startTimeStr = scanner.next();
        System.out.println("Введите время окончания работы (в формате HH:mm):");
        String endTimeStr = scanner.next();


        LocalTime startTime = LocalTime.parse(startTimeStr, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime endTime = LocalTime.parse(endTimeStr, DateTimeFormatter.ofPattern("HH:mm"));

        Schedule schedule = new Schedule();
        schedule.setStaffId(staff.getId());
        schedule.setDayOfWeek("Monday");
        schedule.setStartTime(Time.valueOf(startTime));
        schedule.setEndTime(Time.valueOf(endTime));

        ScheduleDaoImpl scheduleDao = new ScheduleDaoImpl(connection);
        scheduleDao.save(schedule);
        System.out.println("Информация о графике работы успешно добавлена!");
    }


    private static void changeCoffeeName(Connection connection, Scanner scanner) throws SQLException {
        scanner.nextLine();
        System.out.println("Введите текущее название вида кофе:");
        String currentName = scanner.nextLine();

        DrinkDaoImpl drinkDao = new DrinkDaoImpl(connection);
        List<Drink> drinks = drinkDao.findDrink(currentName);

        if (drinks.isEmpty()) {
            System.out.println("Вид кофе с таким названием не найден.");
            return;
        }

        Drink coffee = drinks.get(0);
        scanner.nextLine();
        System.out.println("Введите новое название вида кофе (Eng):");
        String newNameEng = scanner.nextLine();
        System.out.println("Введите новое название вида кофе (Ukr):");
        String newNameUkr = scanner.nextLine();
        coffee.setNameEng(newNameEng);
        coffee.setNameUkr(newNameUkr);

        drinkDao.update(coffee);

        System.out.println("Название вида кофе успешно изменено!");
    }


    private static void updateOrder(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Введите ID заказа, который хотите изменить:");
        int orderId = scanner.nextInt();
        scanner.nextLine();

        OrderDaoImpl orderDao = new OrderDaoImpl(connection);
        Order order = orderDao.findById(orderId);

        if (order == null) {
            System.out.println("Заказ с указанным ID не найден.");
            return;
        }

        System.out.println("Текущая информация о заказе:");
        System.out.println(order);

        System.out.println("Введите новую информацию о заказе:");

        System.out.println("Новая дата заказа (в формате ГГГГ-ММ-ДД):");
        String newOrderDate = scanner.nextLine();

        System.out.println("Новая общая сумма заказа:");
        double newTotalAmount = scanner.nextDouble();

        System.out.println("Введите новый ID официанта:");
        int newWaiterId = scanner.nextInt();

        order.setOrderDate(java.sql.Date.valueOf(newOrderDate));
        order.setTotalAmount(newTotalAmount);
        order.setWaiterId(newWaiterId);
        orderDao.update(order);

        OrderItemDaoImpl orderItemDao = new OrderItemDaoImpl(connection);
        List<OrderItem> orderItems = orderItemDao.findOrderItemsByOrderId(orderId);
        for (OrderItem orderItem : orderItems) {
            System.out.println("Введите новое количество для товара с ID " + orderItem.getId() + ":");
            int newQuantity = scanner.nextInt();
            orderItem.setQuantity(newQuantity);
            orderItemDao.update(orderItem);
        }

        System.out.println("Информация о заказе и связанных элементах успешно изменена!");
    }


    private static void changeDessertName(Connection connection, Scanner scanner) throws SQLException {
        scanner.nextLine();
        System.out.println("Введите текущее название десерта:");
        String currentName = scanner.nextLine();

        DessertDaoImpl dessertDao = new DessertDaoImpl(connection);
        List<Dessert> desserts = dessertDao.findDessert(currentName);

        if (desserts.isEmpty()) {
            System.out.println("Десерт с таким названием не найден.");
            return;
        }

        Dessert dessert = desserts.get(0);
        scanner.nextLine();
        System.out.println("Введите новое название десерта (Eng):");
        String newNameEng = scanner.nextLine();
        System.out.println("Введите новое название десерта (Ukr):");
        String newNameUkr = scanner.nextLine();
        dessert.setNameEng(newNameEng);
        dessert.setNameUkr(newNameUkr);

        dessertDao.update(dessert);

        System.out.println("Название десерта успешно изменено!");
    }

    private static void deleteOrder(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Введите ID заказа для удаления:");
        int orderId = scanner.nextInt();
        scanner.nextLine();

        OrderDaoImpl orderDao = new OrderDaoImpl(connection);
        Order order = orderDao.findById(orderId);

        if (order == null) {
            System.out.println("Заказ с таким ID не найден.");
            return;
        }

        OrderItemDaoImpl orderItemDao = new OrderItemDaoImpl(connection);
        orderItemDao.deleteOrderItemsByOrderId(orderId);

        orderDao.delete(order);

        System.out.println("Заказ успешно удален.");
    }

    private static void deleteScheduleByDay(Connection connection, Scanner scanner) throws SQLException {
        scanner.nextLine();
        System.out.println("Введите день недели для удаления расписания:");
        String dayOfWeek = scanner.nextLine().trim();

        ScheduleDaoImpl scheduleDao = new ScheduleDaoImpl(connection);
        List<Schedule> schedules = scheduleDao.findScheduleByDay(dayOfWeek);

        if (schedules.isEmpty()) {
            System.out.println("Расписание работы на указанный день не найдено.");
            return;
        }

        for (Schedule schedule : schedules) {
            scheduleDao.delete(schedule);
        }

        System.out.println("Расписание работы на " + dayOfWeek + " успешно удалено.");
    }

    private static void showOrdersByDessert(Connection connection, Scanner scanner) throws SQLException {
        scanner.nextLine();
        System.out.println("Введите название десерта:");
        String dessertName = scanner.nextLine().trim();

        DessertDaoImpl dessertDao = new DessertDaoImpl(connection);
        List<Dessert> desserts = dessertDao.findDessert(dessertName);

        if (desserts.isEmpty()) {
            System.out.println("Десерт с указанным названием не найден.");
            return;
        }

        Dessert dessert = desserts.get(0);
        int dessertId = dessert.getId();

        OrderItemDaoImpl orderItemDao = new OrderItemDaoImpl(connection);
        List<OrderItem> orderItems = orderItemDao.findOrderItemsByDessertId(dessertId);

        if (orderItems.isEmpty()) {
            System.out.println("Нет заказов для этого десерта.");
            return;
        }

        System.out.println("Заказы для десерта \"" + dessert.getNameEng() + "\":");
        for (OrderItem orderItem : orderItems) {
            OrderDaoImpl orderDao = new OrderDaoImpl(connection);
            Order order = orderDao.findById(orderItem.getOrderId());
            if (order != null) {
                System.out.println("ID заказа: " + order.getId() + ", Дата: " + order.getOrderDate() +
                        ", Общая сумма: " + order.getTotalAmount() + ", Количество: " + orderItem.getQuantity());
            }
        }
    }


    public static void showScheduleForDay(Connection connection, Scanner scanner) throws SQLException {
        scanner.nextLine();
        System.out.println("Введите название дня недели (например, Monday):");
        String dayOfWeek = scanner.nextLine().trim();

        ScheduleDaoImpl scheduleDao = new ScheduleDaoImpl(connection);
        List<Schedule> scheduleList = scheduleDao.findScheduleByDay(dayOfWeek);

        if (scheduleList.isEmpty()) {
            System.out.println("На указанный день нет расписания.");
        } else {
            System.out.println("Расписание на " + dayOfWeek + ":");
            for (Schedule schedule : scheduleList) {
                System.out.println("ID: " + schedule.getId() +
                        ", ID персонала: " + schedule.getStaffId() +
                        ", Начало: " + schedule.getStartTime() +
                        ", Окончание: " + schedule.getEndTime());
            }
        }

    }

    private static void showOrdersByWaiter(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Введите ID официанта:");
        int waiterId = scanner.nextInt();
        scanner.nextLine();

        OrderDaoImpl orderDao = new OrderDaoImpl(connection);
        List<Order> orders = orderDao.findAllOrdersByWaiterId(waiterId);

        if (orders.isEmpty()) {
            System.out.println("Нет заказов для этого официанта.");
            return;
        }

        System.out.println("Заказы для официанта с ID " + waiterId + ":");
        for (Order order : orders) {
            System.out.println("ID заказа: " + order.getId() + ", ID клиента: " + order.getCustomerId() + ", Дата заказа: " + order.getOrderDate() + ", Общая сумма: " + order.getTotalAmount());
        }
    }

    private static void showOrdersByCustomer(Connection connection, Scanner scanner) throws SQLException {
        scanner.nextLine();
        System.out.println("Введите ФИО клиента:");
        String customerFullName = scanner.nextLine().trim();

        CustomerDaoImpl customerDao = new CustomerDaoImpl(connection);
        List<Customer> customers = customerDao.findCustomer(customerFullName);

        if (customers.isEmpty()) {
            System.out.println("Клиент с указанным ФИО не найден.");
            return;
        }

        Customer customer;
        if (customers.size() > 1) {
            System.out.println("Найдено несколько клиентов с указанным ФИО. Пожалуйста, выберите конкретного клиента из списка:");
            for (int i = 0; i < customers.size(); i++) {
                System.out.println((i + 1) + ". " + customers.get(i).getFullName());
            }
            System.out.println("Введите номер клиента:");
            int choice = scanner.nextInt();
            if (choice < 1 || choice > customers.size()) {
                System.out.println("Некорректный номер клиента.");
                return;
            }
            customer = customers.get(choice - 1);
        } else {
            customer = customers.get(0);
        }

        OrderDaoImpl orderDao = new OrderDaoImpl(connection);
        List<Order> orders = orderDao.findOrdersByCustomerId(customer.getId());

        if (orders.isEmpty()) {
            System.out.println("Нет заказов для этого клиента.");
            return;
        }

        System.out.println("Заказы для клиента " + customerFullName + ":");
        for (Order order : orders) {
            System.out.println("ID заказа: " + order.getId() + ", Дата заказа: " + order.getOrderDate() + ", Общая сумма: " + order.getTotalAmount());
        }
    }

}
