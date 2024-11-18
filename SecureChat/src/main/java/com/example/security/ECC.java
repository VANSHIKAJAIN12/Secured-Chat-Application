//package com.example.security;
//
//import java.math.BigInteger;
//import java.security.SecureRandom;
//
//public class ECC {
//    private final BigInteger p;
//    private final BigInteger a;
//    private final BigInteger b;
//    private final BigInteger n;
//    private final BigInteger h;
//    private final Point G;
//
//    // Define ECC parameters for P-256
//    public ECC() {
//        p = new BigInteger("ffffffff00000001000000000000000000000000ffffffffffffffffffffffff", 16);
//        a = p.subtract(BigInteger.valueOf(3));
//        b = new BigInteger("5ac635d8aa3a93e7b3ebbd55769886bc651d06b0cc53b0f63bce3c3e27d2604b", 16);
//        n = new BigInteger("ffffffff00000000ffffffffffffffffbce6faada7179e84f5b6c1f6c8e99b315f", 16);
//        h = BigInteger.ONE;
//
//        G = new Point(
//                new BigInteger("7d550bc2384fd76a47b8b0871165395e4e4d5ab9cb4ee286d1c60d074d7d60ef", 16),
//                new BigInteger("8cc6dd01e747ccb8bedaae6e7fb875d036ce7e4e6231b75b93993b15202829ac", 16)
//        );
//    }
//
//    public KeyPair generateKeyPair() {
//        SecureRandom random = new SecureRandom();
//        BigInteger privateKey = new BigInteger(256, random).mod(n.subtract(BigInteger.ONE)).add(BigInteger.ONE);
//        Point publicKey = G.multiply(privateKey);
//        return new KeyPair(publicKey, privateKey);
//    }
//
//    public byte[] encryptKey(BigInteger aesKey, Point recipientPublicKey) {
//        BigInteger sharedSecret = recipientPublicKey.multiply(aesKey).getX();
//        return sharedSecret.toByteArray();
//    }
//
//    public BigInteger decryptKey(byte[] encryptedKey, BigInteger privateKey) {
//        BigInteger sharedSecret = new BigInteger(encryptedKey);
//        return sharedSecret;
//    }
//
//    public boolean isPointOnCurve(BigInteger x, BigInteger y) {
//        BigInteger leftSide = y.pow(2).mod(p);
//        BigInteger rightSide = x.pow(3).add(a.multiply(x)).add(b).mod(p);
//        System.out.println("x: " + x.toString(16));
//        System.out.println("y: " + y.toString(16));
//        System.out.println("Left side: " + leftSide.toString(16));
//        System.out.println("Right side: " + rightSide.toString(16));
//        return leftSide.equals(rightSide);
//    }
//
//    public class Point {
//        private final BigInteger x;
//        private final BigInteger y;
//
//        public Point(BigInteger x, BigInteger y) {
//            System.out.println("Initializing Point: x=" + x.toString(16) + " y=" + y.toString(16));
//            if (!isPointOnCurve(x, y)) {
//                throw new IllegalArgumentException("Invalid point");
//            }
//            this.x = x;
//            this.y = y;
//        }
//
//        public BigInteger getX() { return x; }
//        public BigInteger getY() { return y; }
//
//        public Point add(Point other) {
//            if (this.equals(other)) {
//                BigInteger lambda = this.x.pow(2).multiply(BigInteger.valueOf(3))
//                        .add(a).multiply(this.y.multiply(BigInteger.valueOf(2)).modInverse(p)).mod(p);
//                BigInteger x3 = lambda.pow(2).subtract(this.x).subtract(other.x).mod(p);
//                BigInteger y3 = lambda.multiply(this.x.subtract(x3)).subtract(this.y).mod(p);
//                return new Point(x3, y3);
//            } else {
//                BigInteger lambda = (other.y.subtract(this.y)).multiply(other.x.subtract(this.x).modInverse(p)).mod(p);
//                BigInteger x3 = lambda.pow(2).subtract(this.x).subtract(other.x).mod(p);
//                BigInteger y3 = lambda.multiply(this.x.subtract(x3)).subtract(this.y).mod(p);
//                return new Point(x3, y3);
//            }
//        }
//
//        public Point multiply(BigInteger k) {
//            Point result = new Point(BigInteger.ZERO, BigInteger.ZERO);
//            Point current = this;
//            while (k.compareTo(BigInteger.ZERO) > 0) {
//                if (k.mod(BigInteger.TWO).equals(BigInteger.ONE)) {
//                    result = result.add(current);
//                }
//                current = current.add(current);
//                k = k.shiftRight(1);
//            }
//            return result;
//        }
//
//        private boolean isValidPoint(BigInteger x, BigInteger y) {
//            BigInteger leftSide = y.pow(2).mod(p);
//            BigInteger rightSide = x.pow(3).add(a.multiply(x)).add(b).mod(p);
//            return leftSide.equals(rightSide);
//        }
//    }
//
//    public class KeyPair {
//        private final Point publicKey;
//        private final BigInteger privateKey;
//
//        public KeyPair(Point publicKey, BigInteger privateKey) {
//            this.publicKey = publicKey;
//            this.privateKey = privateKey;
//        }
//
//        public Point getPublicKey() { return publicKey; }
//        public BigInteger getPrivateKey() { return privateKey; }
//    }
//}







