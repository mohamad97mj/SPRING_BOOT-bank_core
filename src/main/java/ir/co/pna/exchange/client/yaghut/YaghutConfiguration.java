package ir.co.pna.exchange.client.yaghut;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class YaghutConfiguration {

    @Bean(name = "yaghutMarshaller")
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
//		 this package must match the package in the <generatePackage> specified in
//		 pom.xml
        marshaller.setContextPath("ir.co.pna.exchange.client.yaghut.generated_resources");
        return marshaller;
    }

    @Bean(name="yaghutClient")
    public YaghutClient yaghutClient(@Qualifier("yaghutMarshaller") Jaxb2Marshaller marshaller) {
        YaghutClient client = new YaghutClient();
        client.setDefaultUri("http://10.0.64.50:80/EnBankYaghut/ExchangeService.asmx");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

}
