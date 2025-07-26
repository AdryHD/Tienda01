package com.tienda.controller;

import com.tienda.services.CategoriaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.tienda.domain.Categoria; 
import com.tienda.services.impl.FirebaseStorageServiceImpl;   
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestParam; 
import org.springframework.web.multipart.MultipartFile;

@Controller  //este es el estereotiipo
@Slf4j //significa que este controlador permite manejar solicitudes de http
@RequestMapping("/categoria") //define la ruta en la aplicacion, localhost:8080/categoria
public class CategoriaController {
    
    @Autowired
    private CategoriaService categoriaService; //alimenta una intancia de servicio

    @GetMapping("/listado") //se maneja una solicitud de localhost:8080/categoria/listado es decir muestra el listado
    public String inicio(Model model) { //model lo que hace es unir el codigo con la vista
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("categorias", categorias); //el primer categoria es la variable que se va a mostrar la vista del html, el sgundo es la variable
        model.addAttribute("totalCategorias", categorias.size()); //size cuenta el total de categorias, (una sumatoria)
        return "/categoria/listado"; //esta es la vista que se muestra al usuario
    }
    
     @GetMapping("/nuevo")
    public String categoriaNuevo(Categoria categoria) {
        return "/categoria/modifica";
    }

    @Autowired
    private FirebaseStorageServiceImpl firebaseStorageService;
    
 @PostMapping("/guardar")
public String guardar(Categoria categoria,
                      @RequestParam("imagenFile") MultipartFile imagenFile) {

    // si es nuevo y hay imagen, se guarda primero para obtener el ID
    if (categoria.getIdCategoria() == null && !imagenFile.isEmpty()) {
        categoriaService.save(categoria); // generar ID
    }

    //  si viene imagen, se sube a Firebase y se actualiza la URL
    if (!imagenFile.isEmpty()) {
        String urlImagen = firebaseStorageService.cargaImagen(
                imagenFile, 
                "categoria", 
                categoria.getIdCategoria());
        categoria.setRutaImagen(urlImagen);
    }

  //la entidad con todos los datos  sin imagen)
    categoriaService.save(categoria);

    return "redirect:/categoria/listado";
}


    @GetMapping("/eliminar/{idCategoria}")
    public String categoriaEliminar(Categoria categoria) {
        categoriaService.delete(categoria);
        return "redirect:/categoria/listado";
    }

    @GetMapping("/modificar/{idCategoria}")
    public String categoriaModificar(Categoria categoria, Model model) {
        categoria = categoriaService.getCategoria(categoria);
        model.addAttribute("categoria", categoria);
        return "/categoria/modifica";
    }   
    

}