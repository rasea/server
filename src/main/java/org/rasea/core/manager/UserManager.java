package org.rasea.core.manager;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.rasea.core.domain.User;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.CreateDomainRequest;
import com.amazonaws.services.simpledb.model.GetAttributesRequest;
import com.amazonaws.services.simpledb.model.GetAttributesResult;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.SelectRequest;
import com.amazonaws.services.simpledb.model.SelectResult;

import br.gov.frameworkdemoiselle.transaction.Transactional;

public class UserManager implements Serializable {

	private static final long serialVersionUID = 2097626878174583664L;

	// TODO: injetar aqui o carinha!
	//@Inject
	private AmazonSimpleDB sdb;
	
	// TODO: isso aqui poderia ser definido em uma anotaÃ§Ã£o na classe bean, ex: @Domain("Users")
	public final String DOMAIN = "Users";
	
	public UserManager() throws IOException {
		
		// TODO: isso aqui alguma fÃ¡brica deverÃ¡ prover e manter em escopo de aplicaÃ§Ã£o
		sdb = new AmazonSimpleDBClient(new PropertiesCredentials(
				UserManager.class.getResourceAsStream("AwsCredentials.properties")));
		
		// TODO: isso aqui teria que ficar em algum inicializador para todos os domÃ­nios da aplicaÃ§Ã£o
        sdb.createDomain(new CreateDomainRequest(DOMAIN));
	}

	/**
	 * Cria a conta do usuÃ¡rio persistindo o id, name, email, password e activation que jÃ¡ estÃ£o preenchidos no objeto
	 * passado por parÃ¢metro.
	 * 
	 * @param user
	 */
	@Transactional
	public void createAccount(User user) {
		
        List<ReplaceableAttribute> attrs = new ArrayList<ReplaceableAttribute>();
        attrs.add(new ReplaceableAttribute("name", user.getName(), true));
        attrs.add(new ReplaceableAttribute("password", user.getPassword(), true));
        
        // TODO: fazer para o restante dos campos do bean User 
        // ...
        
        sdb.putAttributes(new PutAttributesRequest(DOMAIN, user.getLogin(), attrs));
	}

	/**
	 * Retorna o usuÃ¡rio com base no seu login.
	 * 
	 * @param login
	 * @return User
	 */
	@Transactional
	public User findByLogin(String login) {
		User user = null;
		
		GetAttributesResult result = sdb.getAttributes(new GetAttributesRequest(DOMAIN, login));
		if (result != null) {
			user = new User();
			user.setLogin(login);
			
			List<Attribute> attributes = result.getAttributes();
			if (attributes != null && !attributes.isEmpty()) {
				for (Attribute attr : attributes) {
					final String name = attr.getName();
					final String value = attr.getValue();
					
					if ("name".equals(name)) {
						user.setName(value);
					} else if ("password".equals(name)) {
						user.setPassword(value);
					}
				}
			}
		}
		
		return user;
	}
	
	/**
	 * Retorna o usuÃ¡rio com base no seu e-mail.
	 * 
	 * @param email
	 * @return
	 */
	@Transactional
	public User findByEmail(String email) {
		User user = null;
		
        final String expr = "select * from `" + DOMAIN + "` where email = '" + email + "'";
        
        SelectRequest request = new SelectRequest(expr);
        SelectResult result = sdb.select(request);
        
        if (result != null) {
			user = new User();
        	
            for (Item item : result.getItems()) {
            	user.setLogin(item.getName());
            	
                for (Attribute attr : item.getAttributes()) {
					final String name = attr.getName();
					final String value = attr.getValue();
					
					if ("name".equals(name)) {
						user.setName(value);
					} else if ("password".equals(name)) {
						user.setPassword(value);
					}
                }
                
                // FIXME: em princÃ­pio o campo "email" serÃ¡ Ãºnico no domÃ­nio...
                break;
            }
        }
        
        return user;
	}

	public static void main(String[] args) throws Exception {
		UserManager manager = new UserManager();
		
		User user = new User();
		user.setLogin("john");
		user.setName("John Doe");
		user.setEmail("john@doe.com");
		user.setPassword("pass");
		
		System.out.println("Creating account: " + user);
		manager.createAccount(user);
		
		User user2 = manager.findByLogin("john");
		System.out.println("Found by login: " + user2);
		
		User user3 = manager.findByEmail("john@doe.com");
		System.out.println("Found by email: " + user3);
	}
	
}