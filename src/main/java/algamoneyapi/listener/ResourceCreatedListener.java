package algamoneyapi.listener;

import algamoneyapi.event.ResourceCreatedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Component
public class ResourceCreatedListener implements ApplicationListener<ResourceCreatedEvent> {

    private static final String PATH = "/{codigo}";
    private static final String LOCATION_HEADER = "location";

    @Override
    public void onApplicationEvent(ResourceCreatedEvent resourceCreatedEvent) {
        final var location = buildUri(resourceCreatedEvent.getCodigo());
        resourceCreatedEvent.getHttpServletResponse().setHeader(LOCATION_HEADER, location.toASCIIString());
    }

    private URI buildUri(final Long codigo) {
        return ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path(PATH)
                .buildAndExpand(codigo).toUri();
    }
}
