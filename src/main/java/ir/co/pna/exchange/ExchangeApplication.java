package ir.co.pna.exchange;

import ir.co.pna.exchange.client.country.CountryClient;
import ir.co.pna.exchange.client.country.generated_resources.GetCountryResponse;
import ir.co.pna.exchange.client.sms.SmsClient;
import ir.co.pna.exchange.client.sms.generated_resources.SMSGateway;
import ir.co.pna.exchange.client.sms.generated_resources.SendSMSResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Calendar;

@SpringBootApplication
public class ExchangeApplication {

    public static void main(String[] args) {
        System.out.println(Calendar.getInstance().getTimeInMillis());
        SpringApplication.run(ExchangeApplication.class, args);
    }

    @Bean
    CommandLineRunner lookup(SmsClient quoteClient) {
        return args -> {
            System.out.println("hello there");
            String mobileNo = "09059242876";
            String message = "hello";
            SMSGateway gateway = SMSGateway.ADVERTISEMENT;
            String serviceName = "mojahed service";

            SendSMSResponse response = quoteClient.sendSms(mobileNo, message, gateway, serviceName);
            System.out.println(response.toString());
            System.err.println(response.getSendSMSResult());
        };
    }
}
