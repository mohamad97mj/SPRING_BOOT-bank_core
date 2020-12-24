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
            String mobileNo = "09365262198";
            String message = "زی زی کوچولو، کوچول موچولو";
            SMSGateway gateway = SMSGateway.ADVERTISEMENT;
            String serviceName = "mojahed service";

            SendSMSResponse response = quoteClient.sendSms(mobileNo, message, gateway, serviceName);


            String mobileNo3 = "09027237097";
            String message3 = "معاونت محترم طرح و برنامه بانک اقتصاد نوین جناب آقای تولایی با سلام، احتراما به اطلاع می رساند با توجه به استعفای آقای سید محمد حسینی مجاهد از شرکت پرداخت نوین و عدم پایبندی به ددلاین های مشخص شده عدم تمایل شرکت پرداخت نوین به ادامه پروژه سامانه صراقی نوین را اعلام میداریم.";
            SMSGateway gateway3 = SMSGateway.ADVERTISEMENT;
            String serviceName3 = "mojahed service";

            SendSMSResponse response3 = quoteClient.sendSms(mobileNo3, message3, gateway3, serviceName3);



            String mobileNo2 = "09027237097";
            String message2 = "سلام رییس مجاهد... دیدی بالاخره بعد از هفت خوان رستم این وامونده رو راه انداختم:))";
            SMSGateway gateway2 = SMSGateway.ADVERTISEMENT;
            String serviceName2 = "mojahed service";

            SendSMSResponse response2 = quoteClient.sendSms(mobileNo2, message2, gateway2, serviceName2);


            System.out.println(response.toString());
            System.err.println(response.getSendSMSResult());
            System.out.println(response2.toString());
            System.err.println(response2.getSendSMSResult());
            System.out.println(response3.toString());
            System.err.println(response3.getSendSMSResult());
        };
    }
}
