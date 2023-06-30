package com.angular.backend.angular_backend.model;

import java.io.Serializable;


import jakarta.persistence.Column;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
public class Cliente implements Serializable{
  
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  @NotEmpty
  @Size(min=4, max=22)
  @Column(nullable = false)// no puede ser null 
  private String nombre;
  
  @NotEmpty
  private String apellido;

  @Column(nullable = false, unique = true) // no puede ser null y debe ser unico
  @Email
  @NotEmpty

  private String email;

  @Column(name="create_at")
  @Temporal(TemporalType.DATE)
  private Date createdAt;

  @PrePersist
  public void prePersist (){
    createdAt = new Date();
  }

  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getNombre() {
    return nombre;
  }
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  public String getApellido() {
    return apellido;
  }
  public void setApellido(String apellido) {
    this.apellido = apellido;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public Date getCreatedAt() {
    return createdAt;
  }
  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }
  
  
  private static final long serialVersionUID = 1L;
  
  
  
  
  
  
  


  
}
