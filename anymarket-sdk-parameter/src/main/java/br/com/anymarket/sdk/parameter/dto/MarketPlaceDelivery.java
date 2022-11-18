package br.com.anymarket.sdk.parameter.dto;

public enum MarketPlaceDelivery {
    AMAZON("Amazon Entregas", "AMAZON", "FBA_ONSITE,DBA"),
    B2W_NEW_API("B2W Entregas", "B2W_NEW_API", "B2W"),
    GFG("Dafiti Entregas", "GFG", "Dafiti"),
    MAGAZINE_LUIZA("Magalu Entregas", "MAGAZINE_LUIZA", "Magalu"),
    MERCADO_LIVRE("Mercado Envios", "MERCADO_LIVRE", "ME2");

    private String name;
    private String marketPlace;
    private String deliveryService;

    MarketPlaceDelivery(String name, String marketPlace, String deliveryService) {
        this.name = name;
        this.marketPlace = marketPlace;
        this.deliveryService = deliveryService;
    }

    public String getName() {
        return name;
    }

    public String getMarketPlace() {
        return marketPlace;
    }

    public String getDeliveryService() {
        return deliveryService;
    }
}
