package ir.co.pna.exchange.client.yaghut;
import ir.co.pna.exchange.client.yaghut.generated_resources.NormalTransfer;
import ir.co.pna.exchange.client.yaghut.generated_resources.NormalTransferResponse;
import ir.co.pna.exchange.client.yaghut.generated_resources.Statement;
import ir.co.pna.exchange.client.yaghut.generated_resources.StatementResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;


public class YaghutClient extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(YaghutClient.class);

    public StatementResponse statement(String username, String password, String depositNo, XMLGregorianCalendar fromDate, XMLGregorianCalendar toDate, int offset, int length) {

        Statement request = new Statement();
        request.setUserName(username);
        request.setPassword(password);
        request.setDepositNo(depositNo);
        request.setFromDate(fromDate);
        request.setToDate(toDate);
        request.setOffset(offset);
        request.setLength(length);

        log.info("Requesting statement for " + depositNo);

        StatementResponse response = (StatementResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://10.0.64.50:80/EnBankYaghut/ExchangeService.asmx", request, new SoapActionCallback("http://tempuri.org/Statement"));

        return response;
    }

    public NormalTransferResponse normalTransfer(String username, String password, String sourceDepositNo, String destinationDepositNo, BigDecimal amount, String destinationComment, String sourceComment) {

        NormalTransfer request = new NormalTransfer();
        request.setUserName(username);
        request.setPassword(password);
        request.setSourceDepositNo(sourceDepositNo);
        request.setDestinationDepositNo(destinationDepositNo);
        request.setAmount(amount);
        request.setDestinationComment(destinationComment);
        request.setSourceComment(sourceComment);

        log.info("Requesting transfer from " + sourceDepositNo + "to " + destinationDepositNo);

        NormalTransferResponse response = (NormalTransferResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://10.0.64.50:80/EnBankYaghut/ExchangeService.asmx", request, new SoapActionCallback("http://tempuri.org/NormalTransfer"));

        return response;
    }

}
