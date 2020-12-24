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
            String message = "this is a test message";
            SMSGateway gateway = SMSGateway.ADVERTISEMENT;
            String serviceName = "mojahed service";

            SendSMSResponse smsResponse = smsClient.sendSms(mobileNo, message, gateway, serviceName);
            System.out.println(smsResponse.toString());
            System.err.println(smsResponse.getSendSMSResult());


            String mojahedUsername = "6471174";
            String mojahedPassword = "55014205";
            String mojahedDepositNo = "159-701-6471174-1";

            String tavalaeeUsername = "matavallaie";
            String tavalaeePassword = "36325045";
            String tavalaeeDepositNo = "151-701-6113835-1";

            String yaghliUsername = "payamyaghli";
            String yaghliPassword = "81750304";
            String yaghliDepositNo = "104-701-121924-1";

            String shahsavaniUsername = "da97349734";
            String shahsavaniPassword = "85438083";
            String shahsavaniDepositNo = "147-1-4681241-1";

            BigDecimal amount = new BigDecimal(1000);
            String destinationComment = "واریز به حساب";
            String sourceComment = "برداشت از حساب";

            NormalTransferResponse transferResponse1 = yaghutClient.normalTransfer(mojahedUsername, mojahedPassword, mojahedDepositNo, shahsavaniDepositNo, amount, destinationComment, sourceComment);
            NormalTransferResponse transferResponse2 = yaghutClient.normalTransfer(mojahedUsername, mojahedPassword, mojahedDepositNo, tavalaeeDepositNo, amount, destinationComment, sourceComment);
            NormalTransferResponse transferResponse3 = yaghutClient.normalTransfer(mojahedUsername, mojahedPassword, mojahedDepositNo, yaghliDepositNo, amount, destinationComment, sourceComment);
            System.err.println(transferResponse1.getNormalTransferResult());
            System.err.println(transferResponse2.getNormalTransferResult());
            System.err.println(transferResponse3.getNormalTransferResult());
        };
    }
}
