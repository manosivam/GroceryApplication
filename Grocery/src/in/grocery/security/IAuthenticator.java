package in.grocery.security;

/**
 * Can be used to authenticate Employee when they are applying for employee discount. 
 * @author Manosivam
 *
 */
interface IAuthenticator {

	boolean authenticateCredential(Credentials credentials);
	boolean changePassword(int emp_id, String oldPassword, String newPassword);
}
