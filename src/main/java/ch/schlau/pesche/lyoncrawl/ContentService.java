package ch.schlau.pesche.lyoncrawl;

import static ch.schlau.pesche.lyoncrawl.ContentService.CONFIG_KEY;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import ch.schlau.pesche.lyoncrawl.model.ContentArray;

@RegisterRestClient(configKey = CONFIG_KEY)
@Path("/rest/api")
public interface ContentService {

    String CONFIG_KEY = "ch.schlau.pesche.lyoncrawl.ContentService";

    @GET
    @Path("/content")
    ContentArray getMany(@HeaderParam("Authorization") String authorization,
            @QueryParam("spaceKey") String spaceKey);

    @GET
    @Path("/content")
    String getManyAsString(@HeaderParam("Authorization") String authorization,
            @QueryParam("spaceKey") String spaceKey);

    @GET
    @Path("/content/{id}")
    JsonObject getContent(@HeaderParam("Authorization") String authorization,
            @PathParam("id") Integer id);

}
