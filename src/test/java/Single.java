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
        RedisAsyncCommands<String, String> cmd = connect.async();


        for(int a = 0; a < 20; a++) {
            for (int i = 0; i <= 10; i++) {

                try {

                    RedisFuture<Boolean> redisFuture = cmd.hset("maizi", "haha", i + "");
//                    System.out.println(redisFuture);
                } catch (Exception e) {
//                    e.printStackTrace();

                }
            }

            Thread.sleep(10);

        }
        System.out.println("===================");

        Thread.sleep(10000);
        for(int a = 0; a < 1; a++) {
            for (int i = 0; i <= 10; i++) {

                try {

                    RedisFuture<Boolean> redisFuture = cmd.hset("maizi", "haha", i + "");
                    System.out.println(redisFuture);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            Thread.sleep(1000);
        }



    }
}