package leader;

import java.util.ArrayList;

import other.ReplyData;

public class ReplyBuffer {
	
	private int capacity;
	private int size;
	private int currentId;
	private ReplyData replies[];
	private UdpReplicaManager manager;
	
	public ReplyBuffer(int numberOfReplicas, UdpReplicaManager rm) {
		manager = rm;
		capacity = numberOfReplicas;
		replies = new ReplyData[capacity];
	}
	
	public void add(ReplyData rd) {
		if (rd.requestId == currentId) {
			replies[rd.replicaId] = rd;
			size++;
			if (size == capacity) {
				String consensus = findMajority();
				reportWrongs(consensus);
			}
		}
	}
	
	public String findMajority() {
		String winner = null;
		int majority = (capacity / 2) + 1 ;
		for (int i = 0; i < majority; i++) {
			int votes = 0;
			String candidate = replies[i].reply;
			for (int j = 0; j < majority; j++) {
				if ( candidate.equals(replies[j].reply) ){
					votes++;
				}
			}
			if ( votes >= majority ) {
				winner = candidate;
				return winner;
			}
		}
		return winner;
	}
	
	public void reportWrongs(String consensus) {
		ArrayList<Integer> wrong = new ArrayList<Integer>();
		
		for (int i = 0; i < capacity; i++) {
			if (!replies[i].equals(consensus)) {
				wrong.add(i);
			}
		}
		manager.report(wrong);
	}

}
