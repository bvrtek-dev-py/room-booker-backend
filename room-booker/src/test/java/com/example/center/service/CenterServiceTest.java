package com.example.center.service;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.address.dto.AddressCreateRequest;
import com.example.address.entity.AddressEntity;
import com.example.address.type.ReferenceType;
import com.example.address.use_case.AddressCreate;
import com.example.address.use_case.AddressUpdate;
import com.example.auth.dto.JwtPayload;
import com.example.center.dto.request.CenterCreateRequest;
import com.example.center.dto.request.CenterUpdateRequest;
import com.example.center.dto.response.CenterResponse;
import com.example.center.entity.CenterAddressEntity;
import com.example.center.entity.CenterEntity;
import com.example.center.mapper.CenterEntityMapper;
import com.example.center.mapper.CenterResponseMapperFacade;
import com.example.center.repository.CenterAddressRepository;
import com.example.center.repository.CenterRepository;
import com.example.center.use_case.CenterGetById;
import com.example.common.exception.ObjectAlreadyExistsException;
import com.example.common.exception.PermissionDeniedException;
import com.example.company.entity.CompanyEntity;
import com.example.company.use_case.CompanyGetById;
import com.example.company.use_case.CompanyGetIfUserIsOwner;
import com.example.user.entity.UserEntity;
import com.example.user.role.UserRole;

@ExtendWith(MockitoExtension.class)
class CenterServiceTest {

    @Mock private CenterRepository centerRepository;
    @Mock private CenterEntityMapper centerEntityMapper;
    @Mock private CenterResponseMapperFacade centerResponseMapperFacade;
    @Mock private CompanyGetIfUserIsOwner companyGetIfUserIsOwner;
    @Mock private CompanyGetById companyGetById;
    @Mock private CenterGetById centerGetById;
    @Mock private AddressCreate addressCreate;
    @Mock private CenterAddressRepository centerAddressRepository;
    @Mock private AddressUpdate addressUpdate;

    @InjectMocks private CenterService centerService;

    @Test
    void shouldCreateCenter() {
        // given
        JwtPayload user = new JwtPayload(10L, "john@doe.com");
    
        AddressCreateRequest addrReq = new AddressCreateRequest(
            "Main Street", "Springfield", "IL", "12345", "USA", ReferenceType.CENTER
        );
    
        CenterCreateRequest request = new CenterCreateRequest(
            "My Center", "Desc", addrReq
        );
    
        UserEntity owner = new UserEntity(10L, "john", "pwd", "john@doe.com", UserRole.USER);
        CompanyEntity company = new CompanyEntity(5L, "Company", owner);
    
        given(companyGetIfUserIsOwner.execute(5L, 10L)).willReturn(company);
        given(centerRepository.existsByName("My Center")).willReturn(false);
    
        CenterEntity mapped = new CenterEntity(null, "My Center", "Desc", company);
        given(centerEntityMapper.map(request, company)).willReturn(mapped);
    
        CenterEntity persisted = new CenterEntity(99L, "My Center", "Desc", company);
        given(centerRepository.save(mapped)).willReturn(persisted);
    
        AddressEntity address = mock(AddressEntity.class);
        given(addressCreate.execute(addrReq, 99L)).willReturn(address);
    
        CenterResponse expectedResponse = new CenterResponse(99L, "My Center", "Desc", null, null);
        given(centerResponseMapperFacade.map(persisted, address)).willReturn(expectedResponse);
    
        // when
        CenterResponse result = centerService.create(request, 5L, user);
    
        // then
        assertThat(result).isEqualTo(expectedResponse);
    }
    

    @Test
    void shouldThrowWhenNameExistsOnCreate() {
        // given
        JwtPayload user = new JwtPayload(10L, "john@doe.com");
        CenterCreateRequest request = mock(CenterCreateRequest.class);
        given(request.getName()).willReturn("Dup");
        given(companyGetIfUserIsOwner.execute(anyLong(), anyLong())).willReturn(mock(CompanyEntity.class));
        given(centerRepository.existsByName("Dup")).willReturn(true);

        // when + then
        assertThatThrownBy(() -> centerService.create(request, 1L, user))
                .isInstanceOf(ObjectAlreadyExistsException.class);
    }

