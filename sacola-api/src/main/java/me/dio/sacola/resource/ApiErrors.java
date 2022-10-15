package me.dio.sacola.resource;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class ApiErrors {
    @Getter
    private List<String> errors;

    public ApiErrors(String errors){
        this.errors = Arrays.asList(errors);
    }

    public ApiErrors(List<String> errors){
        super();
        this.errors = errors;
    }

}
