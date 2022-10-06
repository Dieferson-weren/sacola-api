package me.dio.sacola.resource.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ItemDto {

    private Long produtoId;
    private int quantidade;
    private Long sacolaId;
}
