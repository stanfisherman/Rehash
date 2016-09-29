package ateam.rehashprot2;

public class DefaultSMStatemachine implements IDefaultSMStatemachine {

	protected class SCInterfaceImpl implements SCInterface {

		private boolean got_up;

		public void raiseGot_up() {
			got_up = true;
		}

		private boolean have_breakfast;

		public void raiseHave_breakfast() {
			have_breakfast = true;
		}

		private boolean eaten_breakfast;

		public void raiseEaten_breakfast() {
			eaten_breakfast = true;
		}

		private boolean time_for_medicine;

		public void raiseTime_for_medicine() {
			time_for_medicine = true;
		}

		private boolean medicine_done;

		public void raiseMedicine_done() {
			medicine_done = true;
		}

		private boolean medicine_not_needed;

		public void raiseMedicine_not_needed() {
			medicine_not_needed = true;
		}

		private boolean medicine_needed;

		public void raiseMedicine_needed() {
			medicine_needed = true;
		}

		private boolean time_for_walk;

		public void raiseTime_for_walk() {
			time_for_walk = true;
		}

		private boolean walk_done;

		public void raiseWalk_done() {
			walk_done = true;
		}

		private boolean weather_is_good;

		public void raiseWeather_is_good() {
			weather_is_good = true;
		}

		private boolean weather_is_bad;

		public void raiseWeather_is_bad() {
			weather_is_bad = true;
		}

		protected void clearEvents() {
			got_up = false;
			have_breakfast = false;
			eaten_breakfast = false;
			time_for_medicine = false;
			medicine_done = false;
			medicine_not_needed = false;
			medicine_needed = false;
			time_for_walk = false;
			walk_done = false;
			weather_is_good = false;
			weather_is_bad = false;
		}

	}

	protected SCInterfaceImpl sCInterface;

	private boolean initialized = false;

	public enum State {
		main_region_patient_Awake, main_region_patient_NotEaten, main_region_patient_Eaten, main_region_medicine, main_region_medicine_medicine_region_medicine_NotTaken, main_region_medicine_medicine_region_medicine_Check, main_region_medicine_medicine_region_medicine_DontNeedToTake, main_region_medicine_medicine_region_medicine_Taken, main_region_waitingForSchedule, main_region_walk, main_region_walk_walk_region_notWalked, main_region_walk_walk_region_weather_Unknown, main_region_walk_walk_region_goForWalk, main_region_walk_walk_region_stayAtHome, $NullState$
	};

	private final State[] stateVector = new State[1];

	private int nextStateIndex;

	public DefaultSMStatemachine() {

		sCInterface = new SCInterfaceImpl();
	}

	public void init() {
		this.initialized = true;
		for (int i = 0; i < 1; i++) {
			stateVector[i] = State.$NullState$;
		}

		clearEvents();
		clearOutEvents();

	}

	public void enter() {
		if (!initialized)
			throw new IllegalStateException(
					"The state machine needs to be initialized first by calling the init() function.");

		enterSequence_main_region_default();
	}

	public void exit() {
		exitSequence_main_region();
	}

	/**
	 * @see IStatemachine#isActive()
	 */
	public boolean isActive() {

		return stateVector[0] != State.$NullState$;
	}

	/** 
	* Always returns 'false' since this state machine can never become final.
	*
	 * @see IStatemachine#isFinal()
	 */
	public boolean isFinal() {
		return false;
	}

	/**
	* This method resets the incoming events (time events included).
	*/
	protected void clearEvents() {
		sCInterface.clearEvents();

	}

	/**
	* This method resets the outgoing events.
	*/
	protected void clearOutEvents() {
	}

