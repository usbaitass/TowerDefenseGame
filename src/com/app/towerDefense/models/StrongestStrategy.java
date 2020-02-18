package com.app.towerDefense.models;

/**
 * This class is one of the strategies: where tower targets the strongest
 * critter in fire range
 * 
 * @author usbaitass {@inheritDoc}
 */
public class StrongestStrategy implements Strategy {

	/**
	 * Execute the strongest strategy
	 */
	@Override
	public void execute(Tower new_tower, CritterType new_critter) {

		if (new_tower.getTargetCritter() != null) {

			if (new_tower.getTargetCritter().getCurrentHealth() < new_critter.getCurrentHealth()
					&& new_tower.getTargetCritter().getCritterId() != new_critter.getCritterId()) {

				new_tower.setTargetCritter(new_critter);
			}

		} else {
			new_tower.setTargetCritter(new_critter);
		}

		new_tower.shoot();
	}

	@Override
	public String getStrategyName() {
		return "Strongest";
	}

}
