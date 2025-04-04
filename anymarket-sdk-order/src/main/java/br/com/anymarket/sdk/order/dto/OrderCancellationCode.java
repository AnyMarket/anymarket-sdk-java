package br.com.anymarket.sdk.order.dto;

public enum OrderCancellationCode {

    WITHOUT_STOCK("Sem estoque"),
    SHIPPING_ADDRESS("Endereço de envio indisponível para entrega"),
    CUSTOMER_EXCHANGE("Troca de produto"),
    GENERAL_ADJUSTMENT("Ajuste geral"),
    CUSTOMER_RETURN("Erro de preço"),
    BUYER_CANCELED("Cancelado pelo cliente"),
    OTHER("Outro"),
    OUT_OF_STOCK ("Sem estoque"),
    BUYER_NOT_ENOUGH_MONEY ("Comprador não tem dinheiro suficiente"),
    BUYER_REGRETS ("Comprador se arrependeu da operação"),
    SELLER_REGRETS ("Vendedor se arrependeu da operação"),
    BUYER_DID_NOT_ANSWER ("Comprador não responde"),
    THEY_NOT_HONORING_POLICIES ("Comprador não está atendendo às políticas"),
    OTHER_MY_RESPONSIBILITY ("Responsabilidade própria (outro motivo)"),
    OTHER_THEIR_RESPONSIBILITY ("Responsabilidade da contraparte (outro motivo)"),
    DUBIOUS_BUYER ("Comprador não confiável"),
    HIGH_ML_COMISSION ("Comissão de venda muito alta"),
    HIGH_TAXES ("Taxas muito altas"),
    SELLER_HOLIDAY ("Não há operação por férias"),
    UNFRIENDLY_SHIPMENT_POLICY ("Comprador não aceita política de envio"),
    UNAVAILABLE_PRODUCT ("Produto não disponível"),
    SELLER_ADDRESS_WITHDRAWAL ("Comprador prefere retirar pessoalmente"),
    WRONG_RECEIVER_ADDRESS ("Endereço de entrega errado"),
    HIGH_SHIPMENT_COST ("Custos de envio muito altos"),
    WRONG_SHIPMENT_COST ("Custo de envio mal calculado"),
    UNPRINTED_LABEL ("Etiqueta não pode ser impressa"),
    UNWITHDRAWN_PRODUCT_BY_DELIVER_COMPANY ("Empresa de envio não retirou o produto para entrega"),
    DENIED_PACKAGE ("Empresa de envio não aceita o pacote por causa do tamanho ou peso"),
    UNABLE_TO_READ_LABEL ("Empresa de envio não consegue ler a etiqueta"),
    MANUFACTURING_PRODUCT_NOT_FINISHED ("Produto manufaturado sem acabar"),
    SHIPMENT_PROBLEM_OTHER ("Envio teve algum outro problema"),
    DELIVERY_COMPANY_PROBLEM_OTHER ("Empresa de envio teve outro problema"),
    PRODUCT_PRICE_IS_WRONG("Preço do produto está errado"),
    WRONG_PRODUCT_PRICE("Preço do produto está errado");

    private String description;

    OrderCancellationCode(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
