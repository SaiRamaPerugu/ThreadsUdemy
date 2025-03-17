package com.ram.hackerland;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class SearchParty {

    private static boolean isFound;
    public static void main(String[] args) {
        int[][] matrix = new int[10000][10000];
        matrix[4999][4999] = 1;

        SearchThread searchThread1 = new SearchThread(0,4999, matrix);
        SearchThread searchThread2 = new SearchThread(5000,9999, matrix);
        List<SearchThread> threads = new ArrayList<>();
        threads.add(searchThread1);
        threads.add(searchThread2);
        for(SearchThread thread: threads) {
            thread.start();
        }

        for(SearchThread thread: threads) {
            System.out.println(thread.getName() + " is in state " + thread.getState());
        }

        for(SearchThread thread:threads) {
            try {
                thread.join();
            } catch(InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }


    }

    public static class SearchThread extends Thread {

        private int x1;
        private int x2;
        private int[][] matrix;

        public SearchThread(int x1, int x2, int[][] matrix) {
            this.x1 = x1;
            this.x2 = x2;
            this.matrix = matrix;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " is searching matrix " + " from " + x1 + " to "  + x2 + " rows");
            search();
        }

        private void search() {
            try {
                Thread.sleep(10);
            } catch(InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
            for (int x = x1; x <= x2; x++) {
                for (int y = 0; y < 10000; y++) {
                    if (matrix[x][y] == 1) {
                        System.out.println(Thread.currentThread().getName() + " found the value in " + x + " and " + y + " position");
                        isFound = true;
                        break;
                    }
                }
            }
        }
    }
}
