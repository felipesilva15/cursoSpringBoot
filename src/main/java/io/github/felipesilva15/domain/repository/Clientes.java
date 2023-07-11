package io.github.felipesilva15.domain.repository;

import ch.qos.logback.core.net.server.Client;
import io.github.felipesilva15.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class Clientes {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    private static String INSERT = "INSERT INTO cliente (nome) VALUES (?)";
    private static String UPDATE = "UPDATE cliente SET nome = ? WHERE id = ?";
    private static String SELECT_ALL = "SELECT * FROM cliente";
    private static String DELETE = "DELETE FROM cliente WHERE id = ?";
    private static String SELECT_BY_NAME = SELECT_ALL.concat(" WHERE nome LIKE ?");

    @Transactional
    public Cliente save(Cliente cliente){
        entityManager.persist(cliente);

        return cliente;
    }

    @Transactional
    public Cliente update(Cliente cliente){
        entityManager.merge(cliente);

        return cliente;
    }

    @Transactional
    public List<Cliente> getAll() {
        return entityManager.createQuery("FROM Cliente", Cliente.class).getResultList();
    }

    @Transactional
    public List<Cliente> getByName(String nome) {
        String jpql = "SELECT c FROM Cliente c WHERE c.nome LIKE :nome";
        TypedQuery<Cliente> query =  entityManager.createQuery(jpql, Cliente.class);
        query.setParameter("nome", "%" + nome + "%");

        return query.getResultList();
    }

    @Transactional
    public void delete(Integer id){
        delete(entityManager.find(Cliente.class, id));
    }

    @Transactional
    public void delete(Cliente cliente ){
        if (!entityManager.contains(cliente)) {
            cliente = entityManager.merge(cliente);
        }

        entityManager.remove(cliente);
    }
}
