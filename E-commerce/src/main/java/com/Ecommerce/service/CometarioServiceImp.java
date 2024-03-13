package com.Ecommerce.service;

import com.Ecommerce.repository.IComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class CometarioServiceImp implements IComentarioService{
    @Autowired
    IComentarioRepository comentarioRepository;
}
