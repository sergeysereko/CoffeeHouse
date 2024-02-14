package org.example.dao.drinkDAO;

import org.example.dao.CRUDInterface;
import org.example.model.Drink;

import java.util.List;
public interface DrinkDao extends CRUDInterface<Drink> {

    List<Drink> findDrink(String nameEng);
}
