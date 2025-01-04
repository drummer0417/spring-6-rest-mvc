package nl.androidappfactory.spring6restmvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.androidappfactory.spring6restmvc.model.CustomerDTO;
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
    ArgumentCaptor<CustomerDTO> customerCaptor;
    @Captor
    ArgumentCaptor<UUID> uuidCaptor;

    @MockitoBean
    private CustomerService customerService;

    List<CustomerDTO> testCustomerDTOS;

    @BeforeEach
    void setUp() {
        testCustomerDTOS = createTestCustomers();
    }

    @Test
    void getCustomerById() throws Exception {
        // given
        CustomerDTO testCustomerDTO = testCustomerDTOS.getFirst();

        Mockito.when(customerService.getById(testCustomerDTO.getId())).thenReturn(Optional.of(testCustomerDTO));
        mockMvc.perform(get(CUSTOMER_PATH_ID, testCustomerDTO.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("1638fc3b-ba52-4bdb-8698-f8739728b415")))
                .andExpect(jsonPath("$.name", is("Hans")))
                .andExpect(jsonPath("$.version", is("1.0")));
    }

    @Test
    void getAllCustomers() throws Exception {
        when(customerService.getAll()).thenReturn(testCustomerDTOS);
        mockMvc.perform(get(CUSTOMER_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(4)));
    }

    @Test
    public void postCustomer() throws Exception {
        CustomerDTO testCustomerDTO = testCustomerDTOS.getFirst();

        when(customerService.addCustomer(testCustomerDTO)).thenReturn(testCustomerDTO);
        mockMvc.perform(post(CUSTOMER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCustomerDTO)))
                .andExpect(header().exists("Location"))
                .andExpect(status().isCreated());
    }

    @Test
    public void putCustomer() throws Exception {
        CustomerDTO testCustomerDTO = testCustomerDTOS.getFirst();
        mockMvc.perform(put(CUSTOMER_PATH_ID, testCustomerDTO.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCustomerDTO)))
                .andExpect(status().isNoContent());

        verify(customerService, times(ONCE)).updateCustomer(testCustomerDTO.getId(), testCustomerDTO);
    }

    @Test
    public void deleteCustomer() throws Exception {
        CustomerDTO testCustomerDTO = testCustomerDTOS.getFirst();
        mockMvc.perform(delete(CUSTOMER_PATH_ID, testCustomerDTO.getId()))
                .andExpect(status().isNoContent());

        verify(customerService, times(ONCE)).delete(uuidCaptor.capture());
        assertThat(uuidCaptor.getValue()).isEqualTo(testCustomerDTO.getId());
    }

    @Test
    public void patchCusomer() throws Exception {
        CustomerDTO newCustomerDTO = CustomerDTO.builder().name("Klaas").build();
        CustomerDTO testcustomer = testCustomerDTOS.getFirst();
        mockMvc.perform(patch(CUSTOMER_PATH_ID, testcustomer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCustomerDTO)))
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

    private List<CustomerDTO> createTestCustomers() {
        CustomerDTO customerDTO1 = CustomerDTO.builder()
                .id(UUID.fromString("1638fc3b-ba52-4bdb-8698-f8739728b415"))
                .name("Hans")
                .version("1.0")
                .build();
        CustomerDTO customerDTO2 = CustomerDTO.builder().id(UUID.randomUUID()).name("PietjeP").build();
        CustomerDTO customerDTO3 = CustomerDTO.builder().id(UUID.randomUUID()).name("Jan met de Korteachternaam").build();
        CustomerDTO customerDTO4 = CustomerDTO.builder().id(UUID.randomUUID()).name("Nobody").build();
        return Arrays.asList(customerDTO1, customerDTO2, customerDTO3, customerDTO4);
    }
}