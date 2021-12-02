package scc.db;

import com.azure.cosmos.ConsistencyLevel;
import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.CosmosDatabase;
import com.azure.cosmos.CosmosException;
import com.azure.cosmos.models.CosmosPatchOperations;
import com.azure.cosmos.models.CosmosItemRequestOptions;
import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.models.PartitionKey;
import com.azure.cosmos.util.CosmosPagedIterable;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import scc.cache.RedisCache;
//import scc.utils.AzureProperties;
import scc.data.Channel;
import scc.data.ChannelDAO;
import scc.data.MessageDAO;
import scc.data.UserDAO;
import scc.exceptions.AlreadyExistsException;
import scc.exceptions.CosmosDBException;
import scc.exceptions.InexistentEntityException;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

//import javax.servlet.ServletContext;
//import javax.ws.rs.core.Context;

public class CosmosDBLayer {
	//private static @Context ServletContext context;
	private static final String USERS_CONT = "Users";
	private static final String CHANNELS_CONT = "Channels";
	private static final String MESSAGES_CONT = "Messages";
	private static CosmosDBLayer instance;
	private static String CONNECTION_URL;
	private static String DB_KEY;
	private static String DB_NAME;

	//private Logger LOG = Logger.getLogger(CosmosDBLayer.class.getName());

	public static synchronized CosmosDBLayer getInstance() throws Exception {
		if( instance != null)
			return instance;

		try {
			CONNECTION_URL = System.getenv("AzureCosmosDBConnection");
			DB_KEY = System.getenv("COSMOSDB_KEY");
			DB_NAME = System.getenv("COSMOSDB_DATABASE");
			CosmosClient client = new CosmosClientBuilder()
					.endpoint(CONNECTION_URL)

					.key(DB_KEY)
					.gatewayMode()		// replace by .directMode() for better performance
					.consistencyLevel(ConsistencyLevel.SESSION)
					.connectionSharingAcrossClientsEnabled(true)
					.contentResponseOnWriteEnabled(true)
					.buildClient();
			instance = new CosmosDBLayer(client);
			return instance;
		}
		catch(Exception e) {
			Logger.getLogger(CosmosDBLayer.class.getName()).info("Connection URL is: "+CONNECTION_URL);
			throw new Exception("Could not instanciate cosmosDB client");
		}

	}

	private static CosmosClient client;
	private static CosmosDatabase db;
	private static CosmosContainer users;
	private static CosmosContainer channels;
	private static CosmosContainer messages;


	public CosmosDBLayer(CosmosClient client) {
		CosmosDBLayer.client = client;
	}

	private synchronized static void init() {
		if( db != null)
			return;
		db = client.getDatabase(DB_NAME);
		users = db.getContainer(USERS_CONT);
		messages = db.getContainer(MESSAGES_CONT);
		channels = db.getContainer(CHANNELS_CONT);
	}

	
	//-----------------------------------FUNCTIONS----------------------------------\\
	
	public void delChannel(String id) throws JsonMappingException, JsonProcessingException  {
		
		init();
		PartitionKey key = new PartitionKey(id);
		Channel c = getChannelById(id);
		String[] members = c.getMembers();
		
		RedisCache redis = RedisCache.getInstance();
		redis.delChannel(id, members);
		
		for (String u : members) {
			try {
				UserDAO user = getUserDAOById(u);
				PartitionKey key_user = new PartitionKey(user.getId());
				CosmosPatchOperations patchOps = CosmosPatchOperations.create();
				List<String> channels = Arrays.asList(user.getChannelIds());
				channels.remove(id);
				patchOps.replace("/channelIds", channels.toArray());
				users.patchItem(id, key, patchOps, UserDAO.class);
			}
			catch (InexistentEntityException e) {
				continue;
			}
			//TODO
			//SHOULD REMOVE CHANNEL FROM LIST OF CHANNELS OF THE USER
			//Update user to the new user
			//updateUser(id, user); -> isto pode precisar de alteracoes ou entao de novo metodo, para permitir a alteracao da lista dos channels (atualmente so permite mudar nome e a pass)
		}
		
		
		channels.deleteItem(id, key, new CosmosItemRequestOptions());
		
		
	}

	//------------------------------------------------------------------USERS------------------------------------------\\
	
	public UserDAO getUserDAOById (String id) throws InexistentEntityException {
		init();
		//return users.queryItems("SELECT * FROM Users WHERE Users.id=\"" + id + "\"", new CosmosQueryRequestOptions(), UserDAO.class);
		CosmosPagedIterable<UserDAO> result = users.queryItems("SELECT * FROM USERS u WHERE u.id=\"" + id + "\"", new CosmosQueryRequestOptions(), UserDAO.class);
		try {
			return result.iterator().next();
		} catch (Exception e) {
			throw new InexistentEntityException();
		}
	}
	
	

	//------------------------------------------------------------------CHANNELS------------------------------------------\\

	public Channel getChannelById (String id) throws CosmosException {
		init();
		CosmosPagedIterable<ChannelDAO> returnChannel = 
				channels.queryItems("SELECT * FROM CHANNELS c WHERE c.id = \"" + id + "\"" , new CosmosQueryRequestOptions(), ChannelDAO.class);
		try {
			Channel c = returnChannel.iterator().next().toChannel();
			return c;
		}
		catch (NoSuchElementException e) {
			return null;
		}
		
	}

	//------------------------------------------------------------------MESSAGES------------------------------------------\\

	
}