    @Test
    void shouldUpdateCenter() {
        // given
        AddressCreateRequest addrReq = mock(AddressCreateRequest.class);
        CenterUpdateRequest request = new CenterUpdateRequest("New Name", "New Desc", addrReq);

        UserEntity owner = new UserEntity(10L, "john", "pwd", "john@doe.com", UserRole.USER);
        CompanyEntity company = new CompanyEntity(5L, "Company", owner);
        CenterEntity existing = new CenterEntity(7L, "Old", "Old", company);

        given(centerGetById.execute(7L)).willReturn(existing);
        given(centerRepository.existsByName("New Name")).willReturn(false);

        CenterEntity updated = new CenterEntity(7L, "New Name", "New Desc", company);
        given(centerRepository.save(any())).willReturn(updated);

        AddressEntity address = mock(AddressEntity.class);
        given(addressUpdate.execute(addrReq, 7L)).willReturn(address);

        CenterResponse expected = mock(CenterResponse.class);
        given(centerResponseMapperFacade.map(updated, address)).willReturn(expected);

        // when
        CenterResponse result = centerService.update(7L, request, 10L);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void shouldThrowWhenNotOwnerOnUpdate() {
        // given
        CenterUpdateRequest request = new CenterUpdateRequest("Name", "Desc", mock(AddressCreateRequest.class));

        UserEntity owner = new UserEntity(1L, "john", "pwd", "john@doe.com", UserRole.USER);
        CompanyEntity company = new CompanyEntity(5L, "Company", owner);
        CenterEntity existing = new CenterEntity(7L, "Old", "Old", company);

        given(centerGetById.execute(7L)).willReturn(existing);

        // when + then
        assertThatThrownBy(() -> centerService.update(7L, request, 99L))
                .isInstanceOf(PermissionDeniedException.class);
    }

    @Test
    void shouldGetAllByCompanyId() {
        // given
        Long companyId = 5L;
        CompanyEntity company = mock(CompanyEntity.class);
        given(companyGetById.execute(companyId)).willReturn(company);

        CenterEntity center = mock(CenterEntity.class);
        given(centerRepository.findByCompany(company)).willReturn(List.of(center));

        CenterAddressEntity address = mock(CenterAddressEntity.class);
        given(centerAddressRepository.findByObjectId(anyLong())).willReturn(Optional.of(address));

        given(centerAddressRepository.findByObjectId(anyLong())).willReturn(Optional.of(address));

        CenterResponse response = mock(CenterResponse.class);
        given(centerResponseMapperFacade.map(center, address)).willReturn(response);

        // when
        List<CenterResponse> result = centerService.getAllByCompanyId(companyId);

        // then
        assertThat(result).containsExactly(response);
    }

    @Test
    void shouldGetById() {
        // given
        CenterEntity center = mock(CenterEntity.class);
        given(center.getId()).willReturn(77L);
        given(centerGetById.execute(77L)).willReturn(center);

        given(centerAddressRepository.findByObjectId(77L)).willReturn(Optional.empty());

        CenterResponse response = mock(CenterResponse.class);
        given(centerResponseMapperFacade.map(center, null)).willReturn(response);

        // when
        CenterResponse result = centerService.getById(77L);

        // then
        assertThat(result).isEqualTo(response);
    }

    @Test
    void shouldDeleteWhenOwner() {
        // given
        UserEntity owner = new UserEntity(10L, "john", "pwd", "john@doe.com", UserRole.USER);
        CompanyEntity company = new CompanyEntity(5L, "Company", owner);
        CenterEntity center = new CenterEntity(7L, "Name", "Desc", company);

        given(centerGetById.execute(7L)).willReturn(center);

        // when
        centerService.delete(7L, 10L);

        // then
        then(centerRepository).should().deleteById(7L);
    }

    @Test
    void shouldThrowWhenNotOwnerOnDelete() {
        // given
        UserEntity owner = new UserEntity(1L, "john", "pwd", "john@doe.com", UserRole.USER);
        CompanyEntity company = new CompanyEntity(5L, "Company", owner);
        CenterEntity center = new CenterEntity(7L, "Name", "Desc", company);

        given(centerGetById.execute(7L)).willReturn(center);

        // when + then
        assertThatThrownBy(() -> centerService.delete(7L, 99L))
                .isInstanceOf(PermissionDeniedException.class);
    }
}