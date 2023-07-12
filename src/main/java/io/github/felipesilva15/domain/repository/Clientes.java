package io.github.felipesilva15.domain.repository;

import io.github.felipesilva15.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Clientes extends JpaRepository<Cliente, Integer> {
    List<Cliente> findByNomeLike (String nome);

    @Query(value = "SELECT c FROM Cliente c WHERE nome LIKE :nome")
    List<Cliente> findByNomeLikeCustom (@Param("nome") String nome);

    List<Cliente> findByNomeLikeOrIdOrderByNome (String nome, Integer id);

    Cliente findOneByNome (String nome);

    @Query(value = "DELETE FROM Cliente c WHERE c.nome = :nome")
    @Modifying
    void deleteByNome (String nome);

    boolean existsByNome (String nome);

    @Query(value = "SELECT c FROM Cliente c LEFT JOIN fetch c.pedidos WHERE c.id = :id")
    Cliente findClienteFetchPedidos(@Param("id") Integer id);
}
