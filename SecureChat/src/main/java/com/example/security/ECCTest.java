package com.example.security;

import java.math.BigInteger;

public class ECCTest {
    public static void main(String[] args) {
        ECC ecc = new ECC();
        BigInteger x = new BigInteger("7d550bc2384fd76a47b8b0871165395e4e4d5ab9cb4ee286d1c60d074d7d60ef", 16);
        BigInteger y = new BigInteger("8cc6dd01e747ccb8bedaae6e7fb875d036ce7e4e6231b75b93993b15202829ac", 16);

        System.out.println("Testing point: x=" + x.toString(16) + " y=" + y.toString(16));

        boolean isOnCurve = ecc.isPointOnCurve(x, y);
        System.out.println("Is point on curve? " + isOnCurve);

        if (isOnCurve) {
            ECC.Point testPoint = ecc.new Point(x, y);
            System.out.println("Point initialized successfully.");
        } else {
            System.out.println("Point is not on the curve.");
        }
    }
}
