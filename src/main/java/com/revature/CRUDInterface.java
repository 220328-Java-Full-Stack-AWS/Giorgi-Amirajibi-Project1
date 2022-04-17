package com.revature;

public interface CRUDInterface<T> {

    T read(T t);
    T insert(T t);
    T update(T a, T b);
    T delete(T t);

}
