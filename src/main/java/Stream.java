import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;

import javax.xml.bind.annotation.XmlAnyElement;
import java.io.*;
import java.util.*;


public class Stream extends HashMap<String, Stream.City> {

    public static class City extends HashSet<Mil> {
        public String name;
        public long sum = 0;
        public int count = 0;
        public static City top = null ;
        public static boolean equal = true;

        public static void dayPass(int i){
            if (top != null)
                top.count += i;
        }

        @Override
        public boolean add(Mil mil){
            if (top == null){
                top = this;
            }
           if (super.add(mil)) {
               mil.city =this;
               this.sum += mil.money;
               if (((top.sum > 0) && (this.sum> top.sum)) || (top.sum == 0)) {
                   top = this;
                   equal = false;
               } else if((this.sum == top.sum) && (top!= this) ){
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

    public static class Mil {
        public static Map<String, Mil> all = new HashMap<>();
        public String name;
        public long money;
        public City city;

        public Mil(String name, long money){
            this.name = name;
            this.money = money;
            all.put(this.name, this);
        }

        public static Mil getMilByName(String name){
           return Mil.all.get(name);
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
        Stream stream = new Stream();
        int j = 0;
        int words = 0;
        for (int i = 1; j<Integer.parseInt(st.get(0)); i += 3, j++){
            Mil mil = new Mil(st.get(i), Long.parseLong(st.get(i+2)));
            if (stream.containsKey(st.get(i+1))){
            stream.get(st.get(i+1)).add(mil);
            }
            else {
                City ct = new City();
                ct.add(mil);
                stream.put(st.get(i+1),ct);
            }
            words = i;
        }
        City.dayPass(1);
        int k =0;
        for (int i = words +5; k< Integer.parseInt(st.get(words+4)); i += 3, k++) {
            int count = Integer.parseInt(st.get(i));
            Mil.getMilByName(st.get(i + 1)).city.remove(Mil.getMilByName(st.get(i + 1)));
            if (stream.get(st.get(i + 2)) != null) {
                stream.get(st.get(i + 2)).add(Mil.getMilByName(st.get(i + 1)));
            } else {
                City ct = new City();
                stream.put(st.get(i + 2), ct);
                ct.add(Mil.getMilByName(st.get(i + 1)));
            }
                if (i+3 < st.size() && count != Integer.parseInt(st.get(i + 3))) {
                    City.dayPass(count);
                }
            }
        }
    }
