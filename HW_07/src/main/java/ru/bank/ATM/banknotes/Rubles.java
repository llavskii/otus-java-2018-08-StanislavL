package ru.bank.ATM.banknotes;

public enum Rubles {
    HUNDRED(100),
    TWO_HUNDRED(200),
    FIVE_HUNDRED(500),
    ONE_THOUSAND(1000),
    TWO_THOUSAND(2000),
    FIVE_THOUSAND(5000);
    private Integer denomination;

    Rubles(int i) {
        this.denomination = i;
    }

    public Integer denomination() {
        return denomination;
    }


}
