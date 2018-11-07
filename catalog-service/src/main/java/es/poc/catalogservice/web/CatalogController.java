package es.poc.catalogservice.web;


import es.poc.catalogservice.backend.domain.CatalogEntry;
import es.poc.catalogservice.backend.service.CatalogService;
import es.poc.common.model.CatalogEntryInfo;
import io.eventuate.EntityNotFoundException;
import io.eventuate.EntityWithIdAndVersion;
import io.eventuate.EntityWithMetadata;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

@RestController
@RequestMapping(value = "/catalog")
public class CatalogController {

  private CatalogService catalogService;

  @Autowired
  public CatalogController(CatalogService catalogService) {
    this.catalogService = catalogService;
  }

  @RequestMapping(method = RequestMethod.POST)
  public CreateCatalogEntryResponse createCatalogEntry(@Valid @RequestBody CreateCatalogEntryRequest req) {

    val info = CatalogEntryInfo.of(
      req.getImage(),
      req.getName(),
      req.getDescription(),
      req.getPrice());

    val results = catalogService.createEntry(info);
    return new CreateCatalogEntryResponse(results.getEntityId());
  }

  @RequestMapping(value = "/{entryId}", method = RequestMethod.PUT)
  public CreateCatalogEntryResponse putCatalogEntry(@PathVariable("entryId") String id,
                                                    @Valid @RequestBody CreateCatalogEntryRequest req) {

    val info = CatalogEntryInfo.of(
      req.getImage(),
      req.getName(),
      req.getDescription(),
      req.getPrice());

    val results = catalogService.updateEntry(id, info);
    return new CreateCatalogEntryResponse(results.getEntityId());
  }


  @RequestMapping(value = "/{entryId}", method = DELETE)
  public CreateCatalogEntryResponse  deleteCatalogEntry(@PathVariable("entryId") String id){
    val results = catalogService.deleteEntry(id);
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
    return buildGetCatalogEntryResponse(entryWithMetadata);
  }

  private ResponseEntity<GetCatalogEntryResponse> buildGetCatalogEntryResponse(
    EntityWithMetadata<CatalogEntry> entryWithMetadata) {

    CatalogEntry entry = entryWithMetadata.getEntity();
    GetCatalogEntryResponse response = GetCatalogEntryResponse
      .builder()
      .entryId(entryWithMetadata.getEntityIdAndVersion().getEntityId())
      .entryInfo(entry.getInfo())
      .build();

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
