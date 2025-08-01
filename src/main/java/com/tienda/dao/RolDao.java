
package com.tienda.dao;

import com.tienda.domain.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author adryhd
 */
public interface RolDao extends JpaRepository<Rol, Long> {
    //para cuando se crea un usuario nuevo, aqui se asigna el rol
    
    
}
