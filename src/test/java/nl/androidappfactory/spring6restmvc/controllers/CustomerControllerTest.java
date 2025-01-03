package nl.androidappfactory.spring6restmvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.androidappfactory.spring6restmvc.model.Customer;
import nl.androidappfactory.spring6restmvc.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static nl.androidappfactory.spring6restmvc.controllers.CustomerController.CUSTOMER_PATH;
import static nl.androidappfactory.spring6restmvc.controllers.CustomerController.CUSTOMER_PATH_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CustomerController.class)
class CustomerControllerTest {

    private static final int ONCE = 1;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Captor
    ArgumentCaptor<Customer> customerCaptor;
    @Captor
    ArgumentCaptor<UUID> uuidCaptor;

    @MockitoBean
    private CustomerService customerService;

    List<Customer> testCustomers;

    @BeforeEach
    void setUp() {
        testCustomers = createTestCustomers();
    }

    @Test
    void getCustomer() throws Exception {
        // given
        Customer testCustomer = testCustomers.getFirst();

        Mockito.when(customerService.getById(testCustomer.getId())).thenReturn(Optional.of(testCustomer));
        mockMvc.perform(get(CUSTOMER_PATH_ID, testCustomer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("1638fc3b-ba52-4bdb-8698-f8739728b415")))
                .andExpect(jsonPath("$.name", is("Hans")))
                .andExpect(jsonPath("$.version", is("1.0")));
    }

    @Test
    void getAllCustomers() throws Exception {
        when(customerService.getAll()).thenReturn(testCustomers);
        mockMvc.perform(get(CUSTOMER_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(4)));
    }

    @Test
    public void postCustomer() throws Exception {
        Customer testCustomer = testCustomers.getFirst();

        when(customerService.addCustomer(testCustomer)).thenReturn(testCustomer);
        mockMvc.perform(post(CUSTOMER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCustomer)))
                .andExpect(header().exists("Location"))
                .andExpect(status().isCreated());
    }

    @Test
    public void putCustomer() throws Exception {
        Customer testCustomer = testCustomers.getFirst();
        mockMvc.perform(put(CUSTOMER_PATH_ID, testCustomer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCustomer)))
                .andExpect(status().isNoContent());

        verify(customerService, times(ONCE)).updateCustomer(testCustomer.getId(), testCustomer);
    }

    @Test
    public void deleteCustomer() throws Exception {
        Customer testCustomer = testCustomers.getFirst();
        mockMvc.perform(delete(CUSTOMER_PATH_ID,testCustomer.getId()))
                .andExpect(status().isNoContent());

        verify(customerService, times(ONCE)).delete(uuidCaptor.capture());
        assertThat(uuidCaptor.getValue()).isEqualTo(testCustomer.getId());
    }

    @Test
    public void patchCusomer() throws Exception {
        Customer newCustomer  = Customer.builder().name("Klaas").build();
        Customer testcustomer = testCustomers.getFirst();
        mockMvc.perform(patch(CUSTOMER_PATH_ID, testcustomer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCustomer)))
                .andExpect(status().isNoContent());

        verify(customerService, times(ONCE)).patchCustomer(uuidCaptor.capture(), customerCaptor.capture());
        assertThat(uuidCaptor.getValue()).isEqualTo(testcustomer.getId());
        assertThat(customerCaptor.getValue().getName()).isEqualTo("Klaas");
    }

    @Test
    public void customerNotFoundException() throws Exception {
        when(customerService.getById(any(UUID.class))).thenReturn(Optional.empty());

        mockMvc.perform(get(CUSTOMER_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void customerIllegalArgumentException() throws Exception {
        when(customerService.getById(any(UUID.class))).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(get(CUSTOMER_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void customerRuntimeException() throws Exception {
        when(customerService.getById(any(UUID.class))).thenThrow(ArrayIndexOutOfBoundsException.class);

        mockMvc.perform(get(CUSTOMER_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isInternalServerError());
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