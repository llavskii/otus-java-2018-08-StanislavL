package ru.bank.ATM.banknotes;

public enum Dollars {
    ONE(1),
    TWO(2),
    FIVE(5),
    TEN(10),
    TWENTY(20),
    FIFTY(50),
    HUNDRED(100);
    private Integer denomination;

    Dollars(int i) {
        this.denomination = i;
    }

    public int denomination() {
        return denomination;
    }
}
