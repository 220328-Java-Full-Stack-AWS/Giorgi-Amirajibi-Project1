package com.revature.Interfaces;
import java.util.List;

public interface CRUDInterface<T> {

    T select(T t);
    T insert(T t);
    T update(T t);
    T delete(T t);
    List<T> selectAll();

}
