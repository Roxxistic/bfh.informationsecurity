package ch.marcrey.cryptography.Shamir;

import java.math.BigInteger;

/**
 * Split a Secret in n shards and assemble k to n shards to regain the secret.
 */
public class KeySharing {

    public Shard[] split(BigInteger secret, int n, int k, BigInteger p, BigInteger[] a){
        Shard[] shards = new Shard[n];

        for(int i = 0; i < n; i++){
            BigInteger shard = secret;
            int x = i + 1;

            for(int j = 1; j <= k-1; j++){
                BigInteger xpowj = new BigInteger(Integer.toString((int)Math.pow(x, j)));
                BigInteger ax = a[j-1].multiply(xpowj);
                shard = shard.add(ax);
            }
            shard = shard.mod(p);
            shards[i] = new Shard(x, shard, n, k, p);
        }

        return shards;
    }

    public BigInteger merge(Shard[] shards) {

        BigInteger p = Shard.getP(shards);
        int k = Shard.getK(shards);

        BigInteger sum = BigInteger.ZERO; // initialize the sum.
        for(int i = 0; i < k; i++){
            BigInteger dividend = BigInteger.ONE;
            BigInteger divisor = BigInteger.ONE;

            for(int j = 0; j < k; j++){
                if(j==i){
                    continue;
                }
                dividend = dividend.multiply(BigInteger.valueOf(0 - shards[j].getAgent()));
                divisor = divisor.multiply(BigInteger.valueOf(shards[i].getAgent() - shards[j].getAgent()));
            }

            BigInteger division = dividend.divide(divisor);
            sum = sum.add(shards[i].getShard().multiply(dividend).multiply(divisor.modInverse(p)));
        }

        return sum.mod(p);
    }
}

