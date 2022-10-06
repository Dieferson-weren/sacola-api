package me.dio.sacola.resource;

import lombok.RequiredArgsConstructor;
import me.dio.sacola.model.Item;
import me.dio.sacola.model.Sacola;
import me.dio.sacola.resource.dto.ItemDto;
import me.dio.sacola.services.SacolaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/sacola")
public class SacolaResource {

    private final SacolaService sacolaService;

    @PostMapping
    public Item adicionarItemNaSacola(@RequestBody ItemDto itemDto){
        return sacolaService.adicionarItemNaSacola(itemDto);
    }

    @GetMapping("/{id}")
    public Sacola buscaSacola(@PathVariable("id") Long id){
        return sacolaService.verSacola(id);
    }
}
