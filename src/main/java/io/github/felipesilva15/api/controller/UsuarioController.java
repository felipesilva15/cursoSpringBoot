package io.github.felipesilva15.api.controller;

import io.github.felipesilva15.api.dto.CredenciaisDTO;
import io.github.felipesilva15.api.dto.TokenDTO;
import io.github.felipesilva15.exception.SenhaInvalidaException;
import io.github.felipesilva15.security.jwt.JwtService;
import io.github.felipesilva15.service.impl.UsuarioServiceImpl;
import io.github.felipesilva15.domain.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioServiceImpl service;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @PostMapping
    @ResponseStatus(CREATED)
    public Usuario save(@RequestBody @Valid Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        return service.save(usuario);
    }

    @PostMapping("/auth")
    public TokenDTO auth(@RequestBody CredenciaisDTO dto) {
        try {
            Usuario usuario = new Usuario(0, dto.getLogin(), dto.getSenha(), false);

            UserDetails usuarioAutenticado = service.auth(usuario);
            String token = jwtService.gerarToken(usuario);

            return new TokenDTO(usuario.getLogin(), token);
        } catch (UsernameNotFoundException | SenhaInvalidaException e) {
            throw new ResponseStatusException(UNAUTHORIZED, e.getMessage());
        }
    }
}
