package ru.bank.ATM.AtmState;

import ru.bank.ATM.AtmContext.ATMcontext;
import ru.bank.ATM.MenuMessages;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static ru.bank.ATM.MenuMessages.displayMenuMessage;

public class InsertingBanknotesState extends ATMstate {
    private static Pattern BANKNOTES_BUNDLE_PATTERN = Pattern.compile("([1-9]?[\\d]*\\*?[1|2|5]0?00)");

    @Override
    public void doAction(ATMcontext context) {
        displayMenuMessage(MenuMessages.INSERT_BANKNOTES);
        String inputLine = getInputLineFromClient();

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
                context.getATM().addBundleToBox(quantity, denomination);
                summ += quantity * denomination;
            }
        }
        if (!notValidBanknotes.isEmpty()) {
            displayMenuMessage(MenuMessages.INVALID_BANKNOTES_INPUT, String.join(",", notValidBanknotes));
        }
        context.getCurrentClient().getBankCardByNumber(context.getCurrentClientCardNumber()).replenishment(summ);
        displayMenuMessage(MenuMessages.YOU_HAVE_CREDITED_YOUR_CARD, String.valueOf(summ));
        context.setState(new ChooseOperationState());
    }


}
