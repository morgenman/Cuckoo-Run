
public class Player {
	private int livesleft = 3;

	/**
	 * @return the livesleft
	 */
	public int getLivesleft() {
		return livesleft;
	}

	/**
	 * @param livesleft
	 *            the livesleft to set
	 */
	public void setLivesleft(int livesleft) {
		this.livesleft = livesleft;
		if (this.livesleft == 5) this.livesleft = 4;
	}

}
