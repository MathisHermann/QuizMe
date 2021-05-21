package com.example.quizme;


import java.util.ArrayList;
import java.util.Collections;

import com.example.quizme.database.FlatfileDatabase;
import com.example.quizme.database.DBHandler;
import android.content.Context;


import com.example.quizme.database.Serializer;
import com.example.quizme.database.Deserializer;

public class PlayQuiz {
        private int rights;
        private int round;
        private String ringtAnnser;
        FlatfileDatabase fdb = new FlatfileDatabase(new DBHandler(null));


        public void listAllSets(){
            //change null
            fdb.getAllSets().forEach(quizSet -> {
                System.out.println(quizSet.getName());
            });

    }
    PlayQuiz(String set){




    }
    public void roundPlay(){


    }
    public void getQuestion(){
      //  FlatfileDatabase fdb = new FlatfileDatabase(new DBHandler(null));

        fdb.getAllSets();
    }
//    public ArrayList<String> getChoises(){
//        ArrayList<String> choises;
//        choises = sets.getworngAnnsers();
//        //ringtAnnser = set.getRigtAnnser();
//        choises.add(ringtAnnser);
//        Collections.shuffle(choises);
//        return choises;
//    }

    public boolean select(String choise){
        round++;
        if (choise == ringtAnnser){
            rights++;

            return true;
        }else {
            return false;
        }
    }



    public int getRights(){
        return rights;
    }

}
