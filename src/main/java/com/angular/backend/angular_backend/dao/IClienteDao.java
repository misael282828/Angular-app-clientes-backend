package com.angular.backend.angular_backend.dao;

import org.springframework.data.repository.CrudRepository;

import com.angular.backend.angular_backend.model.Cliente;

public interface IClienteDao extends CrudRepository <Cliente,Long>{

}