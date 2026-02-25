package br.com.anymarket.sdk;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Dados do MarketPlace no banco de dados
 */
public enum MarketPlace {

    CNOVA("Via Varejo", true),
    MAGENTO("Magento"),
    MERCADO_LIVRE("Mercado Livre", true, true),
    B2W("B2W", true),
    ECOMMERCE("E-Commerce"),
    VTEX("VTEX"),
    GFG("GFG - Dafiti", false, true),
    LINIO("Linio"),
    NETSHOES("Netshoes", false, true),
    MAGAZINE_LUIZA("Magazine Luiza"),
    CARREFOUR("Carrefour"),
    AMAZON("Amazon", false, true),
    AMAZON_SPDS("Amazon SPDS"),
    AMAZON_GLOBAL_API("Amazon Global Api", false, true),
    BUSCAPE("Buscapé"),
    ORACLECOMMERCE("Oracle Commerce Cloud"),
    MADEIRA_MADEIRA("Madeira Madeira"),
    CISSA_MAGAZINE("Cissa Magazine"),
    B2W_NEW_API("B2W Nova API", false, true),
    SARAIVA("Saraiva"),
    HUB_SALES("Hub Sales"),
    ZOOM("Zoom"),
    WEB_CONTINENTAL("Web Continental"),
    RICARDO_ELETRO("Ricardo Eletro"),
    CENTAURO("Centauro"),
    PORTAL_DO_MEDICO("Portal do Médico"),
    TRAY("Tray"),
    CAMICADO("Camicado"),
    GALITHX("Go Core Marketplace (Galithx e Gorila Z)"),
    COLOMBO("Lojas Colombo"),
    DIA_GROUP("Dia Group"),
    ELETRUM("Eletrum"),
    OPTEMAIS("Optemais"),
    EFACIL("eFácil"),
    LEROY_MERLIN("Leroy Merlin", false, true),
    COBASI("Cobasi"),
    CATWALK("Catwalk"),
    CASA_SHOW("BRHC"),
    WISH("Wish"),
    MOBLY("Mobly"),
    HOME_TO_GO("Home To Go"),
    CONNECT_PARTS("Connect Parts"),
    GARBARINO("Garbarino"),
    NOVO_MUNDO("Novo Mundo"),
    POSTHAUS("Posthaus"),
    MARABRAZ("Marabraz"),
    LOJA_VIVO("Loja Vivo"),
    ESTANTE_VIRTUAL("Estante Virtual"),
    RI_HAPPY("Ri Happy"),
    FAST_SHOP("Fast Shop"),
    CYBELAR("Cybelar"),
    CLUBE_DE_MARCAS("Clube de Marcas"),
    C_A("C&A"),
    ICBC_MALL("ICBC Mall"),
    TIENDA_SUPERVIELLE("Tienda Supervielle"),
    TIENDA_CLIC("Tienda Clic"),
    QISAR("Qisar"),
    CLAROPROMO("Claropromo"),
    BALCAO_RURAL("Balcao Rural"),
    CENTAURO_NEW_API("Centauro Nova API"),
    KABUM("Kabum"),
    MULTIVAREJO_GPA("Multivarejo GPA"),
    NUVEMSHOP("Nuvemshop"),
    LOJAS_LEBES("Lojas Lebes"),
    DROGA_RAIA("Droga Raia"),
    PASSARELA("Passarela"),
    AVENIDA("Avenida"),
    TIENDA_NARANJA("Tienda Naranja"),
    BANCO_INTER("Banco Inter"),
    HAVAN("Havan"),
    MARKETPLACE_TESTE("Marketplace Teste"),
    ETNA("Etna"),
    WEB_CONTINENTAL_NEW_API("Web Continental Nova API"),
    ELETROSOM("Eletrosom"),
    QUERO_QUERO("Quero-Quero"),
    COMPRA_CERTA("Compra Certa"),
    ARMAZEM_PARAIBA("Armazém Paraíba"),
    RIACHUELO("Riachuelo"),
    CONECTA_LA("Conecta La"),
    OLIST("OLIST"),
    RENNER("Renner e Camicado"),
    AJPLACE("AJPlace"),
    CREDITAS("Creditas"),
    SHOPEE("Shopee", false, true, true),
    CASSOL_CENTERLAR("Cassol Centerlar"),
    MAXMANIA("Maxmania"),
    ONSTORES("ONSTORES"),
    HOMEREFILL("Homerefill"),
    DOTZ("Dotz"),
    DECATHLON("Decathlon"),
    FOODIES_STORE("Foodies Store"),
    ON_STORES_VTEX("On Stores Vtex"),
    FACILY("Facily"),
    CASA_VIDEO("Casa & Vídeo"),
    SUPERCAMPO("SuperCampo"),
    FRAVEGA_MARKET("FravegaMarket"),
    MEGATONE("MEGATONE"),
    BAIANAO("Baianão"),
    MARISA("Marisa"),
    LOJA_DO_MECANICO("Loja do Mecânico"),
    TA_TASHOP("ta-taShop"),
    ALIEXPRESS("AliExpress", false, true),
    WOOD_PRIME("Wood Prime"),
    LOJA_SUMUP("Loja SumUp"),
    OLIST_NEW_API("OLIST New Api"),
    NOVO_MUNDO_NEW_API("Novo Mundo Nova Api"),
    SUA_COMPRA("Sua Compra"),
    SHOPHUB("Shophub"),
    POSTHAUS_NEW_API("Posthaus Nova Api"),
    LOJAS_LEBES_NEW_API("Lojas Lebes Nova Api"),
    PHILIPS_STORE("Philips Store"),
    VINKLO_BY_FESTALAB("Vinklo by Festalab"),
    RAMARIM("Ramarim"),
    COMFORTFLEX("Comfortflex"),
    BELEZA_NA_WEB("Beleza na Web"),
    GIMBA("Gimba"),
    MESBLA("MESBLA"),
    BALAROTI("Balaroti"),
    ZEMA("Zema"),
    NEXTSHOP("NextShop"),
    WEBAR("WebAr"),
    EOS("EOS"),
    LIVESHOP("LiveShop"),
    SHOPPING_LEBLON("Shopping Leblon"),
    PORTAL_DO_MEDICO_NEW_API("Portal do Médico Nova API"),
    CAZCO_MARKETPLACES("Cazco Marketplaces"),
    BRAVIUM("Bravium"),
    FENICIO("Fenicio"),
    RAIA_DROGASIL("Raia Drogasil"),
    MCF_SHOP("MCF Shop"),
    SHOPPING_LIVELO("Shopping Livelo"),
    PRIVALIA("Privalia"),
    AMARO("Amaro"),
    OI_PLACE("Oi Place"),
    KPLACE("Kplace"),
    LOJAS_COLOMBO("Lojas Colombo Nova API", false, true),
    SHOPIFY("Shopify"),
    SICREDI("Sicredi"),
    FALABELLA("Falabella", false, true),
    ANGELONI("Angeloni"),
    WOOCOMMERCE("Woocommerce"),
    GOATPLACE("Goatplace"),
    COMFORTFLEX_NOVA_API("Comfortflex Nova Api"),
    RAMARIM_NOVA_API("Ramarim Nova Api"),
    RIPLEY("Ripley", false, true),
    SHEIN("Shein", false, true, true),
    COPPEL("Coppel"),
    SHOPPING_BB("Shopping BB"),
    VENTURESHOP("Ventureshop"),
    TROCAFONE("Trocafone"),
    LOJAS_QUERO_QUERO("Lojas Quero-Quero"),
    HOUSE_OF_GAMERS("MKPlace"),
    CORPO_PERFEITO("Corpo Perfeito"),
    MUNDO_YOUPLAY("Mundo YouPlay"),
    TENDA_ATACADO("Tenda Atacado"),
    PARIS("Paris", false,true),
    MARCA_SELETA("Marca Seleta"),
    WEB_CONTINENTAL_V2("Web Continental V2"),
    LE_BISCUIT("Le Biscuit"),
    LL_LOYALTY("LL Loyalty"),
    ITAU_SHOP("Itaú Shop"),
    IGA("IGA"),
    AKIN_SHOP("Akin Shop"),
    ELEKTRA("Elektra"),
    HITES("Hites"),
    KOERICH("Koerich"),
    CLARO_SHOP("Claro Shop"),
    THE_HOME_DEPOT("The Home Depot"),
    WOOCOMMERCE2("WooCommerce2"),
    POLISHOP("Polishop"),
    VALE_BONUS("Vale Bônus"),
    JUMPSELLER("Jumpseller"),
    HELP_SELLER("Help Seller"),
    WEHOUSE("Wehouse"),
    LOJA_DO_COOPERADO("Loja do Cooperado"),
    PITSTOP("PitStop"),
    GIRAFA("Girafa"),
    LOJAS_MM("Lojas MM"),
    YA_PLACE_STORE("YA PLACE STORE"),
    SELLER_TEST_USA("Seller Test USA"),
    BABYBIZ("Babybiz"),
    CLAROSHOP_MEXICO("Claroshop México"),
    WAKE("Wake"),
    WAKE_MARKETPLACE("Wake Marketplace"),
    PAGUE_MENOS("Pague Menos"),
    SENFF_SHOPPING("Senff Shopping"),
    FASTSHOP_NEW_API("FastShop Nova Api"),
    PRESTASHOP("Prestashop"),
    RI_HAPPY_NEW_API("Ri Happy Nova Api"),
    CSM("CSM"),
    EPOCA("Época"),
    VANTICO_PENTEST("VANTICO-PENTEST"),
    FARMADELIVERY("FarmaDelivery"),
    GRUPO_MATEUS("Grupo Mateus"),
    DIGIGROW("Digigrow"),
    VOCE("Você"),
    PNEUBEST("PneuBest"),
    DB1_MARKETPLACE("DB1 Marketplace"),
    TIKTOK_SHOP("TIKTOK SHOP", false, true),
    BELEZA_NA_WEB_NEW_API("Beleza na Web"),
    LOJAS_IMPERIO("Lojas Imperio"),
    MARTINS_ATACADO("Martins Atacado"),
    CSU_DIGITAL("CSU Digital"),
    SICOOB("Sicoob"),
    SUBLIMITY("Sublimity"),
    CASSOL("CASSOL"),
    BANDSHOP("Bandshop"),
    MAIS_CORREIOS("Mais Correios"),
    ITAU("Itaú"),
    GAZIN_MARKETPLACE("Gazin Marketplace"),
    VIK("VIK"),
    STECK("Steck"),
    EXTREME_SIMRACING("Extreme Simracing"),
    ANGELONI_NEW_API("Angeloni 2.0"),
    WALMART("Walmart", false, true),
    FC_MARKETPLACE("FC Marketplace"),
    DIMERC("Dimerc"),
    EXTREME("Extreme"),
    INTER_SHOP("Inter Shop"),
    PICPAY_SHOP("PicPay Shop"),
    ZEMA_NOVA_API("Zema nova api"),
    ZOOM_NOVA_API("Zoom Nova Api"),
    PANVEL("Panvel"),
    BYD("BYD SUPPLY"),
    ELETROTRAFO("Eletrotrafo"),
    DROGARIA_PACHECO("Drogaria Pacheco"),
    DROGARIA_SAO_PAULO("Drogaria Sao Paulo"),
    EFACIL_NOVA_API("eFacil Nova Api"),
    COLLECTION("Collection");

