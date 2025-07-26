package com.tienda.services.impl;

import com.tienda.dao.CategoriaDao;
import com.tienda.domain.Categoria;
import com.tienda.services.CategoriaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaServiceImpl implements CategoriaService {
    
    @Autowired //este autowride sobreescribe lo que hay en categoria DAO y conectarse con la base de datos (CRUD)
    private CategoriaDao categoriaDao;

    @Override
    @Transactional(readOnly=true) //tiene que ir a hacer algo a la BD
    public List<Categoria> getCategorias(boolean activos) {
        var lista=categoriaDao.findAll();
        if (activos) {
           lista.removeIf(e -> !e.isActivo());
        }
        return lista;
    }
    
   
@Override
@Transactional(readOnly=true)
public Categoria getCategoria(Categoria categoria) {
    return categoriaDao.findById(categoria.getIdCategoria()).orElse(null);
}

    @Override
    @Transactional
    public void save(Categoria categoria) {
        categoriaDao.save(categoria);
    }
    
    @Override
    @Transactional
    public void delete(Categoria categoria) {
        categoriaDao.delete(categoria);
    }
    
}