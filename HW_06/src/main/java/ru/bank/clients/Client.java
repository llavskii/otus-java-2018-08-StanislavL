package ru.bank.clients;

/**
 * Класс, описывающий клиента как набор "имя + фамилия + год рождения"
 */

public class Client {
    private String name;
    private String lastName;
    private String birthYear;
    //creditLimit - предобренный кредитный лимит для клиента, "персональное предложение", кэш может служить для получения
    //этого параметра взамен расчета кредитного лимита внутри банковских систем. Не применяется при расчете hashcode и в методе equals
    private int creditLimit;
    //largeClientPrivateData - для упрощения подсчета объема данных в памяти по клиенту, какие-либо ёмкие клиентские данные
    private byte[] largeClientPrivateData = new byte[1024 * 1024];

    public Client(String name, String lastName, String birthYear, int creditLimit) {
        this.name = name;
        this.lastName = lastName;
        this.birthYear = birthYear;
        this.creditLimit = creditLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client that = (Client) o;
        if (!name.equals(that.name)) return false;
        if (!lastName.equals(that.lastName)) return false;
        return birthYear.equals(that.birthYear);
    }

    //hascode для клиента служит его уникальным идентификатором, ключ кэша
    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + birthYear.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthYear='" + birthYear + '\'' +
                ", creditLimit=" + creditLimit +
                '}';
    }
}