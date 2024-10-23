package com.example.technical_test.service.impl;

import com.example.technical_test.domain.Address;
import com.example.technical_test.domain.Person;
import com.example.technical_test.dto.AddressDto;
import com.example.technical_test.dto.mapper.AddressMapper;
import com.example.technical_test.enums.AddressType;
import com.example.technical_test.exception.AddressAlreadyExistsException;
import com.example.technical_test.exception.AddressAlreadyInUseException;
import com.example.technical_test.exception.AddressNotFoundByIdException;
import com.example.technical_test.exception.NoEntriesFoundException;
import com.example.technical_test.repository.AddressRepository;
import com.example.technical_test.service.AddressService;
import com.example.technical_test.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final PersonService personService;


    @Override
    public AddressDto createAddress(AddressDto requestBody) {
        Address address = addressMapper.dtoToEntity(requestBody);
        try {
            return addressMapper.entityToDto(addressRepository.save(address));
        } catch (DataIntegrityViolationException e) {
            throw new AddressAlreadyExistsException(requestBody.city());
        }
    }

    @Override
    public AddressDto updateAddress(Integer id, AddressDto requestBody) {
        Address address = findAddressById(id);

        address.setZipCode(requestBody.zipCode());
        address.setCity(requestBody.city());
        address.setStreet(requestBody.street());
        address.setHouseNumber(requestBody.houseNumber());

        return addressMapper.entityToDto(addressRepository.save(address));
    }

    @Override
    public AddressDto getAddressById(Integer id) {
        return addressMapper.entityToDto(findAddressById(id));
    }

    @Override
    public List<AddressDto> getAllAddresses() {
        List<Address> addresses = addressRepository.findAll();

        if (addresses.isEmpty()) {
            throw new NoEntriesFoundException();
        }

        return addresses.stream()
                .map(addressMapper::entityToDto)
                .toList();
    }

    @Override
    public void deleteAddressById(Integer id) {
        Address addressFromRepo = deleteReferenceToAddress(findAddressById(id));
        addressFromRepo.setPerson(null);
        addressRepository.delete(addressFromRepo);
    }

    @Override
    public void connectAddressToPerson(Integer permAddressId, Integer tempAddressId, Integer personId) {
        Address permanentAddress = findAddressById(permAddressId);
        Address temporaryAddress = findAddressById(tempAddressId);
        Person personFromRepo = personService.findPersonFromRepoById(personId);

        if (permanentAddress.getPerson() != null) {
            throw new AddressAlreadyInUseException(permAddressId);
        } else if (temporaryAddress.getPerson() != null) {
            throw new AddressAlreadyInUseException(tempAddressId);
        }

        permanentAddress.setAddressType(AddressType.PERMANENT);
        permanentAddress.setPerson(personFromRepo);
        temporaryAddress.setAddressType(AddressType.TEMPORARY);
        temporaryAddress.setPerson(personFromRepo);

        addressRepository.save(permanentAddress);
        addressRepository.save(temporaryAddress);
        personFromRepo.setPermanentAddress(permanentAddress);
        personFromRepo.setTemporaryAddress(temporaryAddress);

        personService.savePerson(personFromRepo);
    }

    @Override
    public Address findAddressById(Integer id) {
        Optional<Address> addressFromRepo = addressRepository.findById(id);

        if (addressFromRepo.isEmpty()) {
            throw new AddressNotFoundByIdException(id);
        }
        return addressFromRepo.get();
    }

    @Override
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    private Address deleteReferenceToAddress(Address address) {
        Person person = address.getPerson();

        if (person == null) {
            return address;
        } else if (address.equals(person.getPermanentAddress())) {
            person.setPermanentAddress(null);
            personService.savePerson(person);
            return address;
        } else {
            person.setTemporaryAddress(null);
            personService.savePerson(person);
            return address;
        }
    }


}