//package com.example.security;
//
//import java.math.BigInteger;
//import java.security.SecureRandom;
//
//public class ECC {
//    private final BigInteger p; // Prime modulus
//    private final BigInteger a; // Elliptic curve parameter
//    private final BigInteger b; // Elliptic curve parameter
//    private final BigInteger n; // Order of the base point
//    private final BigInteger h; // Cofactor
//    private final Point G; // Base point (generator)
//
//    public ECC() {
//        p = new BigInteger("FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFF", 16);
//        a = p.subtract(BigInteger.valueOf(3));
//        b = new BigInteger("5AC635D8AA3A93E7B3EBBD55769886BC651D06B0CC53B0F63BCE3C3E27D2604B", 16);
//        n = new BigInteger("FFFFFFFF00000000FFFFFFFFFFFFFFFFBCE6FAADA7179E84F4A13944F740E0BE", 16);
//        h = BigInteger.ONE;
//        G = new Point(
//                new BigInteger("7D550BC2384FD76A47B8B0871165395E4E4D5AB9CB4EE286D1C60D074D7D60EF", 16),
//                new BigInteger("8CC6DD01E747CCB8BEDAAE6E7FB875D036CE7E4E6231B75B93993B15202829AC", 16)
//        );
//    }
//
//    public KeyPair generateKeyPair() {
//        SecureRandom random = new SecureRandom();
//        BigInteger privateKey = new BigInteger(256, random).mod(n.subtract(BigInteger.ONE)).add(BigInteger.ONE);
//        Point publicKey = G.multiply(privateKey);
//        return new KeyPair(publicKey, privateKey);
//    }
//
//    public byte[] encryptKey(BigInteger aesKey, Point recipientPublicKey) {
//        BigInteger sharedSecret = recipientPublicKey.multiply(aesKey).getX();
//        return sharedSecret.toByteArray();
//    }
//
//    public BigInteger decryptKey(byte[] encryptedKey, BigInteger privateKey) {
//        BigInteger sharedSecret = new BigInteger(encryptedKey);
//        return sharedSecret;
//    }
//    public boolean isPointOnCurve(BigInteger x, BigInteger y) {
//        BigInteger leftSide = y.pow(2).mod(p);
//        BigInteger rightSide = x.pow(3).add(a.multiply(x)).add(b).mod(p);
//        System.out.println("x: " + x.toString(16));
//        System.out.println("y: " + y.toString(16));
//        System.out.println("Left side: " + leftSide.toString(16));
//        System.out.println("Right side: " + rightSide.toString(16));
//        return leftSide.equals(rightSide);
//    }
//
//    public class Point {
//        private final BigInteger x;
//        private final BigInteger y;
//
//        public Point(BigInteger x, BigInteger y) {
//            if (!isPointOnCurve(x, y)) {
//                throw new IllegalArgumentException("Invalid point");
//            }
//            this.x = x;
//            this.y = y;
//        }
//
//        public BigInteger getX() { return x; }
//        public BigInteger getY() { return y; }
//
//        public Point add(Point other) {
//            if (this.equals(other)) {
//                BigInteger lambda = (BigInteger.valueOf(3).multiply(this.x.pow(2)).add(a))
//                        .multiply(this.y.multiply(BigInteger.valueOf(2)).modInverse(p))
//                        .mod(p);
//                BigInteger x3 = lambda.pow(2).subtract(this.x.multiply(BigInteger.valueOf(2))).mod(p);
//                BigInteger y3 = lambda.multiply(this.x.subtract(x3)).subtract(this.y).mod(p);
//                return new Point(x3, y3);
//            } else {
//                BigInteger lambda = (other.y.subtract(this.y)).multiply(other.x.subtract(this.x).modInverse(p)).mod(p);
//                BigInteger x3 = lambda.pow(2).subtract(this.x).subtract(other.x).mod(p);
//                BigInteger y3 = lambda.multiply(this.x.subtract(x3)).subtract(this.y).mod(p);
//                return new Point(x3, y3);
//            }
//        }
//
//        public Point multiply(BigInteger k) {
//            Point result = new Point(BigInteger.ZERO, BigInteger.ZERO); // Identity point
//            Point current = this;
//            while (k.compareTo(BigInteger.ZERO) > 0) {
//                if (k.mod(BigInteger.TWO).equals(BigInteger.ONE)) {
//                    result = result.add(current);
//                }
//                current = current.add(current);
//                k = k.shiftRight(1);
//            }
//            return result;
//        }
//
//        private boolean isPointOnCurve(BigInteger x, BigInteger y) {
//            BigInteger leftSide = y.pow(2).mod(p);
//            BigInteger rightSide = x.pow(3).add(a.multiply(x)).add(b).mod(p);
//            return leftSide.equals(rightSide);
//        }
//    }
//
//    public class KeyPair {
//        private final Point publicKey;
//        private final BigInteger privateKey;
//
//        public KeyPair(Point publicKey, BigInteger privateKey) {
//            this.publicKey = publicKey;
//            this.privateKey = privateKey;
//        }
//
//        public Point getPublicKey() { return publicKey; }
//        public BigInteger getPrivateKey() { return privateKey; }
//    }
//}











