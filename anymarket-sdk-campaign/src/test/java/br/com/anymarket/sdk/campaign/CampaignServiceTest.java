package br.com.anymarket.sdk.campaign;

import br.com.anymarket.sdk.campaign.dto.Campaign;
import br.com.anymarket.sdk.exception.NotFoundException;
import br.com.anymarket.sdk.http.headers.AnymarketTokenHeader;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CampaignServiceTest {

    private static final String API_ENDPOINT = "http://api.anymarket.com.br/v2";
    private static final String ORIGIN = "Tray";
    private static final String MARKETPLACE = "B2W";
    private static final Long CAMPAIGN_ID = 123L;
    private static final String URL = "http://api.anymarket.com.br/v2/campaigns/active/123/B2W/";
    private static final String KEY = "key";
    private static final String VALUE = "value";
    private static final String TITLE = "Black Friday";
    private static final String ACCOUNT_NAME = "Conta 01";
    private static final Long ACCOUNT_ID = 1L;
    private static final String DESCRIPTION = "Promoções para Black Friday";
    private static final Long PRODUCTS_COUNT = 100L;

    @Rule
    public final ExpectedException expectation = ExpectedException.none();

    @Spy
    @InjectMocks
    private CampaignService campaignService;

    @Mock
    private GetRequest getRequest;
    @Mock
    private HttpResponse httpResponse;


    @Before
    public void setUp() {
        campaignService = new CampaignService(API_ENDPOINT, ORIGIN);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_get_active_campaign_by_id_expected_success() throws UnirestException, IOException {
        try (MockedStatic<Unirest> unirest = Mockito.mockStatic(Unirest.class)) {
            unirest.when((MockedStatic.Verification) Unirest.get(URL)).thenReturn(getRequest);
            when(getRequest.asString()).thenReturn(httpResponse);
            when(httpResponse.getStatus()).thenReturn(HttpStatus.SC_OK);
            when(httpResponse.getBody()).thenReturn(getCampaignResponse());

            AnymarketTokenHeader headers = mock(AnymarketTokenHeader.class);
            when(headers.getKey()).thenReturn(KEY);
            when(headers.getValue()).thenReturn(VALUE);
            when(getRequest.header(KEY, VALUE)).thenReturn(getRequest);

            Campaign campaign = campaignService.getActiveCampaignById(CAMPAIGN_ID, MARKETPLACE, headers);

            LocalDateTime initialDateLT = LocalDateTime.of(2019, 8, 24, 14, 15, 39, 613000000);
            LocalDateTime finalDateLT = LocalDateTime.of(2020, 4, 15, 14, 0, 0);

            Date initialDate = Date.from(initialDateLT.atZone(ZoneId.systemDefault()).toInstant());
            Date finalDate = Date.from(finalDateLT.atZone(ZoneId.systemDefault()).toInstant());

            assertEquals(CAMPAIGN_ID, campaign.getId());
            assertEquals(TITLE, campaign.getTitle());
            assertEquals(ACCOUNT_ID, campaign.getIdAccount());
            assertEquals(ACCOUNT_NAME, campaign.getAccountName());
            assertEquals(DESCRIPTION, campaign.getDescription());
            assertEquals(initialDate, campaign.getInitialDate());
            assertEquals(finalDate, campaign.getFinalDate());
            assertEquals(PRODUCTS_COUNT, campaign.getProductsCount());
        } catch (UnirestException | IOException e) {
            throw e;
        }

    }

    @Test
    public void should_get_active_campaign_by_id_expected_not_found_exception() throws IOException, UnirestException {
        try (MockedStatic<Unirest> unirest = Mockito.mockStatic(Unirest.class)) {
            unirest.when((MockedStatic.Verification) Unirest.get(URL)).thenReturn(getRequest);
            when(getRequest.asString()).thenReturn(httpResponse);
            when(httpResponse.getStatus()).thenReturn(HttpStatus.SC_NOT_FOUND);

            expectation.expect(NotFoundException.class);
            expectation.expectMessage("Campanha com o id 123 não foi encontrada para o marketplace B2W");

            campaignService.getActiveCampaignById(CAMPAIGN_ID, MARKETPLACE);
        } catch (UnirestException e) {
            throw e;
        }
    }

    private String getCampaignResponse() {
        return "{\n" +
                "  \"id\": " + CAMPAIGN_ID + ",\n" +
                "  \"title\": \"" + TITLE + "\",\n" +
                "  \"idAccount\": \"" + ACCOUNT_ID + "\",\n" +
                "  \"accountName\": \"" + ACCOUNT_NAME + "\",\n" +
                "  \"description\": \"" + DESCRIPTION + "\",\n" +
                "  \"initialDate\": \"2019-08-24T14:15:39.613Z\",\n" +
                "  \"finalDate\": \"2020-04-15T14:00:00.000Z\",\n" +
                "  \"marketplace\": \"" + MARKETPLACE + "\",\n" +
                "  \"status\": \"ACTIVE\",\n" +
                "  \"productsCount\": " + PRODUCTS_COUNT + "\n" +
                "}";
    }

}
