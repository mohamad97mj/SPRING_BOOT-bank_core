package ir.co.pna.exchange.client.yaghut2;
import ir.co.pna.exchange.client.yaghut2.generated_resources.Statement;
import ir.co.pna.exchange.client.yaghut2.generated_resources.StatementResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;


public class Yaghut2Client extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(Yaghut2Client.class);

    public StatementResponse statement(String username, String password, String depositNo, XMLGregorianCalendar fromDate, XMLGregorianCalendar toDate, int offset, int length) {

        Statement request = new Statement();
        request.setUserName(username);
        request.setPassword(password);
        request.setDepositNo(depositNo);
        request.setFromDate(fromDate);
        request.setToDate(toDate);
        request.setOffset(offset);
        request.setLength(length);

        log.info("Requesting statement from " + depositNo);

        StatementResponse response = (StatementResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://10.0.64.50:80/EnBankYaghut/ExchangeService.asmx", request, new SoapActionCallback("http://tempuri.org/Statement"));

        return response;
    }

}
