package com.dus.impl.router;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import test.domain.Address;
import test.domain.User;

import com.dus.spi.EntityID;
import com.dus.spi.Tree;
import com.dus.spi.query.IQueryRequest;
import com.dus.spi.query.IQueryResponse;
import com.dus.spi.query.QueryResponse;
import com.dus.spi.router.IQueryRouter;

public class QueryDummyRouter implements IQueryRouter {

	@Override
	public IQueryResponse execute(IQueryRequest qRequest) {
		System.out.println("QUERY: ");
		System.out.println(qRequest);
		
		HashSet<EntityID> results = new HashSet<EntityID>();
		Tree<EntityID, String, Object> values = new Tree<EntityID, String, Object>();
		
		
		EntityID user1 = new EntityID(User.class.getSimpleName(), "user-1");
		EntityID user2 = new EntityID(User.class.getSimpleName(), "user-2");
		
		EntityID address1 = new EntityID(Address.class.getSimpleName(), "address-1");
		EntityID address2 = new EntityID(Address.class.getSimpleName(), "address-2");
		
		List<EntityID> addresses = new LinkedList<EntityID>();
		addresses.add(address1);
		addresses.add(address2);
		
		results.add(user1);
		results.add(user2);
		
		values.add(user1, "name", "Micael Pedrosa");
		values.add(user1, "pass", "password");
		values.add(user1, "addresses", addresses);
		
		values.add(user2, "name", "Micael Cardoso");
		values.add(user2, "pass", "x-password");
		
		values.add(address1, "local", "Aveiro");
		
		return new QueryResponse(qRequest.getId(), results, values);
	}

}
