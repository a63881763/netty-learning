import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
//        jedis.flushAll();
        //添加数据
        Map<String, String> map = new HashMap<String, String>();

        long t0 = 0l;
        for(int i=1000;i<3000;i++){
            long t1 = System.currentTimeMillis();
            map.put("tieyo18071917173106e:"+i, i%100 +"");
            jedis.hset("15289382134","tieyo18071917173106e:"+i, i%100 +"");
            long t2 = System.currentTimeMillis()-t1;
            t0+=t2;
            System.out.println(t2);
        }
        System.out.println("===========");
        System.out.println(t0/2000.0);


//        long t = 0l;
//
//        for(int i=1000;i<3000;i++){
//            long t1 = System.currentTimeMillis();
//            jedis.hget("15289382133","tieyo18071917173106e:"+i);
//            long t2 = System.currentTimeMillis()-t1;
//            t+=t2;
//            System.out.println(t2);
//        }
//
//        System.out.println("===========");
//        System.out.println(t/2000.0);
    }
}
