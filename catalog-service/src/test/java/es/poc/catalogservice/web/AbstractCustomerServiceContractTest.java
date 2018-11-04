package es.poc.catalogservice.web;

public abstract class AbstractCustomerServiceContractTest {
/*
  private CustomerService customerService;

  @Before
  public void setup() {
    customerService = mock(CustomerService.class);
    RestAssuredMockMvc.standaloneSetup(new CustomerController(customerService));
    when(customerService.findById("1223232-none")).thenThrow(new EntityNotFoundException());

    Customer customer = Aggregates.recreateAggregate(Customer.class,
            Collections.singletonList(new CustomerCreatedEvent("Fred", new Money(4566))), DefaultMissingApplyEventMethodStrategy.INSTANCE);

    EntityWithMetadata<Customer> result =
            new EntityWithMetadata<>(new EntityIdAndVersion("1223232", new Int128(1, 2)), Optional.empty(),
            Collections.emptyList(), customer);
    when(customerService.findById("1223232"))
            .thenReturn(result);
  }*/
}
