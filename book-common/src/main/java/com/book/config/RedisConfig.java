/*
 * package com.book.config;
 * 
 * import java.util.HashSet; import java.util.Set;
 * 
 * import org.springframework.beans.factory.annotation.Qualifier; import
 * org.springframework.beans.factory.annotation.Value; import
 * org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.context.annotation.PropertySource; import
 * org.springframework.context.annotation.Scope;
 * 
 * import redis.clients.jedis.HostAndPort; import
 * redis.clients.jedis.JedisCluster;
 * 
 *//**
	 * @author Ran
	 * @date 2020年4月25日
	 *//*
		 * 
		 * @Configuration
		 * 
		 * @PropertySource("classpath:/properties/redis.properties") public class
		 * RedisConfig {
		 * 
		 * @Value("${redis.host}") private String host;
		 * 
		 * @Value("${redis.port}") private Integer port;
		 * 
		 * 
		 * 
		 * @Bean //多例
		 * 
		 * @Scope("prototype") public Jedis jedis() { return new Jedis(host, port); }
		 * 
		 * // 集群配置
		 * 
		 * @Value("${redis.cluster}") private String cluster;
		 * 
		 * @Bean
		 * 
		 * @Scope("prototype") public JedisCluster jediscluster(@Qualifier("redisSet")
		 * Set<HostAndPort> redisSet) { JedisCluster jedisCluster = new
		 * JedisCluster(redisSet); return jedisCluster; }
		 * 
		 * @Bean("redisSet")
		 * 
		 * @Scope public Set<HostAndPort> redisSet() { Set<HostAndPort> redisSet = new
		 * HashSet<>(); String[] redisnode = cluster.split(","); for (String node :
		 * redisnode) { String[] hostAndPort = node.split(":"); String host =
		 * hostAndPort[0]; int prot = Integer.parseInt(hostAndPort[1]); redisSet.add(new
		 * HostAndPort(host, prot)); } return redisSet; }
		 * 
		 * }
		 */
