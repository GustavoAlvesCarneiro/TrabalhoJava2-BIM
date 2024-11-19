package com.kanbam.project.controller;

import com.kanbam.project.TokenService;
import com.kanbam.project.model.user.AuthenticationDTO;
import com.kanbam.project.model.user.LoginResponseDTO;
import com.kanbam.project.model.user.RegisterDTO;
import com.kanbam.project.model.user.User;
import com.kanbam.project.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("auth")
public class AuthenticacaoController {
    @Autowired
    private AuthenticacaoManager authenticacaoManager;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private TokenServico tokenServico;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AutenticacaoDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/registrar")
    public ResponseEntity registrar(@RequestBody @Valid RegisterDTO data){
        if(this.usuariorepository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), encryptedPassword, data.role());

        this.usuariorepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}