package com.revature;

public interface CRUDInterface<T> {

    T read(T t);
    T insert(T t);
    T update(T t);
    T delete(T t);

}
