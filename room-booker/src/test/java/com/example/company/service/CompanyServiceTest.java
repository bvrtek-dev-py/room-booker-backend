package com.example.company.service;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.address.dto.AddressCreateRequest;
import com.example.address.entity.AddressEntity;
import com.example.address.type.ReferenceType;
import com.example.address.use_case.AddressCreate;
import com.example.auth.dto.JwtPayload;
import com.example.common.exception.PermissionDeniedException;
import com.example.company.dto.request.CompanyCreateRequest;
import com.example.company.dto.request.CompanyUpdateRequest;
import com.example.company.dto.response.CompanyResponse;
import com.example.company.entity.CompanyAddressEntity;
import com.example.company.entity.CompanyEntity;
import com.example.company.factory.CompanyEntityFactory;
import com.example.company.mapper.CompanyEntityMapper;
import com.example.company.mapper.CompanyResponseMapperFacade;
import com.example.company.repository.CompanyAddressRepository;
import com.example.company.repository.CompanyRepository;
import com.example.user.entity.UserEntity;
import com.example.user.role.UserRole;
import com.example.user.use_case.UserGetById;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private CompanyEntityFactory companyModelFactory;

    @Mock
    private CompanyEntityMapper companyModelMapper;

    @Mock
    private UserGetById userGetById;

    @Mock
    private CompanyResponseMapperFacade companyResponseMapper;

    @Mock
    private AddressCreate addressCreate;

    @Mock
    private CompanyAddressRepository companyAddressRepository;

    @InjectMocks
    private CompanyService companyService;

    @Test
    void shouldCreateCompany() {
        JwtPayload userPayload = new JwtPayload(10L, "john@doe.com");
        UserEntity userEntity = new UserEntity(10L, "john", "pwd", "john@doe.com", UserRole.USER);

        AddressCreateRequest addressRequest = new AddressCreateRequest(
                "Street 1", "City", "State", "12345", "Country", ReferenceType.COMPANY);

        CompanyCreateRequest companyRequest = new CompanyCreateRequest("My Company", addressRequest);

        given(userGetById.execute(10L)).willReturn(userEntity);

        CompanyEntity mappedEntity = new CompanyEntity(null, "My Company", userEntity);
        given(companyModelMapper.map(companyRequest, userEntity)).willReturn(mappedEntity);

        given(companyRepository.existsByName("My Company")).willReturn(false);

        CompanyEntity savedEntity = new CompanyEntity(1L, "My Company", userEntity);
        given(companyRepository.save(mappedEntity)).willReturn(savedEntity);

        AddressEntity addressEntity = mock(AddressEntity.class);
        given(addressCreate.execute(addressRequest, 1L)).willReturn(addressEntity);

        CompanyResponse expectedResponse = mock(CompanyResponse.class);
        given(companyResponseMapper.map(savedEntity, addressEntity)).willReturn(expectedResponse);

        CompanyResponse result = companyService.create(companyRequest, userPayload);

        assertThat(result).isEqualTo(expectedResponse);
    }

    @Test
    void shouldUpdateCompany() {
        JwtPayload userPayload = new JwtPayload(10L, "john@doe.com");
        UserEntity userEntity = new UserEntity(10L, "john", "pwd", "john@doe.com", UserRole.USER);

        AddressCreateRequest addressRequest = new AddressCreateRequest(
                "Street 1", "City", "State", "12345", "Country", ReferenceType.COMPANY);

        CompanyUpdateRequest updateRequest = new CompanyUpdateRequest("Updated Company", addressRequest);

        CompanyEntity existing = new CompanyEntity(1L, "Old Company", userEntity);

        given(companyRepository.existsByName("Updated Company")).willReturn(false);
        given(userGetById.execute(10L)).willReturn(userEntity);
        given(companyRepository.findById(1L)).willReturn(Optional.of(existing));
        given(companyModelFactory.make(1L, "Updated Company", userEntity))
                .willReturn(new CompanyEntity(1L, "Updated Company", userEntity));

        CompanyEntity updated = new CompanyEntity(1L, "Updated Company", userEntity);
        given(companyRepository.save(updated)).willReturn(updated);

        AddressEntity addressEntity = mock(AddressEntity.class);
        given(companyAddressRepository.findByObjectId(1L)).willReturn(Optional.empty());
        given(addressCreate.execute(addressRequest, 1L)).willReturn(addressEntity);

        CompanyResponse expected = mock(CompanyResponse.class);
        given(companyResponseMapper.map(updated, addressEntity)).willReturn(expected);

        CompanyResponse result = companyService.update(1L, updateRequest, userPayload);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void shouldGetAllCompanies() {
        UserEntity userEntity = new UserEntity(10L, "john", "pwd", "john@doe.com", UserRole.USER);
        CompanyEntity company = new CompanyEntity(1L, "Company", userEntity);
        given(companyRepository.findAll()).willReturn(List.of(company));

        CompanyAddressEntity address = mock(CompanyAddressEntity.class);
        given(companyAddressRepository.findByObjectId(1L)).willReturn(Optional.of(address));

        CompanyResponse expected = mock(CompanyResponse.class);
        given(companyResponseMapper.map(company, address)).willReturn(expected);

        List<CompanyResponse> result = companyService.getAllCompanies();

        assertThat(result).containsExactly(expected);
    }

    @Test
    void shouldGetCompanyById() {
        UserEntity userEntity = new UserEntity(10L, "john", "pwd", "john@doe.com", UserRole.USER);
        CompanyEntity company = new CompanyEntity(1L, "Company", userEntity);
        given(companyRepository.findById(1L)).willReturn(Optional.of(company));

        CompanyAddressEntity address = mock(CompanyAddressEntity.class);
        given(companyAddressRepository.findByObjectId(1L)).willReturn(Optional.of(address));

        CompanyResponse expected = mock(CompanyResponse.class);
        given(companyResponseMapper.map(company, address)).willReturn(expected);

        CompanyResponse result = companyService.getById(1L);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void shouldDeleteCompany() {
        JwtPayload userPayload = new JwtPayload(10L, "john@doe.com");
        UserEntity userEntity = new UserEntity(10L, "john", "pwd", "john@doe.com", UserRole.USER);
        CompanyEntity company = new CompanyEntity(1L, "Company", userEntity);

        given(companyRepository.findById(1L)).willReturn(Optional.of(company));

        companyService.delete(1L, userPayload);

        verify(companyRepository).deleteById(1L);
    }

    @Test
    void shouldThrowIfDeleteNotOwner() {
        JwtPayload userPayload = new JwtPayload(99L, "other@doe.com");
        UserEntity owner = new UserEntity(10L, "john", "pwd", "john@doe.com", UserRole.USER);
        CompanyEntity company = new CompanyEntity(1L, "Company", owner);

        given(companyRepository.findById(1L)).willReturn(Optional.of(company));

        assertThrows(PermissionDeniedException.class, () -> companyService.delete(1L, userPayload));
    }
}