//package com.example.security;
//
//import java.math.BigInteger;
//import java.security.SecureRandom;
//
//public class ECC {
//    private final BigInteger p; // Prime modulus
//    private final BigInteger a; // Elliptic curve parameter
//    private final BigInteger b; // Elliptic curve parameter
//    private final BigInteger n; // Order of the base point
//    private final BigInteger h; // Cofactor
//    private final Point G; // Base point (generator)
//
//    public ECC() {
//        p = new BigInteger("FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFF", 16);
//        a = p.subtract(BigInteger.valueOf(3));
//        b = new BigInteger("5AC635D8AA3A93E7B3EBBD55769886BC651D06B0CC53B0F63BCE3C3E27D2604B", 16);
//        n = new BigInteger("FFFFFFFF00000000FFFFFFFFFFFFFFFFBCE6FAADA7179E84F4A13944F740E0BE", 16);
//        h = BigInteger.ONE;
//        G = new Point(
//                new BigInteger("7D550BC2384FD76A47B8B0871165395E4E4D5AB9CB4EE286D1C60D074D7D60EF", 16),
//                new BigInteger("8CC6DD01E747CCB8BEDAAE6E7FB875D036CE7E4E6231B75B93993B15202829AC", 16)
//        );
//    }
//
//    public KeyPair generateKeyPair() {
//        SecureRandom random = new SecureRandom();
//        BigInteger privateKey = new BigInteger(256, random).mod(n.subtract(BigInteger.ONE)).add(BigInteger.ONE);
//        Point publicKey = G.multiply(privateKey);
//        return new KeyPair(publicKey, privateKey);
//    }
//
//    public byte[] encryptKey(BigInteger aesKey, Point recipientPublicKey) {
//        BigInteger sharedSecret = recipientPublicKey.multiply(aesKey).getX();
//        return sharedSecret.toByteArray();
//    }
//
//    public BigInteger decryptKey(byte[] encryptedKey, BigInteger privateKey) {
//        BigInteger sharedSecret = new BigInteger(encryptedKey);
//        return sharedSecret;
//    }
//
//    public boolean isPointOnCurve(BigInteger x, BigInteger y) {
//        BigInteger leftSide = y.pow(2).mod(p);
//        BigInteger rightSide = x.pow(3).add(a.multiply(x)).add(b).mod(p);
//        System.out.println("x: " + x.toString(16));
//        System.out.println("y: " + y.toString(16));
//        System.out.println("Left side: " + leftSide.toString(16));
//        System.out.println("Right side: " + rightSide.toString(16));
//        return leftSide.equals(rightSide);
//    }
//
//
//
//    public class Point {
//        private final BigInteger x;
//        private final BigInteger y;
//
//        public Point(BigInteger x, BigInteger y) {
//            this.x = x;
//            this.y = y;
//
//            // Validate that this point is on the curve: y^2 = x^3 + ax + b mod p
//            BigInteger lhs = y.modPow(BigInteger.TWO, p); // y^2
//            BigInteger rhs = x.modPow(BigInteger.valueOf(3), p).add(a.multiply(x)).add(b).mod(p); // x^3 + ax + b mod p
//
//            if (!lhs.equals(rhs)) {
//                throw new IllegalArgumentException("Invalid point: (" + x + ", " + y + ") is not on the curve.");
//            }
//        }
//
//
//        public BigInteger getX() {
//            return x;
//        }
//
//        public BigInteger getY() {
//            return y;
//        }
//
////        public Point add(Point other) {
////            if (this.equals(other)) {
////                BigInteger lambda = (BigInteger.valueOf(3).multiply(this.x.pow(2)).add(a))
////                        .multiply(this.y.multiply(BigInteger.valueOf(2)).modInverse(p))
////                        .mod(p);
////                BigInteger x3 = lambda.pow(2).subtract(this.x.multiply(BigInteger.valueOf(2))).mod(p);
////                BigInteger y3 = lambda.multiply(this.x.subtract(x3)).subtract(this.y).mod(p);
////                return new Point(x3, y3);
////            } else {
////                BigInteger lambda = (other.y.subtract(this.y)).multiply(other.x.subtract(this.x).modInverse(p)).mod(p);
////                BigInteger x3 = lambda.pow(2).subtract(this.x).subtract(other.x).mod(p);
////                BigInteger y3 = lambda.multiply(this.x.subtract(x3)).subtract(this.y).mod(p);
////                return new Point(x3, y3);
////            }
////        }
//public Point add(Point other) {
//    if (this.x == null && this.y == null) return other;  // Adding point at infinity
//    if (other.x == null && other.y == null) return this;  // Adding point at infinity
//
//    // If the points are equal, perform point doubling
//    if (this.x.equals(other.x) && this.y.equals(other.y)) {
//        return this.doublePoint();
//    }
//
//    // Slope = (y2 - y1) / (x2 - x1) mod p
//    BigInteger slope = (other.y.subtract(this.y))
//            .multiply(other.x.subtract(this.x).modInverse(p)).mod(p);
//
//    // x3 = slope^2 - x1 - x2 mod p
//    BigInteger x3 = slope.pow(2).subtract(this.x).subtract(other.x).mod(p);
//
//    // y3 = slope * (x1 - x3) - y1 mod p
//    BigInteger y3 = slope.multiply(this.x.subtract(x3)).subtract(this.y).mod(p);
//
//    return new Point(x3, y3);
//}
//
//
//        public Point doublePoint() {
//            if (this.y == null || this.x == null) {
//                // Doubling the point at infinity returns the point at infinity
//                return this;
//            }
//
//            BigInteger slope = (this.x.pow(2).multiply(BigInteger.valueOf(3)).add(a))
//                    .multiply(this.y.multiply(BigInteger.TWO).modInverse(p)).mod(p);
//
//            BigInteger x3 = slope.pow(2).subtract(this.x.multiply(BigInteger.TWO)).mod(p);
//            BigInteger y3 = slope.multiply(this.x.subtract(x3)).subtract(this.y).mod(p);
//
//            return new Point(x3, y3);
//        }
//
//
//        public Point multiply(BigInteger k) {
//            if (k.equals(BigInteger.ZERO)) {
//                // Multiplication by 0 returns the point at infinity (null, null) in this case
//                return new Point(null, null);
//            }
//
//            Point result = new Point(null, null);  // Start with the point at infinity
//            Point current = this;  // This is the base point
//
//            while (k.compareTo(BigInteger.ZERO) > 0) {
//                if (k.testBit(0)) {  // If the current bit is 1, add the current point to the result
//                    result = result.add(current);
//                }
//                current = current.doublePoint();  // Double the current point
//                k = k.shiftRight(1);  // Move to the next bit
//            }
//
//            return result;
//        }
//
//
//        private boolean isPointOnCurve(BigInteger x, BigInteger y) {
//            BigInteger leftSide = y.pow(2).mod(p);
//            BigInteger rightSide = x.pow(3).add(a.multiply(x)).add(b).mod(p);
//            return leftSide.equals(rightSide);
//        }
//
//        @Override
//        public boolean equals(Object obj) {
//            if (this == obj) return true;
//            if (obj == null || getClass() != obj.getClass()) return false;
//            Point point = (Point) obj;
//            return x.equals(point.x) && y.equals(point.y);
//        }
//
//        @Override
//        public int hashCode() {
//            return x.hashCode() + y.hashCode();
//        }
//    }
//
//    public class KeyPair {
//        private final Point publicKey;
//        private final BigInteger privateKey;
//
//        public KeyPair(Point publicKey, BigInteger privateKey) {
//            this.publicKey = publicKey;
//            this.privateKey = privateKey;
//        }
//
//        public Point getPublicKey() {
//            return publicKey;
//        }
//
//        public BigInteger getPrivateKey() {
//            return privateKey;
//        }
//    }
//}








