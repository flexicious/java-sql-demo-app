package com.companyname.projectname.pacakge.service;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.companyname.projectname.pacakge.hibernate.dao.ServerRecordsHome;
import com.companyname.projectname.pacakge.model.MyFilter;
import com.companyname.projectname.pacakge.model.MyResponse;

@Component
@Path("/server_records")
public class ServerRecordsService {
	@Autowired
	private ServerRecordsHome serverRecordsHome;

	@Autowired
	private ObjectMapper objectMapper;

	public ServerRecordsHome getServerRecordsHome() {
		return serverRecordsHome;
	}

	public void setServerRecordsHome(ServerRecordsHome serverRecordsHome) {
		this.serverRecordsHome = serverRecordsHome;
	}

	@GET
	@Produces(value = "text/plain")
	public Response get(@Context UriInfo uriInfo) {
		MultivaluedMap<String, String> queryMap = uriInfo.getQueryParameters();
		if (queryMap == null || queryMap.get("name") == null|| (!("top_data".equals(queryMap.get("name").get(0))) && !("child_data".equals(queryMap.get("name").get(0)))))
			return Response.status(Status.BAD_REQUEST).build();

		MyFilter filterPageSort = null;
		if (null != queryMap.get("filterPageSort") && null != queryMap.get("filterPageSort").get(0))
			try {
				filterPageSort = objectMapper.readValue(queryMap.get("filterPageSort").get(0), MyFilter.class);
			} catch (IOException e) {
				e.printStackTrace();
			}

		MyResponse response = null;
		if ("top_data".equals(queryMap.get("name").get(0))) {
			response = serverRecordsHome.getTopLevelData(filterPageSort);
		} else if("child_data".equals(queryMap.get("name").get(0))){
			response = serverRecordsHome.getChildLevelData(filterPageSort, queryMap.get("parent_id"));
		}

		String jsonResponse = null;
		try {
			jsonResponse = objectMapper.writeValueAsString(response);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return Response.ok(jsonResponse, MediaType.TEXT_PLAIN_TYPE).build();

	}
}
