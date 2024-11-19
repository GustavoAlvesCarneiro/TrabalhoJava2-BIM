package com.kanbam.project.repository;

import com.kanbam.project.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepositorio extends JpaRepository<User, String> {
     UserDetails findByLogin(String login);


}