package com.example.security;

import java.math.BigInteger;
import java.security.SecureRandom;

public class ECC {
    private final BigInteger p; // Prime modulus
    private final BigInteger a; // Elliptic curve parameter
    private final BigInteger b; // Elliptic curve parameter
    private final BigInteger n; // Order of the base point
    private final BigInteger h; // Cofactor
    private final Point G; // Base point (generator)

    // Point at infinity
    private final Point POINT_AT_INFINITY;

    public ECC() {
        p = new BigInteger("FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFF", 16);
        a = p.subtract(BigInteger.valueOf(3));
        b = new BigInteger("5AC635D8AA3A93E7B3EBBD55769886BC651D06B0CC53B0F63BCE3C3E27D2604B", 16);
        n = new BigInteger("FFFFFFFF00000000FFFFFFFFFFFFFFFFBCE6FAADA7179E84F4A13944F740E0BE", 16);
        h = BigInteger.ONE;
        G = new Point(
                new BigInteger("7D550BC2384FD76A47B8B0871165395E4E4D5AB9CB4EE286D1C60D074D7D60EF", 16),
                new BigInteger("8CC6DD01E747CCB8BEDAAE6E7FB875D036CE7E4E6231B75B93993B15202829AC", 16)
        );

        POINT_AT_INFINITY = new Point(null, null); // Initialize the point at infinity
    }

