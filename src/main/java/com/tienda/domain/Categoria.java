/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.domain;

import java.io.Serializable;
import jakarta.persistence.*; // o javax.persistence.* dependiendo de tu versión
import lombok.Data;

@Data
@Entity
@Table(name="categoria")

/**
 *
 * @author adryhd
 */
public class Categoria implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_categoria")
    
    private Long idCategoria;
    private String descripcion;
    private String rutaImagen;
    private boolean activo;

    public boolean isActivo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    

  
}
