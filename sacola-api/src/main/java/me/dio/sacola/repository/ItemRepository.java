package me.dio.sacola.repository;

import me.dio.sacola.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(value = "select * from item  where produto_id = :idProduto and sacola_id = :idSacola", nativeQuery = true)
    Item encontrarPorProdutoeSacola(@Param("idProduto") Long idProduto, @Param("idSacola") Long idSacola);
}
