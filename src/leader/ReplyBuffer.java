package leader;

import java.util.ArrayList;

import other.ReplyData;

public class ReplyBuffer {
	
	private int capacity;
	private int size;
	private int currentId;
	private ReplyData replies[];
	private UdpReplicaManager manager;
	private UdpFrontEndRequestServer frontEnd;
	private RequestBuffer requests;
	
	public ReplyBuffer(int numberOfReplicas, UdpReplicaManager rm, UdpFrontEndRequestServer fe, RequestBuffer req) {
		manager = rm;
		frontEnd = fe;
		requests = req;
		capacity = numberOfReplicas;
		replies = new ReplyData[capacity];
		clearBuffer();
	}
	
	public void add(ReplyData rd) {
		if (rd.requestId == currentId) {
			if (replies[rd.replicaId] != null) { return; } // previous response still in there don't overwrite.
			
			replies[rd.replicaId] = rd;
			size++;
			if (size == capacity) {
				ReplyData consensus = findMajority();
				System.out.println(consensus.reply);
				reportWrongs(consensus.reply);
				frontEnd.sendResponse(consensus);
				clearBuffer();
				currentId++;
				size = 0;
				requests.sendNextRequest();
			}
		} else {
			// TODO Resolve the out of order responses.
			requests.sendCurrentRequest();
		}
	}
	
	private void clearBuffer() {
		for (int i = 0; i < capacity; i++) {
			replies[i] = null;
		}
	}

	public ReplyData findMajority() {
		int majority = (capacity / 2) + 1 ;
		for (int i = 0; i < majority; i++) {
			int votes = 0;
			String candidate = replies[i].reply;
			for (int j = 0; j < size; j++) {
				if ( candidate.equals(replies[j].reply) ){
					votes++;
				}
			}
			if ( votes >= majority ) {
				System.out.println("Majority reply: " + replies[i].toString());
				return replies[i];
			}
		}
		return null;
	}
	
	public void reportWrongs(String consensus) {
		ArrayList<Integer> wrong = new ArrayList<Integer>();
		
		for (int i = 0; i < capacity; i++) {
			if (!consensus.equals(replies[i].reply)) {
				wrong.add(i);
			}
		}
		System.out.println("Wrong replicas are: " + wrong.toString());
		manager.report(wrong);
	}

}
