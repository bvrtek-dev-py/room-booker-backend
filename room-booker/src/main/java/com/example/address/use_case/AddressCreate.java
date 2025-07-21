package com.example.address.use_case;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.address.dto.AddressCreateRequest;
import com.example.address.entity.AddressEntity;
import com.example.address.factory.AddressEntityFactory;
import com.example.address.factory.AddressEntityFactoryStrategy;
import com.example.address.repository.AddressEntityRepositoryStrategy;
import com.example.address.repository.AddressRepository;

@Component
public class AddressCreate {
    @Autowired
    private AddressEntityFactoryStrategy addressEntityFactoryStrategy;
    @Autowired
    private AddressEntityRepositoryStrategy addressEntityRepositoryStrategy;

    public AddressEntity execute(AddressCreateRequest request, Long objectId) {
        AddressEntityFactory addressEntityFactory = addressEntityFactoryStrategy.get(request.getReferenceType());
        AddressEntity entity = addressEntityFactory.make(request, objectId);
        AddressRepository<AddressEntity> addressRepository = addressEntityRepositoryStrategy.get(request.getReferenceType());

        AddressEntity savedEntity = addressRepository.save(entity);

        return savedEntity;
    }
}
