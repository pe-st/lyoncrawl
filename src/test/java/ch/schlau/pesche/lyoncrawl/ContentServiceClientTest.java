package ch.schlau.pesche.lyoncrawl;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ch.schlau.pesche.lyoncrawl.model.ContentArray;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@QuarkusTestResource(WiremockLyon.class)
class ContentServiceClientTest {

    static ContentServiceClient lyon;

    @BeforeAll
    static void setupClient() throws MalformedURLException {
        URL baseurl = new URL(System.getProperty(ContentService.CONFIG_KEY + "/mp-rest/url"));
        String authorization = ContentServiceClient.basicAuth("u", "pw");
        lyon = new ContentServiceClient(baseurl, authorization);
    }

    @Test
    void basicAuth() {
        assertThat(ContentServiceClient.basicAuth("u", "pw"), equalTo("Basic dTpwdw=="));
    }

    @Test
    void getMany_asString() {
        // when
        String jsonString = lyon.getManyAsString("LYON");

        // then
        assertThatJson(jsonString)
                .inPath("$.results")
                .isArray();
    }

    @Test
    void getMany_asObject() {
        // when
        ContentArray contents = lyon.getMany("LYON");

        // then
        assertThat(contents, is(notNullValue()));
        assertThat(contents.getResults().size(), greaterThan(0));
        assertThat(contents.getStart(), equalTo(0));
    }
}