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
	
	// TODO: isso aqui poderia ser definido em uma anotação na classe bean, ex: @Domain("Users")
	public final String DOMAIN = "Users";
	
	public UserManager() throws IOException {
		
		// TODO: isso aqui alguma fábrica deverá prover e manter em escopo de aplicação
		sdb = new AmazonSimpleDBClient(new PropertiesCredentials(
				UserManager.class.getResourceAsStream("/AwsCredentials.properties")));
		
		// TODO: isso aqui teria que ficar em algum inicializador para todos os domínios da aplicação
        sdb.createDomain(new CreateDomainRequest(DOMAIN));
	}

	/**
	 * Cria a conta do usuário persistindo o id, name, email, password e activation que já estão preenchidos no objeto
	 * passado por parâmetro.
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
	 * Retorna o usuário com base no seu login.
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
	 * Retorna o usuário com base no seu e-mail.
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
                
                // FIXME: em princípio o campo "email" será único no domínio...
                break;
            }
        }
        
        return user;
	}
	
}
