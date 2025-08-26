package com.example.center.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.address.dto.AddressResponse;
import com.example.address.entity.AddressEntity;
import com.example.address.mapper.AddressResponseMapper;
import com.example.center.dto.response.CenterResponse;
import com.example.center.entity.CenterEntity;
import com.example.company.dto.response.CompanyResponse;
import com.example.company.entity.CompanyEntity;
import com.example.company.mapper.CompanyResponseMapperFacade;

@ExtendWith(MockitoExtension.class)
class CenterResponseMapperFacadeTest {

    @Mock
    private CenterResponseMapper centerResponseMapper;

    @Mock
    private CompanyResponseMapperFacade companyResponseMapperFacade;

    @Mock
    private AddressResponseMapper addressResponseMapper;

    @InjectMocks
    private CenterResponseMapperFacade facade;

    @Test
    void shouldMapCenterWithAddress() {
        // given
        CenterEntity centerEntity = mock(CenterEntity.class);
        CompanyEntity companyEntity = mock(CompanyEntity.class);
        AddressEntity addressEntity = mock(AddressEntity.class);

        CompanyResponse companyResponse = mock(CompanyResponse.class);
        AddressResponse addressResponse = mock(AddressResponse.class);
        CenterResponse expectedResponse = mock(CenterResponse.class);

        given(centerEntity.getCompany()).willReturn(companyEntity);
        given(companyResponseMapperFacade.map(companyEntity, null)).willReturn(companyResponse);
        given(addressResponseMapper.map(addressEntity)).willReturn(addressResponse);
        given(centerResponseMapper.map(centerEntity, companyResponse, addressResponse)).willReturn(expectedResponse);

        // when
        CenterResponse result = facade.map(centerEntity, addressEntity);

        // then
        assertThat(result).isEqualTo(expectedResponse);
        verify(centerResponseMapper).map(centerEntity, companyResponse, addressResponse);
        verify(companyResponseMapperFacade).map(companyEntity, null);
        verify(addressResponseMapper).map(addressEntity);
    }

    @Test
    void shouldMapCenterWithoutAddress() {
        // given
        CenterEntity centerEntity = mock(CenterEntity.class);
        CompanyEntity companyEntity = mock(CompanyEntity.class);

        CompanyResponse companyResponse = mock(CompanyResponse.class);
        CenterResponse expectedResponse = mock(CenterResponse.class);

        given(centerEntity.getCompany()).willReturn(companyEntity);
        given(companyResponseMapperFacade.map(companyEntity, null)).willReturn(companyResponse);
        given(centerResponseMapper.map(centerEntity, companyResponse, null)).willReturn(expectedResponse);

        // when
        CenterResponse result = facade.map(centerEntity, null);

        // then
        assertThat(result).isEqualTo(expectedResponse);
        verify(centerResponseMapper).map(centerEntity, companyResponse, null);
        verify(companyResponseMapperFacade).map(companyEntity, null);
        verifyNoInteractions(addressResponseMapper);
    }
}