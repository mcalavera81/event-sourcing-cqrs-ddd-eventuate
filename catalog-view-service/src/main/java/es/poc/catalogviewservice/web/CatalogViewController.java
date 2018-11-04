package es.poc.catalogviewservice.web;

import es.poc.catalogviewservice.backend.domain.CatalogView;
import es.poc.catalogviewservice.backend.repository.CatalogViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/catalog")
public class CatalogViewController {

  private CatalogViewRepository catalogViewRepository;

  @Autowired
  public CatalogViewController(CatalogViewRepository catalogViewRepository) {
    this.catalogViewRepository = catalogViewRepository;
  }


  @RequestMapping(value="/{entryId}", method= RequestMethod.GET)
  public ResponseEntity<CatalogView> getEntry(@PathVariable String entryId) {

    CatalogView ov = catalogViewRepository.findOne(entryId);
    if (ov == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(ov, HttpStatus.OK);
    }
  }

  @RequestMapping(method= RequestMethod.GET)
  public ResponseEntity<List<CatalogView>> getAll() {

    List<CatalogView> all = catalogViewRepository.findAll();
    return new ResponseEntity<>(all, HttpStatus.OK);
  }


}
