package com.example.company.use_case;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.common.exception.PermissionDeniedException;
import com.example.company.entity.CompanyEntity;
import com.example.user.entity.UserEntity;

@ExtendWith(MockitoExtension.class)
class CompanyGetIfUserIsOwnerTest {

    @Mock
    private CompanyGetById companyGetById;

    @InjectMocks
    private CompanyGetIfUserIsOwner companyGetIfUserIsOwner;

    @Test
    void shouldReturnCompanyWhenUserIsOwner() {
        // given
        UserEntity owner = new UserEntity(10L, "john", "pwd", "john@doe.com", null);
        CompanyEntity company = new CompanyEntity(1L, "My Company", owner);
        given(companyGetById.execute(1L)).willReturn(company);

        // when
        CompanyEntity result = companyGetIfUserIsOwner.execute(1L, 10L);

        // then
        assertThat(result).isEqualTo(company);
    }

    @Test
    void shouldThrowPermissionDeniedWhenUserIsNotOwner() {
        // given
        UserEntity owner = new UserEntity(10L, "john", "pwd", "john@doe.com", null);
        CompanyEntity company = new CompanyEntity(1L, "My Company", owner);
        given(companyGetById.execute(1L)).willReturn(company);

        // when / then
        assertThrows(PermissionDeniedException.class, () -> companyGetIfUserIsOwner.execute(1L, 99L));
    }
}
