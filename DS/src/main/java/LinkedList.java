import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LinkedList {
    public static void main(String[] args) {
        ListNode head = createLinkedList(new int[]{5,4,2,1});
        System.out.println(Arrays.toString(convertListToArray(head).toArray()));
        System.out.println(pairSum(head));
        //System.out.println(Arrays.toString(convertListToArray(reverse(head)).toArray()));
    }

    public ListNode swapPairs(ListNode head) {

        if (head == null || head.next == null) return head;
        ListNode prev = null;
        ListNode curr = head;
        ListNode next = null;
        int k = 0;

        while(curr != null && k < 2){
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
            k++;
        }

        head.next = swapPairs(curr);
        return prev;

    }

    static ListNode createLinkedList(int [] arr){
        ListNode head = new ListNode();
        ListNode ptr = null;
        for (int n: arr){
            if (ptr == null){
                head.val = n;
                ptr = head;
            }
            else {
                ListNode node = new ListNode();
                node.val = n;
                ptr.next = node;
                ptr = ptr.next;
            }
        }
        return head;
    }

    static List<Integer> convertListToArray(ListNode head){
        List<Integer> list = new ArrayList<>();
        while (head!=null){
            list.add(head.val);
            head = head.next;
        }
        return list;
    }

    public static int pairSum(ListNode head) {
        if (head == null || head.next == null) return head.val;
        ListNode slow = head, fast = head;
        while(fast!=null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode revHead = reverse(slow);
        ListNode ptrHead = head;
        int sum = 0;
        while(ptrHead!=slow && revHead!=null){
            sum = sum + (ptrHead.val + revHead.val);
            ptrHead = ptrHead.next;
            revHead = revHead.next;
        }

        while(revHead!=null){
            sum = sum + revHead.val;
            revHead = revHead.next;
        }

        return sum;
    }

    static ListNode reverse(ListNode head){
        if (head == null || head.next == null) return head;
        ListNode rev = reverse(head.next);
        head.next.next = head;
        head.next = null;
        return rev;
    }

}

 class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }

