package ir.co.pna.exchange.emum;

public enum ContractStatus {
    NONE,
    NOT_CHARGED, // money is not in exchanger account
    CHARGED, // money is in exchanger account
    PAYED, // exchanger has payed money to exporter
    CLAIMED,
    SUCCESSFUL,
    SEMI_SUCCESSFUL,
    FAILED,
    UNKNOWN, // will be automatically set by system
}
