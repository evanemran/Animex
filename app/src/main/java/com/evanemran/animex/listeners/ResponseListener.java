package com.evanemran.animex.listeners;

public interface ResponseListener<T> {
    void didFetch(T response, String message);
    void didError(String message);
}
