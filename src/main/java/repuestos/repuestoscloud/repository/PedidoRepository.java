package repuestos.repuestoscloud.repository;

import repuestos.repuestoscloud.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // 👉 obtener pedidos del usuario ordenados por fecha descendente
    List<Pedido> findByUsuarioIdUsuarioOrderByFechaDesc(Long idUsuario);

}