	/**
	* Returns true if the given state is currently active otherwise false.
	*/
	public boolean isStateActive(State state) {
		switch (state) {
			case main_region_patient_Awake :
				return stateVector[0] == State.main_region_patient_Awake;
			case main_region_patient_NotEaten :
				return stateVector[0] == State.main_region_patient_NotEaten;
			case main_region_patient_Eaten :
				return stateVector[0] == State.main_region_patient_Eaten;
			case main_region_medicine :
				return stateVector[0].ordinal() >= State.main_region_medicine.ordinal() && stateVector[0]
						.ordinal() <= State.main_region_medicine_medicine_region_medicine_Taken.ordinal();
			case main_region_medicine_medicine_region_medicine_NotTaken :
				return stateVector[0] == State.main_region_medicine_medicine_region_medicine_NotTaken;
			case main_region_medicine_medicine_region_medicine_Check :
				return stateVector[0] == State.main_region_medicine_medicine_region_medicine_Check;
			case main_region_medicine_medicine_region_medicine_DontNeedToTake :
				return stateVector[0] == State.main_region_medicine_medicine_region_medicine_DontNeedToTake;
			case main_region_medicine_medicine_region_medicine_Taken :
				return stateVector[0] == State.main_region_medicine_medicine_region_medicine_Taken;
			case main_region_waitingForSchedule :
				return stateVector[0] == State.main_region_waitingForSchedule;
			case main_region_walk :
				return stateVector[0].ordinal() >= State.main_region_walk.ordinal()
						&& stateVector[0].ordinal() <= State.main_region_walk_walk_region_stayAtHome.ordinal();
			case main_region_walk_walk_region_notWalked :
				return stateVector[0] == State.main_region_walk_walk_region_notWalked;
			case main_region_walk_walk_region_weather_Unknown :
				return stateVector[0] == State.main_region_walk_walk_region_weather_Unknown;
			case main_region_walk_walk_region_goForWalk :
				return stateVector[0] == State.main_region_walk_walk_region_goForWalk;
			case main_region_walk_walk_region_stayAtHome :
				return stateVector[0] == State.main_region_walk_walk_region_stayAtHome;
			default :
				return false;
		}
	}

	public SCInterface getSCInterface() {
		return sCInterface;
	}

	public void raiseGot_up() {
		sCInterface.raiseGot_up();
	}
	public void raiseHave_breakfast() {
		sCInterface.raiseHave_breakfast();
	}
	public void raiseEaten_breakfast() {
		sCInterface.raiseEaten_breakfast();
	}
	public void raiseTime_for_medicine() {
		sCInterface.raiseTime_for_medicine();
	}
	public void raiseMedicine_done() {
		sCInterface.raiseMedicine_done();
	}
	public void raiseMedicine_not_needed() {
		sCInterface.raiseMedicine_not_needed();
	}
	public void raiseMedicine_needed() {
		sCInterface.raiseMedicine_needed();
	}
	public void raiseTime_for_walk() {
		sCInterface.raiseTime_for_walk();
	}
	public void raiseWalk_done() {
		sCInterface.raiseWalk_done();
	}
	public void raiseWeather_is_good() {
		sCInterface.raiseWeather_is_good();
	}
	public void raiseWeather_is_bad() {
		sCInterface.raiseWeather_is_bad();
	}

	private boolean check_main_region_patient_Awake_tr0_tr0() {
		return sCInterface.got_up;
	}

	private boolean check_main_region_patient_NotEaten_tr0_tr0() {
		return sCInterface.have_breakfast;
	}

	private boolean check_main_region_patient_Eaten_tr0_tr0() {
		return sCInterface.eaten_breakfast;
	}

	private boolean check_main_region_medicine_tr0_tr0() {
		return sCInterface.medicine_done;
	}

	private boolean check_main_region_medicine_medicine_region_medicine_NotTaken_tr0_tr0() {
		return sCInterface.time_for_medicine;
	}

	private boolean check_main_region_medicine_medicine_region_medicine_Check_tr0_tr0() {
		return sCInterface.medicine_not_needed;
	}

	private boolean check_main_region_medicine_medicine_region_medicine_Check_tr1_tr1() {
		return sCInterface.medicine_needed;
	}

	private boolean check_main_region_waitingForSchedule_tr0_tr0() {
		return sCInterface.time_for_medicine;
	}

	private boolean check_main_region_waitingForSchedule_tr1_tr1() {
		return sCInterface.time_for_walk;
	}

	private boolean check_main_region_walk_tr0_tr0() {
		return sCInterface.walk_done;
	}

	private boolean check_main_region_walk_walk_region_notWalked_tr0_tr0() {
		return sCInterface.time_for_walk;
	}

	private boolean check_main_region_walk_walk_region_weather_Unknown_tr0_tr0() {
		return sCInterface.weather_is_bad;
	}

	private boolean check_main_region_walk_walk_region_weather_Unknown_tr1_tr1() {
		return sCInterface.weather_is_good;
	}

	private void effect_main_region_patient_Awake_tr0() {
		exitSequence_main_region_patient_Awake();

		enterSequence_main_region_patient_NotEaten_default();
	}

