package com.revature;

public interface CRUDInterface<T> {

    T read();
    T insert();
    T update();
    T delete();

}
