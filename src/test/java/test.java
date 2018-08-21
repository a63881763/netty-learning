import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class test {

    private Jedis jedis;

    @Before
    public void setJedis() {
        //连接redis服务器(在这里是连接本地的)
        jedis = new Jedis("47.93.103.72", 6379);
        //权限认证
        System.out.println("连接服务成功");
    }

    @Test
    public void testMap() {
        int[][] grid = new int[5][5];
        System.out.println(grid.length);
        System.out.println(fizzBuzz(3));
    }

    private String fval = "Fizz";
    private String bval = "Buzz";
    private String fbval = "FizzBuzz";
    public List<String> fizzBuzz(int n) {
        return new java.util.AbstractList<String>(){
            public int size(){
                return n;
            }

            public String get(int val){
                val++;
                if(val % 15 == 0)
                    return fbval;
                else if(val % 3 == 0)
                    return fval;
                else if(val % 5 == 0)
                    return bval;
                else
                    return String.valueOf(val);
            }
        };
    }
}
