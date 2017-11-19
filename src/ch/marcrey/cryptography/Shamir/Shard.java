package ch.marcrey.cryptography.Shamir;

import com.sun.scenario.effect.impl.hw.ShaderSource;

import java.math.BigInteger;
import java.util.Objects;

public class Shard {
    private final int agent;
    private final BigInteger shard;
    private final int n;
    private final int k;
    private final BigInteger p;

    public Shard(int agent, BigInteger shard, int n, int k, BigInteger p) {
        this.agent = agent;
        this.shard = shard;
        this.n = n;
        this.k = k;
        this.p = p;
    }

    public int getAgent() {
        return agent;
    }

    public BigInteger getShard() {
        return shard;
    }

    @Override
    public String toString(){
        return "[ Agent: " + this.agent + ", Shard: " + this.shard + "]";
    }

    public int getN() {
        return n;
    }

    public int getK() {
        return k;
    }

    public BigInteger getP() {
        return p;
    }

    public boolean sameParams(Shard other) {
        return (Objects.equals(this.p, other.p)) && (this.n == other.n) && (this.k == other.k);
    }

    public boolean sameParams(Shard[] others){
        if(others.length < 1){
            return true;
        }
        for(int i = 0; i < others.length; i++){
            if(!this.sameParams(others[i])){
                return false;
            }
        }
        return true;
    }

    public static int getK(Shard[] shards){
        if(shards.length < 1 || !shards[0].sameParams(shards)){
            return 0; // TODO: throw exception
        }
        return shards[0].k;
    }

    public static BigInteger getP(Shard[] shards){
        if(shards.length < 1 || !shards[0].sameParams(shards)){
            return null; // TODO: throw exception
        }
        return shards[0].p;
    }

}
