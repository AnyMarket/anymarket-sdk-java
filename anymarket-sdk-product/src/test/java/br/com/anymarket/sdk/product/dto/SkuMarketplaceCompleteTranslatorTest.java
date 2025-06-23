package br.com.anymarket.sdk.product.dto;


import br.com.anymarket.sdk.MarketPlace;
import br.com.anymarket.sdk.SKUMpOrigin;
import br.com.anymarket.sdk.brand.dto.Brand;
import br.com.anymarket.sdk.categories.dto.SimpleCategory;
import br.com.anymarket.sdk.dto.AvailableStockDTO;
import br.com.anymarket.sdk.dto.IndividualAvailableStockDTO;
import br.com.anymarket.sdk.variation.Variation;
import br.com.anymarket.sdk.variation.VariationType;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class SkuMarketplaceCompleteTranslatorTest {

    private SkuMarketplaceCompleteTranslator translator;

    @Before
    public void setup() {
        translator = new SkuMarketplaceCompleteTranslator();
    }

    @Test
    public void should_translate_to_sku() {
        SkuMarketplaceComplete complete = skuMarketplaceComplete();
        Sku sku = translator.toSku(complete);

        assertEquals(complete.getSku().getId(), sku.getId());
        assertEquals(complete.getSku().getPartnerId(), sku.getPartnerId());
        assertEquals(complete.getSku().getAmount(), sku.getStockAmount());
        assertEquals(complete.getSku().getEan(), sku.getEan());
        assertEquals(complete.getSku().getTitle(), sku.getTitle());
        assertEquals(complete.getSku().getPrice(), sku.getPrice());
        assertEquals(complete.getSku().getSellPrice(), sku.getSellPrice());
        assertEquals(complete.getSku().getAdditionalTime(), sku.getAdditionalTime());
        assertEquals(complete.getSku().getVariations().size(), sku.getVariations().size());
        assertEquals("var1value", sku.getVariations().get("tipovariacao"));
    }

    @Test
    public void should_translate_to_sku_complete() {
        SkuMarketplaceComplete complete = skuMarketplaceComplete();
        SkuComplete sku = translator.toSkuComplete(complete);
        assertEquals(Long.valueOf(45), sku.getId());
        assertEquals("Titulo sku", sku.getTitle());
        assertEquals("skupartnerid", sku.getPartnerId());
        assertEquals("56895689", sku.getEan());
        assertEquals(BigDecimal.valueOf(89.99), sku.getPrice());
        assertEquals(BigDecimal.valueOf(5), sku.getStockAmount());
        assertEquals(Integer.valueOf(2), sku.getAdditionalTime());
        assertEquals(1, sku.getVariations().size());
        assertEquals("var1", sku.getVariations().get(0).getPartnerId());
        assertEquals("var1value", sku.getVariations().get(0).getValue());
        assertEquals(Long.valueOf(1), sku.getVariations().get(0).getId());
        assertEquals(Long.valueOf(1), sku.getVariations().get(0).getType().getId());
        assertEquals("tipovariacao", sku.getVariations().get(0).getType().getName());
        assertEquals("varpartnerid", sku.getVariations().get(0).getType().getPartnerId());
        assertFalse(sku.getVariations().get(0).getType().getVisualVariation());
    }

    @Test
    public void should_translate_to_sku_marketplace() {
        SkuMarketplaceComplete complete = skuMarketplaceComplete();
        SkuMarketPlace skuMarketPlace = translator.toSkuMarketplace(complete);

        assertEquals(complete.getId(), skuMarketPlace.getId());
        assertEquals(complete.getIdInMarketplace(), skuMarketPlace.getIdInMarketplace());
        assertEquals(complete.getMarketPlace(), skuMarketPlace.getMarketPlace());
        assertEquals(complete.getIndex(), skuMarketPlace.getIndex());
        assertEquals(complete.getPublicationStatus(), skuMarketPlace.getPublicationStatus());
        assertEquals(complete.getStatusInMarketplace(), skuMarketPlace.getMarketplaceStatus());
        assertEquals(complete.getTransmissionStatus(), skuMarketPlace.getTransmissionStatus());
        assertEquals(complete.getErrorMsg(), skuMarketPlace.getErrorMsg());
        assertEquals(complete.getPrice(), skuMarketPlace.getPrice());
        assertEquals(complete.getPriceFactor(), skuMarketPlace.getPriceFactor());
        assertEquals(complete.getDiscountPrice(), skuMarketPlace.getDiscountPrice());
        assertEquals(complete.getSkuInMarketplace(), skuMarketPlace.getSkuInMarketplace());
        assertEquals(complete.getMarketplaceItemCode(), skuMarketPlace.getMarketplaceItemCode());
        assertEquals(complete.getFields().size(), skuMarketPlace.getFields().size());
        assertEquals("USED_VERY_GOOD", skuMarketPlace.getFields().get("CONDITION"));
        assertEquals("false", skuMarketPlace.getFields().get("HAS_FULFILLMENT"));
        assertEquals("2019-12-24T16:49:46.809Z", skuMarketPlace.getFields().get("LAUNCH_DATE"));
        assertEquals("1", skuMarketPlace.getFields().get("SHIPPING_MODEL"));
        assertEquals("1", skuMarketPlace.getFields().get("priceFactor"));

        AvailableStockDTO completeStock = complete.getAvailableStock();
        AvailableStockDTO translatedStock = skuMarketPlace.getAvailableStock();
        assertEquals(completeStock.getAmount(), translatedStock.getAmount());
        assertEquals(completeStock.getAvailableAmount(), translatedStock.getAvailableAmount());
        assertEquals(completeStock.getCost(), translatedStock.getCost());
        assertEquals(completeStock.getTotalReservation(), translatedStock.getTotalReservation());
        assertEquals(completeStock.getPhysicalAmount(), translatedStock.getPhysicalAmount());
        assertEquals(completeStock.getReservedPending(), translatedStock.getReservedPending());
        assertEquals(completeStock.getReservedPaid(), translatedStock.getReservedPaid());
        assertEquals(completeStock.getTotalInInvoicedStatus(), translatedStock.getTotalInInvoicedStatus());
        assertEquals(completeStock.getStockLocal(), translatedStock.getStockLocal());
        assertEquals(completeStock.getAdditionalTime(), translatedStock.getAdditionalTime());

        assertEquals(complete.getIndividualAvailableStocks().size(), skuMarketPlace.getIndividualAvailableStocks().size());

        IndividualAvailableStockDTO individualAvailableStockDTO = complete.getIndividualAvailableStocks().get(0);
        IndividualAvailableStockDTO translatedIndividualAvailableStockDTO = skuMarketPlace.getIndividualAvailableStocks().get(0);
        assertEquals(individualAvailableStockDTO.getAmount(), translatedIndividualAvailableStockDTO.getAmount());
        assertEquals(individualAvailableStockDTO.getAvailableAmount(), translatedIndividualAvailableStockDTO.getAvailableAmount());
        assertEquals(individualAvailableStockDTO.getCost(), translatedIndividualAvailableStockDTO.getCost());
        assertEquals(individualAvailableStockDTO.getTotalReservation(), translatedIndividualAvailableStockDTO.getTotalReservation());
        assertEquals(individualAvailableStockDTO.getReservedPending(), translatedIndividualAvailableStockDTO.getReservedPending());
        assertEquals(individualAvailableStockDTO.getReservedPaid(), translatedIndividualAvailableStockDTO.getReservedPaid());
        assertEquals(individualAvailableStockDTO.getStockLocal(), translatedIndividualAvailableStockDTO.getStockLocal());
        assertEquals(individualAvailableStockDTO.getStockLocalId(), translatedIndividualAvailableStockDTO.getStockLocalId());
        assertEquals(individualAvailableStockDTO.getAdditionalTime(), translatedIndividualAvailableStockDTO.getAdditionalTime());

        assertEquals(complete.getAttributes().size(), skuMarketPlace.getAttributes().size());
        assertEquals("VALOR", skuMarketPlace.getAttributes().get("ATRIBUTO"));
        assertEquals("FOLHAS", skuMarketPlace.getAttributes().get("ESTAMPA"));
        assertEquals("10", skuMarketPlace.getAttributes().get("Numero de pontas"));

        assertEquals(complete.getIdAccount(), skuMarketPlace.getIdAccount());
        assertEquals(complete.getAccountName(), skuMarketPlace.getAccountName());
        assertEquals(complete.getSkuMarketPlaceMessage().getMessage(), skuMarketPlace.getSkuMarketPlaceMessage().getMessage());
        assertEquals(complete.getSkuMarketPlaceMessage().getKey(), skuMarketPlace.getSkuMarketPlaceMessage().getKey());
        assertEquals(complete.getSkuMarketPlaceMessage().getParameters().size(), skuMarketPlace.getSkuMarketPlaceMessage().getParameters().size());
        assertEquals("val1", skuMarketPlace.getSkuMarketPlaceMessage().getParameters().get("par1"));
        assertEquals("val2", skuMarketPlace.getSkuMarketPlaceMessage().getParameters().get("par2"));
        assertEquals(3, skuMarketPlace.getSkuMarketPlaceMessage().getParameters().get("par3"));

        assertEquals(complete.getOrigin(), skuMarketPlace.getOrigin());

        Sku translatedSku = skuMarketPlace.getSku();
        assertEquals(Long.valueOf(45), translatedSku.getId());
        assertEquals("Titulo sku", translatedSku.getTitle());
        assertEquals("skupartnerid", translatedSku.getPartnerId());
        assertEquals("56895689", translatedSku.getEan());
        assertEquals(BigDecimal.valueOf(89.99), translatedSku.getPrice());
        assertEquals(BigDecimal.valueOf(98.99), translatedSku.getSellPrice());
        assertEquals(BigDecimal.valueOf(5), translatedSku.getStockAmount());
        assertEquals(Integer.valueOf(2), translatedSku.getAdditionalTime());
        assertEquals(1, translatedSku.getVariations().size());
    }

    private SkuMarketplaceComplete skuMarketplaceComplete() {
        SkuMarketplaceComplete skuMarketplaceComplete = new SkuMarketplaceComplete();
        skuMarketplaceComplete.setId(144L);
        skuMarketplaceComplete.setAccountName("ACCOUNT");
        skuMarketplaceComplete.setIdAccount(2L);
        skuMarketplaceComplete.setIdInMarketplace("idinmarketplace");
        skuMarketplaceComplete.setMarketPlace(MarketPlace.AMAZON);
        skuMarketplaceComplete.setIndex(5);
        skuMarketplaceComplete.setPublicationStatus(PublicationStatus.ACTIVE.name());
        skuMarketplaceComplete.setTransmissionStatus(TransmissionStatus.OK.name());
        skuMarketplaceComplete.setErrorMsg("error message");
        skuMarketplaceComplete.setStatusInMarketplace("statusInMarketplace");
        skuMarketplaceComplete.setPrice(BigDecimal.valueOf(5.789));
        skuMarketplaceComplete.setPriceFactor(BigDecimal.valueOf(5.4));
        skuMarketplaceComplete.setDiscountPrice(BigDecimal.valueOf(145.6));
        skuMarketplaceComplete.setSkuInMarketplace("skuInMarketplace");

        Map<String, String> fields = new HashMap<>();
        fields.put("CONDITION", "USED_VERY_GOOD");
        fields.put("HAS_FULFILLMENT", "false");
        fields.put("LAUNCH_DATE", "2019-12-24T16:49:46.809Z");
        fields.put("SHIPPING_MODEL", "1");
        fields.put("priceFactor", "1");

        skuMarketplaceComplete.setFields(fields);

        AvailableStockDTO availableStockDTO = new AvailableStockDTO();
        availableStockDTO.setAmount(BigDecimal.valueOf(13));
        availableStockDTO.setAvailableAmount(BigDecimal.TEN);
        availableStockDTO.setCost(BigDecimal.valueOf(45.99));
        availableStockDTO.setTotalReservation(BigDecimal.valueOf(15));
        availableStockDTO.setPhysicalAmount(BigDecimal.valueOf(5));
        availableStockDTO.setReservedPaid(BigDecimal.valueOf(2));
        availableStockDTO.setTotalInInvoicedStatus(BigDecimal.ONE);
        availableStockDTO.setStockLocal("local");
        availableStockDTO.setAdditionalTime(5);

        skuMarketplaceComplete.setAvailableStock(availableStockDTO);

        IndividualAvailableStockDTO individualAvailableStockDTO = new IndividualAvailableStockDTO();
        individualAvailableStockDTO.setAmount(BigDecimal.valueOf(3));
        individualAvailableStockDTO.setAvailableAmount(BigDecimal.valueOf(2));
        individualAvailableStockDTO.setCost(BigDecimal.valueOf(15.3));
        individualAvailableStockDTO.setTotalReservation(BigDecimal.ZERO);
        individualAvailableStockDTO.setReservedPending(BigDecimal.valueOf(1));
        individualAvailableStockDTO.setReservedPaid(BigDecimal.valueOf(10.0));
        individualAvailableStockDTO.setStockLocal("individual");
        individualAvailableStockDTO.setStockLocalId(3L);
        individualAvailableStockDTO.setAdditionalTime(2);

        skuMarketplaceComplete.setIndividualAvailableStocks(Collections.singletonList(individualAvailableStockDTO));

        skuMarketplaceComplete.setMarketplaceItemCode("marketplaceItemCode");

        Map<String, String> attributes = new HashMap<>();
        attributes.put("ATRIBUTO", "VALOR");
        attributes.put("ESTAMPA", "FOLHAS");
        attributes.put("Numero de pontas", "10");

        skuMarketplaceComplete.setAttributes(attributes);

        skuMarketplaceComplete.setSku(skuResource());

        SkuMarketPlaceMessage skuMarketPlaceMessage = new SkuMarketPlaceMessage();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("par1", "val1");
        parameters.put("par2", "val2");
        parameters.put("par3", 3);
        skuMarketPlaceMessage.setKey("teste");
        skuMarketPlaceMessage.setMessage("message test");
        skuMarketPlaceMessage.setParameters(parameters);

        skuMarketplaceComplete.setSkuMarketPlaceMessage(skuMarketPlaceMessage);

        Set<String> warnings = new HashSet<>();
        warnings.add("warning um");
        warnings.add("warning dois");

        skuMarketplaceComplete.setWarnings(warnings);

        skuMarketplaceComplete.setOrigin(SKUMpOrigin.CREATE);
        skuMarketplaceComplete.setVariationId("11");

        return skuMarketplaceComplete;
    }

    private SkuResource skuResource() {
        SkuResource skuResource = new SkuResource();
        skuResource.setId(45L);
        skuResource.setTitle("Titulo sku");
        skuResource.setPartnerId("skupartnerid");
        skuResource.setEan("56895689");
        skuResource.setPrice(BigDecimal.valueOf(89.99));
        skuResource.setSellPrice(BigDecimal.valueOf(98.99));
        skuResource.setAmount(BigDecimal.valueOf(5));
        skuResource.setAdditionalTime(2);

        Variation variation = new Variation();
        variation.setId(1L);
        variation.setPartnerId("var1");
        variation.setValue("var1value");

        VariationType variationType = new VariationType();
        variationType.setId(1L);
        variationType.setName("tipovariacao");
        variationType.setPartnerId("varpartnerid");
        variationType.setVisualVariation(false);

        variation.setType(variationType);

        skuResource.setVariations(Collections.singletonList(variation));

        skuResource.setProduct(productBase());

        return skuResource;
    }

    private ProductBase productBase() {
        ProductBase productBase = new ProductBase();
        productBase.setId(12L);
        productBase.setTitle("Titulo do produto");
        productBase.setDescription("descricao do produto");
        productBase.setModel("modelo");

        Nbm nbm = new Nbm();
        nbm.setId("1");
        nbm.setDescription("nbmdesc");

        productBase.setNbm(nbm);

        productBase.setDefinitionPriceScope(DefinitionPriceScope.COST);

        Characteristics characteristics = new Characteristics();
        characteristics.setIndex(1);
        characteristics.setName("characteristics");
        characteristics.setValue("charvalue");

        productBase.setCharacteristics(Collections.singletonList(characteristics));

        productBase.setOrigin(Origin.NACIONAL);

        SimpleCategory simpleCategory = new SimpleCategory();
        simpleCategory.setId(2L);
        simpleCategory.setName("simplecategory");
        simpleCategory.setPath("categorypath");

        productBase.setCategory(simpleCategory);

        productBase.setHeight(BigDecimal.valueOf(14.5));
        productBase.setLength(BigDecimal.valueOf(156.5));
        productBase.setWidth(BigDecimal.valueOf(145.5));
        productBase.setWeight(BigDecimal.valueOf(20.5));

        Image image1 = new Image();
        Image image2 = new Image();
        Image imageDelete1 = new Image();
        Image imageDelete2 = new Image();

        productBase.setImages(Arrays.asList(image1, image2));
        productBase.setImagesForDelete(Arrays.asList(imageDelete1, imageDelete2));

        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("brand");
        brand.setReducedName("brd");
        brand.setPartnerId("brandpartnerid");

        productBase.setBrand(brand);

        productBase.setWarrantyTime(2);
        productBase.setWarrantyText("text");
        productBase.setPriceFactor(BigDecimal.valueOf(10));
        productBase.setCalculatedPrice(true);
        productBase.setHasVariations(true);

        productBase.setGender(ProductGender.FEMALE);
        productBase.setAllowAutomaticSkuMarketplaceCreation(false);


        return productBase;
    }
}