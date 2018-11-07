package es.poc.catalogviewservice;

import es.poc.catalogviewservice.backend.domain.CatalogView;
import es.poc.common.model.CatalogEntryInfo;
import es.poc.common.model.Money;
import lombok.val;

public class TestUtils {

  public static CatalogView newCatalogView(String suffix, int price){
    val info= CatalogEntryInfo.of(
      "ulr"+suffix,
      "name"+suffix,
      "desc"+suffix,
      Money.of(price));

    return new CatalogView("id"+suffix, info);

  }
}