	private void effect_main_region_patient_NotEaten_tr0() {
		exitSequence_main_region_patient_NotEaten();

		enterSequence_main_region_patient_Eaten_default();
	}

	private void effect_main_region_patient_Eaten_tr0() {
		exitSequence_main_region_patient_Eaten();

		enterSequence_main_region_waitingForSchedule_default();
	}

	private void effect_main_region_medicine_tr0() {
		exitSequence_main_region_medicine();

		enterSequence_main_region_waitingForSchedule_default();
	}

	private void effect_main_region_medicine_medicine_region_medicine_NotTaken_tr0() {
		exitSequence_main_region_medicine_medicine_region_medicine_NotTaken();

		enterSequence_main_region_medicine_medicine_region_medicine_Check_default();
	}

	private void effect_main_region_medicine_medicine_region_medicine_Check_tr0() {
		exitSequence_main_region_medicine_medicine_region_medicine_Check();

		enterSequence_main_region_medicine_medicine_region_medicine_DontNeedToTake_default();
	}

	private void effect_main_region_medicine_medicine_region_medicine_Check_tr1() {
		exitSequence_main_region_medicine_medicine_region_medicine_Check();

		enterSequence_main_region_medicine_medicine_region_medicine_Taken_default();
	}

	private void effect_main_region_waitingForSchedule_tr0() {
		exitSequence_main_region_waitingForSchedule();

		enterSequence_main_region_medicine_default();
	}

	private void effect_main_region_waitingForSchedule_tr1() {
		exitSequence_main_region_waitingForSchedule();

		enterSequence_main_region_walk_default();
	}

	private void effect_main_region_walk_tr0() {
		exitSequence_main_region_walk();

		enterSequence_main_region_waitingForSchedule_default();
	}

	private void effect_main_region_walk_walk_region_notWalked_tr0() {
		exitSequence_main_region_walk_walk_region_notWalked();

		enterSequence_main_region_walk_walk_region_weather_Unknown_default();
	}

	private void effect_main_region_walk_walk_region_weather_Unknown_tr0() {
		exitSequence_main_region_walk_walk_region_weather_Unknown();

		enterSequence_main_region_walk_walk_region_stayAtHome_default();
	}

	private void effect_main_region_walk_walk_region_weather_Unknown_tr1() {
		exitSequence_main_region_walk_walk_region_weather_Unknown();

		enterSequence_main_region_walk_walk_region_goForWalk_default();
	}

	/* 'default' enter sequence for state patient.Awake */
	private void enterSequence_main_region_patient_Awake_default() {
		nextStateIndex = 0;
		stateVector[0] = State.main_region_patient_Awake;
	}

	/* 'default' enter sequence for state patient.NotEaten */
	private void enterSequence_main_region_patient_NotEaten_default() {
		nextStateIndex = 0;
		stateVector[0] = State.main_region_patient_NotEaten;
	}

	/* 'default' enter sequence for state patient.Eaten */
	private void enterSequence_main_region_patient_Eaten_default() {
		nextStateIndex = 0;
		stateVector[0] = State.main_region_patient_Eaten;
	}

	/* 'default' enter sequence for state medicine */
	private void enterSequence_main_region_medicine_default() {
		enterSequence_main_region_medicine_medicine_region_default();
	}

	/* 'default' enter sequence for state medicine.NotTaken */
	private void enterSequence_main_region_medicine_medicine_region_medicine_NotTaken_default() {
		nextStateIndex = 0;
		stateVector[0] = State.main_region_medicine_medicine_region_medicine_NotTaken;
	}

	/* 'default' enter sequence for state medicine.Check */
	private void enterSequence_main_region_medicine_medicine_region_medicine_Check_default() {
		nextStateIndex = 0;
		stateVector[0] = State.main_region_medicine_medicine_region_medicine_Check;
	}

	/* 'default' enter sequence for state medicine.DontNeedToTake */
	private void enterSequence_main_region_medicine_medicine_region_medicine_DontNeedToTake_default() {
		nextStateIndex = 0;
		stateVector[0] = State.main_region_medicine_medicine_region_medicine_DontNeedToTake;
	}

	/* 'default' enter sequence for state medicine.Taken */
	private void enterSequence_main_region_medicine_medicine_region_medicine_Taken_default() {
		nextStateIndex = 0;
		stateVector[0] = State.main_region_medicine_medicine_region_medicine_Taken;
	}

