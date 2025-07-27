package com.tienda.controller;
 
import com.tienda.domain.Categoria;
import com.tienda.services.CategoriaService;
import com.tienda.services.impl.FirebaseStorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;
 
@Controller
@Slf4j //manejar solicitudes de HHTP
@RequestMapping("/categoria")//localhost:8080/categoria
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;
 
    @GetMapping("/listado")//localhost:8080/categoria/listado
    public String inicio(Model model) {
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("categorias", categorias); //union de el codigo con la vista *> telefono, computadora, descripcion
        model.addAttribute("totalCategorias", categorias.size()); // >2
        return "/categoria/listado";
    }
     @GetMapping("/nuevo")
    public String categoriaNuevo(Categoria categoria) {
        return "/categoria/modifica";
    }
 
    @Autowired
    private FirebaseStorageServiceImpl firebaseStorageService;
    @PostMapping("/guardar")
    public String categoriaGuardar(Categoria categoria,
            @RequestParam("imagenFile") MultipartFile imagenFile) {        
        if (!imagenFile.isEmpty()) {
            categoriaService.save(categoria);
            categoria.setRutaImagen(
                    firebaseStorageService.cargaImagen(
                            imagenFile, 
                            "categoria", 
                            categoria.getIdCategoria()));
        }
        categoriaService.save(categoria);
        return "redirect:/categoria/listado";
    }
 
    @GetMapping("/eliminar/{idCategoria}")//localhost:8080/categoria/listado/eliminar/
    public String categoriaEliminar(Categoria categoria) {
        categoriaService.delete(categoria);
        return "redirect:/categoria/listado";
    }
 
    @GetMapping("/modificar/{idCategoria}")//localhost:8080/categoria/modificar/
    public String categoriaModificar(Categoria categoria, Model model) {
        categoria = categoriaService.getCategoria(categoria);
        model.addAttribute("categoria", categoria);
        return "/categoria/modifica";
    }   
}