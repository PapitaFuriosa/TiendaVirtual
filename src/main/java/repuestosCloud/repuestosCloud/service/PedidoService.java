package com.repuestos.repuestoscloud.service;

import com.repuestos.repuestoscloud.entity.*;
import com.repuestos.repuestoscloud.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class PedidoService {

  private final PedidoRepository pedidoRepo;
  private final PedidoItemRepository pedidoItemRepo;
  private final CarritoRepository carritoRepo;
  private final CarritoItemRepository carritoItemRepo;
  private final ProductoRepository productoRepo;

  public PedidoService(PedidoRepository pr, PedidoItemRepository pir, CarritoRepository cr, CarritoItemRepository cir, ProductoRepository prod) {
    this.pedidoRepo = pr; this.pedidoItemRepo = pir;
    this.carritoRepo = cr; this.carritoItemRepo = cir; this.productoRepo = prod;
  }

  @Transactional
  public void confirmarCompra(Usuario u) {
    Carrito carrito = carritoRepo.findByUsuarioIdUsuarioAndEstado(u.getIdUsuario(), Carrito.EstadoCarrito.ACTIVO)
        .orElseThrow();

    List<CarritoItem> items = carritoItemRepo.findByIdCarrito(carrito.getIdCarrito());
    if (items.isEmpty()) return;

    Pedido pedido = new Pedido();
    pedido.setUsuario(u);
    pedido.setFecha(Instant.now());
    pedido.setEstado(Pedido.EstadoPedido.CONFIRMADO);
    pedido.setTotal(BigDecimal.ZERO);
    pedido = pedidoRepo.save(pedido);

    BigDecimal total = BigDecimal.ZERO;

    for (CarritoItem it : items) {
      Producto p = productoRepo.findById(it.getIdProducto()).orElseThrow();

      // validar stock
      int cant = it.getCantidad();
      if (p.getStock() < cant) cant = p.getStock();
      if (cant <= 0) continue;

      // descuento stock
      p.setStock(p.getStock() - cant);
      productoRepo.save(p);

      PedidoItem pi = new PedidoItem();
      pi.setIdPedido(pedido.getIdPedido());
      pi.setIdProducto(p.getIdProducto());
      pi.setPrecioUnitario(p.getPrecio());
      pi.setCantidad(cant);
      pedidoItemRepo.save(pi);

      total = total.add(p.getPrecio().multiply(BigDecimal.valueOf(cant)));
    }

    pedido.setTotal(total);
    pedidoRepo.save(pedido);

    // cerrar carrito
    carrito.setEstado(Carrito.EstadoCarrito.CERRADO);
    carritoRepo.save(carrito);
  }

  public List<Pedido> historial(Usuario u) {
    return pedidoRepo.findByIdUsuarioOrderByFechaDesc(u.getIdUsuario());
  }
}