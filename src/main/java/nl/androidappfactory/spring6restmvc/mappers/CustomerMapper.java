
package nl.androidappfactory.spring6restmvc.mappers;

import nl.androidappfactory.spring6restmvc.entities.Customer;
import nl.androidappfactory.spring6restmvc.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO customerDTO);

    CustomerDTO customerToCustomerDTO(Customer customer);
}
