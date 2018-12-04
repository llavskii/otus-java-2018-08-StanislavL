package ru.bank.ATM;

import ru.bank.ATM.banknotes.Rubles;
import ru.bank.bankCard.BankCard;
import ru.bank.clients.BankClientGenerator;
import ru.bank.clients.BankDataBase;
import ru.bank.clients.Client;

import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Pattern;

public class RuBankATM implements Runnable {
    private BankClientGenerator clientGenerator = new BankClientGenerator();
    private final Map<Rubles, Integer> ATMrubleBoxes = new HashMap<>();
    private static int currentMenuItem = 0;
    private static Pattern BANKNOTES_BUNDLE_PATTERN = Pattern.compile("([1-9]?[\\d]*\\*?[1|2|5]0?00)");
    private static Pattern BANKNOTES_WITHDRAW_PATTERN = Pattern.compile("([1-9][0-9]{0,3}00)");
    private static Client currentClient = null;
    private static String currentClientCardNumber = null;
    private static boolean isNewClient;
    private static Comparator<Integer> banknotesDescComparator = (o1, o2) -> o2.compareTo(o1);


    //Конструктор банкомата с параметром int имитирует загрузку одинаковым количеством банкнот в каждую ячейку
    public RuBankATM(int boxCapacity) {
        Rubles[] rubles = Rubles.values();
        for (int i = 0; i < rubles.length; i++) {
            ATMrubleBoxes.put(rubles[i], boxCapacity);
        }
    }

    @Override
    public void run() {
        String inputLine = null;
        while (true) {
            menu();
            try {
                inputLine = getInputLineFromClient();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            if (inputLine != null) {
                flowMainMenuItem(inputLine);
            } else {
                menu(92);
                currentMenuItem = 0;
            }
        }
    }

    public void menu() {
        MenuItems.displayMenuItem(currentMenuItem);
    }

    private static String getInputLineFromClient() throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Callable<String> callable = new ConsoleInputReadTask();
        Future<String> future = executor.submit(callable);
        while (currentMenuItem == 0 || System.currentTimeMillis() - start < 8_000) {
            Thread.sleep(10);
            if (future.isDone()) return future.get();
        }

        future.cancel(true);
        executor.shutdown();
        return null;

    }

    private void flowMainMenuItem(String inputLine) {
        switch (currentMenuItem) {
            case 0:
                if (BankCard.validateCardNumber(inputLine)) {
                    //Присваиваем введенный номер переменной currentClientCardNumber
                    currentClientCardNumber = inputLine.replaceAll("\\s", "");
                    //Поиск по клиентам в БД на наличие у них карты с введенным номером
                    Client client = BankDataBase.availabilityCheckClientByCardNumber(currentClientCardNumber);
                    //Если клиент найден, то присваиваем его переменной currentClient
                    if (client != null) {
                        currentClient = client;
                        //Устанавливаем переменную = false, это один из активных клиентов
                        isNewClient = false;
                    } else {
                        //Если клиент не найден, то генерим для него тестового клиента
                        System.out.println("[ Сгенерирован тестовый клиент! ]");
                        currentClient = clientGenerator.getGeneratedClient();

                        //Устанавлиаем переменную isNewClient, если этого сгененированного
                        // клиента не было /или был в БД (у нас есть клиенты с несколькими картами!)
                        isNewClient = !BankDataBase.availabilityCheckClient(currentClient);
                    }
                    currentMenuItem = 10;
                } else {
                    menu(1);
                }
                break;
            case 10:
                if (BankCard.validatePINnumber(inputLine)) {
                    BankCard bankCard = new BankCard(currentClientCardNumber, inputLine);
                    //Если это новый клиент, то добавляем карту данному клиенту с номером и пин кодом и вносим его в БД
                    if (isNewClient) {
                        currentClient.addCard(bankCard);
                        BankDataBase.addClient(currentClient);
                    } //Если это не новый клиент, и у него есть карта с таким номером
                    else if (currentClient.getClientCards().contains(bankCard)) {
                        //то проверяем валидность пин кода
                        if (!currentClient.getBankCardByNumber(currentClientCardNumber).getPIN().equals(inputLine)) {
                            menu(12);
                            currentMenuItem = 10;
                            break;
                        }
                    } //Если это не новый клиент, и у него нет карты с таким номером, то добавляем ему новую карту
                    else {
                        currentClient.addCard(bankCard);
                    }
                    currentMenuItem = 20;
                } else {
                    menu(11);
                    currentMenuItem = 10;
                }
                break;
            case 20:
                validationInputLineOfOperationType(inputLine);
                break;
            case 30:
                validationInsertedBanknotes(inputLine);
                break;
            case 40:
                validationWithdraw(inputLine);
                break;
            case 50:
                knowClientBalance();
                break;
            default:
                throw new UnsupportedOperationException("ATM operation was interrupted!");
        }
    }

