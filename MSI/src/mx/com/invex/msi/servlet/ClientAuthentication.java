/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
package mx.com.invex.msi.servlet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
public class ClientAuthentication {
 
  private String cookies;
  private HttpClient client = HttpClientBuilder.create().build();
  private final String USER_AGENT = "Mozilla/5.0";
 
  public static void main(String[] args) throws Exception {
 
	String url = "http://localhost:8080/InvexPortalSeguridad/faces/admin/listaUsuarios.xhtml";
	String gmail = "http://localhost:8080/InvexPortalSeguridad/";
 
	// make sure cookies is turn on
	CookieHandler.setDefault(new CookieManager());
 
	ClientAuthentication http = new ClientAuthentication();
 
	String page = http.GetPageContent(url);
 
	List<NameValuePair> postParams = 
               http.getFormParams(page, "adminUsr","Invex2014");
 
	http.sendPost(url, postParams);
 
	String result = http.GetPageContent(gmail);
	System.out.println(result);
 
	System.out.println("Done");
  }
 
  public void sendPost(String url, List<NameValuePair> postParams) 
        throws Exception {
 
	HttpPost post = new HttpPost(url);
 
	// add header
	post.setHeader("Host", "localhost:8080/MSI");
	post.setHeader("User-Agent", USER_AGENT);
	post.setHeader("Accept", 
             "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	post.setHeader("Accept-Language", "en-US,en;q=0.5");
	post.setHeader("Cookie", getCookies());
	post.setHeader("Connection", "keep-alive");
	post.setHeader("Referer", "http://localhost:8080/MSI/faces/home.xhtml");
	post.setHeader("Content-Type", "application/x-www-form-urlencoded");
 
	post.setEntity(new UrlEncodedFormEntity(postParams));
 
	HttpResponse response = client.execute(post);
 
	int responseCode = response.getStatusLine().getStatusCode();
 
	System.out.println("\nSending 'POST' request to URL : " + url);
	System.out.println("Post parameters : " + postParams);
	System.out.println("Response Code : " + responseCode);
 
	BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));
 
	StringBuffer result = new StringBuffer();
	String line = "";
	while ((line = rd.readLine()) != null) {
		result.append(line);
	}
 
	// System.out.println(result.toString());
 
  }
 
  public String GetPageContent(String url) throws Exception {
 
	HttpGet request = new HttpGet(url);
 
	request.setHeader("User-Agent", USER_AGENT);
	request.setHeader("Accept",
		"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	request.setHeader("Accept-Language", "en-US,en;q=0.5");
 
	HttpResponse response = client.execute(request);
	int responseCode = response.getStatusLine().getStatusCode();
 
	System.out.println("\nSending 'GET' request to URL : " + url);
	System.out.println("Response Code : " + responseCode);
 
	BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));
 
	StringBuffer result = new StringBuffer();
	String line = "";
	while ((line = rd.readLine()) != null) {
		result.append(line);
	}
 
	// set cookies
	setCookies(response.getFirstHeader("Set-Cookie") == null ? "" : 
                     response.getFirstHeader("Set-Cookie").toString());
 
	return result.toString();
 
  }
 
  public List<NameValuePair> getFormParams(
          String html, String username, String password)
			throws UnsupportedEncodingException {

	System.out.println("Extracting form's data...");

	Document doc = Jsoup.parse(html);

	// Google form id
	Element loginform = doc.getElementById("login_form");
	Elements inputElements = loginform.getElementsByTag("input");

	List<NameValuePair> paramList = new ArrayList<NameValuePair>();

	for (Element inputElement : inputElements) {
		String key = inputElement.attr("name");
		String value = inputElement.attr("value");

		if (key.equals("j_username"))
			value = username;
		else if (key.equals("j_password"))
			value = password;

		paramList.add(new BasicNameValuePair(key, value));

	}

	return paramList;
}
 
  public String getCookies() {
	return cookies;
  }
 
  public void setCookies(String cookies) {
	this.cookies = cookies;
  }
 
}