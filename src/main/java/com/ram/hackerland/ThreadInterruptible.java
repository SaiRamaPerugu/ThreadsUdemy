package com.ram.hackerland;

import java.math.BigInteger;

public class ThreadInterruptible {

    public static void main(String[] args) {
        //Thread longTask = new Thread(new LongRunningTask());
        Thread longTask = new Thread(new PowerCalculation(BigInteger.valueOf(2000000000),BigInteger.valueOf(1000000000)));
        longTask.setName("LongTask");
        longTask.setDaemon(true);
        longTask.start();
        longTask.interrupt();
    }

    public static class LongRunningTask implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(200000);
            } catch(InterruptedException ex) {
                System.out.println("Thread has been interrupted");
            }
        }
    }

    public static class PowerCalculation implements  Runnable {
        private BigInteger base;
        private BigInteger power;
        public PowerCalculation(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }
        @Override
        public void run() {
            System.out.println("Result of " + base + "^" + power + "=" + calculatePower(base,power));
        }

        private BigInteger calculatePower(BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;
            for(BigInteger i=BigInteger.ZERO; i.compareTo(power) != 0; i=i.add(BigInteger.ONE)) {
                if(Thread.currentThread().isInterrupted()) {
                    System.out.println("Processing stopped.");
                    return BigInteger.ZERO;
                }
                result = result.multiply(base);
            }

            return result;
        }
    }
}
