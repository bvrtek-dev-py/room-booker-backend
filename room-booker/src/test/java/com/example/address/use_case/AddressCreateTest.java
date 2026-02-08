package com.example.address.use_case;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.address.dto.AddressCreateRequest;
import com.example.address.entity.AddressEntity;
import com.example.address.factory.AddressEntityFactory;
import com.example.address.factory.AddressEntityFactoryStrategy;
import com.example.address.repository.AddressEntityRepositoryStrategy;
import com.example.address.repository.AddressRepository;
import com.example.address.type.ReferenceType;

@ExtendWith(MockitoExtension.class)
class AddressCreateTest {

    @Mock
    private AddressEntityFactoryStrategy addressEntityFactoryStrategy;

    @Mock
    private AddressEntityRepositoryStrategy addressEntityRepositoryStrategy;

    @Mock
    private AddressEntityFactory addressEntityFactory;

    @Mock
    private AddressRepository<AddressEntity> addressRepository;

    @InjectMocks
    private AddressCreate addressCreate;

    private AddressCreateRequest request;
    private AddressEntity entity;
    private AddressEntity savedEntity;

    @BeforeEach
    void setUp() {
        request = new AddressCreateRequest("Main St", "New York", "NY", "10001", "USA", ReferenceType.COMPANY);

        entity = new AddressEntity(1L, "Main St", "New York", "NY", "10001", "USA", 42L) {
            @Override
            public AddressEntity with(String street, String city, String state, String zipCode, String country) {
                return this;
            }
        };

        savedEntity = entity;
    }

    @Test
    void testExecute() {
        // given
        given(addressEntityFactoryStrategy.get(request.getReferenceType())).willReturn(addressEntityFactory);
        given(addressEntityFactory.make(request, 42L)).willReturn(entity);
        given(addressEntityRepositoryStrategy.get(request.getReferenceType())).willReturn(addressRepository);
        given(addressRepository.save(entity)).willReturn(savedEntity);

        // when
        AddressEntity result = addressCreate.execute(request, 42L);

        // then
        verify(addressEntityFactoryStrategy).get(request.getReferenceType());
        verify(addressEntityFactory).make(request, 42L);
        verify(addressEntityRepositoryStrategy).get(request.getReferenceType());
        verify(addressRepository).save(entity);

        assertThat(result).isEqualTo(savedEntity);
    }
}
