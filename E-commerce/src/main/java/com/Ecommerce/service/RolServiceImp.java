package com.Ecommerce.service;

import com.Ecommerce.repository.IRolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class RolServiceImp implements IRolService{
    @Autowired
    IRolRepository rolRepository;
}
