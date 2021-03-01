package ir.co.pna.exchange.utility;

import ir.co.pna.exchange.emum.OwnerType;
import ir.co.pna.exchange.entity.OperationalOwner;

import java.util.Calendar;

public class GlobalVariables {

    private static final String OPERATIONAL_EXCHANGER_OWNER_BANK_ACCOUNT_ID = "151-701-6113835-1"; // tavalaeeDepositNo
    private static String tavalaeeUsername = "matavallaie";
    private static String tavalaeePassword = "36325045";
//    private static String tavalaeeMobileNumber = "09027237097";
    private static String tavalaeeMobileNumber = "09059242876";

    private static final String OPERATIONAL_EXPORTER_OWNER_BANK_ACCOUNT_ID = "104-701-121924-1"; // yaghliDepositNo
    private static String yaghliUsername = "payamyaghli";
    private static String yaghliPassword = "81750304";
//    private static String yaghliMobileNumber = "09123896102";
    private static String yaghliMobileNumber = "09059242876";


    private static final String OPERATIONAL_CLAIM_OWNER_BANK_ACCOUNT_ID = "147-1-4681241-1"; // shahsavani
    private static String shahsavaniUsername = "da97349734";
    private static String shahsavaniPassword = "85438083";
    private static String shahsavaniMobileNumber = "09355772318";

    private static final String OPERATIONAL_RETURN_OWNER_BANK_ACCOUNT_ID = "159-701-6471174-1"; // mojahed
    private static String mojahedUsername = "6471174";
    private static String mojahedPassword = "55014205";
    private static String mojahedMobileNumber = "09059242876";


    public static OperationalOwner operationalExchangerOwner = new OperationalOwner(OPERATIONAL_EXCHANGER_OWNER_BANK_ACCOUNT_ID, OwnerType.EXCHANGER, tavalaeeUsername, tavalaeePassword, tavalaeeMobileNumber);
    public static OperationalOwner operationalExporterOwner = new OperationalOwner(OPERATIONAL_EXPORTER_OWNER_BANK_ACCOUNT_ID, OwnerType.EXPORTER, yaghliUsername, yaghliPassword, yaghliMobileNumber);
    public static OperationalOwner operationalClaimOwner = new OperationalOwner(OPERATIONAL_CLAIM_OWNER_BANK_ACCOUNT_ID, OwnerType.CLAIM, shahsavaniUsername, shahsavaniPassword, shahsavaniMobileNumber);
    public static OperationalOwner operationalReturnOwner = new OperationalOwner(OPERATIONAL_RETURN_OWNER_BANK_ACCOUNT_ID, OwnerType.RETURN, mojahedUsername, mojahedPassword, mojahedMobileNumber);


    public static long getNow() {
        return Calendar.getInstance().getTimeInMillis() / 1000;
    }

    public static String getThousandsSeparated(long number) {
        return String.format("%,d", number);
    }
}
