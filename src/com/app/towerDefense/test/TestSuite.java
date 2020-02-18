package com.app.towerDefense.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * The class is a test suite for all the tower model test cases.
 * 
 * 
 * @author Amritansh Mishra
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ BurnerTest.class, FreezerTest.class, MapModelTest.class, MapTest.class, PlayerModelTest.class,
		ShooterTest.class, SplasherTest.class, FileStorageTest.class, MiscellaneousHelperTest.class, CritterTest.class,
		JFileChooserComponentTest.class, JMenuBarComponentTest.class, SpecialEffectsTest.class, StrategyTest.class,
		LogReaderTest.class,  GameLoaderTest.class, GameSaverTest.class })
public class TestSuite {

}
