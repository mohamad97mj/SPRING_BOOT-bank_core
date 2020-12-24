package ir.co.pna.exchange.client.country;
//
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class CountryConfiguration {

	@Bean(name = "countryMarshaller")
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
//		 this package must match the package in the <generatePackage> specified in
//		 pom.xml
		marshaller.setContextPath("ir.co.pna.exchange.client.country.generated_resources");
		return marshaller;
	}

	@Bean
	public CountryClient countryClient(@Qualifier("countryMarshaller") Jaxb2Marshaller marshaller) {
		CountryClient client = new CountryClient();
		client.setDefaultUri("http://localhost:8080/ws");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}

}

