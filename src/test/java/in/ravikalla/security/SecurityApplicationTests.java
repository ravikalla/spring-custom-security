package in.ravikalla.security;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.Cookie;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import in.ravikalla.security.config.HttpHeaderModificationConfig;
import static in.ravikalla.security.util.Constants.CONTROLLER_CHECK_HEADER;
import static in.ravikalla.security.util.Constants.CUSTOM_HEADER_NAME;
import static in.ravikalla.security.util.Constants.METHOD_HEADER_LOOKUP;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityApplicationTests {
	private Logger logger = LoggerFactory.getLogger(HttpHeaderModificationConfig.class);

	@Autowired
	private MockMvc mvc;

	/**
	 * Pass the cookie name(CUSTOM_HEADER_NAME) and value(strCookieValue).
	 * Expect it in the response as the controller pick it up from the
	 * header(CUSTOM_HEADER_NAME) and return the value(strCookieValue).
	 * 
	 * Code for copying cookie content to header is in HttpHeaderModificationConfig.java
	 */
	@Test
	public void copyCookieContentToHTTPHeader() throws Exception {
		logger.info("35 : Start : SecurityApplicationTests.copyCookieContentToHTTPHeader()");

		final String strCookieValue = "Ravi Test Cookie Value";

		final Cookie customCookie = new Cookie(CUSTOM_HEADER_NAME, strCookieValue);
		mvc.perform(MockMvcRequestBuilders
				.get(CONTROLLER_CHECK_HEADER + METHOD_HEADER_LOOKUP + "/" + CUSTOM_HEADER_NAME)
				.cookie(customCookie))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().is2xxSuccessful())
			.andExpect(content().string(equalTo(strCookieValue)));

		logger.info("48 : End : SecurityApplicationTests.copyCookieContentToHTTPHeader()");
	}
}