    private void knowClientBalance() {
        menu(50, currentClient.getBankCardByNumber(currentClientCardNumber).getBalance());
    }

    private void validationInsertedBanknotes(String inputLine) {
        String[] banknotes = inputLine.split("\\s");
        List<String> notValidBanknotes = new ArrayList<>();

        int quantity;
        int denomination;
        int summ = 0;

        for (String bundle : banknotes) {
            if (!BANKNOTES_BUNDLE_PATTERN.matcher(bundle).matches()) {
                notValidBanknotes.add(bundle);
            } else {
                String[] quantityAndDenomination = bundle.split("\\*");
                if (quantityAndDenomination.length == 2) {
                    quantity = Integer.parseInt(quantityAndDenomination[0]);
                    denomination = Integer.parseInt(quantityAndDenomination[1]);
                } else if (quantityAndDenomination.length == 1) {
                    quantity = 1;
                    denomination = Integer.parseInt(quantityAndDenomination[0]);
                } else throw new UnsupportedOperationException("ATM operation was interrupted!");

                addBundleToBox(quantity, denomination);
                summ += quantity * denomination;
            }
        }
        if (!notValidBanknotes.isEmpty()) menu(32, String.join(",", notValidBanknotes));
        currentClient.getBankCardByNumber(currentClientCardNumber).replenishment(summ);
        menu(31, String.valueOf(summ));
        currentMenuItem = 20;

    }

    private void validationWithdraw(String inputLine) {
        String withdrawSumm = inputLine.trim();
        if (!BANKNOTES_WITHDRAW_PATTERN.matcher(withdrawSumm).matches() || !checkAvailabilityWithdrawSumm(withdrawSumm)) {
            menu(41);
            currentMenuItem = 40;
            return;
        }
        Map<Integer, Integer> availableBanknotes = checkAvailabilityBanknotesInATM(inputLine);
        if (availableBanknotes == null) return;
        ATMrubleBoxes.forEach((k, v) -> {
            if (availableBanknotes.containsKey(k.denomination())) {
                ATMrubleBoxes.put(k, v - availableBanknotes.get(k.denomination()));
            }
        });
        menu(42, inputLine);
        currentMenuItem = 20;
    }


    private Map<Integer, Integer> checkAvailabilityBanknotesInATM(String stringRequestWithdrawSumm) {
        int requestWithdrawSumm = Integer.parseInt(stringRequestWithdrawSumm);
        Map<Integer, Integer> availableBanknotes = new TreeMap<>(banknotesDescComparator);
        Map<Integer, Integer> withdrawBanknotes = new TreeMap<>(banknotesDescComparator);
        ATMrubleBoxes.forEach((k, v) -> {
            if (v > 0) availableBanknotes.put(k.denomination(), v);
        });

        Iterator iterator = availableBanknotes.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            //номинал секции
            int k = (int) pair.getKey();
            //количество купюр в секции
            int v = (int) pair.getValue();
            //проверка наличия купюр в секции
            if (v == 0) continue;
            int integerQuantity = requestWithdrawSumm / k;
            //ограничение в выдаче по достаточности купюр в выбранной секции
            if (integerQuantity > v) integerQuantity = v;
            if (integerQuantity == 0) continue;
            withdrawBanknotes.put(k, integerQuantity);
            requestWithdrawSumm -= k * integerQuantity;
            //если остаток от запрашиваемой суммы = 0, прерываем цикл
            if (requestWithdrawSumm == 0) break;
        }
        //если осталась какая либо невыданная сумма, возвращаем null
        if (requestWithdrawSumm > 0) return null;
        return withdrawBanknotes;

    }

    private boolean checkAvailabilityWithdrawSumm(String withdrawSumm) {
        Rubles[] boxes = Rubles.values();
        int quantity;
        int summ = 0;
        for (Rubles box : boxes) {
            quantity = ATMrubleBoxes.get(box);
            summ += quantity * box.denomination();
        }
        return summ > Integer.parseInt(withdrawSumm);
    }

    private void addBundleToBox(int quantity, int denomination) {
        Rubles[] boxes = Rubles.values();
        for (Rubles box : boxes) {
            if (box.denomination() == denomination) {
                int previous = ATMrubleBoxes.get(box);
                ATMrubleBoxes.put(box, quantity + previous);
            }
        }
    }

    private void validationInputLineOfOperationType(String inputLine) {
        switch (inputLine.toUpperCase()) {
            case "D":
                currentMenuItem = 30;
                break;
            case "W":
                currentMenuItem = 40;
                break;
            case "K":
                knowClientBalance();
                currentMenuItem = 20;
                break;
            case "R":
                menu(90);
                currentMenuItem = 0;
                break;
            default:
                currentMenuItem = 20;
                break;
        }
    }

    public void menu(int itemNumber, String... args) {
        MenuItems.displayMenuItem(itemNumber, args);
    }
}