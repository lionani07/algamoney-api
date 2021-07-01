package algamoneyapi.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;

@Getter
public class ResourceCreatedEvent extends ApplicationEvent {

    private final Long codigo;
    private final HttpServletResponse httpServletResponse;

    public ResourceCreatedEvent(Object source, HttpServletResponse httpServletResponse, Long codigo) {
        super(source);
        this.codigo = codigo;
        this.httpServletResponse = httpServletResponse;
    }
}
