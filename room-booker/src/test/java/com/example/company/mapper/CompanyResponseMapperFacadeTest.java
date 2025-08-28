package com.example.company.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.address.dto.AddressResponse;
import com.example.address.entity.AddressEntity;
import com.example.address.mapper.AddressResponseMapper;
import com.example.company.dto.response.CompanyResponse;
import com.example.company.entity.CompanyAddressEntity;
import com.example.company.entity.CompanyEntity;
import com.example.user.dto.response.UserResponse;
import com.example.user.entity.UserEntity;
import com.example.user.mapper.UserResponseMapper;
import com.example.user.role.UserRole;

@ExtendWith(MockitoExtension.class)
class CompanyResponseMapperFacadeTest {

    @Mock
    private CompanyResponseMapper companyResponseMapper;

    @Mock
    private UserResponseMapper userResponseMapper;

    @Mock
    private AddressResponseMapper addressResponseMapper;

    @InjectMocks
    private CompanyResponseMapperFacade facade;

    @Test
    void shouldMapCompanyEntityWithAddress() {
        // given
        UserEntity user = new UserEntity(1L, "john", "pwd", "john@doe.com", null);
        CompanyEntity company = new CompanyEntity(1L, "My Company", user);
        AddressEntity address = new CompanyAddressEntity("Street", "City", "State", "Zip", "Country", 1L);

        UserResponse userResponse = new UserResponse(1L, "john", "john@doe.com", UserRole.USER);
        AddressResponse addressResponse = new AddressResponse(1L, "Street", "City", "State", "Zip", "Country", 1L);
        CompanyResponse expectedResponse = new CompanyResponse(1L, "My Company", userResponse, addressResponse);

        given(userResponseMapper.map(user)).willReturn(userResponse);
        given(addressResponseMapper.map(address)).willReturn(addressResponse);
        given(companyResponseMapper.map(company, userResponse, addressResponse)).willReturn(expectedResponse);

        // when
        CompanyResponse result = facade.map(company, address);

        // then
        assertThat(result).isEqualTo(expectedResponse);
    }

    @Test
    void shouldMapCompanyEntityWithoutAddress() {
        // given
        UserEntity user = new UserEntity(1L, "john", "pwd", "john@doe.com", null);
        CompanyEntity company = new CompanyEntity(1L, "My Company", user);

        UserResponse userResponse = new UserResponse(1L, "john", "john@doe.com", UserRole.USER);
        CompanyResponse expectedResponse = new CompanyResponse(1L, "My Company", userResponse, null);

        given(userResponseMapper.map(user)).willReturn(userResponse);
        given(companyResponseMapper.map(company, userResponse, null)).willReturn(expectedResponse);

        // when
        CompanyResponse result = facade.map(company, null);

        // then
        assertThat(result).isEqualTo(expectedResponse);
    }
}
