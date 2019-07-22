package in.grocery.registers;

/**
 * @author Manosivam
 *
 */
public interface IRegisterHandler {

	public void releaseRegisterIntoCommonPool(Register register);
	public IRegister getAvailableRegisterInstance();
	
}