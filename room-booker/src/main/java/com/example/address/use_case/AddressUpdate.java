package com.example.address.use_case;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.address.dto.AddressCreateRequest;
import com.example.address.entity.AddressEntity;
import com.example.address.repository.AddressEntityRepositoryStrategy;
import com.example.address.repository.AddressRepository;
import com.example.common.exception.ObjectNotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddressUpdate {
    private final AddressEntityRepositoryStrategy addressEntityRepositoryStrategy;

    @Transactional
    public AddressEntity execute(@NotNull AddressCreateRequest request, @NotNull Long objectId) {
        AddressRepository<AddressEntity> addressRepository = addressEntityRepositoryStrategy.get(request.getReferenceType());

        AddressEntity address = addressRepository.findByObjectId(objectId)
                .orElseThrow(() -> new ObjectNotFoundException("Address not found for objectId: " + objectId));

        AddressEntity updated = address.with(
                request.getStreet(), request.getCity(), request.getState(), request.getZipCode(), request.getCountry());

        return addressRepository.save(updated);
    }
}
