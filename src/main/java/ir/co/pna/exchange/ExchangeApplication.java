package ir.co.pna.exchange;

import ir.co.pna.exchange.client.country.CountryClient;
import ir.co.pna.exchange.client.country.generated_resources.GetCountryResponse;
import ir.co.pna.exchange.client.sms.SmsClient;
import ir.co.pna.exchange.client.sms.generated_resources.SMSGateway;
import ir.co.pna.exchange.client.sms.generated_resources.SendSMSResponse;
import ir.co.pna.exchange.client.yaghut.YaghutClient;
import ir.co.pna.exchange.client.yaghut.generated_resources.NormalTransferResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Calendar;

@SpringBootApplication
public class ExchangeApplication {

    public static void main(String[] args) {
        System.out.println(Calendar.getInstance().getTimeInMillis());
        SpringApplication.run(ExchangeApplication.class, args);
    }

    @Bean
    CommandLineRunner lookup(SmsClient smsClient, YaghutClient yaghutClient) {
        return args -> {
            System.out.println("application started");
            String mobileNo = "09059242876";
            String message = "this is a message for test";
            SMSGateway gateway = SMSGateway.ADVERTISEMENT;
            String serviceName = "mojahed service";

            SendSMSResponse smsResponse = smsClient.sendSms(mobileNo, message, gateway, serviceName);
            System.out.println(smsResponse.toString());
            System.err.println(smsResponse.getSendSMSResult());


            String username = "6471174";
            String password = "55014205";
//            String sourceDepositNo = "159-701-6471174-1";
            String sourceDepositNo = "1570164711741";
//            String destinationDepositNo = "151-701-6113835-1";
            String destinationDepositNo = "15170161138351";
            BigDecimal amount = new BigDecimal(10000);
            String destinationComment = "واریز به حساب";
            String sourceComment = "برداشت از حساب";

            NormalTransferResponse transferResponse = yaghutClient.normalTransfer(username, password, sourceDepositNo, destinationDepositNo, amount, destinationComment, sourceComment);
            System.out.println(transferResponse.toString());
            System.err.println(transferResponse.getNormalTransferResult());
        };
    }
}
