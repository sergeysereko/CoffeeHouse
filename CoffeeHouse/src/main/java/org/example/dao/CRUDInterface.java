package org.example.dao;

import java.sql.SQLException;
import java.util.List;

public interface CRUDInterface<T> {

    void save(T course) throws SQLException;

    void saveMany(List<T> courses) ;

    void update(T course);

    void delete(T course);

    List<T> findAll();

    void deleteAll();

}