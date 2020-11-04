package org.example.router;

import org.example.filter.HttpLogResponseFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomHttpEndpointRouter implements HttpEndpointRouter {
    private final static Logger LOGGER = LoggerFactory.getLogger(RandomHttpEndpointRouter.class);

    @Override
    public String route(List<String> endpoints) {
        if (endpoints == null || endpoints.isEmpty()) {
            return null;
        }

        if (endpoints.size() == 1) {
            return endpoints.get(0);
        }

        // 随机实现
        int randomIndex = ThreadLocalRandom.current().nextInt(0, endpoints.size());
        LOGGER.info("random router find index is {}", randomIndex);
        return endpoints.get(randomIndex);
    }
}
