package me.dio.sacola.services;

import me.dio.sacola.enumeration.FormaPagamento;
import me.dio.sacola.model.*;
import me.dio.sacola.resource.dto.ItemDto;

public interface SacolaService {

    Item adicionarItemNaSacola(ItemDto itemDto);
    Sacola verSacola(long id);
    Sacola fecharSacola(long id, FormaPagamento formaPagamento);

}
