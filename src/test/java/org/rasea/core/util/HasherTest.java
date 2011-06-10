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

		hash1 = Hasher.digest("secretPassword1", salt);
		hash2 = Hasher.digest("secretPassword2", salt);
		assertFalse(hash1 == hash2);

		hash1 = Hasher.digest("1", salt);
		hash2 = Hasher.digest("2", salt);
		assertFalse(hash1 == hash2);

		hash1 = Hasher
				.digest("thisIsAVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryBigPassword1",
						salt);
		hash2 = Hasher
				.digest("thisIsAVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryVeryBigPassword2",
						salt);
		assertFalse(hash1 == hash2);
	}

	@Test
	public void sameLiteralsMustGenerateSameHashesPassingEmptyOrNullSalt() {
		String hash1, hash2;
		String litral = "secretPassword";

		hash1 = Hasher.digest(litral, "");
		hash2 = Hasher.digest(litral, null);
		assertEquals(hash1, hash2);
	}

	@Test
	public void nullLiteralGeneratesNullHashPassingNotEmptySalt() {
		String hash = Hasher.digest(null, "fixedSalt");
		assertNull(hash);
	}

	@Test
	public void emptyLiteralGeneratesNotNullAndNotEmptyHash() {
		String hash = Hasher.digest("", "fixedSalt");
		assertNotNull(hash);
		assertFalse("".equals(hash));
	}
}
