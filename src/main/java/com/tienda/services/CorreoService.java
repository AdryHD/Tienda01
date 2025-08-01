/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tienda.services;

import jakarta.mail.MessagingException;

/**
 *
 * @author adryhd
 */
public interface CorreoService {
    public void enviarCorreoHtml (
            String para, //remitente
            String asunto, //titulo principal
            String contenidoHtml) //cuerpo del correo
            throws MessagingException;  // cuando no se envia el correo  aparece el porque no se envio 
    
}
