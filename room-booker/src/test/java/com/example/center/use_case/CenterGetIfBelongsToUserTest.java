package com.example.center.use_case;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.center.entity.CenterEntity;
import com.example.common.exception.PermissionDeniedException;
import com.example.company.entity.CompanyEntity;
import com.example.user.entity.UserEntity;

@ExtendWith(MockitoExtension.class)
class CenterGetIfBelongsToUserTest {

    @Mock
    private CenterGetById centerGetById;

    @InjectMocks
    private CenterGetIfBelongsToUser centerGetIfBelongsToUser;

    @Test
    void shouldReturnCenterWhenUserIsOwner() {
        // given
        UserEntity user = new UserEntity(1L, "john", "pwd", "john@doe.com", null);
        CompanyEntity company = new CompanyEntity(2L, "Company", user);
        CenterEntity center = new CenterEntity(10L, "Center", "Desc", company);

        given(centerGetById.execute(10L)).willReturn(center);

        // when
        CenterEntity result = centerGetIfBelongsToUser.execute(10L, 1L);

        // then
        assertThat(result).isEqualTo(center);
    }

    @Test
    void shouldThrowPermissionDeniedWhenUserIsNotOwner() {
        // given
        UserEntity user = new UserEntity(1L, "john", "pwd", "john@doe.com", null);
        CompanyEntity company = new CompanyEntity(2L, "Company", user);
        CenterEntity center = new CenterEntity(10L, "Center", "Desc", company);

        given(centerGetById.execute(10L)).willReturn(center);

        // when / then
        assertThrows(PermissionDeniedException.class, () -> centerGetIfBelongsToUser.execute(10L, 999L));
    }
}
