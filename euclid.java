//Name: Rohit Mhatre
//Date : 08.29.2023
//Assignment 1 : Impleement Euclid's GCD Algorithm

import java.util.*;
import java.lang.*;
class euclid{
    public static void main(String[] args) {
        Scanner scan = new Scanner (System.in);
        int a = scan.nextInt();
        int b = scan.nextInt();
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