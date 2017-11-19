package ch.marcrey.cryptography.Shamir;

import java.math.BigInteger;

public class Example {
    public static void main(String[] args){
        KeySharing keySharing = new KeySharing();

        // SHARDING

        BigInteger secret = new BigInteger("190503180520");
        BigInteger p = new BigInteger("1234567890133");
        int n = 4;
        int k = 3;

        BigInteger a1 = new BigInteger("482943028839");
        BigInteger a2 = new BigInteger("1206749628665");
        BigInteger[] as = new BigInteger[]{a1, a2};

        Shard[] shards = keySharing.split(secret,n,k,p,as);
        for(int i = 0; i < n; i++){
            System.out.println(shards[i]);
        }

        // RECONSTRUCTION

        Shard p1 = shards[0];
        Shard p2 = shards[1];
        Shard p3 = shards[2];
        Shard p4 = shards[3];

        Shard[] kShards;
        BigInteger keyReconstructed;

        kShards = new Shard[]{p1, p2, p3};
        keyReconstructed = keySharing.merge(kShards);
        System.out.println(keyReconstructed);
        kShards = new Shard[]{p1, p2, p4};
        keyReconstructed = keySharing.merge(kShards);
        System.out.println(keyReconstructed);
        kShards = new Shard[]{p1, p3, p4};
        keyReconstructed = keySharing.merge(kShards);
        System.out.println(keyReconstructed);
        kShards = new Shard[]{p2, p3, p4};
        keyReconstructed = keySharing.merge(kShards);
        System.out.println(keyReconstructed);
    }
}
