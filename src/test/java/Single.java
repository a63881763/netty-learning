import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;

public class Single
{

    public static void main(String[] args) throws InterruptedException {
        // 利用redis-server所绑定的IP和Port创建URI，
        RedisURI redisURI = RedisURI.create("47.93.103.72", 6379);

        // 创建集Redis单机模式客户端
        RedisClient redisClient = RedisClient.create(redisURI);
        ClientOptions clientOptions = ClientOptions.builder().autoReconnect(true).requestQueueSize(10000).cancelCommandsOnReconnectFailure(true).build();
        redisClient.setOptions(clientOptions);
        // 开启连接
        StatefulRedisConnection<String, String> connect = redisClient.connect();
        RedisAsyncCommands<String, String> cmd = connect.async();

        for(int i =0;i<100000;i++){
            Thread.sleep(10);
            // set操作，成功则返回OK
            cmd.set("key", "value-test");
            // get操作，成功命中则返回对应的value，否则返回null
            cmd.get("key");
            // 删除指定的key
            cmd.del("key");
            // 获取redis-server信息，内容极为丰富
            cmd.info();

            // 列表操作
            String[] valuelist = {"China","Americal","England"};
            // 将一个或多个值插入到列表头部，此处插入多个
            cmd.lpush("listName", valuelist);
            // 移出并获取列表的第一个元素
            System.out.println(cmd.lpop("listName"));
            // 获取列表长度
            System.out.println(cmd.llen("listName"));
            // 通过索引获取列表中的元素
            System.out.println(cmd.lindex("listName", 1));
        }

    }
}