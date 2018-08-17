import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

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

    }
}
