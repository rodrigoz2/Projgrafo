package com.example.lucas.trabgrafo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoDeDados extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION= 1;
    private static final String DATABASE_NAME="isso8000";


    public static final String TABELA = "es";


    public BancoDeDados(Context ctx){
        super(ctx,DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {



        String sql = "CREATE TABLE "+TABELA+
                " (enlace varchar(100) PRIMARY KEY,ma varchar(200),"+
                " mb varchar(200), mc varchar(200));";



     String sql2 =  "INSERT INTO "+TABELA+" (enlace,ma,mb,mc)  values('POA – FLO','1','6','2'), ('POA – BLU','1','7','2'), ('FLO – BLU','1','1','3'), ('FLO – CUR','1','2','5'), ('FLO – RJO','1','12','10'), ('BLU – CUR','1','2','5'), ('CUR – LON','1','6','2'), ('CUR – SPO','1','5','10'), ('LON – SPO','1','7','2'), ('LON – BAU','1','3','2'), ('SPO – RJO','1','5','15'), ('SPO – CAM','1','1','7'), ('SPO – SJC','1','2','16'), ('SJC – CAM','1','2','10'), ('RJO – SJC','1','3','10'), ('RJO – BHO','1','7','6'), ('RJO – SLV','1','20','6'), ('BHO – SJC','1','7','8'), ('BHO – BSB','1','9','6'), ('CAM – BAU','1','3','6'), ('CAM – RBP','1','2','4'), ('RBP – BSB','1','8','4'), ('BAU – CPG','1','10','3'), ('CPG – CUI','1','8','2'), ('CUI – MAN','1','20','3'), ('MAN – BEL','1','18','2'), ('BEL – NTL','1','21','3'), ('BSB – MAN','1','22','6'), ('BSB – NTL','1','22','7'), ('NTL – REC','1','4','3'), ('REC – SLV','1','8','5'), ('SLV – NTL','1','15','4');";





            db.execSQL(sql);
   db.execSQL(sql2);







    }





    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
