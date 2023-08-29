//Name: Rohit Mhatre
//Date : 08.29.2023
//Assignment 1 : Impleement Euclid's GCD Algorithm

import java.util.*;
class Euclid{
    public static void main(String[] args) {
        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);
        int gcd = euclid(a,b);
        System.out.println(gcd);
    }
    public static int euclid(int a, int b){
        if(a==0){
            return b;
        }
        else {
            return euclid(b % a, a);
        }
    }
}