	/* 'default' enter sequence for state waitingForSchedule */
	private void enterSequence_main_region_waitingForSchedule_default() {
		nextStateIndex = 0;
		stateVector[0] = State.main_region_waitingForSchedule;
	}

	/* 'default' enter sequence for state walk */
	private void enterSequence_main_region_walk_default() {
		enterSequence_main_region_walk_walk_region_default();
	}

	/* 'default' enter sequence for state notWalked */
	private void enterSequence_main_region_walk_walk_region_notWalked_default() {
		nextStateIndex = 0;
		stateVector[0] = State.main_region_walk_walk_region_notWalked;
	}

	/* 'default' enter sequence for state weather.Unknown */
	private void enterSequence_main_region_walk_walk_region_weather_Unknown_default() {
		nextStateIndex = 0;
		stateVector[0] = State.main_region_walk_walk_region_weather_Unknown;
	}

	/* 'default' enter sequence for state goForWalk */
	private void enterSequence_main_region_walk_walk_region_goForWalk_default() {
		nextStateIndex = 0;
		stateVector[0] = State.main_region_walk_walk_region_goForWalk;
	}

	/* 'default' enter sequence for state stayAtHome */
	private void enterSequence_main_region_walk_walk_region_stayAtHome_default() {
		nextStateIndex = 0;
		stateVector[0] = State.main_region_walk_walk_region_stayAtHome;
	}

	/* 'default' enter sequence for region main region */
	private void enterSequence_main_region_default() {
		react_main_region__entry_Default();
	}

	/* 'default' enter sequence for region medicine region */
	private void enterSequence_main_region_medicine_medicine_region_default() {
		react_main_region_medicine_medicine_region__entry_Default();
	}

	/* 'default' enter sequence for region walk region */
	private void enterSequence_main_region_walk_walk_region_default() {
		react_main_region_walk_walk_region__entry_Default();
	}

