
import java.util.ArrayList;

/**
 *
 * @author Ronald Sun
 * @date 5/13/2019
 */
public class SS {

    public static void print(int[] i) {
        for (int k = 0; k < i.length; k++) {
            System.out.print(i[k] + " ");
        }
        System.out.println();
    }
    
    public static void print(ArrayList<Integer> i) {
        for (int k = 0; k < i.size(); k++) {
            System.out.print(i.get(k) + " ");
        }
        System.out.println();
    }
    
    public static void printString(ArrayList<String> i) {
        for (int k = 0; k < i.size(); k++) {
            System.out.print(i.get(k) + " ");
        }
        System.out.println();
    }

    public static void bubble(int[] list) {
        int temp;
        for (int k = 0; k < list.length - 1; k++) {
            for (int i = 0; i < list.length - 1; i++) {
                if (list[i] > list[i + 1]) {
                    temp = list[i];
                    list[i] = list[i + 1];
                    list[i + 1] = temp;
                }
            }
        }
    }

    public static void insertion(int[] list) {
        int temp = 0;
        int index = 0;
        int temp2 = 0;
        for (int i = 0; i < list.length; i++) {
            for (int j = list.length - 1 - i; j >= 0; j--) {
                if (list[j] > temp) {
                    temp = list[j];
                    index = j;
                }
            }
            temp2 = list[list.length - 1 - i];
            list[list.length - 1 - i] = list[index];
            list[index] = temp2;
            temp = 0;
        }
    }

    public static void insertion(Comparable[] list) {
        Comparable temp = 0;
        Comparable temp2 = 0;
        int index = 0;

        for (int i = 0; i < list.length; i++) {
            for (int j = list.length - i - 1; j >= 0; j--) {
                if (list[j].compareTo(temp) > 0) {
                    temp = list[j];
                    index = j;
                }
            }
            temp2 = list[list.length - i - 1];
            list[list.length - i - 1] = list[index];
            list[index] = temp2;
            temp = 0;
        }
    }

    public static void insertion(ArrayList<String> list) {

        int i, j;
        String currentElement;
        for (i = 1; i < list.size(); i++) {
            j = i;
            currentElement = list.get(j);
            while ((j > 0) && (list.get(j - 1).compareToIgnoreCase(currentElement) > 0)) {
                list.set(j, list.get(j - 1));
                j--;
            }
            list.set(j, currentElement);
        }

    }
    
    public static void reverseSort(ArrayList<Integer> list, ArrayList<String> list1) {

        for (int i = 0; i <list.size(); i++){
        int min = i;
        for (int j = i + 1; j < list.size(); j++){
            if (list.get(j) > list.get(min)){
                min = j;
            }
        }

        int tmp = list.get(i);
        list.set(i, list.get(min));
        list.set(min,tmp);
        String tmp1 = list1.get(i);
        list1.set(i, list1.get(min));
        list1.set(min,tmp1);
    }
    }

    public static void selection(int[] list) {
        int temp = 0;
        for (int i = 0; i < list.length; i++) {
            for (int k = 0; k < list.length; k++) {
                if (list[k] > list[i]) {
                    temp = list[i];
                    list[i] = list[k];
                    list[k] = temp;
                }
            }
        }
    }

    public static int search(int[] list, int key) {
        for (int i = 0; i < list.length; i++) {
            if (list[i] == key) {
                return i;
            }
        }
        return -1;
    }

    public static int search(Comparable[] list, Comparable key) {
        for (int i = 0; i < list.length; i++) {
            if (list[i].compareTo(key) == 0) {
                return i;
            }
        }
        return -1;
    }

    public static int search(Object[] list, Object key) {
        for (int i = 0; i < list.length; i++) {
            if (list[i].equals(key)) {
                return i;
            }
        }
        return -1;
    }

    public static int search(ArrayList<String> list, String key) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).compareTo(key) == 0) {
                return i;
            }
        }
        return -1;
    }
    
    public static int searchUsername(ArrayList<String> list, String key) { 
        ArrayList<String> username = new ArrayList();
        for(int j = 0; j < list.size(); j++){
            username.add(list.get(j).substring(0, list.get(j).indexOf(",")));
        }
        return search(username, key);
    }

    public static int binarySearch(int[] list, int key) {
        int high = list.length - 1;
        int low = 0;
        int mid = (low + high) / 2;
        while (high >= low) {
            mid = (low + high) / 2;
            if (list[mid] == key) {
                return mid;
            }
            if (key < list[mid]) {
                high = mid;
            } else if (key > list[mid]) {
                low = mid;
            } else {
                return -1;
            }
        }
        return -1;
    }

    public static int binarySearch(Comparable[] list, Comparable key) {
        int high = list.length - 1;
        int low = 0;
        int mid = (low + high) / 2;
        while (high >= low) {
            mid = (low + high) / 2;
            if (list[mid].compareTo(key) == 0) {
                return mid;
            }
            if (list[mid].compareTo(key) > 0) {
                high = mid;
            } else if (list[mid].compareTo(key) < 0) {
                low = mid;
            }
        }
        return -1;
    }


} // end class
