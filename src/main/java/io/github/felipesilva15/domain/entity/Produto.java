package io.github.felipesilva15.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "descricao", length = 80)
    private String descricao;

    @Column(name = "preuni", precision = 20, scale = 2)
    private BigDecimal preco;
}
