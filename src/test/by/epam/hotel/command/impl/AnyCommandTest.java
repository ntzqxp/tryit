package test.by.epam.hotel.command.impl;

import org.junit.Test;

import by.epam.hotel.controller.Controller;

import static org.mockito.Mockito.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AnyCommandTest {

	@Test
	public void test() {
		
		final Controller servlet = new Controller();
		final HttpServletRequest request = mock(HttpServletRequest.class);
		final HttpServletResponse response = mock(HttpServletResponse.class);
		final RequestDispatcher diaptcher = mock(RequestDispatcher.class);
		
		
		
		
		
		
	}

}
