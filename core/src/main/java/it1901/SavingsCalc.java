package it1901;

public class SavingsCalc {
    
    public static long calculation(int monthlyAmount, int lumpAmount, double interestRate, int period) {
        if (monthlyAmount == 0) {
            double sum = lumpAmount * Math.pow(1 + (interestRate / 100), period);
            return Math.round(sum);
        }

        double lumpAmountSum = lumpAmount;
        double monthlyAmountSum = 0;

        for (int i = 0; i < period; i++) {
            monthlyAmountSum *= (1 + interestRate);
            lumpAmountSum *= (1 + interestRate);
            for (int j = 1; j <= 12; j++) {
                monthlyAmountSum += monthlyAmount * (1 + (j * interestRate / 12));
            }
            
        }
        return Math.round(lumpAmountSum + monthlyAmountSum);
    }

}

