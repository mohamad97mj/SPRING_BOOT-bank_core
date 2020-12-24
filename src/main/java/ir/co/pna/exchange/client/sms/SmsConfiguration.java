package ir.co.pna.exchange.client.sms;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class SmsConfiguration {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
//		 this package must match the package in the <generatePackage> specified in
//		 pom.xml
        marshaller.setContextPath("ir.co.pna.exchange.client.sms.generated_resources");
        return marshaller;
    }

    @Bean
    public SmsClient countryClient(Jaxb2Marshaller marshaller) {
        SmsClient client = new SmsClient();
        client.setDefaultUri("http://10.0.32.43/SendSMS.asmx");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

}
