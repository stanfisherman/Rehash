package ateam.rehashprot2;

public interface IDefaultSMStatemachine extends IStatemachine {
	public interface SCInterface {
		public void raiseGot_up();
		public void raiseHave_breakfast();
		public void raiseEaten_breakfast();
		public void raiseTime_for_medicine();
		public void raiseMedicine_done();
		public void raiseMedicine_not_needed();
		public void raiseMedicine_needed();
		public void raiseTime_for_walk();
		public void raiseWalk_done();
		public void raiseWeather_is_good();
		public void raiseWeather_is_bad();

	}

	public SCInterface getSCInterface();

}
