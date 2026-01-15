package com.lionani07.algamoney_api.event.listener;

import com.lionani07.algamoney_api.event.ResourceCriadoEvent;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class ResourceCriadoListener implements ApplicationListener<ResourceCriadoEvent> {

    @Override
    public void onApplicationEvent(ResourceCriadoEvent event) {
        val response = event.getHttpServletResponse();
        val codigo = event.getCodigo();

        addHeaderLocation(codigo, response);
    }

    private void addHeaderLocation(Long codigo, HttpServletResponse response) {
        val location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{codigo}")
                .buildAndExpand(codigo)
                .toUri();

        response.setHeader("Location", location.toASCIIString());
    }
}
