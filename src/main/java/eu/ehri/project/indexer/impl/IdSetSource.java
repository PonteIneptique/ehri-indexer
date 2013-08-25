package eu.ehri.project.indexer.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import eu.ehri.project.indexer.CloseableIterable;
import org.codehaus.jackson.JsonNode;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

/**
* @author Mike Bryant (http://github.com/mikesname)
*/
class IdSetSource extends ServiceSource implements CloseableIterable<JsonNode> {
    private final Client client;
    private final String[] idSet;

    public IdSetSource(Client client, String[] idSet) {
        this.client = client;
        this.idSet = idSet;
    }

    @Override
    public ClientResponse getResponse() {
        WebResource resource = client.resource(
                UriBuilder.fromPath(RestServiceSource.URL).segment("entities").build());
        for (String id : idSet) {
            resource = resource.queryParam("id", id);
        }

        return resource
                .queryParam("limit", "-1") // Ugly, but there's a default limit
                .accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
    }
}
