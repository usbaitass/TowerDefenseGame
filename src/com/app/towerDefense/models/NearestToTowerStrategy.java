package com.app.towerDefense.models;

/**
 * This class is one of the strategies: where tower targets the critter which is
 * nearest to end tower
 * 
 * @author usbaitass {@inheritDoc}
 */
public class NearestToTowerStrategy implements Strategy {

	@Override
	public void execute(Tower new_tower, CritterType new_critter) {

		if (new_tower.getTargetCritter() != null) {

			if (new_tower.getTargetCritter().getCritterId() != new_critter.getCritterId()) {

				int x0 = new_tower.getXMid();
				int y0 = new_tower.getYMid();

				int x1 = new_tower.getTargetCritter().getXCr();
				int y1 = new_tower.getTargetCritter().getYCr();

				int x2 = new_critter.getXCr();
				int y2 = new_critter.getYCr();

				double d1 = Math.sqrt(((x0 - x1) < 0 ? -1 * (x0 - x1) : (x0 - x1))
						^ 2 + ((y0 - y1) < 0 ? -1 * (y0 - y1) : (y0 - y1)) ^ 2);
				double d2 = Math.sqrt(((x0 - x2) < 0 ? -1 * (x0 - x2) : (x0 - x2))
						^ 2 + ((y0 - y2) < 0 ? -1 * (y0 - y2) : (y0 - y2)) ^ 2);

				if (d1 > d2) {
					new_tower.setTargetCritter(new_critter);
				}
			}
		} else {
			new_tower.setTargetCritter(new_critter);
		}

		new_tower.shoot();

	}

	/**
	 * Returns strategy name
	 */
	@Override
	public String getStrategyName() {
		return "NearTower";
	}

}
