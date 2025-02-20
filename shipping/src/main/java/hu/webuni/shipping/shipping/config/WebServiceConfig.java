package hu.webuni.shipping.shipping.config;

import hu.webuni.shipping.shipping.xmlws.ShippingServiceXmlWs;
import jakarta.xml.ws.Endpoint;
import lombok.RequiredArgsConstructor;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class WebServiceConfig {
    private final Bus bus;
    private final ShippingServiceXmlWs shippingServiceXmlWs;

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, shippingServiceXmlWs);
        endpoint.publish("/shippingService");

        return endpoint;
    }
}