    private String name;
    private final boolean specificConsumer;
    private final boolean anyBindEnabled;
    private final boolean allowsChangeIdInMarketplace;

    MarketPlace(String name) {
        this.name = name;
        this.specificConsumer = false;
        this.anyBindEnabled = false;
        this.allowsChangeIdInMarketplace = false;
    }

    MarketPlace(String name, boolean specificConsumer) {
        this.name = name;
        this.specificConsumer = specificConsumer;
        this.anyBindEnabled = false;
        this.allowsChangeIdInMarketplace = false;
    }

    MarketPlace(String name, boolean specificConsumer, boolean anyBindEnabled) {
        this.name = name;
        this.specificConsumer = specificConsumer;
        this.anyBindEnabled = anyBindEnabled;
        this.allowsChangeIdInMarketplace = false;
    }

    MarketPlace(String name, boolean specificConsumer, boolean anyBindEnabled, boolean allowsChangeIdInMarketplace) {
        this.name = name;
        this.specificConsumer = specificConsumer;
        this.anyBindEnabled = anyBindEnabled;
        this.allowsChangeIdInMarketplace = allowsChangeIdInMarketplace;
    }

    public String getDescription() {
        return name;
    }

    public void setDescription(String description) {
        this.name = description;
    }

    public static MarketPlace fromName(String name) {
        if (StringUtils.isNotBlank(name)) {
            return Arrays.stream(MarketPlace.values())
                .filter(marketplace -> name.equalsIgnoreCase(marketplace.getDescription()))
                .findFirst()
                .orElse(null);
        }

        return null;
    }

    public static List<MarketPlace> nameContaining(String searchParam) {
        return Arrays.stream(values()).filter(marketplace ->
            marketplaceDescriptionContains(marketplace, searchParam)
        ).collect(Collectors.toList());
    }

    private static boolean marketplaceDescriptionContains(MarketPlace marketplace, String searchParam) {
        String uppercaseDescription = marketplace.getDescription().toUpperCase();
        return uppercaseDescription.contains(searchParam.toUpperCase());
    }

    public String getQueueName() {
        return this.name().replace("_", "");
    }

    public String getName() {
        return name;
    }

    public boolean isSpecificConsumer() {
        return specificConsumer;
    }

    public boolean isAnyBindEnabled() {
        return anyBindEnabled;
    }

    public boolean isAllowsChangeIdInMarketplace() {
        return allowsChangeIdInMarketplace;
    }

}
