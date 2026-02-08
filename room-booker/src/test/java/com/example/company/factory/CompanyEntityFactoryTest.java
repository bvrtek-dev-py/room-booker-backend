package com.example.company.factory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.company.entity.CompanyEntity;
import com.example.user.entity.UserEntity;

@ExtendWith(MockitoExtension.class)
class CompanyEntityFactoryTest {

    @InjectMocks
    private CompanyEntityFactory factory;

    @Test
    void shouldCreateCompanyEntity() {
        // given
        UserEntity user = mock(UserEntity.class);

        // when
        CompanyEntity result = factory.make(5L, "My Company", user);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(5L);
        assertThat(result.getName()).isEqualTo("My Company");
        assertThat(result.getUser()).isEqualTo(user);
    }
}
