package com.example.company.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.example.address.entity.AddressEntity;

class CompanyEntityTest {
    @Test
    void shouldCopyAndOverrideFieldsUsingWith() {
        // given
        CompanyAddressEntity original =
                new CompanyAddressEntity("Old St", "Old City", "Old State", "00000", "Old Country", 20L);

        // when
        AddressEntity copied = original.with("New St", null, "New State", null, "New Country");

        // then
        assertThat(copied).isInstanceOf(CompanyAddressEntity.class);
        assertThat(copied.getStreet()).isEqualTo("New St");
        assertThat(copied.getCity()).isEqualTo("Old City");
        assertThat(copied.getState()).isEqualTo("New State");
        assertThat(copied.getZipCode()).isEqualTo("00000");
        assertThat(copied.getCountry()).isEqualTo("New Country");
        assertThat(copied.getObjectId()).isEqualTo(20L);
    }
}
