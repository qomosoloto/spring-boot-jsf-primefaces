package com.shenbian.admin.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.context.annotation.Configuration;

/**
 * Created by qomo on 15-9-28.
 */
@Configuration
public class MyMimeMapper implements EmbeddedServletContainerCustomizer {
    @Override
    public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
        MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
        mappings.add("ico", "image/x-icon");
        configurableEmbeddedServletContainer.setMimeMappings(mappings);
    }
}