    public KeyPair generateKeyPair() {
        SecureRandom random = new SecureRandom();
        BigInteger privateKey = new BigInteger(256, random).mod(n.subtract(BigInteger.ONE)).add(BigInteger.ONE);
        Point publicKey = G.multiply(privateKey);
        return new KeyPair(publicKey, privateKey);
    }

    public byte[] encryptKey(BigInteger aesKey, Point recipientPublicKey) {
        BigInteger sharedSecret = recipientPublicKey.multiply(aesKey).getX();
        return sharedSecret.toByteArray();
    }

    public BigInteger decryptKey(byte[] encryptedKey, BigInteger privateKey) {
        BigInteger sharedSecret = new BigInteger(encryptedKey);
        return sharedSecret;
    }

    public boolean isPointOnCurve(BigInteger x, BigInteger y) {
        if (x == null || y == null) {
            return false;
        }
        BigInteger leftSide = y.pow(2).mod(p);
        BigInteger rightSide = x.pow(3).add(a.multiply(x)).add(b).mod(p);
        return leftSide.equals(rightSide);
    }

    public class Point {
        private final BigInteger x;
        private final BigInteger y;

        public Point(BigInteger x, BigInteger y) {
            this.x = x;
            this.y = y;

            // Validate that this point is on the curve
            if (x != null && y != null && !isPointOnCurve(x, y)) {
                throw new IllegalArgumentException("Invalid point: (" + x + ", " + y + ") is not on the curve.");
            }
        }

