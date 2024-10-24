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
        Person person = addressFromRepo.getPerson();
        addressFromRepo.setPerson(null);

        personService.savePerson(person);
        addressRepository.delete(addressFromRepo);
    }

    @Override
    public void connectAddressToPerson(Integer permAddressId, Integer tempAddressId, Integer personId) {
        Address permanentAddress = findAddressById(permAddressId);
        Address temporaryAddress = findAddressById(tempAddressId);
        Person personFromRepo = personService.findPersonFromRepoById(personId);


        if (personFromRepo.getPermanentAddress() == null || personFromRepo.getTemporaryAddress() == null) {

            if (temporaryAddress.getPerson() != null && !temporaryAddress.getPerson().getId().equals(personId)) {
                throw new AddressAlreadyInUseException(tempAddressId);
            } else if (permanentAddress.getPerson() != null && !permanentAddress.getPerson().getId().equals(personId)) {
                throw new AddressAlreadyInUseException(permAddressId);
            }

            moveIn(permanentAddress, temporaryAddress, personFromRepo);
        }


        if (permanentAddress.getPerson() == null || temporaryAddress.getPerson() == null) {
            moveIn(permanentAddress, temporaryAddress, personFromRepo);
        }
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

        if (address.equals(person.getPermanentAddress())) {
            person.setPermanentAddress(null);
            personService.savePerson(person);
            return address;
        } else if (address.equals(person.getTemporaryAddress())) {
            person.setTemporaryAddress(null);
            personService.savePerson(person);
            return address;
        }
        
        return address;
    }

    private void moveIn(Address permanentAddress, Address temporaryAddress, Person person) {
        Address currentPermanentAddress = person.getPermanentAddress();
        Address currentTemporaryAddress = person.getTemporaryAddress();

        deletePersonReference(currentPermanentAddress);
        deletePersonReference(currentTemporaryAddress);


        permanentAddress.setAddressType(AddressType.PERMANENT);
        permanentAddress.setPerson(person);
        temporaryAddress.setAddressType(AddressType.TEMPORARY);
        temporaryAddress.setPerson(person);

        addressRepository.save(permanentAddress);
        addressRepository.save(temporaryAddress);
        person.setPermanentAddress(permanentAddress);
        person.setTemporaryAddress(temporaryAddress);

        personService.savePerson(person);
    }

    private void deletePersonReference(Address address) {
        if (address != null) {
            address.setPerson(null);
            address.setAddressType(null);
            addressRepository.save(address);
        }
    }


}
