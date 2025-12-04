package com.learning.gymback.exception;

public record ErrorObject(int code,
                          String message,
                          Object details) {
}
