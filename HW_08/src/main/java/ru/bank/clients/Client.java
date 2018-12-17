package ru.bank.clients;

import ru.bank.bankCard.BankCard;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс, описывающий уникальность клиента как набор "имя + фамилия"
 */

public class Client implements Comparable {
    private String firstName;
    private String lastName;
    private Set<BankCard> clientCards = new HashSet<>();


    public Client(String name, String lastName) {
        this.firstName = name;
        this.lastName = lastName;
    }

    public void addCard(BankCard bankCard) {
        this.clientCards.add(bankCard);
    }


    public Set<BankCard> getClientCards() {
        return clientCards;
    }

    public BankCard getBankCardByNumber(String cardNumber) {
        for (BankCard bankCard : this.clientCards) {
            if (bankCard.getNumber().equals(cardNumber)) return bankCard;
        }
        return null;
    }

    public boolean availabilityCheckCard(String cardNumber) {
        for (BankCard bankCard : clientCards) {
            if (bankCard.getNumber().equals(cardNumber)) return true;
        }
        return false;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (!firstName.equals(client.firstName)) return false;
        return lastName.equals(client.lastName);
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        return result;
    }

    @Override
    public int compareTo(Object o) {
        Client that = (Client) o;
        int result = this.lastName.compareToIgnoreCase(that.lastName);
        if (result != 0) return result;
        return this.firstName.compareToIgnoreCase(that.firstName);
    }
}