package arraysAndString;

public class _0Intro {

    // Two Pointer - O(N)
//    function fn(arr1, arr2):
//    i = j = 0
//            while i < arr1.length AND j < arr2.length:
//    Do some logic here depending on the problem
//    Do some more logic here to decide on one of the following:
//            1. i++
//            2. j++
//            3. Both i++ and j++
//
//            // Step 4: make sure both iterables are exhausted
//            while i < arr1.length:
//    Do some logic here depending on the problem
//    i++
//
//            while j < arr2.length:
//    Do some logic here depending on the problem
//    j++

// Sliding Window = O(N)
// No. of subarrays:
// Let's look at [1,2,3,4] => [1][2][3][4]| [1,2][2,3][3,4]|[1,2,3][2,3,4]|[1,2,3,4]
// No of subarray of size 1 = 4 (N), of size 2 = 3 (N-1),
// which is 1+2+3+...+N = N(N+1)/2 = N^2. So, trivial solution if we calculate all subarrays we will have at least N^2 complexity

    /*
    function fn(arr):
        left = 0
        for right in [0, arr.length - 1]:
            Do some logic to "add" element at arr[right] to window

            while left < right AND condition from problem not met:
                Do some logic to "remove" element at arr[left] from window
                left++

            Do some logic to update the answer

You may be think there is a while loop inside of the for loop, so it's O(N^2)
The reason it is still O(n) is that the while loop can only iterate
n times in total for the entire algorithm (left starts at 0, only increases, and never exceeds n).
If the while loop were to run n times on one iteration of the for loop, that would mean it wouldn't run at all
for all the other iterations of the for loop.
This is what we refer to as amortized analysis - even though the worst case for an iteration inside the for loop is
O(n), it averages out to O(1) when you consider the entire runtime of the algorithm.
     */



}
