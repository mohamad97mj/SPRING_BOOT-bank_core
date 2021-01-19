package ir.co.pna.exchange;

import ir.co.pna.exchange.client.sms.SmsClient;
import ir.co.pna.exchange.client.sms.generated_resources.SMSGateway;
import ir.co.pna.exchange.client.sms.generated_resources.SendSMSResponse;
import ir.co.pna.exchange.client.yaghut.YaghutClient;
import ir.co.pna.exchange.client.yaghut.generated_resources.NormalTransferResponse;
import ir.co.pna.exchange.utility.GlobalConstant;
import ir.co.pna.exchange.utility.IdGen;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Calendar;

@SpringBootApplication
public class ExchangeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExchangeApplication.class, args);
    }

//    @Bean
    CommandLineRunner lookup(SmsClient smsClient, YaghutClient yaghutClient) {
        return args -> {
            System.out.println("application started");
            String mobileNo = "09059242876";
            String message = "یک پیام برای تست";
            SMSGateway gateway = SMSGateway.ADVERTISEMENT;
            String serviceName = "mojahed service";

            SendSMSResponse smsResponse = smsClient.sendSms(mobileNo, message, gateway, serviceName);
            System.out.println(smsResponse.toString());
            System.err.println(smsResponse.getSendSMSResult());


//            String mojahedUsername = "6471174";
//            String mojahedPassword = "55014205";
//            String mojahedDepositNo = "159-701-6471174-1";
//
//            String tavalaeeUsername = "matavallaie";
//            String tavalaeePassword = "36325045";
//            String tavalaeeDepositNo = "151-701-6113835-1";
//
//            String yaghliUsername = "payamyaghli";
//            String yaghliPassword = "81750304";
//            String yaghliDepositNo = "104-701-121924-1";
//
//            String shahsavaniUsername = "da97349734";
//            String shahsavaniPassword = "85438083";
//            String shahsavaniDepositNo = "147-1-4681241-1";
//
//            BigDecimal amount = new BigDecimal(1000);
//            String destinationComment = "واریز به حساب";
//            String sourceComment = "برداشت از حساب";
//
//            NormalTransferResponse transferResponse1 = yaghutClient.normalTransfer(yaghliUsername, yaghliPassword, yaghliDepositNo, shahsavaniDepositNo, amount, destinationComment, sourceComment);
//            NormalTransferResponse transferResponse2 = yaghutClient.normalTransfer(yaghliUsername, yaghliPassword, yaghliDepositNo, tavalaeeDepositNo, amount, destinationComment, sourceComment);
//            NormalTransferResponse transferResponse3 = yaghutClient.normalTransfer(yaghliUsername, yaghliPassword, yaghliDepositNo, mojahedDepositNo, amount, destinationComment, sourceComment);
//
//            NormalTransferResponse transferResponse4 = yaghutClient.normalTransfer(shahsavaniUsername, shahsavaniPassword, shahsavaniDepositNo, yaghliDepositNo, amount, destinationComment, sourceComment);
//            NormalTransferResponse transferResponse5 = yaghutClient.normalTransfer(shahsavaniUsername, shahsavaniPassword, shahsavaniDepositNo, tavalaeeDepositNo, amount, destinationComment, sourceComment);
//            NormalTransferResponse transferResponse6 = yaghutClient.normalTransfer(shahsavaniUsername, shahsavaniPassword, shahsavaniDepositNo, mojahedDepositNo, amount, destinationComment, sourceComment);
//
//            NormalTransferResponse transferResponse7 = yaghutClient.normalTransfer(tavalaeeUsername, tavalaeePassword, tavalaeeDepositNo, yaghliDepositNo, amount, destinationComment, sourceComment);
//            NormalTransferResponse transferResponse8 = yaghutClient.normalTransfer(tavalaeeUsername, tavalaeePassword, tavalaeeDepositNo, shahsavaniDepositNo, amount, destinationComment, sourceComment);
//            NormalTransferResponse transferResponse9 = yaghutClient.normalTransfer(tavalaeeUsername, tavalaeePassword, tavalaeeDepositNo, mojahedDepositNo, amount, destinationComment, sourceComment);
//
//            NormalTransferResponse transferResponse10 = yaghutClient.normalTransfer(mojahedUsername, mojahedPassword, mojahedDepositNo, yaghliDepositNo, amount, destinationComment, sourceComment);
//            NormalTransferResponse transferResponse11 = yaghutClient.normalTransfer(mojahedUsername, mojahedPassword, mojahedDepositNo, shahsavaniDepositNo, amount, destinationComment, sourceComment);
//            NormalTransferResponse transferResponse12 = yaghutClient.normalTransfer(mojahedUsername, mojahedPassword, mojahedDepositNo, tavalaeeDepositNo, amount, destinationComment, sourceComment);
//
//
//            System.out.println("yaghli to shahsavani:");
//            System.err.println(transferResponse1.getNormalTransferResult());
//            System.out.println("yaghli to tavalaee:");
//            System.err.println(transferResponse2.getNormalTransferResult());
//            System.out.println("yaghli to mojahed:");
//            System.err.println(transferResponse3.getNormalTransferResult());
//
//            System.out.println("shahsavani to yaghli");
//            System.err.println(transferResponse4.getNormalTransferResult());
//            System.out.println("shahsavani to tavalaee");
//            System.err.println(transferResponse5.getNormalTransferResult());
//            System.out.println("shahsavani to mojahed");
//            System.err.println(transferResponse6.getNormalTransferResult());
//
//            System.out.println("tavalaee to yaghli");
//            System.err.println(transferResponse7.getNormalTransferResult());
//            System.out.println("tavalaee to shahsavani");
//            System.err.println(transferResponse8.getNormalTransferResult());
//            System.out.println("tavalaee to mojahed");
//            System.err.println(transferResponse9.getNormalTransferResult());
//
//            System.out.println("mojahed to yaghli");
//            System.err.println(transferResponse10.getNormalTransferResult());
//            System.out.println("mojahed to shahsavani");
//            System.err.println(transferResponse11.getNormalTransferResult());
//            System.out.println("mojahed to tavalaee");
//            System.err.println(transferResponse12.getNormalTransferResult());
        };
    }
}
