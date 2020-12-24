//package ir.co.pna.exchange.client.country;//
//import ir.co.pna.exchange.client.country.generated_resources.GetCountryRequest;
//import ir.co.pna.exchange.client.country.generated_resources.GetCountryResponse;
//import ir.co.pna.exchange.client.yaghut.generated_resources.NormalTransfer;
//import ir.co.pna.exchange.client.yaghut.generated_resources.NormalTransferResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
//import org.springframework.ws.soap.client.core.SoapActionCallback;
//
//public class CountryClient extends WebServiceGatewaySupport {
//
//    private static final Logger log = LoggerFactory.getLogger(CountryClient.class);
//
//    public NormalTransferResponse getCountry(String country) {
//
//        NormalTransfer request = new NormalTransfer();
////		request.setName(country);
//
//        log.info("Requesting location for " + country);
//
//        NormalTransferResponse response = (NormalTransferResponse) getWebServiceTemplate()
//                .marshalSendAndReceive("http://localhost:8080/ws/countries", request,
//                        new SoapActionCallback(
//                                "http://localhost:8080/GetCountryRequest"));
//
//        return response;
//    }
//
//}
