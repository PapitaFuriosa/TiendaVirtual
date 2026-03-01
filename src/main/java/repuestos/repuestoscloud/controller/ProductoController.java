package repuestos.repuestoscloud.controller;
import repuestos.repuestoscloud.repository.MarcaVehiculoRepository;
import repuestos.repuestoscloud.repository.ProductoRepository;
import repuestos.repuestoscloud.repository.TipoRepuestoRepository;
import repuestos.repuestoscloud.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductoController {

  private final ProductoService productoService;
  private final MarcaVehiculoRepository marcaRepo;
  private final TipoRepuestoRepository tipoRepo;
  private final ProductoRepository productoRepo;

  public ProductoController(ProductoService ps, MarcaVehiculoRepository mr, TipoRepuestoRepository tr, ProductoRepository pr) {
    this.productoService = ps; this.marcaRepo = mr; this.tipoRepo = tr; this.productoRepo = pr;
  }

  @GetMapping({"/", "/productos"})
  public String productos(@RequestParam(required=false) String q,
                          @RequestParam(required=false) String marca,
                          @RequestParam(required=false) String tipo,
                          Model model) {
    model.addAttribute("lista", productoService.listar(q, marca, tipo));
    model.addAttribute("marcas", marcaRepo.findAllByOrderByNombreAsc());
    model.addAttribute("tipos", tipoRepo.findAllByOrderByNombreAsc());
    model.addAttribute("q", q);
    model.addAttribute("marca", marca);
    model.addAttribute("tipo", tipo);
    return "productos";
  }

  @GetMapping("/producto/{id}")
  public String detalle(@PathVariable Long id, Model model) {
    model.addAttribute("p", productoRepo.findById(id).orElseThrow());
    return "ProductoDetalle";
  }
}