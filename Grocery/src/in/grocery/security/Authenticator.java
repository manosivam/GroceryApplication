package in.grocery.security;

import in.grocery.backend.Backend;

/**
 * Can be used to authenticate Employee when they are applying for employee discount. 
 * 
 * @author Manosivam
 *
 */
public class Authenticator implements IAuthenticator{

	@Override
	public boolean authenticateCredential(Credentials credentials) {
		
		return Backend.getInstance().authenticateCredential(credentials);
	}

	/**
	 * This can take help from BackendLayer and update the password in DB.
	 */
	@Override
	public boolean changePassword(int emp_id, String oldPassword, String newPassword) {
		throw new UnsupportedOperationException();
	}
}
