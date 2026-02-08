package com.example.address.use_case;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.address.dto.AddressCreateRequest;
import com.example.address.entity.AddressEntity;
import com.example.address.factory.AddressEntityFactory;
import com.example.address.factory.AddressEntityFactoryStrategy;
import com.example.address.repository.AddressEntityRepositoryStrategy;
import com.example.address.repository.AddressRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddressCreate {
    private final AddressEntityFactoryStrategy addressEntityFactoryStrategy;

    private final AddressEntityRepositoryStrategy addressEntityRepositoryStrategy;

    @Transactional
    public AddressEntity execute(@NotNull AddressCreateRequest request, @NotNull Long objectId) {
        AddressEntityFactory addressEntityFactory = addressEntityFactoryStrategy.get(request.getReferenceType());

        AddressEntity entity = addressEntityFactory.make(request, objectId);
        AddressRepository<AddressEntity> addressRepository = addressEntityRepositoryStrategy.get(request.getReferenceType());

        return addressRepository.save(entity);
    }
}
