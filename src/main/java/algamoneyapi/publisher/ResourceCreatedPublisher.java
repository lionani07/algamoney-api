package algamoneyapi.publisher;

import algamoneyapi.event.ResourceCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

@Component
public class ResourceCreatedPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publish(HttpServletResponse httpServletResponse, Long codigo) {
        final var eventCreated = new ResourceCreatedEvent(this, httpServletResponse, codigo);
        this.applicationEventPublisher.publishEvent(eventCreated);
    }

}
