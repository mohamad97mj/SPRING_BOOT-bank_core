package ir.co.pna.exchange.client.yaghut;
import ir.co.pna.exchange.client.yaghut.generated_resources.NormalTransfer;
import ir.co.pna.exchange.client.yaghut.generated_resources.NormalTransferResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import java.math.BigDecimal;


public class YaghutClient extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(YaghutClient.class);

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
                .marshalSendAndReceive("http://10.0.78.29/enbankyaghut/EnbankYaghutService.asmx", request, new SoapActionCallback("http://tempuri.org/NormalTransfer"));

        return response;
    }

}
