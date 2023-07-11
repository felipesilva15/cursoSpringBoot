package io.github.felipesilva15.domain.repository;

import io.github.felipesilva15.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class Clientes {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static String INSERT = "INSERT INTO CLIENTE (NOME) VALUES (?)";
    private static String UPDATE = "UPDATE CLIENTE SET NOME = ? WHERE ID = ?";
    private static String SELECT_ALL = "SELECT * FROM CLIENTE";
    private static String DELETE = "DELETE FROM CLIENTE WHERE ID = ?";
    private static String SELECT_BY_NAME = SELECT_ALL.concat(" WHERE NOME LIKE ?");

    public Cliente save(Cliente cliente){
        jdbcTemplate.update(INSERT, new Object[]{cliente.getNome()});

        return cliente;
    }

    public Cliente update(Cliente cliente){
        jdbcTemplate.update(UPDATE, new Object[]{
                cliente.getNome(),
                cliente.getId()
        });

        return cliente;
    }

    public List<Cliente> getAll() {
        return jdbcTemplate.query(SELECT_ALL, this.getRowMapper());
    }

    public List<Cliente> getByName(String nome) {
        return jdbcTemplate.query(SELECT_BY_NAME, new Object[]{"%" + nome + "%"}, this.getRowMapper());
    }

    public void delete(Integer id){
        jdbcTemplate.update(DELETE, new Object[]{id});
    }

    public void delete(Cliente cliente ){
        jdbcTemplate.update(DELETE, new Object[]{cliente.getId()});
    }

    private RowMapper<Cliente> getRowMapper () {
        return new RowMapper<Cliente>() {
            @Override
            public Cliente mapRow(ResultSet resultSet, int i) throws SQLException {
                Integer id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");

                return new Cliente(id, nome);
            }
        };
    }
}
