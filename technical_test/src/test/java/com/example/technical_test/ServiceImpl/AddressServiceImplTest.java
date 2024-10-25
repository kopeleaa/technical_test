package com.example.technical_test.ServiceImpl;

import com.example.technical_test.domain.Address;
import com.example.technical_test.dto.AddressDto;
import com.example.technical_test.dto.mapper.AddressMapper;
import com.example.technical_test.repository.AddressRepository;
import com.example.technical_test.service.impl.AddressServiceImpl;
import com.example.technical_test.service.impl.PersonServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressMapper addressMapper;

    @Mock
    private PersonServiceImpl personService;

    @InjectMocks
    private AddressServiceImpl addressService;

    @Test
    void givenNoAddressInDB_WhenCreateAddress_ThenCreateAddress() {
        AddressDto dto = new AddressDto(
                "1234",
                "London",
                "Apple",
                12
        );

        Address address = new Address();
        address.setZipCode("1234");
        address.setCity("London");
        address.setStreet("Apple");
        address.setHouseNumber(12);

        when(addressMapper.dtoToEntity(dto)).thenReturn(address);
        addressService.createAddress(dto);

        ArgumentCaptor<Address> dtoArgumentCaptor = ArgumentCaptor.forClass(Address.class);
        verify(addressRepository).save(dtoArgumentCaptor.capture());
        Address address1 = dtoArgumentCaptor.getValue();
        assertEquals(address.getCity(), address1.getCity());
        assertEquals(address.getStreet(), address1.getStreet());

    }


}

