package com.example.address.use_case;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.address.dto.AddressCreateRequest;
import com.example.address.entity.AddressEntity;
import com.example.address.repository.AddressEntityRepositoryStrategy;
import com.example.address.repository.AddressRepository;
import com.example.address.type.ReferenceType;

@ExtendWith(MockitoExtension.class)
class AddressUpdateTest {

    @Mock
    private AddressEntityRepositoryStrategy repositoryStrategy;

    @Mock
    private AddressRepository<AddressEntity> addressRepository;

    @InjectMocks
    private AddressUpdate addressUpdate;

    private AddressEntity addressEntity;
    

    @BeforeEach
    void setUp() {
        addressEntity = new AddressEntity(1L, "Old St", "Old City", "Old State", "00000", "Old Country", 42L) {
            @Override
            public AddressEntity with(String street, String city, String state, String zipCode, String country) {
                return new AddressEntity(getId(), street, city, state, zipCode, country, getObjectId()) {
                    @Override
                    public AddressEntity with(String s, String c, String st, String z, String co) {
                        return this;
                    }
                };
            }
        };
    }

    @Test
    void testExecute() {
        // given
        AddressCreateRequest request = mock(AddressCreateRequest.class);
        when(request.getReferenceType()).thenReturn(ReferenceType.COMPANY);
        when(request.getStreet()).thenReturn("New St");
        when(request.getCity()).thenReturn("New City");
        when(request.getState()).thenReturn("New State");
        when(request.getZipCode()).thenReturn("12345");
        when(request.getCountry()).thenReturn("New Country");

        when(repositoryStrategy.get(ReferenceType.COMPANY)).thenReturn(addressRepository);
        when(addressRepository.findByObjectId(42L)).thenReturn(Optional.of(addressEntity));
        when(addressRepository.save(any(AddressEntity.class))).thenAnswer(i -> i.getArgument(0));

        // when
        AddressEntity result = addressUpdate.execute(request, 42L);

        // then
        assertNotNull(result);
        assertEquals("New St", result.getStreet());
        assertEquals("New City", result.getCity());
        assertEquals("New State", result.getState());
        assertEquals("12345", result.getZipCode());
        assertEquals("New Country", result.getCountry());

        verify(addressRepository).findByObjectId(42L);
        verify(addressRepository).save(any(AddressEntity.class));
    }
}
