package com.angular.backend.angular_backend.controllers;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.angular.backend.angular_backend.model.Cliente;
import com.angular.backend.angular_backend.service.IClienteService;

import jakarta.validation.Valid;

// croos intercambio de recursos de oringe cruzado, 
//permite la interacion entre angular y el backend
//ejemplo nuestro backend esta en el corriendo ene l puerto
//8080, pero el de angular 4200, Cross nos ayuda con los
//envios de todo tipo de datos en las urls
// podemso establecer los metodos permitidos en este backend
// ejemplo podemos indicar que no se hace PUT desde el frontend
@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class Controllers {

  @Autowired
  private IClienteService service;

  // Mostrar todos los clientes
  @GetMapping(value = "clientes")
  public List<Cliente> getClientes() {
    return service.findAll();
  }

  // Mostrar clientes por ID
  // agregamos ResponseEntity para manejar errores

  @GetMapping(value = "/clientes/{id}")
  public ResponseEntity<?> getClienteId(@PathVariable Long id) {

    Cliente cliente = null;
    Map<String, Object> response = new HashMap<>();

    try {
      cliente = service.findById(id);

    } catch (DataAccessException e) {
      response.put("mensaje", "Error al realizar la consulta en la base de datos");
      response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    if (cliente == null) {
      response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);

    }
    return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);

  }





  @PostMapping("/clientes")
  // @ResponseStatus(HttpStatus.CREATED) // cambiar la respuesta a 201
  ResponseEntity<?> createCliente(@Valid @RequestBody Cliente cliente, BindingResult result) {

    Cliente newCliente = null;
    Map<String, Object> response = new HashMap<>();

    // Este if es para validar los campos con las anotaciones que usamos en el Modelo
    if(result.hasErrors()){

      List<String> errors = result.getFieldErrors()
            .stream()
            .map(err -> "El campo '" + err.getField() + " ' " + err.getDefaultMessage())
            .collect(Collectors.toList());

      response.put("errors", errors);
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
    }


    try {
      newCliente = service.saveCliente(cliente);
    } catch (DataAccessException e) {
      response.put("mensaje", "Error intente con otro email");
      response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    response.put("mensaje", "cliente ha sido creado exitosamente!");
    response.put("cliente", newCliente);
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

  }

  // Update cliente
  @PutMapping(value = "/clientes/{id}")
  public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result , @PathVariable Long id) {

    Cliente clienteActual = service.findById(id);
    Cliente clienteUpdate = null;

    Map<String, Object> response = new HashMap<>();

    
    // Este if es para validar los campos con las anotaciones que usamos en el Modelo
    if(result.hasErrors()){

      List<String> errors = result.getFieldErrors()
            .stream()
            .map(err -> "El campo ''" + err.getField() + " ' " + err.getDefaultMessage())
            .collect(Collectors.toList());

      response.put("errors", errors);
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
    }

    if (clienteActual == null) {
      response.put("mensaje",
          "Error: no se pudo editar, el cliente ID:".concat(id.toString().concat(" no existe en la base de datos")));
        
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);

    }
    try {
      clienteActual.setApellido(cliente.getApellido());
      clienteActual.setNombre(cliente.getNombre());
      clienteActual.setEmail(cliente.getEmail());
      clienteActual.setCreatedAt(cliente.getCreatedAt());

      clienteUpdate = service.saveCliente(clienteActual);

    } catch (DataAccessException e) {
      response.put("mensaje", "Error al actualizar el cliente en la base de datos");
      response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    response.put("mensaje", "Cliente ha sido actualizado con exito ");
    response.put("cliente", clienteUpdate);

    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

  }

  // Eliminar clientes por ID
  // @ResponseStatus(HttpStatus.NO_CONTENT) // cambiar la respuesta a 204

  @DeleteMapping(value = "/clientes/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {

    Cliente clienteEliminado = service.findById(id);
    Map<String, Object> response = new HashMap<>();

    if (clienteEliminado == null) {
      response.put("mensaje",
          "Error: no se pudo borrar, el cliente ID:".concat(id.toString().concat(" no existe en la base de datos")));
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
    }
    try{
      service.deleteById(id);

    }catch(DataAccessException e){
      response.put("mensaje", "Error al eliminar el cliente en la base de datos");
      response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    response.put("mensaje", "Cliente eliminado con exito!");
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
  }

}
