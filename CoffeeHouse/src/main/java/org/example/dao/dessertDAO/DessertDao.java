package org.example.dao.dessertDAO;

import org.example.dao.CRUDInterface;
import org.example.model.Dessert;

import java.util.List;
public interface DessertDao extends CRUDInterface<Dessert> {

    List<Dessert> findDessert(String nameEng);
    void deleteOrderItemsByDessertName(String nameEng);
}
