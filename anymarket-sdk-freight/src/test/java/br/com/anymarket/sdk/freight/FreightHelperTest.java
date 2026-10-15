package br.com.anymarket.sdk.freight;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import br.com.anymarket.sdk.freight.dto.FreightDimension;
import br.com.anymarket.sdk.freight.dto.FreightProduct;
import br.com.anymarket.sdk.freight.dto.FreightResponse;

public class FreightHelperTest {

    @Test
    public void should_return_zero_when_product_list_is_empty() {
        FreightResponse response = mock(FreightResponse.class);
        when(response.getProducts()).thenReturn(Collections.emptyList());

        BigDecimal result = FreightHelper.getTotalWeight(response);

        assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    public void should_return_zero_when_all_products_have_null_dimensions() {
        FreightProduct product = mock(FreightProduct.class);
        when(product.getDimensions()).thenReturn(null);

        FreightResponse response = mock(FreightResponse.class);
        when(response.getProducts()).thenReturn(Collections.singletonList(product));

        BigDecimal result = FreightHelper.getTotalWeight(response);

        assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    public void should_return_weight_times_amount_for_single_product() {
        FreightDimension dimension = mock(FreightDimension.class);
        when(dimension.getWeight()).thenReturn(new BigDecimal("2.5"));

        FreightProduct product = mock(FreightProduct.class);
        when(product.getDimensions()).thenReturn(dimension);
        when(product.getAmount()).thenReturn(new BigDecimal("3"));

        FreightResponse response = mock(FreightResponse.class);
        when(response.getProducts()).thenReturn(Collections.singletonList(product));

        BigDecimal result = FreightHelper.getTotalWeight(response);

        assertEquals(new BigDecimal("7.5"), result);
    }

    @Test
    public void should_sum_weight_of_all_products() {
        FreightDimension dimension1 = mock(FreightDimension.class);
        when(dimension1.getWeight()).thenReturn(new BigDecimal("1.0"));

        FreightProduct product1 = mock(FreightProduct.class);
        when(product1.getDimensions()).thenReturn(dimension1);
        when(product1.getAmount()).thenReturn(new BigDecimal("2"));

        FreightDimension dimension2 = mock(FreightDimension.class);
        when(dimension2.getWeight()).thenReturn(new BigDecimal("3.0"));

        FreightProduct product2 = mock(FreightProduct.class);
        when(product2.getDimensions()).thenReturn(dimension2);
        when(product2.getAmount()).thenReturn(new BigDecimal("2"));

        FreightResponse response = mock(FreightResponse.class);
        when(response.getProducts()).thenReturn(Arrays.asList(product1, product2));

        BigDecimal result = FreightHelper.getTotalWeight(response);

        assertEquals(new BigDecimal("8.0"), result);
    }

    @Test
    public void should_skip_products_with_null_dimensions() {
        FreightDimension dimension = mock(FreightDimension.class);
        when(dimension.getWeight()).thenReturn(new BigDecimal("5.0"));

        FreightProduct productWithDimension = mock(FreightProduct.class);
        when(productWithDimension.getDimensions()).thenReturn(dimension);
        when(productWithDimension.getAmount()).thenReturn(BigDecimal.ONE);

        FreightProduct productWithoutDimension = mock(FreightProduct.class);
        when(productWithoutDimension.getDimensions()).thenReturn(null);

        FreightResponse response = mock(FreightResponse.class);
        when(response.getProducts()).thenReturn(Arrays.asList(productWithDimension, productWithoutDimension));

        BigDecimal result = FreightHelper.getTotalWeight(response);

        assertEquals(new BigDecimal("5.0"), result);
    }


    @Test
    public void should_return_full_price_when_product_weight_equals_total() {
        FreightDimension dimension = mock(FreightDimension.class);
        when(dimension.getWeight()).thenReturn(BigDecimal.ONE);

        FreightProduct product = mock(FreightProduct.class);
        when(product.getDimensions()).thenReturn(dimension);
        when(product.getAmount()).thenReturn(BigDecimal.ONE);

        BigDecimal result = FreightHelper.getPriceRatingByWeight(BigDecimal.ONE, new BigDecimal("100.00"), product);

        assertEquals(new BigDecimal("100.00"), result);
    }

    @Test
    public void should_return_half_price_when_weight_is_half_of_total() {
        FreightDimension dimension = mock(FreightDimension.class);
        when(dimension.getWeight()).thenReturn(BigDecimal.ONE);

        FreightProduct product = mock(FreightProduct.class);
        when(product.getDimensions()).thenReturn(dimension);
        when(product.getAmount()).thenReturn(BigDecimal.ONE);

        BigDecimal result = FreightHelper.getPriceRatingByWeight(new BigDecimal("2"), new BigDecimal("100.00"), product);

        assertEquals(new BigDecimal("50.00"), result);
    }

    @Test
    public void should_apply_half_up_rounding_to_scale2() {
        FreightDimension dimension = mock(FreightDimension.class);
        when(dimension.getWeight()).thenReturn(BigDecimal.ONE);

        FreightProduct product = mock(FreightProduct.class);
        when(product.getDimensions()).thenReturn(dimension);
        when(product.getAmount()).thenReturn(BigDecimal.ONE);

        BigDecimal result = FreightHelper.getPriceRatingByWeight(new BigDecimal("3"), new BigDecimal("10.00"), product);

        assertEquals(new BigDecimal("3.33"), result);
    }

    @Test
    public void should_multiply_proportional_price_by_amount() {
        FreightDimension dimension = mock(FreightDimension.class);
        when(dimension.getWeight()).thenReturn(new BigDecimal("2"));

        FreightProduct product = mock(FreightProduct.class);
        when(product.getDimensions()).thenReturn(dimension);
        when(product.getAmount()).thenReturn(new BigDecimal("3"));

        BigDecimal result = FreightHelper.getPriceRatingByWeight(new BigDecimal("4"), new BigDecimal("100.00"), product);

        assertEquals(new BigDecimal("150.00"), result);
    }
}
