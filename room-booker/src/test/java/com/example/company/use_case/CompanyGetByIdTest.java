package com.example.company.use_case;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.common.exception.ObjectNotFoundException;
import com.example.company.entity.CompanyEntity;
import com.example.company.repository.CompanyRepository;

@ExtendWith(MockitoExtension.class)
class CompanyGetByIdTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyGetById companyGetById;

    @Test
    void shouldReturnCompanyWhenExists() {
        // given
        CompanyEntity company = new CompanyEntity(1L, "My Company", null);
        given(companyRepository.findById(1L)).willReturn(Optional.of(company));

        // when
        CompanyEntity result = companyGetById.execute(1L);

        // then
        assertThat(result).isEqualTo(company);
    }

    @Test
    void shouldThrowExceptionWhenCompanyNotFound() {
        // given
        given(companyRepository.findById(1L)).willReturn(Optional.empty());

        // when / then
        assertThrows(ObjectNotFoundException.class, () -> companyGetById.execute(1L));
    }
}
