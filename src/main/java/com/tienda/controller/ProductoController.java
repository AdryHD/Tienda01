package com.tienda.controller;
 
import com.tienda.domain.Producto;
import com.tienda.services.CategoriaService;
import com.tienda.services.ProductoService;
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
@RequestMapping("/producto")//localhost:8080/producto
public class ProductoController {
    @Autowired
    private ProductoService productoService;
        @Autowired
    private CategoriaService categoriaService;

 
    @GetMapping("/listado")//localhost:8080/producto/listado
    public String inicio(Model model) {
        var productos = productoService.getProductos(false);
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("categorias", categorias);
        model.addAttribute("productos", productos); //union de el codigo con la vista *> telefono, computadora, descripcion
        model.addAttribute("totalProductos", productos.size()); // >2
        return "/producto/listado";
    }
     @GetMapping("/nuevo")
    public String productoNuevo(Producto producto) {
        return "/producto/modifica";
    }
 
    @Autowired
    private FirebaseStorageServiceImpl firebaseStorageService;
    @PostMapping("/guardar")
    public String productoGuardar(Producto producto,
            @RequestParam("imagenFile") MultipartFile imagenFile) {        
        if (!imagenFile.isEmpty()) {
            productoService.save(producto);
            producto.setRutaImagen(
                    firebaseStorageService.cargaImagen(
                            imagenFile, 
                            "producto", 
                            producto.getIdProducto()));
        }
        productoService.save(producto);
        return "redirect:/producto/listado";
    }
 
    @GetMapping("/eliminar/{idProducto}")//localhost:8080/producto/listado/eliminar/
    public String productoEliminar(Producto producto) {
        productoService.delete(producto);
        return "redirect:/producto/listado";
    }
 
    @GetMapping("/modificar/{idProducto}")//localhost:8080/producto/modificar/
    public String productoModificar(Producto producto, Model model) {
        producto = productoService.getProducto(producto);
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("categorias", categorias);
        model.addAttribute("producto", producto);
        return "/producto/modifica";
    }   
}