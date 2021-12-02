package scc.cache;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisException;
import scc.data.UserDAO;

public class RedisCache {
	
	private static final String USER = "User:";
	private static final String SESSION = "Session:";
	private static final String CHANNEL = "Channel:";
	private static final String CHANNEL_PAGES="ChannelPages:";
	private static final String MESSAGE = "Message:";
	private Logger LOG = Logger.getLogger(RedisCache.class.getName());
	private static final Long EXPIRATION = 1800L;
	private static String REDISHOSTNAME;
	private static String REDISKEY;
	
	private static JedisPool instance;
	
	public synchronized static JedisPool getCachePool() {
		if( instance != null)
			return instance;
		final JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(128);
		poolConfig.setMaxIdle(128);
		poolConfig.setMinIdle(16);
		poolConfig.setTestOnBorrow(true);
		poolConfig.setTestOnReturn(true);
		poolConfig.setTestWhileIdle(true);
		poolConfig.setNumTestsPerEvictionRun(3);
		poolConfig.setBlockWhenExhausted(true);
		instance = new JedisPool(poolConfig, System.getenv("REDIS_URL"), 6380, 1000, System.getenv("REDIS_KEY"), true);
		return instance;
		
	}
	
	public synchronized static RedisCache getInstance() {
		return new RedisCache(getCachePool());
	}

	public RedisCache(JedisPool instance) {
		RedisCache.instance = instance;
	}

	private static ObjectMapper init() {
		ObjectMapper mapper = new ObjectMapper();

		Locale.setDefault(Locale.US);

		return mapper;
	}

	public void delChannel(String id, String[] members) throws JsonMappingException, JsonProcessingException {
		
		try (Jedis jedis = instance.getResource()) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				jedis.del(CHANNEL+id);
			}
			catch (JedisException e) {
				;
			}
			finally {
				for (String uid : members) {
					try {
						String user = jedis.get(USER+uid);
						if (user != null) {
							UserDAO u = mapper.readValue(user, UserDAO.class);
							List<String> channels = Arrays.asList(u.getChannelIds());
							channels.remove(id);
							u.setChannelIds((String[])channels.toArray());
							updateUser(id, u);
						}
					}
					catch (JedisException e) {
						continue;
					}
				} 
			}
		}
		catch (JedisException e) {
			LOG.severe(e.getMessage());
			return;		
		}
		
	}
	
	public boolean updateUser (String id, UserDAO user) {
		String key = USER + id;
		try (Jedis jedis = instance.getResource()) {
			if (jedis.exists(key)) {
				removeUser(id);
				putUser(user);
				jedis.expire(key, EXPIRATION);
			}
			return true;
		}
		catch (JedisException e) {
			LOG.severe(e.getMessage());
			return false;		
		}
	}
	
	public boolean removeUser(String id) {
		String key = USER + id;
		try (Jedis jedis = instance.getResource()) {
			jedis.del(key);
			return true;
		} 
		catch (JedisException e) {
			LOG.severe(e.getMessage());
			return false;		
		}
	}
	
	public void putUser (UserDAO user) {
		String key = USER + user.getId();
		try {
			ObjectMapper mapper = init();
			try (Jedis jedis = instance.getResource()) {
				jedis.set(key, mapper.writeValueAsString(user));
				jedis.expire(key, EXPIRATION);
				
				Long cnt = jedis.lpush("MostRecentUsers", mapper.writeValueAsString(user));
				if (cnt > 5)
					jedis.ltrim("MostRecentUsers", 0, 4);
				cnt = jedis.incr("NumUsers");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
