package com.example.address.factory;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.address.type.ReferenceType;
import com.example.center.factory.CenterAddressEntityFactory;
import com.example.company.factory.CompanyAddressEntityFactory;

@ExtendWith(MockitoExtension.class)
class AddressEntityFactoryStrategyTest {

    @Mock
    private CompanyAddressEntityFactory companyAddressEntityFactory;

    @Mock
    private CenterAddressEntityFactory centerAddressEntityFactory;

    @InjectMocks
    private AddressEntityFactoryStrategy strategy;

    @Test
    void testGetCompanyFactory() {
        // when 
        AddressEntityFactory factory = strategy.get(ReferenceType.COMPANY);

        // then
        assertSame(companyAddressEntityFactory, factory);
    }

    @Test
    void testGetCenterFactory() {
        // when
        AddressEntityFactory factory = strategy.get(ReferenceType.CENTER);

        // then
        assertSame(centerAddressEntityFactory, factory);
    }
}
