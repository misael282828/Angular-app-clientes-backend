package com.angular.backend.angular_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.angular.backend.angular_backend.dao.IClienteDao;
import com.angular.backend.angular_backend.model.Cliente;

@Service

public class ClienteServiceImpl implements IClienteService {

  @Autowired
  private IClienteDao clienteDao;

  @Override
  @Transactional(readOnly = true)
  public List<Cliente> findAll() {

    return (List<Cliente>) clienteDao.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Cliente findById(Long id) {

    return clienteDao.findById(id).orElse(null);
  }

  @Override
  @Transactional
  public Cliente saveCliente(Cliente cliente) {
  
    return clienteDao.save(cliente);
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    clienteDao.deleteById(id);
  }

}
