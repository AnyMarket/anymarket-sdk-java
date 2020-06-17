package br.com.anymarket.sdk;


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Strings.isNullOrEmpty;

public class MarketPlace {

    public static final MarketPlace CNOVA = new MarketPlace("CNOVA", "Via Varejo");
    public static final MarketPlace MAGENTO = new MarketPlace("MAGENTO", "Magento");
    public static final MarketPlace MERCADO_LIVRE = new MarketPlace("MERCADO_LIVRE", "Mercado Livre");
    public static final MarketPlace B2W = new MarketPlace("B2W", "B2W");
    public static final MarketPlace ECOMMERCE = new MarketPlace("ECOMMERCE", "E-Commerce");
    public static final MarketPlace WALMART = new MarketPlace("WALMART", "Walmart");
    public static final MarketPlace VTEX = new MarketPlace("VTEX", "VTEX");
    public static final MarketPlace GFG = new MarketPlace("GFG", "GFG - Dafiti");
    public static final MarketPlace LINIO = new MarketPlace("LINIO", "Linio");
    public static final MarketPlace NETSHOES = new MarketPlace("NETSHOES", "Netshoes");
    public static final MarketPlace MAGAZINE_LUIZA = new MarketPlace("MAGAZINE_LUIZA", "Magazine Luiza");
    public static final MarketPlace CARREFOUR = new MarketPlace("CARREFOUR", "Carrefour");
    public static final MarketPlace AMAZON = new MarketPlace("AMAZON", "Amazon");
    public static final MarketPlace BUSCAPE = new MarketPlace("BUSCAPE", "Buscapé");
    public static final MarketPlace ORACLECOMMERCE = new MarketPlace("ORACLECOMMERCE", "Oracle Commerce Cloud");
    public static final MarketPlace MADEIRA_MADEIRA = new MarketPlace("MADEIRA_MADEIRA", "Madeira Madeira");
    public static final MarketPlace CISSA_MAGAZINE = new MarketPlace("CISSA_MAGAZINE", "Cissa Magazine");
    public static final MarketPlace B2W_NEW_API = new MarketPlace("B2W_NEW_API", "B2W Nova API");
    public static final MarketPlace SARAIVA = new MarketPlace("SARAIVA", "Saraiva");
    public static final MarketPlace HUB_SALES = new MarketPlace("HUB_SALES", "Hub Sales");
    public static final MarketPlace ZOOM = new MarketPlace("ZOOM", "Zoom");
    public static final MarketPlace WEB_CONTINENTAL = new MarketPlace("WEB_CONTINENTAL", "Web Continental");
    public static final MarketPlace RICARDO_ELETRO = new MarketPlace("RICARDO_ELETRO", "Ricardo Eletro");
    public static final MarketPlace CENTAURO = new MarketPlace("CENTAURO", "Centauro");
    public static final MarketPlace PORTAL_DO_MEDICO = new MarketPlace("PORTAL_DO_MEDICO", "Portal do Médico");
    public static final MarketPlace TRAY = new MarketPlace("TRAY", "Tray");
    public static final MarketPlace CAMICADO = new MarketPlace("CAMICADO", "Camicado");
    public static final MarketPlace GALITHX = new MarketPlace("GALITHX", "Go Core Marketplace (Galithx e Gorila Z)");
    public static final MarketPlace COLOMBO = new MarketPlace("COLOMBO", "Lojas Colombo");
    public static final MarketPlace DIA_GROUP = new MarketPlace("DIA_GROUP", "Dia Group");
    public static final MarketPlace ELETRUM = new MarketPlace("ELETRUM", "Eletrum");
    public static final MarketPlace OPTEMAIS = new MarketPlace("OPTEMAIS", "Optemais");
    public static final MarketPlace EFACIL = new MarketPlace("EFACIL", "eFácil");
    public static final MarketPlace LEROY_MERLIN = new MarketPlace("LEROY_MERLIN", "Leroy Merlin");
    public static final MarketPlace COBASI = new MarketPlace("COBASI", "Cobasi");
    public static final MarketPlace CATWALK = new MarketPlace("CATWALK", "Catwalk");
    public static final MarketPlace WISH = new MarketPlace("WISH", "Wish");
    public static final MarketPlace HOME_TO_GO = new MarketPlace("HOME_TO_GO", "Home To Go");
    public static final MarketPlace CONNECT_PARTS = new MarketPlace("CONNECT_PARTS", "Connect Parts");
    public static final MarketPlace GARBARINO = new MarketPlace("GARBARINO", "Garbarino");
    public static final MarketPlace NOVO_MUNDO = new MarketPlace("NOVO_MUNDO", "Novo Mundo");
    public static final MarketPlace POSTHAUS = new MarketPlace("POSTHAUS", "Posthaus");
    public static final MarketPlace MARABRAZ = new MarketPlace("MARABRAZ", "Marabraz");
    public static final MarketPlace LOJA_VIVO = new MarketPlace("LOJA_VIVO", "Loja Vivo");
    public static final MarketPlace ESTANTE_VIRTUAL = new MarketPlace("ESTANTE_VIRTUAL", "Estante Virtual");
    public static final MarketPlace RI_HAPPY = new MarketPlace("RI_HAPPY", "Ri Happy");
    public static final MarketPlace FAST_SHOP = new MarketPlace("FAST_SHOP", "Fast Shop");
    public static final MarketPlace CYBELAR = new MarketPlace("CYBELAR", "Cybelar");
    public static final MarketPlace CLUBE_DE_MARCAS = new MarketPlace("CLUBE_DE_MARCAS", "Clube de Marcas");
    public static final MarketPlace C_A = new MarketPlace("C_A", "C&A");

    private static final Map<String, MarketPlace> MARKETPLACE_MAP = new HashMap<>();

    static {
        for (Field field : MarketPlace.class.getDeclaredFields()) {
            if (field.getType().equals(MarketPlace.class)) {
                try {
                    MarketPlace marketPlace = (MarketPlace) field.get(null);
                    MARKETPLACE_MAP.put(marketPlace.name(), marketPlace);
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    }

    private String enumName;
    private String name;

    private MarketPlace(String enumName, String name) {
        this.enumName = enumName;
        this.name = name;
    }

    public String getDescription() {
        return this.name;
    }

    public static MarketPlace valueOf(String name) {
        if (isNullOrEmpty(name)) {
            throw new IllegalArgumentException();
        } else {
            if (!MARKETPLACE_MAP.containsKey(name)) {
                String[] parts = name.split("_");
                StringBuilder result = new StringBuilder();
                for (String part : parts) {
                    if (result.length() > 0) {
                        result.append(" ");
                    }
                    result.append(part.substring(0, 1).toUpperCase()).append(part.substring(1).toLowerCase());
                }
                MarketPlace marketPlace = new MarketPlace(name, result.toString());
                MARKETPLACE_MAP.put(name, marketPlace);
            }
            return MARKETPLACE_MAP.get(name);
        }
    }

    public String name() {
        return enumName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MarketPlace that = (MarketPlace) o;
        return enumName.equals(that.enumName);
    }

    @Override
    public int hashCode() {
        return enumName.hashCode();
    }

    @Override
    public String toString() {
        return enumName;
    }

}
