package io.github.felipesilva15.domain.repository;

import io.github.felipesilva15.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos extends JpaRepository<Produto, Integer> {

}
