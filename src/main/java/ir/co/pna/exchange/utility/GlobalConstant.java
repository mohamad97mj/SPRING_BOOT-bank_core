package ir.co.pna.exchange.utility;

import ir.co.pna.exchange.emum.OwnerType;
import ir.co.pna.exchange.entity.OperationalOwner;

public class GlobalConstant {

    public static final String OPERATIONAL_EXCHANGER_OWNER_BANK_ACCOUNT_ID = "9001";
    public static final String OPERATIONAL_EXPORTER_OWNER_BANK_ACCOUNT_ID = "9002";
    public static final String OPERATIONAL_CLAIM_OWNER_BANK_ACCOUNT_ID = "9003";
    public static final String OPERATIONAL_RETURN_OWNER_BANK_ACCOUNT_ID = "9004";

    public static OperationalOwner operationalExchangerOwner = new OperationalOwner(OPERATIONAL_EXCHANGER_OWNER_BANK_ACCOUNT_ID, OwnerType.EXCHANGER);
    public static OperationalOwner operationalExporterOwner = new OperationalOwner(OPERATIONAL_EXPORTER_OWNER_BANK_ACCOUNT_ID, OwnerType.EXPORTER);
    public static OperationalOwner operationalClaimOwner = new OperationalOwner(OPERATIONAL_CLAIM_OWNER_BANK_ACCOUNT_ID, OwnerType.CLAIM);
    public static OperationalOwner operationalReturnOwner = new OperationalOwner(OPERATIONAL_RETURN_OWNER_BANK_ACCOUNT_ID, OwnerType.RETURN);

}
