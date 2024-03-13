package com.Ecommerce.service;

import com.Ecommerce.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class UsuarioServiceImp implements IUsuarioService{
    @Autowired
    IUsuarioRepository usuarioRepository;
}
