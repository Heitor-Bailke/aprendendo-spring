package com.heitor.aprendendo_spring.business;

import ch.qos.logback.classic.spi.IThrowableProxy;
import com.heitor.aprendendo_spring.infrastructure.entity.Usuario;
import com.heitor.aprendendo_spring.infrastructure.exceptions.ConflictException;
import com.heitor.aprendendo_spring.infrastructure.exceptions.ResourceNotFoundException;
import com.heitor.aprendendo_spring.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    //Metodo que usei para Apenas salvar o usuario, com regra de negocio para ve se o email já existe ou nao
    public Usuario salvaUsuario(Usuario usuario) {
        try {
            emailExiste(usuario.getEmail());
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
            return usuarioRepository.save(usuario);
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado", e.getCause());
        }
    }
    //Essa regra é para verificar se esse email já existe, caso existir ele gera uma Exeception
    public void emailExiste(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConflictException("Email já cadastrado" + email);
            }
        } catch (Exception e) {
            throw new ConflictException("Email já cadastrado", e.getCause());
        }
    }
    // Metodo é para apenas chamar a função de dentro da pasta repository
    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario buscaUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado" + email));
    }

    public void deleteUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }
}
