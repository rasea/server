/*
 * Rasea Server
 * 
 * Copyright (c) 2008, Rasea <http://rasea.org>. All rights reserved.
 *
 * Rasea Server is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, see <http://www.gnu.org/licenses/>
 * or write to the Free Software Foundation, Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.rasea.ws.v0.exception;

public class ErrorCode {

	public static final int AUTHENTICATION_FAILED = 5;

	public static final int DUPLICATE_NAME = 2;

	public static final int INVALID_EMAIL = 3;

	public static final int NOT_FOUND = 0;

	public static final int PERMISSION_DENIED = 1;

	public static final int REQUIRED = 4;

	public static final int STORE_ERROR = 6;

	public static final int UNKNOWN = 99;
}
