package com.Ecommerce.service;

import com.Ecommerce.repository.IOrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class OrdenServiceImp implements IOrdenService{
    @Autowired
    IOrdenRepository ordenRepository;
}
