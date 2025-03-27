package com.interview.core.servlets;

import javax.servlet.Servlet;

import com.interview.core.config.ApiConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component(service={Servlet.class}, property={"sling.servlet.methods=get", "sling.servlet.paths=/bin/dynamicsearchcards", "sling.servlet.extensions=json"})
@Designate(ocd = ApiConfiguration.class)
public class DynamicSearchCardsServlet extends SlingAllMethodsServlet
{
    private  final Logger LOG = LoggerFactory.getLogger(getClass());

    private static final long serialVersionUID = 1L;
    private volatile String apiUrl;
    private volatile String xRapidapiHost;
    private volatile String xRapidapiKey;

    @Activate
    @Modified
    protected void activate(ApiConfiguration config) {
        this.apiUrl = config.apiUrl();
        this.xRapidapiHost = config.xRapidapiHost();
        this.xRapidapiKey = config.xRapidapiKey();
        LOG.info("Configuración activada: apiUrl={}, host={}, key={}", apiUrl, xRapidapiHost, xRapidapiKey);
    }

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

        LOG.info("Solicitud recibida en servlet /bin/DynamicSearchCardsServlet desde: {}", request.getRemoteAddr());

        try(CloseableHttpClient httpClient = HttpClients.createDefault())
        {
            URIBuilder builder = new URIBuilder(apiUrl);
            builder.addParameter("minimum_rating","3");
            builder.addParameter("order_by","asc");
            builder.addParameter("genre", request.getParameter("genre"));
            builder.addParameter("query_term", request.getParameter("query_term"));

            HttpGet httpGet = new HttpGet(builder.build());
            httpGet.setHeader("x-rapidapi-host", xRapidapiHost);
            httpGet.setHeader("x-rapidapi-key", xRapidapiKey);
            LOG.info("request de la API: {}", httpGet);
            CloseableHttpResponse apiResponse = httpClient.execute(httpGet);
            String apiResponseString = IOUtils.toString(apiResponse.getEntity().getContent(), StandardCharsets.UTF_8);

            LOG.info("Respuesta de la API: {}", apiResponseString);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(apiResponseString);
        }
        catch(Exception e)
        {
            LOG.error("Error al obtener datos de la API externa", e);
            response.sendError(400,e.getMessage());
        }
    }
}
