package com.example.technical_test.ServiceImpl;

import com.example.technical_test.dto.mapper.AddressMapper;
import com.example.technical_test.repository.AddressRepository;
import com.example.technical_test.service.impl.AddressServiceImpl;
import com.example.technical_test.service.impl.PersonServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    }


}

