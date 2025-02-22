package hu.webuni.order.mapper;

import hu.webuni.order.dto.AddressDto;
import hu.webuni.order.model.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressDto addressToDto(Address address);
    Address dtoToAddress(AddressDto addressDto);

    hu.webuni.shipping.wsclient.Address addressToWsClientAddress(Address address);
}