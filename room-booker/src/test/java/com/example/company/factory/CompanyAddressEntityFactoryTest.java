package com.example.company.factory;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.address.dto.AddressCreateRequest;
import com.example.address.entity.AddressEntity;
import com.example.company.entity.CompanyAddressEntity;

@ExtendWith(MockitoExtension.class)
class CompanyAddressEntityFactoryTest {

    @InjectMocks
    private CompanyAddressEntityFactory factory;

    @Test
    void shouldCreateCompanyAddressEntityFromRequest() {
        // given
        AddressCreateRequest request = mock(AddressCreateRequest.class);
        given(request.getStreet()).willReturn("Main St");
        given(request.getCity()).willReturn("Springfield");
        given(request.getState()).willReturn("IL");
        given(request.getZipCode()).willReturn("12345");
        given(request.getCountry()).willReturn("USA");

        Long objectId = 10L;

        // when
        AddressEntity result = factory.make(request, objectId);

        // then
        assertThat(result).isInstanceOf(CompanyAddressEntity.class);

        CompanyAddressEntity address = (CompanyAddressEntity) result;
        assertThat(address.getStreet()).isEqualTo("Main St");
        assertThat(address.getCity()).isEqualTo("Springfield");
        assertThat(address.getState()).isEqualTo("IL");
        assertThat(address.getZipCode()).isEqualTo("12345");
        assertThat(address.getCountry()).isEqualTo("USA");
        assertThat(address.getObjectId()).isEqualTo(10L);
    }
}