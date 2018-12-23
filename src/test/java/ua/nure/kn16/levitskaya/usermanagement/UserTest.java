package ua.nure.kn16.levitskaya.usermanagement;

import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserTest {
	private static final String FIRST_NAME = "Ivan";
	private static final String LAST_NAME = "Ivanov";
	private static final long ID = 1L;
	private User user;

	/* True for 11-Oct-2018 */
	private static final int CURRENT_YEAR = 2018;
	private static final int YEAR_OF_BIRTH = 1971;

	/*
	 * Case where the day of birth has already passed, but the month has not passed
	 */
	private static final int ETALON_AGE_1 = CURRENT_YEAR - YEAR_OF_BIRTH;
	private static final int DAY_OF_BIRTH_1 = 5;
	private static final int MOUNTH_OF_BIRTH_1 = Calendar.OCTOBER;

	/* Case where the day and month have already passed */
	private static final int DAY_OF_BIRTH_2 = 23;
	private static final int MOUNTH_OF_BIRTH_2 = Calendar.AUGUST;

	/* Case where the birthday is on this day */
	private static final int DAY_OF_BIRTH_3 = 11;
	private static final int MOUNTH_OF_BIRTH_3 = Calendar.OCTOBER;

	/* Case where the birthday is in this month, but the day has not come yet */
	private static final int ETALON_AGE_2 = CURRENT_YEAR - YEAR_OF_BIRTH - 1;
	private static final int DAY_OF_BIRTH_4 = 30;
	private static final int MOUNTH_OF_BIRTH_4 = Calendar.OCTOBER;

	/* Case where the day and the month have not come yet */
	private static final int DAY_OF_BIRTH_5 = 25;
	private static final int MOUNTH_OF_BIRTH_5 = Calendar.NOVEMBER;

	@Before
	public void setUp() throws Exception {
		user = new User(ID, FIRST_NAME, LAST_NAME, new Date());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetFullName() {
		assertEquals("Ivan, Ivanov", user.getFullName());
	}

	@Test
	public void testGetAge1() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(YEAR_OF_BIRTH, MOUNTH_OF_BIRTH_1, DAY_OF_BIRTH_1);
		Date dateOfBirth = calendar.getTime();
		user.setDateOfBirth(dateOfBirth);
		assertEquals(ETALON_AGE_1, user.getAge());
	}

	@Test
	public void testGetAge2() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(YEAR_OF_BIRTH, MOUNTH_OF_BIRTH_2, DAY_OF_BIRTH_2);
		Date dateOfBirth = calendar.getTime();
		user.setDateOfBirth(dateOfBirth);
		assertEquals(ETALON_AGE_1, user.getAge());
	}

	@Test
	public void testGetAge3() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(YEAR_OF_BIRTH, MOUNTH_OF_BIRTH_3, DAY_OF_BIRTH_3);
		Date dateOfBirth = calendar.getTime();
		user.setDateOfBirth(dateOfBirth);
		assertEquals(ETALON_AGE_1, user.getAge());
	}

	@Test
	public void testGetAge4() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(YEAR_OF_BIRTH, MOUNTH_OF_BIRTH_4, DAY_OF_BIRTH_4);
		Date dateOfBirth = calendar.getTime();
		user.setDateOfBirth(dateOfBirth);
		assertEquals(ETALON_AGE_2, user.getAge());
	}

	@Test
	public void testGetAge5() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(YEAR_OF_BIRTH, MOUNTH_OF_BIRTH_5, DAY_OF_BIRTH_5);
		Date dateOfBirth = calendar.getTime();
		user.setDateOfBirth(dateOfBirth);
		assertEquals(ETALON_AGE_2, user.getAge());
	}

}
