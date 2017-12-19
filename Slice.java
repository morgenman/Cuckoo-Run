
// This class has main character powerup, and hazards in it
public class Slice {
	Boolean	hasEnemy	= false;
	int		height;
	Boolean	hasPlatform;
	Boolean	powerup;
	int		pheight;
	Boolean	hitPowerup	= false;

	/**
	 * @return the hitPowerup
	 */
	public Boolean getHitPowerup() {
		return hitPowerup;
	}

	/**
	 * @param hitPowerup
	 *            the hitPowerup to set
	 */
	public void setHitPowerup(Boolean hitPowerup) {
		this.hitPowerup = hitPowerup;
	}

	Slice(int height, Boolean hasEnemy, Boolean hasPlatform, int pheight, Boolean powerup) {
		this.height = height;
		this.hasEnemy = hasEnemy;
		this.hasPlatform = hasPlatform;
		this.pheight = pheight;
		this.powerup = powerup;
	}

	/**
	 * @return the hasEnemy
	 */
	public Boolean getHasEnemy() {
		return false;
	}

	/**
	 * @param hasEnemy
	 *            the hasEnemy to set
	 */
	public void setHasEnemy(Boolean hasEnemy) {
		this.hasEnemy = hasEnemy;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the hasPlatform
	 */
	public Boolean getHasPlatform() {
		return hasPlatform;
	}

	/**
	 * @param hasPlatform
	 *            the hasPlatform to set
	 */
	public void setHasPlatform(Boolean hasPlatform) {
		this.hasPlatform = hasPlatform;
	}

	/**
	 * @return the powerup
	 */
	public Boolean getPowerup() {
		return powerup;
	}

	/**
	 * @param powerup
	 *            the powerup to set
	 */
	public void setPowerup(Boolean powerup) {
		this.powerup = powerup;
	}

	public String toString() {
		System.out.println(height);
		return "";
	}
}
