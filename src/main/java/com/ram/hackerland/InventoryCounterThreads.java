package com.ram.hackerland;

public class InventoryCounterThreads {

    public static void main(String[] args) throws InterruptedException {
        InventoryCounter inventoryCounter = new InventoryCounter();
        IncrementingThread incrementingThread = new IncrementingThread(inventoryCounter);
        DecrementingThread decrementingThread = new DecrementingThread(inventoryCounter);
        long fromTime = System.currentTimeMillis();
        incrementingThread.start();
        decrementingThread.start();
        incrementingThread.join();
        decrementingThread.join();
        long toTime = System.currentTimeMillis();
        System.out.println(toTime - fromTime + " ms");
        System.out.println(inventoryCounter.getItems());
    }

    public static class IncrementingThread extends Thread {

        InventoryCounter inventoryCounter;
        public IncrementingThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }
        @Override
        public void run() {
            for(int i=0; i < 10000;i++) {
                inventoryCounter.increment();
            }
        }
    }

    public static class DecrementingThread extends Thread {

        InventoryCounter inventoryCounter;
        public DecrementingThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }
        @Override
        public void run() {
            for(int i=0; i < 10000;i++) {
                inventoryCounter.decrement();
            }
        }
    }
    public static class InventoryCounter {
        private long items = 0;
        Object lock1 = new Object();
        Object lock2 = new Object();

        public  synchronized void increment() {
                items++;
        }

        public synchronized   void decrement() {
                items--;
        }

        public long getItems() {
            return items;
        }
    }
}
