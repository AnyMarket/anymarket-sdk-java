package br.com.anymarket.sdk.order.dto.packages;

import br.com.anymarket.sdk.order.dto.OrderStatus;
import br.com.anymarket.sdk.order.dto.TrackingResource;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderPackageSendRequest {

    private final OrderStatus status;
    private final TrackingResource tracking;

    public OrderPackageSendRequest(TrackingResource tracking) {
        this.status = OrderStatus.PAID_WAITING_DELIVERY;
        this.tracking = tracking;
    }
}
