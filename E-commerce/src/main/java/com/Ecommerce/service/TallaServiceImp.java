package com.Ecommerce.service;

import com.Ecommerce.repository.ITallaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class TallaServiceImp implements ITallaService{
    @Autowired
    ITallaRepository tallaRepository;
}
