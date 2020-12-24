package ir.co.pna.exchange.client.sms;

import ir.co.pna.exchange.client.country.generated_resources.GetCountryRequest;
import ir.co.pna.exchange.client.country.generated_resources.GetCountryResponse;
import ir.co.pna.exchange.client.sms.generated_resources.SMSGateway;
import ir.co.pna.exchange.client.sms.generated_resources.SendSMS;
import ir.co.pna.exchange.client.sms.generated_resources.SendSMSResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;

//@XmlElement(name = "MobileNo")
//protected String mobileNo;
//@XmlElement(name = "Message")
//protected String message;
//@XmlElement(name = "Gateway", required = true)
//@XmlSchemaType(name = "string")
//protected SMSGateway gateway;
//@XmlElement(name = "ServiceName")
//protected String serviceName;

public class SmsClient extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(SmsClient.class);

    public SendSMSResponse sendSms(String mobileNo, String message, SMSGateway gateway, String serviceName) {

        SendSMS request = new SendSMS();
        request.setMobileNo(mobileNo);
        request.setMessage(message);
        request.setGateway(gateway);
        request.setServiceName(serviceName);

        log.info("Requesting sms for " + mobileNo);

        SendSMSResponse response = (SendSMSResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://10.0.32.43/SendSMS.asmx", request, new SoapActionCallback(""));

        return response;
    }

}
