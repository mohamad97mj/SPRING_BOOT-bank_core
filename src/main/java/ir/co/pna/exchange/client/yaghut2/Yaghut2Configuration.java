package ir.co.pna.exchange.client.yaghut2;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class Yaghut2Configuration {

    @Bean(name = "yaghut2Marshaller")
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
//		 this package must match the package in the <generatePackage> specified in
//		 pom.xml
        marshaller.setContextPath("ir.co.pna.exchange.client.yaghut2.generated_resources");
        return marshaller;
    }

    @Bean(name="yaghut2Client")
    public Yaghut2Client yaghutClient(@Qualifier("yaghut2Marshaller") Jaxb2Marshaller marshaller) {
        Yaghut2Client client = new Yaghut2Client();
        client.setDefaultUri("http://10.0.64.50:80/EnBankYaghut/ExchangeService.asmx");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

}
