import java.util.Comparator;

public class StateComparator implements Comparator<State>{
	
	Heuristic h = Heuristic.Null;
	
	public StateComparator(Heuristic h) {
		this.h = h;
	}

	@Override
	public int compare(State s0, State s1) {
		switch (h) {
			case Null:
				return s0.statesFromInit-s1.statesFromInit;
			case BlockingVehicles:
				return (s0.statesFromInit+s0.vehiclesToExit)-(s1.statesFromInit+s1.vehiclesToExit);
			case DistanceToExit:
				return (s0.statesFromInit+s0.distanceToExit())-(s1.statesFromInit+s1.distanceToExit());
		}
		return 0;
	}

}
