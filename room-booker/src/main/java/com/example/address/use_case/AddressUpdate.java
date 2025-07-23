package com.example.address.use_case;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.address.dto.AddressCreateRequest;
import com.example.address.entity.AddressEntity;
import com.example.address.repository.AddressEntityRepositoryStrategy;
import com.example.address.repository.AddressRepository;

@Component
public class AddressUpdate {
    @Autowired
    private AddressEntityRepositoryStrategy addressEntityRepositoryStrategy;

    public AddressEntity execute(AddressCreateRequest request, Long objectId) {
        AddressRepository<AddressEntity> addressRepository = addressEntityRepositoryStrategy.get(request.getReferenceType());

        AddressEntity address = addressRepository.findByObjectId(objectId).orElseThrow();

        AddressEntity updated = address.with(
            request.getStreet(),
            request.getCity(),
            request.getState(),
            request.getZipCode(),
            request.getCountry()
        );

        return addressRepository.save(updated);
    }
}
