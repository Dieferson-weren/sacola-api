package me.dio.sacola.resource.controller;

import me.dio.sacola.exception.ProdutoNaoEncontradoException;
import me.dio.sacola.exception.ProdutosException;
import me.dio.sacola.exception.SacolaException;
import me.dio.sacola.exception.SacolaNaoEncontradaException;
import me.dio.sacola.resource.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(ProdutosException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ApiErrors handleProduto(ProdutosException pex){
        return new ApiErrors(pex.getMessage());
    }

    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ApiErrors handleProdutoNaoEncontrado(ProdutoNaoEncontradoException pnex){
        return new ApiErrors(pnex.getMessage());
    }

    @ExceptionHandler(SacolaException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ApiErrors handleSacola(SacolaException e){
        return new ApiErrors(e.getMessage());
    }

    @ExceptionHandler(SacolaNaoEncontradaException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ApiErrors handleSacolaNaoEncontrada(SacolaNaoEncontradaException e){
        return new ApiErrors(e.getMessage());
    }
}
