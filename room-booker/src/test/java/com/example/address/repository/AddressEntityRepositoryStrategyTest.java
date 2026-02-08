package com.example.address.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.address.entity.AddressEntity;
import com.example.address.type.ReferenceType;
import com.example.center.repository.CenterAddressRepository;
import com.example.company.repository.CompanyAddressRepository;

@ExtendWith(MockitoExtension.class)
class AddressEntityRepositoryStrategyTest {

    @Mock
    private CompanyAddressRepository companyAddressRepository;

    @Mock
    private CenterAddressRepository centerAddressRepository;

    @InjectMocks
    private AddressEntityRepositoryStrategy strategy;

    @Test
    void givenCompanyType_whenGet_thenReturnCompanyAddressRepository() {
        // given
        ReferenceType type = ReferenceType.COMPANY;

        // when
        AddressRepository<? extends AddressEntity> result = strategy.get(type);

        // then
        assertThat(result).isSameAs(companyAddressRepository);
    }

    @Test
    void givenCenterType_whenGet_thenReturnCenterAddressRepository() {
        // given
        ReferenceType type = ReferenceType.CENTER;

        // when
        AddressRepository<? extends AddressEntity> result = strategy.get(type);

        // then
        assertThat(result).isSameAs(centerAddressRepository);
    }
}
