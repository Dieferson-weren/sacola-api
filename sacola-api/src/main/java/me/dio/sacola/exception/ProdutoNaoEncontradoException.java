package me.dio.sacola.exception;

public class ProdutoNaoEncontradoException extends RuntimeException{
    public ProdutoNaoEncontradoException(String message) {
        super(message);
    }
}
