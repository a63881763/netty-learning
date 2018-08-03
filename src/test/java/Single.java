import com.lambdaworks.redis.ClientOptions;
import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisFuture;
import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.async.RedisAsyncCommands;
import com.lambdaworks.redis.api.sync.RedisCommands;

public class Single
{

    public static void main(String[] args) throws InterruptedException {
        // 利用redis-server所绑定的IP和Port创建URI，
        RedisURI redisURI = RedisURI.create("47.94.144.29", 6379);

        // 创建集Redis单机模式客户端
        RedisClient redisClient = RedisClient.create(redisURI);
            ClientOptions clientOptions = ClientOptions.builder().autoReconnect(true).disconnectedBehavior(ClientOptions.DisconnectedBehavior.DEFAULT).requestQueueSize(100).cancelCommandsOnReconnectFailure(true).build();
        redisClient.setOptions(clientOptions);

        // 创建集Redis单机模式客户端
        RedisClient redisClient1 = RedisClient.create(redisURI);
        ClientOptions clientOptions1 = ClientOptions.builder().autoReconnect(true).disconnectedBehavior(ClientOptions.DisconnectedBehavior.DEFAULT).requestQueueSize(100).cancelCommandsOnReconnectFailure(true).build();
        redisClient1.setOptions(clientOptions1);

        // 开启连接
        StatefulRedisConnection<String, String> connect = redisClient.connect();
        RedisAsyncCommands<String, String> cmd = connect.async();

        StatefulRedisConnection<String, String> connect1 = redisClient1.connect();
        RedisCommands<String, String> cmd1 = connect1.sync();

//        cmd.hset("maizi","skr","666");

        for(int i = 0; i < 10000; i++){
            Thread.sleep(10);
            try{

                RedisFuture<Boolean> redisFuture = cmd.hset("maizi","haha",i+"");
                RedisFuture<Boolean> redisFuture2 = cmd.hset("maizi","skr",i+"");
                System.out.println(redisFuture);
            }catch (Exception e){
                e.printStackTrace();
            }
        }




    }
}