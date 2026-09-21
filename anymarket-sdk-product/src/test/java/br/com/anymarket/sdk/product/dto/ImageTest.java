package br.com.anymarket.sdk.product.dto;

import br.com.anymarket.sdk.dto.ImageStatus;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.*;

public class ImageTest {

    private URL url(String value) throws MalformedURLException {
        try {
            return new URI(value).toURL();
        } catch (URISyntaxException e) {
            throw new MalformedURLException(e.getMessage());
        }
    }

    @Test
    public void should_create_image_with_default_constructor() {
        Image image = new Image();

        assertNull(image.getUrl());
        assertNull(image.getVariation());
        assertFalse(image.isMain());
    }

    @Test
    public void should_create_image_with_url_constructor() throws MalformedURLException {
        URL url = url("http://www.anymarket.com.br/image.jpg");

        Image image = new Image(url);

        assertEquals(url, image.getUrl());
        assertNull(image.getVariation());
        assertFalse(image.isMain());
    }

    @Test
    public void should_create_image_with_url_variation_and_main_constructor() throws MalformedURLException {
        URL url = url("http://www.anymarket.com.br/image.jpg");

        Image image = new Image(url, "P1", true);

        assertEquals(url, image.getUrl());
        assertEquals("P1", image.getVariation());
        assertTrue(image.isMain());
    }

    @Test
    public void should_set_and_get_id() {
        Image image = new Image();

        image.setId(10L);

        assertEquals(Long.valueOf(10L), image.getId());
    }

    @Test
    public void should_set_and_get_index() {
        Image image = new Image();

        image.setIndex(2);

        assertEquals(2, image.getIndex());
    }

    @Test
    public void should_set_and_get_variation() {
        Image image = new Image();

        image.setVariation("P2");

        assertEquals("P2", image.getVariation());
    }

    @Test
    public void should_set_and_get_thumbnail_url() throws MalformedURLException {
        URL thumbnailUrl = url("http://www.anymarket.com.br/thumbnail.jpg");
        Image image = new Image();

        image.setThumbnailUrl(thumbnailUrl);

        assertEquals(thumbnailUrl, image.getThumbnailUrl());
    }

    @Test
    public void should_set_and_get_standard_url() throws MalformedURLException {
        URL standardUrl = url("http://www.anymarket.com.br/standard.jpg");
        Image image = new Image();

        image.setStandardUrl(standardUrl);

        assertEquals(standardUrl, image.getStandardUrl());
    }

    @Test
    public void should_set_and_get_low_resolution_url() throws MalformedURLException {
        URL lowResolutionUrl = url("http://www.anymarket.com.br/low.jpg");
        Image image = new Image();

        image.setLowResolutionUrl(lowResolutionUrl);

        assertEquals(lowResolutionUrl, image.getLowResolutionUrl());
    }

    @Test
    public void should_set_and_get_original_image_in_s3_url() throws MalformedURLException {
        URL originalImageInS3URL = url("http://www.anymarket.com.br/original.jpg");
        Image image = new Image();

        image.setOriginalImageInS3URL(originalImageInS3URL);

        assertEquals(originalImageInS3URL, image.getOriginalImageInS3URL());
    }

    @Test
    public void should_set_and_get_url() throws MalformedURLException {
        URL url = url("http://www.anymarket.com.br/image.jpg");
        Image image = new Image();

        image.setUrl(url);

        assertEquals(url, image.getUrl());
    }

    @Test
    public void should_set_and_get_status_message() {
        Image image = new Image();

        image.setStatusMessage("erro ao processar imagem");

        assertEquals("erro ao processar imagem", image.getStatusMessage());
    }

    @Test
    public void should_set_and_get_status() {
        Image image = new Image();

        image.setStatus(ImageStatus.PROCESSED);

        assertEquals(ImageStatus.PROCESSED, image.getStatus());
    }

    @Test
    public void should_set_and_get_main() {
        Image image = new Image();

        image.setMain(true);

        assertTrue(image.isMain());
    }

    @Test
    public void should_set_and_get_standard_width() {
        Image image = new Image();

        image.setStandardWidth(800);

        assertEquals(Integer.valueOf(800), image.getStandardWidth());
    }

    @Test
    public void should_set_and_get_standard_height() {
        Image image = new Image();

        image.setStandardHeight(600);

        assertEquals(Integer.valueOf(600), image.getStandardHeight());
    }

    @Test
    public void should_set_and_get_original_width() {
        Image image = new Image();

        image.setOriginalWidth(1920);

        assertEquals(Integer.valueOf(1920), image.getOriginalWidth());
    }

    @Test
    public void should_set_and_get_original_height() {
        Image image = new Image();

        image.setOriginalHeight(1080);

        assertEquals(Integer.valueOf(1080), image.getOriginalHeight());
    }

    @Test
    public void should_set_and_get_type() {
        Image image = new Image();

        image.setType(AnyImageType.MODEL_IMAGE);

        assertEquals(AnyImageType.MODEL_IMAGE, image.getType());
    }

    @Test
    public void should_return_string_with_all_fields_on_to_string() throws MalformedURLException {
        Image image = new Image();
        image.setId(1L);
        image.setIndex(0);
        image.setVariation("P1");
        image.setThumbnailUrl(url("http://www.anymarket.com.br/thumbnail.jpg"));
        image.setStandardUrl(url("http://www.anymarket.com.br/standard.jpg"));
        image.setLowResolutionUrl(url("http://www.anymarket.com.br/low.jpg"));
        image.setOriginalImageInS3URL(url("http://www.anymarket.com.br/original.jpg"));
        image.setUrl(url("http://www.anymarket.com.br/image.jpg"));
        image.setStatusMessage("ok");
        image.setStatus(ImageStatus.PROCESSED);
        image.setMain(true);
        image.setStandardWidth(800);
        image.setStandardHeight(600);
        image.setOriginalWidth(1920);
        image.setOriginalHeight(1080);
        image.setType(AnyImageType.MODEL_IMAGE);

        String toString = image.toString();

        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("index=0"));
        assertTrue(toString.contains("variation=P1"));
        assertTrue(toString.contains("statusMessage=ok"));
        assertTrue(toString.contains("status=PROCESSED"));
        assertTrue(toString.contains("main=true"));
        assertTrue(toString.contains("standardWidth=800"));
        assertTrue(toString.contains("standardHeight=600"));
        assertTrue(toString.contains("originalWidth=1920"));
        assertTrue(toString.contains("originalHeight=1080"));
        assertTrue(toString.contains("type=MODEL_IMAGE"));
    }
}
