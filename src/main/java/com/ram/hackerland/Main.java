package com.ram.hackerland;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static final int MAX_PASSWORD = 9999;
    public static void main(String[] args) {
        System.out.println("Hello world!");

        List<Thread> threads = new ArrayList<>();
        Random random = new Random();
        Vault vault = new Vault(random.nextInt(MAX_PASSWORD));
        threads.add(new AscendingThread(vault));
        threads.add(new DescendingThread(vault));
        threads.add(new PoliceThread());
        for(Thread thread: threads) {
            thread.start();
        }

    }

    private static class Vault {
        int password;
        public Vault(int password) {
            this.password = password;
        }

        public boolean isCorrectPassword(int guess) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {

            }
            return guess == password;
        }
    }

    private static class HackerThread extends Thread {
        protected Vault vault;
        public HackerThread(Vault vault) {
            this.vault = vault;
            this.setName(this.getClass().getSimpleName());
            this.setPriority(Thread.MAX_PRIORITY);
        }

        @Override
        public void run() {
            super.run();
        }
    }

    private static class AscendingThread extends HackerThread {

        public AscendingThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for(int i=0; i < Main.MAX_PASSWORD; i++) {
                if(vault.isCorrectPassword(i)) {
                    System.out.println(this.getName() + " guessed the password. ");
                    System.exit(0);
                }
            }
        }
    }

    private static class DescendingThread extends HackerThread {

        public DescendingThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for(int i=Main.MAX_PASSWORD; i > 0; i--) {
                if(vault.isCorrectPassword(i)) {
                    System.out.println(this.getName() + " guessed the password. ");
                    System.exit(0);
                }
            }
        }
    }

    private static class PoliceThread extends Thread {
        @Override
        public void run() {
            try {
                for (int i = 10; i > 0; i--) {
                    Thread.sleep(1000);
                    System.out.println(i);
                }
            } catch(InterruptedException e) {
            }
            System.out.println("Game over for hackers");
            System.exit(0);
        }

    }

}