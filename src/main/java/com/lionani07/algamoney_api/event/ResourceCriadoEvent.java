package com.lionani07.algamoney_api.event;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ResourceCriadoEvent extends ApplicationEvent {

    private final HttpServletResponse httpServletResponse;
    private final Long codigo;

    public ResourceCriadoEvent(Object source, HttpServletResponse httpServletResponse, Long codigo) {
        super(source);
        this.httpServletResponse = httpServletResponse;
        this.codigo = codigo;
    }
}
