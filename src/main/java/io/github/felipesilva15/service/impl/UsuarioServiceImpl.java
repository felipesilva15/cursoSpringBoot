package io.github.felipesilva15.service.impl;

import io.github.felipesilva15.domain.entity.Usuario;
import io.github.felipesilva15.domain.repository.Usuarios;
import io.github.felipesilva15.exception.SenhaInvalidaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UsuarioServiceImpl implements UserDetailsService {
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private Usuarios repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = repository
                .findByLogin(username)
                .orElseThrow( () -> new UsernameNotFoundException("Usuário não encontrado na base de dados."));

        String[] roles = user.isAdmin() ? new String[] {"ADMIN", "USER"} : new String[] {"USER"};

        return User
                .builder()
                .username(user.getLogin())
                .password(user.getSenha())
                .roles(roles)
                .build();
    }

    @Transactional
    public Usuario save(Usuario user) {
        return repository.save(user);
    }

    public UserDetails auth(Usuario usuario) {
        UserDetails user = loadUserByUsername(usuario.getLogin());

        if (!encoder.matches(usuario.getSenha(), user.getPassword())){
            throw new SenhaInvalidaException();
        }

        return user;
    }

}
