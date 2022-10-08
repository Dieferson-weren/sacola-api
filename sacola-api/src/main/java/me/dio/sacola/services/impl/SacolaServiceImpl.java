package me.dio.sacola.services.impl;

import lombok.RequiredArgsConstructor;
import me.dio.sacola.model.Item;
import me.dio.sacola.model.Restaurante;
import me.dio.sacola.model.Sacola;
import me.dio.sacola.repository.ItemRepository;
import me.dio.sacola.repository.ProdutoRepository;
import me.dio.sacola.repository.SacolaRepository;
import me.dio.sacola.resource.dto.ItemDto;
import me.dio.sacola.services.SacolaService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SacolaServiceImpl implements SacolaService {

    private final SacolaRepository sacolaRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemRepository itemRepository;
    @Override
    public Item adicionarItemNaSacola(ItemDto itemDto) {
        Sacola sacola = verSacola(itemDto.getSacolaId());
        if(sacola.isFechada()){
            throw  new RuntimeException("Esta sacola está fechada");
        }
        Item itensParaAdicionar = Item.builder()
                .quantidade(itemDto.getQuantidade())
                .sacola(sacola)
                .produto(produtoRepository.findById(itemDto.getProdutoId())
                        .orElseThrow(() -> {throw new RuntimeException("Produto não encontradio");}))
                .build();
        List<Item> itensDaSacola = sacola.getItens();
        if(itensDaSacola.isEmpty()){
            itensDaSacola.add((itensParaAdicionar));
        }else{
            Restaurante restauranteAtual = sacola.getItens().get(0).getProduto().getRestaurante();
            Restaurante restauranteAserAdicionado = itensParaAdicionar.getProduto().getRestaurante();
            if(restauranteAtual.equals(restauranteAserAdicionado)){
                if(itensDaSacola.stream().anyMatch(item -> item.getProduto().getId().equals(itensParaAdicionar.getProduto().getId()))){
                    itensDaSacola.forEach(item -> {if (item.getProduto().getId() == itensParaAdicionar.getProduto().getId()){
                        item.setQuantidade( item.getQuantidade() + itensParaAdicionar.getQuantidade());}
                    });
                }else {
                    itensDaSacola.add((itensParaAdicionar));
                }




            }else{
                throw new RuntimeException("Não é possível inserir produtos de outro restaurante. Feche a sacola ou esvazie.");
            }
        }

        Double valorDosItens = 0.0;
        for(Item item : itensDaSacola) {

            double valorTotalDosItens = item.getProduto().getValorUnitario() * item.getQuantidade();
            valorDosItens += valorTotalDosItens;
        }
        sacola.setValorTotal(valorDosItens);
        sacolaRepository.save(sacola);
        return itensParaAdicionar;
    }

    @Override
    public Sacola verSacola(long id) {
        return sacolaRepository.findById(id).orElseThrow(() -> {throw new RuntimeException("Sacola Não encontrada");});
    }

    @Override
    public Sacola fecharSacola(long id, int formaPagamento) {
        return null;
    }
}
