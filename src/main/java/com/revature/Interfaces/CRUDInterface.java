package com.revature.Interfaces;

import java.sql.ResultSet;

public interface CRUDInterface<T> {

    T select(T t);
    T insert(T t);
    T update(T a, T b);
    T delete(T t);
    ResultSet selectAll();

}
