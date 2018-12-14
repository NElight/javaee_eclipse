import java.lang.reflect.Array;
import java.util.LinkedList;

public class YZGProblem24 {
	
	public static void main(String[] args) {
		YZGProblem24 problem24 = new YZGProblem24();
		System.out.println(problem24.testP(new int[] {0,1,2,3}));
	}
	
	public LinkedList<String> testP(int[] array){
		LinkedList<String> rel = new LinkedList<>();
		if (array.length == 2) {
			String first = String.valueOf(array[0]) + String.valueOf(array[1]);
			String second = String.valueOf(array[1]) + String.valueOf(array[0]);
			LinkedList<String> reLinkedList =  new LinkedList<String>();
			reLinkedList.add(first);
			reLinkedList.add(second);
			return reLinkedList;
		}else {
			
			for (int i:array) {
				String[] temp;
				int [] minuxArr;
				minuxArr = remove(array, i);
				temp = printPermutations(minuxArr);
				for (String s:temp) {
					
					rel.add(s + String.valueOf(i));
				}
			}
			
			return rel;
		}
	}
	
	public String[] printPermutations(int[] array) {
		if (array.length == 2) {
			String first = String.valueOf(array[0]) + String.valueOf(array[1]);
			String second = String.valueOf(array[1]) + String.valueOf(array[0]);
			return new String[] {first, second};
		}
		for (int i:array) {
			String[] temp;
			int [] minuxArr;
			minuxArr = remove(array, i);
			temp = printPermutations(minuxArr);
			String[] rel;
			for (String s:temp) {
				
			}
		}
		return null;
	}
	
	private int[] remove(int[] arr, int num) {
        int[] tmp = new int[arr.length - 1];
        int idx = 0;
        boolean hasRemove = false;
        for (int i = 0; i < arr.length; i++) {
 
            if (!hasRemove && arr[i] == num) {
                hasRemove = true;
                continue;
            }
 
            tmp[idx++] = arr[i];
        }
 
        return tmp;
    }

}
