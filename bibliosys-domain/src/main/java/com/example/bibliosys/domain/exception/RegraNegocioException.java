// src/main/java/com/example/bibliosys/domain/exception/RegraNegocioException.java
package com.example.bibliosys.domain.exception;

public class RegraNegocioException extends RuntimeException {
    public RegraNegocioException(String message) {
        super(message);
    }
}