package by.epam.hotel.command.impl.client;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.entity.FullInfoOrder;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.logic.ToAccountOrdersLogic;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.apptype.RoleType;
import by.epam.hotel.util.apptype.RouterType;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.ParameterConstant;
import by.epam.hotel.util.constant.PropertyConstant;

public class ToAccountOrdersCommand implements ActionCommand {

	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		if (sessionData.getRole() == RoleType.CLIENT) {
			String currentLogin = sessionData.getLogin();
			int currentPage = Integer.valueOf(request.getParameter(ParameterConstant.CURRENT_PAGE));
			int recordsPerPage = Integer.valueOf(request.getParameter(ParameterConstant.RECORDS_PER_PAGE));
			try {
				List<FullInfoOrder> listAccountFullInfoOrder = ToAccountOrdersLogic.getFullInfoOrderList(currentLogin,
						currentPage, recordsPerPage);
				sessionData.setListAccountFullInfoOrder(listAccountFullInfoOrder);
				int rows = ToAccountOrdersLogic.getNumberOfRows(currentLogin);
				int noOfPages = rows / recordsPerPage;
				if (rows % recordsPerPage > 0) {
					noOfPages++;
				}
				sessionData.setNoOfPages(noOfPages);
				sessionData.setCurrentPage(currentPage);
				sessionData.setRecordsPerPage(recordsPerPage);
				page = ConfigurationManager.getProperty(PropertyConstant.PAGE_ACCOUNT_ORDERS);
			} catch (ServiceException e) {
				throw new CommandException(e);
			}
		} else {
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_WELCOME);
		}
		router.setType(RouterType.FORWARD);
		router.setPage(page);
		return router;
	}

}
