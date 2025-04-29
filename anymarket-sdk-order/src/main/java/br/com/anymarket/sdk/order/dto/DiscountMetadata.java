package br.com.anymarket.sdk.order.dto;

import lombok.Data;

import java.util.List;

@Data
public class DiscountMetadata {

    List<DiscountType> type;

}
