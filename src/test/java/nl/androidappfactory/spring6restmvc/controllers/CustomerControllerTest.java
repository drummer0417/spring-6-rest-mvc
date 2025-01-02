package nl.androidappfactory.spring6restmvc.controllers;

import nl.androidappfactory.spring6restmvc.model.Customer;
import nl.androidappfactory.spring6restmvc.services.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    @Test
    void getCustomer() throws Exception {
        // given
        Customer testCustomer = createTestCustomers().get(0);

        Mockito.when(customerService.getById(testCustomer.getId())).thenReturn(testCustomer);
        mockMvc.perform(get("/api/v1/customer/" + testCustomer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("1638fc3b-ba52-4bdb-8698-f8739728b415")))
                .andExpect(jsonPath("$.name", is("Hans")))
                .andExpect(jsonPath("$.version", is("1.0")));
    }

    @Test
    void getAllCustomers() throws Exception {
        List<Customer> testCustomers = createTestCustomers();

        when(customerService.getAll()).thenReturn(testCustomers);
        mockMvc.perform(get("/api/v1/customer")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(4)));
    }

    private List<Customer> createTestCustomers() {
        Customer customer1 = Customer.builder()
                .id(UUID.fromString("1638fc3b-ba52-4bdb-8698-f8739728b415"))
                .name("Hans")
                .version("1.0")
                .build();
        Customer customer2 = Customer.builder().id(UUID.randomUUID()).name("PietjeP").build();
        Customer customer3 = Customer.builder().id(UUID.randomUUID()).name("Jan met de Korteachternaam").build();
        Customer customer4 = Customer.builder().id(UUID.randomUUID()).name("Nobody").build();
        return Arrays.asList(customer1, customer2, customer3, customer4);
    }
}