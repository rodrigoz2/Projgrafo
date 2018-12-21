package com.example.lucas.trabgrafo;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class MainActivity extends AppCompatActivity {
    private Context ctx = this;

    ImageView mapa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {





        SQLiteDatabase db =new BancoDeDados(ctx).getWritableDatabase();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        mapa = (ImageView) this.findViewById(R.id.mapa);
        Bitmap bitmap = Bitmap.createBitmap((int) getWindowManager()
                .getDefaultDisplay().getWidth(), (int) getWindowManager()
                .getDefaultDisplay().getHeight(), Bitmap.Config.ARGB_8888);



        Bitmap mapa2 = BitmapFactory.decodeResource(getResources(), R.drawable.brasil1);

        Canvas canvas = new Canvas(bitmap);
        mapa.setImageBitmap(bitmap);

        // Line
        Paint paint = new Paint();
        paint.setColor(Color.rgb(255, 153, 51));
        paint.setStrokeWidth(10);
        int startx = 50;
        int starty = 90;
        int endx = 150;
        int endy = 360;
        canvas.drawLine(startx, starty, endx, endy, paint);


    }

    public void calcular(View view) {

        int cont = 1;
        EditText recebV1 = (EditText) findViewById(R.id.valor1);
        EditText recebV2 = (EditText) findViewById(R.id.valor2);
        TextView result = (TextView) findViewById(R.id.resultado);
        TextView caminho = (TextView) findViewById(R.id.tamanhoCaminho);
        TextView metrica = (TextView) findViewById(R.id.metrica);




        String v1 = recebV1.getText().toString();
        String v2 = recebV2.getText().toString();
        String m= metrica.getText().toString();




        Graph g = new Graph();


        SQLiteDatabase db = new BancoDeDados(ctx).getWritableDatabase();
        List<Enlace> lista = new ArrayList<Enlace>();

        Cursor cursor = db.query(BancoDeDados.TABELA, null, null, null, null,
                null, "enlace");
        while (cursor.moveToNext()) {
            Enlace enlace = new Enlace();
             if(m.equalsIgnoreCase("A")){

                 enlace.setMa(cursor.getInt(cursor.getColumnIndex("ma")));

             }  else if(m.equalsIgnoreCase("B")){

                enlace.setMa(cursor.getInt(cursor.getColumnIndex("mb")));

            }
             else if(m.equalsIgnoreCase("C")){

                 enlace.setMa(cursor.getInt(cursor.getColumnIndex("mc")));

             }

            enlace.setEnlace(cursor.getString(cursor.getColumnIndex("enlace")));



            lista.add(enlace);
        }

        if (lista.size() > 0) {
            int[] salvaValores = new int[10];
            for (int i = 0; i < lista.size(); i++) {


                Enlace a = lista.get(i);
                String s = a.getEnlace().substring(0, 3);


                int conta = 0;
                ArrayList<Vertex> lista0 = new ArrayList<Vertex>();


                for (int j = 0; j < lista.size(); j++) {


                    if (s.equals(lista.get(j).getEnlace().substring(0, 3))) {
                        salvaValores[conta] = j;
                        conta++;
                    }
                }

                Vertex[] vv = new Vertex[conta]; //Vetor que recebe o numero de vezes que "POA" existe no banco
                for (int contaInsere = 0; contaInsere < conta; contaInsere++) {
                    int inserir = salvaValores[contaInsere];
                    Enlace xs = lista.get(inserir);
                    Vertex v = new Vertex(xs.getEnlace().substring(6,9), xs.getMa());
                    vv[contaInsere] = v;
                }

                for (int contTamanho = 0; contTamanho < vv.length; contTamanho++) {
                    Vertex v = new Vertex(vv[contTamanho].getId(),vv[contTamanho].getDistance()); //No lugar do "z, a.getMa()" deve ser vv[contTamanho].ALGO
                    lista0.add(v);
                }
                g.addVertex(s, lista0);
            }
            salvaValores = null;
        }

        //Recebe o tamanho da lista
        int tamanho = g.getShortestPath(v1, v2).size();

        //O vetor recebeLista recebe o valor de tamanho
        String recebeLista[] = new String[tamanho];

        //Insere nosso caminho em um vetor
        for (int i = 0; tamanho > i; i++) {
            recebeLista[i] = g.getShortestPath(v1, v2).get(i).toString();
        }


        //Reverte nosso caminho pois esta gerando ao contrario
        for (int i = 0; i < tamanho / 2; i++) {
            String temp = recebeLista[i];
            recebeLista[i] = recebeLista[recebeLista.length - 1 - i];
            recebeLista[recebeLista.length - 1 - i] = temp;
        }

        //Inseriomos o tamanho +1 para aumentarmos o limite e inserir o inicio do vetor
        tamanho += 1;

        //Criamos nosso vetor que vai receber o inicio
        String vetFinal[] = new String[tamanho];

        //passamos todos dados do vetor que recebeu a lista para o vetFinal com o inicio dele
        for (int i = 0; i < recebeLista.length; i++) {
            vetFinal[cont] = recebeLista[i];
            cont++;
        }
        vetFinal[0] = v1 + "";


        result.setText(Arrays.toString(vetFinal).toString());
        caminho.setText(vetFinal.length + "");


    }


    public void listartudo(View view){
        SQLiteDatabase db =new BancoDeDados(ctx).getWritableDatabase();
        List<Enlace> lista = new ArrayList<Enlace>();
        Cursor cursor = db.query(BancoDeDados.TABELA,null,null,null,null,
                null,"enlace");
        while(cursor.moveToNext()){
            Enlace enlace = new Enlace();
            enlace.setEnlace(cursor.getString(cursor.getColumnIndex("enlace")));
            enlace.setMa(cursor.getInt(cursor.getColumnIndex("ma")));
            enlace.setMb(cursor.getInt(cursor.getColumnIndex("mb")));
            enlace.setMc(cursor.getInt(cursor.getColumnIndex("mc")));
            lista.add(enlace);
        }

        if (lista.size() > 0){
            String dados[] = new String[lista.size()];
            for (int i=0;i<lista.size();i++){
                Enlace a = lista.get(i);
                dados[i] = "Enlace: "+a.getEnlace()+"\n"+a.getMa()+"\n"+a.getMb()+"\n"+a.getMc();
            }
            ArrayAdapter<String> myadapter = new ArrayAdapter<String>(
                    getApplicationContext(),
                    R.layout.item_list,
                    R.id.item,
                    dados
            );
            ListView lista1 = (ListView)findViewById(R.id.listview);
            lista1.setAdapter(myadapter);
        }else{
            Toast.makeText(getApplicationContext(),"Banco de dados vazio",
                    Toast.LENGTH_LONG).show();
        }



    }






}

