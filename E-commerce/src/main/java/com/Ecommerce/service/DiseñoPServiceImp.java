package com.Ecommerce.service;

import com.Ecommerce.repository.IDiseñoPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class DiseñoPServiceImp implements IDiseñoPService{
    @Autowired
    IDiseñoPRepository diseñoPRepository;
}
