package br.com.anymarket.sdk.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DiscountMetadata {

    List<DiscountType> type;

}
