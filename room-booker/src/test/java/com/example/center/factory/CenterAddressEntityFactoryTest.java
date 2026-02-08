package com.example.center.factory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.address.dto.AddressCreateRequest;
import com.example.address.entity.AddressEntity;
import com.example.center.entity.CenterAddressEntity;

@ExtendWith(MockitoExtension.class)
class CenterAddressEntityFactoryTest {

    @InjectMocks
    private CenterAddressEntityFactory factory;

    @Test
    void shouldCreateCenterAddressEntityFromRequest() {
        // given
        AddressCreateRequest request = mock(AddressCreateRequest.class);
        given(request.getStreet()).willReturn("Main Street");
        given(request.getCity()).willReturn("Springfield");
        given(request.getState()).willReturn("IL");
        given(request.getZipCode()).willReturn("12345");
        given(request.getCountry()).willReturn("USA");

        Long objectId = 42L;

        // when
        AddressEntity result = factory.make(request, objectId);

        // then
        assertThat(result).isInstanceOf(CenterAddressEntity.class);

        CenterAddressEntity address = (CenterAddressEntity) result;
        assertThat(address.getStreet()).isEqualTo("Main Street");
        assertThat(address.getCity()).isEqualTo("Springfield");
        assertThat(address.getState()).isEqualTo("IL");
        assertThat(address.getZipCode()).isEqualTo("12345");
        assertThat(address.getCountry()).isEqualTo("USA");
        assertThat(address.getObjectId()).isEqualTo(42L);
    }
}
