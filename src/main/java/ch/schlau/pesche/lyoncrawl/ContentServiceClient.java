package ch.schlau.pesche.lyoncrawl;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.json.JsonObject;

import org.eclipse.microprofile.rest.client.RestClientBuilder;

import ch.schlau.pesche.lyoncrawl.model.ContentArray;

public class ContentServiceClient {

    private final ContentService lyon;
    private final String authorization;

    ContentServiceClient(URL baseurl, String authorization) {
        this.lyon = RestClientBuilder.newBuilder()
                .baseUrl(baseurl)
                .build(ContentService.class);
        this.authorization = authorization;
    }

    public static String basicAuth(String user, String password) {
        String authValue = user + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(authValue.getBytes(StandardCharsets.UTF_8));
    }

    public ContentArray getMany(String spaceKey) {
        return lyon.getMany(authorization, spaceKey);
    }

    public String getManyAsString(String spaceKey) {
        return lyon.getManyAsString(authorization, spaceKey);
    }

    public JsonObject getContent(Integer id) {
        return lyon.getContent(authorization, id);
    }
}
