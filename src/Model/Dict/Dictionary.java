package Model.Dict;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public class Dictionary  {
    private Map<String,String> dict;

    public Dictionary() throws IOException {
        dict = new TreeMap<String, String>();
        loadData();
        System.out.println(dict);
    }

//    làm việc với file thì throws ra exception
    private void loadData() throws IOException {
        File f = new File("slangtest.txt"); //lấy dữ liệu từ file
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);

        while (true) {
            String line = br.readLine();
            if(line == null || line.isEmpty()) break;


            String[] words = line.split("[-]");
//            thêm lệnh trim() để xóa khoảng trống
            String eng = words[0].trim(),vi =words[1].trim();
            dict.put(eng,vi);
        }

//      đóng file để không bị mất dữ liệu
        br.close();
        fr.close();
    }

    private void saveData() throws IOException {
        File f = new File("slangtest.txt");
//        đọc dữ liệu
        FileWriter fw = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(fw);

//        mỗi lần add là update data
        for(Map.Entry< String,String> entry : dict.entrySet())
            bw.write(entry.getKey() + " - " + entry.getValue() + "\n");



        bw.close();
        fw.close();
    }


    public void addWord() throws IOException {
        System.out.println("------------Add-----------");
//      tái sử dụng lại hàm Validation để nhập option của người dùng
        String eng = Validation.getString("Enter English");

//      nếu từ có sẵn rồi thì hỏi có muốn overwrite không
//        nếu không
        if (dict.containsKey(eng) && !Validation.getYN(eng +" is already existed in dictionary, do you want to overwrite (Y/N)? "))
            return;
//        nếu có
        String vi = Validation.getString("Enter Vietnamese : ");
        dict.put(eng, vi);
        saveData();
        System.out.println("add successful");
    }

    public void RemoveWord() throws IOException {
        System.out.println("------------Delete-----------");
        String eng = Validation.getString("Enter English");
        if(dict.containsKey(eng)) {
            System.err.println("NOT FOUND");
            return;
        }
        dict.remove(eng);
        saveData();
        System.out.println("delete successful");
    }

    public void Translate()
    {
        System.out.println("------------Translate-----------");
        String eng = Validation.getString("Enter English");
        if(!dict.containsKey(eng))
        {
            System.err.println("NOT FOUND");
            return;
        }
//      nhập vào tiếng anh trả ra tiếng việt
        System.out.println("Vietnamese : " + dict.get(eng));
    }
}
