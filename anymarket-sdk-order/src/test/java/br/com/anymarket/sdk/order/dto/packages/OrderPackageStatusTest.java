package br.com.anymarket.sdk.order.dto.packages;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OrderPackageStatusTest {

    @Test
    public void should_have_all_expected_values() {
        assertNotNull(OrderPackageStatus.CONFIRMED);
        assertNotNull(OrderPackageStatus.PENDING);
        assertNotNull(OrderPackageStatus.WAITTING_CONFIRMATION);
        assertNotNull(OrderPackageStatus.NOT_ACCEPTED);
        assertNotNull(OrderPackageStatus.ACCEPTED);
        assertNotNull(OrderPackageStatus.ERROR);
    }

    @Test
    public void value_of_should_return_correct_status() {
        assertEquals(OrderPackageStatus.CONFIRMED, OrderPackageStatus.valueOf("CONFIRMED"));
        assertEquals(OrderPackageStatus.PENDING, OrderPackageStatus.valueOf("PENDING"));
        assertEquals(OrderPackageStatus.WAITTING_CONFIRMATION, OrderPackageStatus.valueOf("WAITTING_CONFIRMATION"));
        assertEquals(OrderPackageStatus.NOT_ACCEPTED, OrderPackageStatus.valueOf("NOT_ACCEPTED"));
        assertEquals(OrderPackageStatus.ACCEPTED, OrderPackageStatus.valueOf("ACCEPTED"));
        assertEquals(OrderPackageStatus.ERROR, OrderPackageStatus.valueOf("ERROR"));
    }

    @Test
    public void values_should_return_all_statuses() {
        assertEquals(6, OrderPackageStatus.values().length);
    }
}
