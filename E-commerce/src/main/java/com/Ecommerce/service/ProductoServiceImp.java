package com.Ecommerce.service;

import com.Ecommerce.repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ProductoServiceImp implements IProductoService{
    @Autowired
    IProductoRepository productoRepository;
}
