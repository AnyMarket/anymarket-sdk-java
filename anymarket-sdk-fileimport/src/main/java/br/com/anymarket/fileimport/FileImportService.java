package br.com.anymarket.fileimport;

import br.com.anymarket.fileimport.dto.FileImport;
import br.com.anymarket.sdk.SDKConstants;
import br.com.anymarket.sdk.http.HttpService;
import br.com.anymarket.sdk.http.headers.IntegrationHeader;
import com.google.common.base.Strings;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

import static br.com.anymarket.sdk.http.headers.AnymarketHeaderUtils.addModuleOriginHeader;

public class FileImportService extends HttpService {

    private static final String FILE_IMPORT_URI = "/fileimport";
    private static final String FIELD_FILE = "file";
    private static final String FIELD_TYPE = "type";
    private final String apiEndPoint;
    private String moduleOrigin;

    public FileImportService(String apiEndPoint) {
        this.apiEndPoint = !Strings.isNullOrEmpty(apiEndPoint) ? apiEndPoint :
                SDKConstants.ANYMARKET_HOMOLOG_API_ENDPOINT;
    }

    public FileImportService(String apiEndPoint, String origin) {
        this.apiEndPoint = !Strings.isNullOrEmpty(apiEndPoint) ? apiEndPoint :
                SDKConstants.ANYMARKET_HOMOLOG_API_ENDPOINT;
        this.moduleOrigin = origin;
    }

    public FileImport importFile(MultipartFile file, String type, IntegrationHeader... headers) throws UnirestException {
        String url = apiEndPoint.concat(FILE_IMPORT_URI);
        Map<String, Object> fields = getFields(file, type);
        HttpRequest response = postFile(url, fields, addModuleOriginHeader(headers, this.moduleOrigin));
        return response.asObject(FileImport.class).getBody();
    }

    private static Map<String, Object> getFields(MultipartFile file, String type) {
        Map<String, Object> fields = new HashMap<>();
        fields.put(FIELD_FILE, file);
        fields.put(FIELD_TYPE, type);
        return fields;
    }
}
