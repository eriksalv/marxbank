package it1901;

public class SavingsCalc {

    /**
     * Caclulates the total amount of the savings after a period. 
     * If the period text field is equal to 0, then we change it to 1.
     * 
     * @param monthlyAmount
     * @param lumpAmount
     * @param interestRate
     * @param period
     * @return the total amount after the given period.
     */
    public static int calculation(int monthlyAmount, int lumpAmount, double interestRate, int period) {
        if (period == 0) 
            period = 1;
        
        double lumpAmountSum = lumpAmount;
        double monthlyAmountSum = 0;

        for (int i = 0; i < period; i++) {
            monthlyAmountSum *= (1 + interestRate);
            lumpAmountSum *= (1 + interestRate);
            for (int j = 1; j <= 12; j++) {
                monthlyAmountSum += monthlyAmount * (1 + (j * interestRate / 12));
            }
        }

        double sum = lumpAmountSum + monthlyAmountSum;
        return (int) Math.round(sum) / 1;
    }

}

