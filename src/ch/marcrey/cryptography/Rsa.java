package ch.marcrey.cryptography;


import java.math.BigInteger;

public class Rsa {

    private BigInteger nil = new BigInteger("0");
    private BigInteger one = new BigInteger("1");
    private BigInteger two = new BigInteger("2");

    public BigInteger encrypt(BigInteger m, BigInteger e, BigInteger p, BigInteger q){
        BigInteger N = calcN(p,q);
        return modexp(m, e, N);
    }

    public BigInteger decrypt(BigInteger m, BigInteger d, BigInteger p, BigInteger q){
        BigInteger N = calcN(p,q);
        return modexp(m, d, N);
    }

    private BigInteger calcN(BigInteger p, BigInteger q){
        return p.multiply(q);
    }

    // return m^k mod N
    private BigInteger modexp(BigInteger m, BigInteger k, BigInteger N){

        // Base Case
        if(k.equals(nil)){
            return one;
        }

        BigInteger rec = modexp(m,k.divide(two),N);
        BigInteger c = (rec.multiply(rec)).mod(N);

        // k is even
        if(k.mod(two).equals(nil)){
            return c;
        }
        // k is odd
        else{
            return (c.multiply(m)).mod(N);
        }
    }
}