        public BigInteger getX() {
            return x;
        }

        public BigInteger getY() {
            return y;
        }

        public Point add(Point other) {
            if (this.equals(POINT_AT_INFINITY)) return other;
            if (other.equals(POINT_AT_INFINITY)) return this;

            if (this.equals(other)) {
                return this.doublePoint();
            }

            if (this.x.equals(other.x) && !this.y.equals(other.y)) {
                return POINT_AT_INFINITY;
            }

            BigInteger slope = (other.y.subtract(this.y))
                    .multiply(other.x.subtract(this.x).modInverse(p)).mod(p);

            BigInteger x3 = slope.pow(2).subtract(this.x).subtract(other.x).mod(p);
            BigInteger y3 = slope.multiply(this.x.subtract(x3)).subtract(this.y).mod(p);

            return new Point(x3, y3);
        }

        public Point doublePoint() {
            if (this.equals(POINT_AT_INFINITY)) {
                return this;
            }

            BigInteger slope = (this.x.pow(2).multiply(BigInteger.valueOf(3)).add(a))
                    .multiply(this.y.multiply(BigInteger.TWO).modInverse(p)).mod(p);

            BigInteger x3 = slope.pow(2).subtract(this.x.multiply(BigInteger.TWO)).mod(p);
            BigInteger y3 = slope.multiply(this.x.subtract(x3)).subtract(this.y).mod(p);

            return new Point(x3, y3);
        }

        public Point multiply(BigInteger k) {
            if (k.equals(BigInteger.ZERO)) {
                return POINT_AT_INFINITY;
            }

            Point result = POINT_AT_INFINITY;
            Point current = this;

            while (k.compareTo(BigInteger.ZERO) > 0) {
                if (k.testBit(0)) {
                    result = result.add(current);
                }
                current = current.doublePoint();
                k = k.shiftRight(1);
            }

            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Point point = (Point) obj;
            return (x == null && point.x == null && y == null && point.y == null) ||
                    (x.equals(point.x) && y.equals(point.y));
        }

        @Override
        public int hashCode() {
            return (x != null ? x.hashCode() : 0) + (y != null ? y.hashCode() : 0);
        }
    }

    public class KeyPair {
        private final Point publicKey;
        private final BigInteger privateKey;

        public KeyPair(Point publicKey, BigInteger privateKey) {
            this.publicKey = publicKey;
            this.privateKey = privateKey;
        }

        public Point getPublicKey() {
            return publicKey;
        }

        public BigInteger getPrivateKey() {
            return privateKey;
        }
    }
}
