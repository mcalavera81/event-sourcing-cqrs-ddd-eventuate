package es.poc.orderservice.web.product;


import es.poc.orderservice.backend.domain.Order;
import es.poc.orderservice.backend.domain.Product;
import es.poc.orderservice.backend.repository.ProductRepository;
import es.poc.orderservice.backend.service.OrderService;
import io.eventuate.EntityNotFoundException;
import io.eventuate.EntityWithIdAndVersion;
import io.eventuate.EntityWithMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

  private ProductRepository productRepo;

  @Autowired
  public ProductController(ProductRepository productRepo) {
    this.productRepo = productRepo;
  }


  @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
  public ResponseEntity<Product> getProduct(@PathVariable String productId) {
    Product product= productRepo.findOne(productId);
    if (product == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(product, HttpStatus.OK);
    }
  }

}
