import java.util.*;

public class LOL {
/****************************************************************/
	
	static int Budget = 1400;
	static int DisplayListSize = 30;
	static int NumberOfPlayersToChoose = 8;
	
/****************************************************************/
	
	static String[] Name = {"Mango", "Armada", "HBox", "M2K", "Westballz", 
	         "Plup", "SFAT", "Shroomed", "Axe", "PPU", "S2J", "Silentwolf", "ESAM", "Ice", "Wobbles", "Duck"};
	
	static int[] Cost = {220, 230, 230, 210, 190, 210, 170, 170, 190, 150, 150, 170, 140, 170, 180, 150};
	
	static int[] Payout = {550, 500, 450, 400, 350, 350, 300, 300, 250, 250, 250, 250, 0, 0, 0, 0};

/****************************************************************/
	
	public static class PlayerSet implements Comparator<PlayerSet>{
		HashSet<Integer> set;
		Integer SetReward;
		Integer SetCost;
		
		public PlayerSet() {
		}
		
		public PlayerSet(HashSet<Integer> player_set) {
			set = player_set;
			SetReward = calcReward(player_set);
			SetCost = calcCost(player_set);
		}
		
		public int compare(PlayerSet o1, PlayerSet o2) {
			PlayerSet perm1 = (PlayerSet) o1;
			PlayerSet perm2 = (PlayerSet) o2;
			
			return perm2.SetReward - perm1.SetReward;
		}
		
		public int calcReward(HashSet<Integer> perm) {
			int result = 0;
			for (Integer player : set) {
				result += Payout[player];
			}
			
			return result;
		}
		
		public ArrayList<String> getPlayerNames() {
			ArrayList<String> result = new ArrayList<String>();
			
			for (Integer player : set) {
				result.add(Name[player]);
			}
			
			return result;
		}
		
		public int calcCost(HashSet<Integer> perm) {
			int result = 0;
			for (Integer player : perm) {
				result += Cost[player];
			}
			
			return result;
		}
	}

	
	public static HashSet<HashSet<Integer>> getPerm(int[] list, int count) {
		HashSet<HashSet<Integer>> result = new HashSet<HashSet<Integer>>();
		
		if (count == 0) {
			return result;
		}
		
		if (count == 1) {
			for (Integer element : list) {
				HashSet<Integer> ar = new HashSet<Integer>();
				ar.add(element);
				result.add(ar);
			}
			return result;
		}
		
		HashSet<HashSet<Integer>> permutations = getPerm(list, count - 1);

		for (HashSet<Integer> grabperm : permutations) {
			
			HashSet<Integer> perm = new HashSet<Integer>(grabperm);
			
			for (int k2 = 0; k2 < list.length; k2++) {
				if (perm.contains(list[k2])) {
					continue;
				}
				perm.add(list[k2]);
				HashSet<Integer> out_perm = new HashSet<Integer>(perm);
				
				result.add(out_perm);
		
				perm.remove(list[k2]);
				
			}
		}
		
		return result;
		
		
	}
	
	
	public static void main(String[] args) {
		
		int[] list = new int[16];
		for (int k1 = 0; k1 < list.length; k1++) {
			list[k1] = k1;
		}
		
		HashSet<HashSet<Integer>> perm_list = getPerm(list, NumberOfPlayersToChoose);
		ArrayList<PlayerSet> playys = new ArrayList<PlayerSet>();
		
		for (HashSet<Integer> perm : perm_list) {
			PlayerSet ob = new PlayerSet(perm);
			playys.add(ob);			
		}
		
		Collections.sort(playys, new PlayerSet());
		
		int cnt = 0;
		int rewardtracker = 0;
		int rewardcnt = 1;
		
		for (int k1 = 0; k1 < playys.size(); k1++) {
			PlayerSet playys_set = playys.get(k1);
			
			if (playys_set.SetCost > Budget) {
				continue;
			}
			
			cnt++;
			
			if (cnt == 1) rewardtracker = playys_set.SetReward;
			
			if (playys_set.SetReward < rewardtracker) {
				rewardtracker = playys_set.SetReward;
				rewardcnt++;
			}
			
			System.out.println(rewardcnt);
			System.out.println("Names : " + playys_set.getPlayerNames());
			System.out.println("Reward : " + playys_set.SetReward);
			System.out.println("Cost : " + playys_set.SetCost);
			System.out.println("------------------------------");
			
			if (cnt >= DisplayListSize) {
				break;
			}
		}		
	}
}

