package br.com.anymarket.sdk.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class SDKUrlEncoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(SDKUrlEncoder.class);
    private static final String DEFAULT_ENCODE = "UTF-8";

    /**
     * Método utilizado para codificar o parametro de URL utilizando o padrão de códigos informado
     *
     * @param parameterToEncode Parâmetro para codificação
     * @param characterEncoding Mapa de caracteres de codificação
     * @return String parâmetro codificado
     */
    public static String encodeParameter(final String parameterToEncode, final String characterEncoding) {
        try {
            return URLEncoder.encode(parameterToEncode, characterEncoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LOGGER.error("Não foi possível executar URLEncode no parêmetro {}. Erro: {}", parameterToEncode, e.getMessage());
            return parameterToEncode;
        }
    }

    /**
     * Método utilizado para codificar o parâmetro de URL utilizando o padrão UTF-8
     *
     * @param parameterToEncode Parâmetro para codificação
     * @return String parâmetro codificado
     */
    public static String encodeParameterToUTF8(final String parameterToEncode) {
        return encodeParameter(parameterToEncode, DEFAULT_ENCODE);
    }

    public static String encodeToUrlEnconded(String value) throws UnsupportedEncodingException {
        try {
            return URLEncoder.encode(value, String.valueOf(StandardCharsets.UTF_8))
                    .replace("+", "%20")
                    .replace("%5C", "%5C%5C")
                    .replace("*", "%2A")
                    .replace("/", "%2F")
                    .replace("(", "%28")
                    .replace(")", "%29")
                    .replace("!", "%21")
                    .replace("@", "%40")
                    .replace("$", "%24");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedEncodingException(String.format("Could no enconde value: %s to UrlEnconde, cause: %s", value, e.getMessage()));
        }
    }
}
