package by.epam.hotel.logic;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.hotel.dao.TransactionHelper;
import by.epam.hotel.dao.impl.AccountDao;
import by.epam.hotel.dao.impl.BankAccountDao;
import by.epam.hotel.entity.Account;
import by.epam.hotel.entity.BankAccount;
import by.epam.hotel.exception.CloseTransactionException;
import by.epam.hotel.exception.DaoException;
import by.epam.hotel.exception.ServiceException;

public class ReplenishLogic {
	private static final Logger LOG = LogManager.getLogger(ReplenishLogic.class);

	public static boolean updateBankAccount(String login, BigDecimal currentAmount) throws ServiceException {
		boolean flag = false;
		AccountDao accountDao = new AccountDao();
		BankAccountDao bankAccountDao = new BankAccountDao();
		try (TransactionHelper helper = new TransactionHelper()) {
			helper.doTransaction(accountDao, bankAccountDao);
			try {
				Account account = accountDao.findAccountByLogin(login);
				if(account != null) {
					flag = bankAccountDao.update(new BankAccount(account.getId(), currentAmount));
					helper.commit();
				}
			} catch (DaoException e) {
				LOG.error(e);
				helper.rollback();
				throw new ServiceException(e);
			}
		} catch (CloseTransactionException e) {
			LOG.error("Resources cannot be closed", e);
			throw new ServiceException("Resources cannot be closed", e);
		}
		return flag;
	}

}
