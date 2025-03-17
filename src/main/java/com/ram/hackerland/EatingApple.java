package com.ram.hackerland;

public class EatingApple {

    public static void main(String[] args) {

        Apple apple = new Apple(8);

        Thread thread1 = new Thread(apple);
        Thread thread2 = new Thread(apple);
        thread1.start();
        thread2.start();
    }

    public static class Apple extends Thread {

        private int pieces;
        public Apple(int pieces) {
            this.pieces = pieces;
        }
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " is eating a piece.");
            eatAPiece();
            System.out.println(pieces + " are left");
        }

        public void eatAPiece() {
            synchronized (this) {
                this.pieces--;
            }
        }
    }


}
