package com.pruebatecnica.GestorEquipos.exceptions;

public class ResourceAlreadyExists extends RuntimeException{

    public ResourceAlreadyExists(String message) {
        super(message);
    }
}
