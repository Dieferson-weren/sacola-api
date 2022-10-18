package me.dio.sacola.resource;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class ApiErrors {
    @Getter
    private String errors;

    public ApiErrors(String errors){
        this.errors = errors;
    }
}
