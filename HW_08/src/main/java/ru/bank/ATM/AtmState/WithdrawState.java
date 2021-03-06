package ru.bank.ATM.AtmState;

import ru.bank.ATM.AtmContext.AtmContext;
import ru.bank.ATM.MenuMessages;
import ru.bank.ATM.banknotes.Rubles;
import ru.bank.bankCard.BankCard;
import ru.bank.clients.Client;

import java.util.Map;
import java.util.regex.Pattern;

import static ru.bank.ATM.MenuMessages.displayMenuMessage;

public class WithdrawState extends AtmState {
    private static Pattern BANKNOTES_WITHDRAW_PATTERN = Pattern.compile("([1-9][0-9]{0,3}00)");

    @Override
    public void doAction(AtmContext context) {
        displayMenuMessage(MenuMessages.INPUT_WITHDRAW_AMOUNT, context.getATM().getAtmName());
        String withdrawSumm = getInputLineFromClient().trim();
        if (!BANKNOTES_WITHDRAW_PATTERN.matcher(withdrawSumm).matches() || !context.getATM().checkAvailabilityWithdrawSumm(withdrawSumm)) {
            displayMenuMessage(MenuMessages.AMOUNT_CANNOT_BE_ISSUED, context.getATM().getAtmName());
            return;
        }
        Map<Integer, Integer> availableBanknotes = context.getATM().checkAvailabilityBanknotesInATM(withdrawSumm);
        if (availableBanknotes == null) return;
        Map<Rubles, Integer> ATMrubleBoxes = context.getATM().getATMrubleBoxes();
        ATMrubleBoxes.forEach((k, v) -> {
            if (availableBanknotes.containsKey(k.denomination())) {
                ATMrubleBoxes.put(k, v - availableBanknotes.get(k.denomination()));
            }
        });
        Client client = context.getCurrentClient();
        BankCard bankCard = client.getBankCardByNumber(context.getCurrentClientCardNumber());
        bankCard.setBalance(bankCard.getBalance() - Integer.parseInt(withdrawSumm));
        displayMenuMessage(MenuMessages.YOU_GOT_THE_FOLLOWING, context.getATM().getAtmName(), withdrawSumm);
        context.setState(new ChooseOperationState());
    }
}
