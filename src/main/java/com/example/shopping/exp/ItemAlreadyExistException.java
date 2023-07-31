package com.example.shopping.exp;

public class ItemAlreadyExistException extends RuntimeException{
    public ItemAlreadyExistException(String message) {
        super(message);
    }
}
