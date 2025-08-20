

import java.util.Arrays;

class containsDuplicate {
    public boolean containsDuplicate(int[] nums) {
        Arrays.sort(nums); // ordena
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == nums[i + 1]) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] num = {1, 2, 3, 1};
        containsDuplicate sol = new containsDuplicate();
        boolean resultado = sol.containsDuplicate(num);

        System.out.println("" + resultado);
    }

}
