package es.poc.catalogservice.web;


import es.poc.catalogservice.backend.domain.CatalogEntry;
import es.poc.catalogservice.backend.service.CatalogService;
import io.eventuate.EntityNotFoundException;
import io.eventuate.EntityWithIdAndVersion;
import io.eventuate.EntityWithMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/catalog")
public class CatalogController {

  private CatalogService catalogService;

  @Autowired
  public CatalogController(CatalogService catalogService) {
    this.catalogService = catalogService;
  }

  @RequestMapping(method = RequestMethod.POST)
  public CreateCatalogEntryResponse createCatalogEntry(@RequestBody CreateCatalogEntryRequest createCustomerRequest) {
    EntityWithIdAndVersion<CatalogEntry> results =
      catalogService.createEntry(createCustomerRequest.getInfo());
    return new CreateCatalogEntryResponse(results.getEntityId());
  }

  @RequestMapping(value = "/{entryId}", method = RequestMethod.GET)
  public ResponseEntity<GetCatalogEntryResponse> getCatalogEntry(@PathVariable String entryId) {
    EntityWithMetadata<CatalogEntry> entryWithMetadata;
    try {
      entryWithMetadata = catalogService.findById(entryId);
    } catch (EntityNotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    CatalogEntry entry = entryWithMetadata.getEntity();
    GetCatalogEntryResponse response =
      new GetCatalogEntryResponse(entryWithMetadata.getEntityIdAndVersion().getEntityId(), entry.getInfo());
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
