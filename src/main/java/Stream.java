import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;

import javax.xml.bind.annotation.XmlAnyElement;
import java.io.*;
import java.util.*;


public class Stream extends HashMap<String, Stream.City> {

    public static class City extends HashSet<Mil> {
        public long sum = 0;
        public int count = 0;
        public static City top = null ;
        public static boolean equal = true;

        public static void dayPass(int i){
            top.count += i;
        }

        @Override
        public boolean add(Mil mil){
            if (top == null){
                top = this;
            }
           if (super.add(mil)) {
               this.sum += mil.money;
               if (((top.sum > 0) && (this.sum> top.sum)) || (top.sum == 0)) {
                   top = this;
                   equal = false;
               } else if(this.sum == top.sum ){
                   top = null;
                   equal =true;
               }
               return true;
           }
           return false;
        }

        @Override
        public boolean remove(Object mil){
           if (super.remove(mil)){
            this.sum -= ((Mil)mil).money;
            return true;
           }
            return false;
        }
    }

    public class Mil {
        public String name;
        public long money;

        public Mil(String name, long money){
            this.name = name;
            this.money = money;
        }
    }

    public static void main(String[] args) throws IOException {
        boolean oj = System.getProperty("ONLINE_JUDGE") != null;
        Reader reader = oj ? new InputStreamReader(System.in) : new FileReader("Info.txt");
        Writer writer = oj ? new OutputStreamWriter(System.out) : new FileWriter("output.txt");
        Scanner sc = new Scanner(reader);
        PrintWriter out = new PrintWriter(writer);
        ArrayList<String> st = new ArrayList<>();
        while (sc.hasNext()) {
           st.add(sc.next());
        }
        System.out.println(st.get(1));
    }
}
