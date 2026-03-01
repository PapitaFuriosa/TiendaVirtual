package com.repuestos.repuestoscloud.repository;

import com.repuestos.repuestoscloud.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByIdUsuarioOrderByFechaDesc(Long idUsuario); // ✅

}