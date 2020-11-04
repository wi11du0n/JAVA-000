package org.example.router;

import java.util.List;

public class RandomHttpEndpointRouter implements HttpEndpointRouter {

    @Override
    public String route(List<String> endpoints) {
        if (endpoints == null || endpoints.isEmpty()) {
            return null;
        }

        if (endpoints.size() == 1) {
            return endpoints.get(0);
        }
        //todo
        return endpoints.get(0);
    }
}
