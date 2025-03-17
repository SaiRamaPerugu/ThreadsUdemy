package com.ram.hackerland;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FactorialThreads {

    public static void main(String[] args) {
        List<Long> inputNumbers = Arrays.asList(0L, 3435L, 35435L, 2324L, 4546L, 23L, 2345L, 5566L);
        List<CalculateFactorial> threads = new ArrayList<>();
        for(Long number: inputNumbers) {
            threads.add(new CalculateFactorial(number));
        }

        for(Thread thread: threads) {
            thread.start();
        }

        for(Thread thread: threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        for(int i=0; i < inputNumbers.size();i++) {
            CalculateFactorial calculateFactorial = threads.get(i);
            if(calculateFactorial.isFinished()) {
                System.out.println("The result of " + inputNumbers.get(i) + " is " + calculateFactorial.result );
            } else {
                System.out.println("The calculation is still runnning for " + inputNumbers.get(i));
            }
        }
    }

    public static class CalculateFactorial extends Thread {

        private Long number;
        private boolean isFinished;
        private BigInteger result;
        public  CalculateFactorial(long number) {
            this.number = number;
            this.isFinished = false;
        }
        @Override
        public void run() {
            this.result = calc(number);
            try {
                Thread.sleep(1000);
            } catch(InterruptedException ex) {
                System.out.println("THread interrupted");
            }
            //System.out.println("Factorial of " + number + " is " + result);
            this.isFinished = true;
        }

        private BigInteger calc(Long number) {
            BigInteger result = BigInteger.ONE;
            for(long i=1L; i <= number;i++) {
                result = result.multiply(new BigInteger(Long.toString(i)));
            }
            return result;
        }


        public boolean isFinished() {
            return isFinished;
        }

    }
}
