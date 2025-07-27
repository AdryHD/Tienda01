package com.tienda.services;
 
import com.tienda.domain.Producto; 
import java.util.List; 
import org.springframework.data.repository.query.Param;
 
public interface ProductoService {
    //asigna el metodo para despues implementarlo en el serviceImpl (Read)
    //CRUDS
    public List<Producto> getProductos(boolean activos);
    public Producto getProducto(Producto producto);
    public void save (Producto producto);
    public void delete (Producto producto);
    //consultas ammpliadas
    public List<Producto> findByPrecioBetweenOrderByDescripcion(double precioInf, double precioSup);
    //jpql
    public List<Producto> metodoJPQL(@Param("precioInf") double precioInf, @Param("precioSup") double precioSup);
    //nativo
    public List<Producto> metodoNativo(@Param("precioInf") double precioInf, @Param("precioSup") double precioSup);
}