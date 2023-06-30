package com.angular.backend.angular_backend.service;

import java.util.List;

import com.angular.backend.angular_backend.model.Cliente;

public interface IClienteService {
  
  public List<Cliente> findAll();

  public Cliente findById(Long id);

  public Cliente saveCliente(Cliente cliente);

  public void deleteById(Long id);
}
