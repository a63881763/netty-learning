import com.lambdaworks.redis.ClientOptions;
import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisFuture;
import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.async.RedisAsyncCommands;

import java.util.concurrent.atomic.AtomicInteger;

public class Single
{

    public static void main(String[] args) throws InterruptedException {
        // 利用redis-server所绑定的IP和Port创建URI，
        RedisURI redisURI = RedisURI.create("47.94.144.29", 6379);

        // 创建集Redis单机模式客户端
        RedisClient redisClient = RedisClient.create(redisURI);
            ClientOptions clientOptions = ClientOptions.builder().autoReconnect(true).disconnectedBehavior(ClientOptions.DisconnectedBehavior.DEFAULT).requestQueueSize(10).cancelCommandsOnReconnectFailure(true).build();
        redisClient.setOptions(clientOptions);

        // 开启连接
        StatefulRedisConnection<String, String> connect = redisClient.connect();


        //每次传入大于队列大小的命令，使生产速度大于消费速度，以造成Internal stack size exceeded 的异常，产生空命令，多循环几次，使空命令占满请求队列
        for(int j = 0; j < 200; j++) {
            for (int i = 0; i < 15; i++) {
                try {
                    RedisFuture<Boolean> redisFuture = connect.async().hset("maizi", "haha", i + "");
                } catch (Exception e) {
                }
            }
            Thread.sleep(10);
        }
        System.out.println("===================");
        Thread.sleep(10000);
        for(int x = 0; x < 100000; x++) {
            try {
                RedisFuture<Boolean> redisFuture = connect.async().hset("maizi", "haha", x + "");
                System.out.println(redisFuture);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Thread.sleep(1000);
        }

    }
}