package me.dio.sacola.services.impl;

import lombok.RequiredArgsConstructor;
import me.dio.sacola.enumeration.FormaPagamento;
import me.dio.sacola.exception.ProdutoNaoEncontradoException;
import me.dio.sacola.exception.ProdutosException;
import me.dio.sacola.exception.SacolaException;
import me.dio.sacola.exception.SacolaNaoEncontradaException;
import me.dio.sacola.model.Item;
import me.dio.sacola.model.Restaurante;
import me.dio.sacola.model.Sacola;
import me.dio.sacola.repository.ItemRepository;
import me.dio.sacola.repository.ProdutoRepository;
import me.dio.sacola.repository.SacolaRepository;
import me.dio.sacola.resource.dto.ItemDto;
import me.dio.sacola.services.SacolaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
            throw  new SacolaException("Esta sacola está fechada");
        }
        Item itensParaAdicionar = Item.builder()
                .quantidade(itemDto.getQuantidade())
                .sacola(sacola)
                .produto(produtoRepository.findById(itemDto.getProdutoId())
                        .orElseThrow(() -> {throw new ProdutoNaoEncontradoException("Produto não encontrado");}))
                .build();
        List<Item> itensDaSacola = sacola.getItens();
        if(itensDaSacola.isEmpty()){
            itensDaSacola.add((itensParaAdicionar));
        }else{
            Restaurante restauranteAtual = sacola.getItens().get(0).getProduto().getRestaurante();
            Restaurante restauranteAserAdicionado = itensParaAdicionar.getProduto().getRestaurante();
            if(restauranteAtual.equals(restauranteAserAdicionado)){
                Item item = itemRepository.encontrarPorProdutoeSacola(itensParaAdicionar.getProduto().getId(),
                        itensParaAdicionar.getSacola().getId());

                if(item != null){
                    itensDaSacola
                            .get(itensDaSacola.indexOf(item))
                            .setQuantidade(item.getQuantidade() + itensParaAdicionar.getQuantidade());
                }else{
                    itensDaSacola.add(itensParaAdicionar);
                }
            }else{
                throw new ProdutosException("Não é possível inserir produtos de outro restaurante. Feche a sacola ou esvazie.");
            }
        }
        Double valorTotal = 0.0;
        for(Item item : itensDaSacola) {
            double valorTotalDosItens = item.getProduto().getValorUnitario() * item.getQuantidade();
            valorTotal += valorTotalDosItens;
        }
        sacola.setValorTotal(valorTotal);
        sacolaRepository.save(sacola);
        return itensParaAdicionar;
    }

    @Override
    public Sacola verSacola(long id) {
        return sacolaRepository.findById(id).orElseThrow(() -> {throw new SacolaNaoEncontradaException("Sacola Não encontrada");});
    }

    @Override
    public Sacola fecharSacola(long id, FormaPagamento formaPagamento) {
        Sacola sacola = verSacola(id);
        sacola.setFormaPagamento(formaPagamento);
        sacola.setFechada(true);
        sacolaRepository.save(sacola);
        return sacola;
    }
}
