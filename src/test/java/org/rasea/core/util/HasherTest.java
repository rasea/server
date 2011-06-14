package org.rasea.core.util;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

import org.junit.Test;

public class HasherTest {

	@Test
	public void differentLiteralsMustGenerateDifferentHashesPassingTheSameNotEmptySalt() {
		String hash1, hash2;
		String salt = "fixedSalt";

		hash1 = Hasher.getInstance().digest("secretPassword1", salt);
		hash2 = Hasher.getInstance().digest("secretPassword2", salt);
		assertFalse(hash1 == hash2);

		hash1 = Hasher.getInstance().digest("1", salt);
		hash2 = Hasher.getInstance().digest("2", salt);
		assertFalse(hash1 == hash2);

		hash1 = Hasher
				.getInstance()
				.digest("thisIsAVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryBigPassword1",
						salt);
		hash2 = Hasher
				.getInstance()
				.digest("thisIsAVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryBigPassword2",
						salt);
		assertFalse(hash1 == hash2);
	}

	@Test
	public void sameLiteralsMustGenerateSameHashesPassingEmptyOrNullSalt() {
		String hash1, hash2;
		String litral = "secretPassword";

		hash1 = Hasher.getInstance().digest(litral, "");
		hash2 = Hasher.getInstance().digest(litral, null);
		assertEquals(hash1, hash2);
	}

	@Test
	public void nullLiteralGeneratesNullHashPassingNotEmptySalt() {
		String hash = Hasher.getInstance().digest(null, "fixedSalt");
		assertNull(hash);
	}

	@Test
	public void emptyLiteralGeneratesNotNullAndNotEmptyHash() {
		String hash = Hasher.getInstance().digest("", "fixedSalt");
		assertNotNull(hash);
		assertFalse("".equals(hash));
	}
	
	@Test
	public void md5WithNullString() {
		assertNull(Hasher.md5(null));
	}

	@Test
	public void md5WithEmptyString() {
		assertNotNull(Hasher.md5(""));
		assertEquals("d41d8cd98f00b204e9800998ecf8427e", Hasher.md5(""));
	}

	@Test
	public void md5WithRegularString() {
		assertEquals("6ba88c88c712a7ac248a8c15e59edc31", Hasher.md5("simple message 1"));
		assertEquals("543ca4510adf17fe9f89eddf849bcce9", Hasher.md5("simple message 2"));
		assertEquals(Hasher.md5("a simple message"), Hasher.md5("a simple message"));
	}

}
