package com.ram.hackerland;

import java.util.Arrays;

public class MoveZeroes {

    public static void main(String[] args) {
        int[] arr = {0,1,1,1,0,0};
        int l = 0;
        for(int r=0; r < arr.length;r++) {
            if(arr[r] != 0) {
                int temp = arr[r];
                arr[r] = arr[l];
                arr[l] = temp;
                l++;
            }
            System.out.println(Arrays.toString(arr));
        }

    }
}
