package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrar(@RequestBody Usuario novoUsuario) {
        Optional<Usuario> existente = usuarioRepository.findByEmail(novoUsuario.getEmail());
        if (existente.isPresent()) {
            return ResponseEntity.badRequest().body("Este email já está registado!");
        }
        usuarioRepository.save(novoUsuario);
        return ResponseEntity.ok("Utilizador cadastrado com sucesso!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario dadosLogin) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(dadosLogin.getEmail());
        
        if (usuario.isPresent() && usuario.get().getSenha().equals(dadosLogin.getSenha())) {
            return ResponseEntity.ok("Login efetuado com sucesso!");
        }
        return ResponseEntity.status(401).body("Email ou senha incorretos!");
    }
}