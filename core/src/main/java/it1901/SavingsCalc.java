package it1901;

public class SavingsCalc {
    
    public static long calculation(int monthlyAmount, int lumpAmount, double interestRate, int period) {
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


    public static void main(String[] args) {
        System.out.println(SavingsCalc.calculation(0, 1000, 0.10, 5));
    }

}