	/* Default exit sequence for state patient.Awake */
	private void exitSequence_main_region_patient_Awake() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}

	/* Default exit sequence for state patient.NotEaten */
	private void exitSequence_main_region_patient_NotEaten() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}

	/* Default exit sequence for state patient.Eaten */
	private void exitSequence_main_region_patient_Eaten() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}

	/* Default exit sequence for state medicine */
	private void exitSequence_main_region_medicine() {
		exitSequence_main_region_medicine_medicine_region();
	}

	/* Default exit sequence for state medicine.NotTaken */
	private void exitSequence_main_region_medicine_medicine_region_medicine_NotTaken() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}

	/* Default exit sequence for state medicine.Check */
	private void exitSequence_main_region_medicine_medicine_region_medicine_Check() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}

	/* Default exit sequence for state medicine.DontNeedToTake */
	private void exitSequence_main_region_medicine_medicine_region_medicine_DontNeedToTake() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}

	/* Default exit sequence for state medicine.Taken */
	private void exitSequence_main_region_medicine_medicine_region_medicine_Taken() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}

	/* Default exit sequence for state waitingForSchedule */
	private void exitSequence_main_region_waitingForSchedule() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}

	/* Default exit sequence for state walk */
	private void exitSequence_main_region_walk() {
		exitSequence_main_region_walk_walk_region();
	}

	/* Default exit sequence for state notWalked */
	private void exitSequence_main_region_walk_walk_region_notWalked() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}

	/* Default exit sequence for state weather.Unknown */
	private void exitSequence_main_region_walk_walk_region_weather_Unknown() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}

	/* Default exit sequence for state goForWalk */
	private void exitSequence_main_region_walk_walk_region_goForWalk() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}

	/* Default exit sequence for state stayAtHome */
	private void exitSequence_main_region_walk_walk_region_stayAtHome() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}

	/* Default exit sequence for region main region */
	private void exitSequence_main_region() {
		switch (stateVector[0]) {
			case main_region_patient_Awake :
				exitSequence_main_region_patient_Awake();
				break;

			case main_region_patient_NotEaten :
				exitSequence_main_region_patient_NotEaten();
				break;

			case main_region_patient_Eaten :
				exitSequence_main_region_patient_Eaten();
				break;

			case main_region_medicine_medicine_region_medicine_NotTaken :
				exitSequence_main_region_medicine_medicine_region_medicine_NotTaken();
				break;

			case main_region_medicine_medicine_region_medicine_Check :
				exitSequence_main_region_medicine_medicine_region_medicine_Check();
				break;

			case main_region_medicine_medicine_region_medicine_DontNeedToTake :
				exitSequence_main_region_medicine_medicine_region_medicine_DontNeedToTake();
				break;

			case main_region_medicine_medicine_region_medicine_Taken :
				exitSequence_main_region_medicine_medicine_region_medicine_Taken();
				break;

			case main_region_waitingForSchedule :
				exitSequence_main_region_waitingForSchedule();
				break;

			case main_region_walk_walk_region_notWalked :
				exitSequence_main_region_walk_walk_region_notWalked();
				break;

			case main_region_walk_walk_region_weather_Unknown :
				exitSequence_main_region_walk_walk_region_weather_Unknown();
				break;

			case main_region_walk_walk_region_goForWalk :
				exitSequence_main_region_walk_walk_region_goForWalk();
				break;

			case main_region_walk_walk_region_stayAtHome :
				exitSequence_main_region_walk_walk_region_stayAtHome();
				break;

			default :
				break;
		}
	}

	/* Default exit sequence for region medicine region */
	private void exitSequence_main_region_medicine_medicine_region() {
		switch (stateVector[0]) {
			case main_region_medicine_medicine_region_medicine_NotTaken :
				exitSequence_main_region_medicine_medicine_region_medicine_NotTaken();
				break;

			case main_region_medicine_medicine_region_medicine_Check :
				exitSequence_main_region_medicine_medicine_region_medicine_Check();
				break;

			case main_region_medicine_medicine_region_medicine_DontNeedToTake :
				exitSequence_main_region_medicine_medicine_region_medicine_DontNeedToTake();
				break;

			case main_region_medicine_medicine_region_medicine_Taken :
				exitSequence_main_region_medicine_medicine_region_medicine_Taken();
				break;

			default :
				break;
		}
	}

	/* Default exit sequence for region walk region */
	private void exitSequence_main_region_walk_walk_region() {
		switch (stateVector[0]) {
			case main_region_walk_walk_region_notWalked :
				exitSequence_main_region_walk_walk_region_notWalked();
				break;

			case main_region_walk_walk_region_weather_Unknown :
				exitSequence_main_region_walk_walk_region_weather_Unknown();
				break;

			case main_region_walk_walk_region_goForWalk :
				exitSequence_main_region_walk_walk_region_goForWalk();
				break;

			case main_region_walk_walk_region_stayAtHome :
				exitSequence_main_region_walk_walk_region_stayAtHome();
				break;

			default :
				break;
		}
	}

	/* The reactions of state patient.Awake. */
	private void react_main_region_patient_Awake() {
		if (check_main_region_patient_Awake_tr0_tr0()) {
			effect_main_region_patient_Awake_tr0();
		}
	}

	/* The reactions of state patient.NotEaten. */
	private void react_main_region_patient_NotEaten() {
		if (check_main_region_patient_NotEaten_tr0_tr0()) {
			effect_main_region_patient_NotEaten_tr0();
		}
	}

	/* The reactions of state patient.Eaten. */
	private void react_main_region_patient_Eaten() {
		if (check_main_region_patient_Eaten_tr0_tr0()) {
			effect_main_region_patient_Eaten_tr0();
		}
	}

	/* The reactions of state medicine.NotTaken. */
	private void react_main_region_medicine_medicine_region_medicine_NotTaken() {
		if (check_main_region_medicine_tr0_tr0()) {
			effect_main_region_medicine_tr0();
		} else {
			if (check_main_region_medicine_medicine_region_medicine_NotTaken_tr0_tr0()) {
				effect_main_region_medicine_medicine_region_medicine_NotTaken_tr0();
			}
		}
	}

	/* The reactions of state medicine.Check. */
	private void react_main_region_medicine_medicine_region_medicine_Check() {
		if (check_main_region_medicine_tr0_tr0()) {
			effect_main_region_medicine_tr0();
		} else {
			if (check_main_region_medicine_medicine_region_medicine_Check_tr0_tr0()) {
				effect_main_region_medicine_medicine_region_medicine_Check_tr0();
			} else {
				if (check_main_region_medicine_medicine_region_medicine_Check_tr1_tr1()) {
					effect_main_region_medicine_medicine_region_medicine_Check_tr1();
				}
			}
		}
	}

	/* The reactions of state medicine.DontNeedToTake. */
	private void react_main_region_medicine_medicine_region_medicine_DontNeedToTake() {
		if (check_main_region_medicine_tr0_tr0()) {
			effect_main_region_medicine_tr0();
		} else {
		}
	}

	/* The reactions of state medicine.Taken. */
	private void react_main_region_medicine_medicine_region_medicine_Taken() {
		if (check_main_region_medicine_tr0_tr0()) {
			effect_main_region_medicine_tr0();
		} else {
		}
	}

	/* The reactions of state waitingForSchedule. */
	private void react_main_region_waitingForSchedule() {
		if (check_main_region_waitingForSchedule_tr0_tr0()) {
			effect_main_region_waitingForSchedule_tr0();
		} else {
			if (check_main_region_waitingForSchedule_tr1_tr1()) {
				effect_main_region_waitingForSchedule_tr1();
			}
		}
	}

	/* The reactions of state notWalked. */
	private void react_main_region_walk_walk_region_notWalked() {
		if (check_main_region_walk_tr0_tr0()) {
			effect_main_region_walk_tr0();
		} else {
			if (check_main_region_walk_walk_region_notWalked_tr0_tr0()) {
				effect_main_region_walk_walk_region_notWalked_tr0();
			}
		}
	}

	/* The reactions of state weather.Unknown. */
	private void react_main_region_walk_walk_region_weather_Unknown() {
		if (check_main_region_walk_tr0_tr0()) {
			effect_main_region_walk_tr0();
		} else {
			if (check_main_region_walk_walk_region_weather_Unknown_tr0_tr0()) {
				effect_main_region_walk_walk_region_weather_Unknown_tr0();
			} else {
				if (check_main_region_walk_walk_region_weather_Unknown_tr1_tr1()) {
					effect_main_region_walk_walk_region_weather_Unknown_tr1();
				}
			}
		}
	}

	/* The reactions of state goForWalk. */
	private void react_main_region_walk_walk_region_goForWalk() {
		if (check_main_region_walk_tr0_tr0()) {
			effect_main_region_walk_tr0();
		} else {
		}
	}

	/* The reactions of state stayAtHome. */
	private void react_main_region_walk_walk_region_stayAtHome() {
		if (check_main_region_walk_tr0_tr0()) {
			effect_main_region_walk_tr0();
		} else {
		}
	}

	/* Default react sequence for initial entry  */
	private void react_main_region__entry_Default() {
		enterSequence_main_region_patient_Awake_default();
	}

	/* Default react sequence for initial entry  */
	private void react_main_region_medicine_medicine_region__entry_Default() {
		enterSequence_main_region_medicine_medicine_region_medicine_NotTaken_default();
	}

	/* Default react sequence for initial entry  */
	private void react_main_region_walk_walk_region__entry_Default() {
		enterSequence_main_region_walk_walk_region_notWalked_default();
	}

	public void runCycle() {
		if (!initialized)
			throw new IllegalStateException(
					"The state machine needs to be initialized first by calling the init() function.");

		clearOutEvents();

		for (nextStateIndex = 0; nextStateIndex < stateVector.length; nextStateIndex++) {

			switch (stateVector[nextStateIndex]) {
				case main_region_patient_Awake :
					react_main_region_patient_Awake();
					break;
				case main_region_patient_NotEaten :
					react_main_region_patient_NotEaten();
					break;
				case main_region_patient_Eaten :
					react_main_region_patient_Eaten();
					break;
				case main_region_medicine_medicine_region_medicine_NotTaken :
					react_main_region_medicine_medicine_region_medicine_NotTaken();
					break;
				case main_region_medicine_medicine_region_medicine_Check :
					react_main_region_medicine_medicine_region_medicine_Check();
					break;
				case main_region_medicine_medicine_region_medicine_DontNeedToTake :
					react_main_region_medicine_medicine_region_medicine_DontNeedToTake();
					break;
				case main_region_medicine_medicine_region_medicine_Taken :
					react_main_region_medicine_medicine_region_medicine_Taken();
					break;
				case main_region_waitingForSchedule :
					react_main_region_waitingForSchedule();
					break;
				case main_region_walk_walk_region_notWalked :
					react_main_region_walk_walk_region_notWalked();
					break;
				case main_region_walk_walk_region_weather_Unknown :
					react_main_region_walk_walk_region_weather_Unknown();
					break;
				case main_region_walk_walk_region_goForWalk :
					react_main_region_walk_walk_region_goForWalk();
					break;
				case main_region_walk_walk_region_stayAtHome :
					react_main_region_walk_walk_region_stayAtHome();
					break;
				default :
					// $NullState$
			}
		}

		clearEvents();
	}
}
