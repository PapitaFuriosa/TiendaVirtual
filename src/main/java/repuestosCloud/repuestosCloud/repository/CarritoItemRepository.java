package com.repuestos.repuestoscloud.repository;

import com.repuestos.repuestoscloud.entity.CarritoItem;
import com.repuestos.repuestoscloud.entity.CarritoItemId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarritoItemRepository extends JpaRepository<CarritoItem, CarritoItemId> {

    List<CarritoItem> findByIdCarrito(Long idCarrito);

    Optional<CarritoItem> findByIdCarritoAndIdProducto(Long idCarrito, Long idProducto); // ✅ ESTE ES EL QUE TE FALTA
}
