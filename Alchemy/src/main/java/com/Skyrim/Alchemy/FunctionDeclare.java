package com.Skyrim.Alchemy;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 14. 1. 20.
 */
public class FunctionDeclare {
    public static KOEN Effect[] = new KOEN[55];
    public static KOEN Item[]= new KOEN[102];
    public static Ingre Ingredient[]= new Ingre[102];
    public static int LangVer=1;//0=영어,1=한국어
    public static String BTnamelist[][] = new String[2][2];//버튼 이름
    public static int IoE;//Item or Effect 스피너의 선택 index 0=아이템,1=효과
    public static int TabCnt=4;//tab개수
    public static int DrawerSelected=0;//DrawerList 선택 Index
    public static int ArrarySelected=1;//DrawerList에 선택에 대한 배열에서의 index
    public static String[] DrawerListList;//Drawer에 올라갈 List QuickSort를 하면 바뀔 index를 대신해 이것을 사용,

    public String[] CreateListList(int flag){//flag=Effect정렬(0)orItem정렬(1)
        int itmp=0;
        KOEN Ktmp[] = new KOEN[110];
        switch (flag){
            case 0:
                itmp = Item.length;
                Ktmp = Item;
                break;
            case 1:
                itmp = Effect.length;
                Ktmp = Effect;
                break;
        }
        DrawerListList = new String[itmp];
        for(int i=0;i<itmp;i++){
            if(flag==1)
                DrawerListList[i] = ChangeLang(LangVer,Ktmp[i]);
            else
                DrawerListList[i] = Ingredient[i].Name;
        }
        quickSort(DrawerListList,0,itmp-1);
        return DrawerListList;
    }

    public String GetActionbarTitle(int pos){
        String tmp = DrawerListList[pos];
        return tmp;
    }

    public String[] GetTabsStrings(int pos){
        String [] tmp= new String[4];
        String temp = GetActionbarTitle(pos);
        for(int i=0;i<DrawerListList.length;i++){
            if(temp.equals(ChangeLang(LangVer,Item[i]))){
                tmp[0] = Ingredient[i].Effect[0];
                tmp[1] = Ingredient[i].Effect[1];
                tmp[2] = Ingredient[i].Effect[2];
                tmp[3] = Ingredient[i].Effect[3];
            }
        }
        return tmp;
    }

    public Bitmap getBitmap(Context con,int pos){//pos = index,FN = FileName
        Bitmap bm=null;
        String tmp = "image/";
        InputStream in = GetAssetsIS(con,tmp+Ingredient[pos].FileName);
        try{
            BufferedInputStream buf = new BufferedInputStream(in);
            bm = BitmapFactory.decodeStream(in);
        }catch (Exception e){}
        return bm;
    }

    public String[] CreateEffectList(int pos){//pos = 0~3
        String tmp[] = new String[Ingredient.length];
        int a=0;
        for(int i=0;i<Ingredient.length;i++){
            for(int j=0;j<4;j++){
                if(IoE==0){
                    if(Ingredient[ArrarySelected].Effect[pos].equals(Ingredient[i].Effect[j]) && i != ArrarySelected){
                        tmp[a] = Ingredient[i].Name;
                        a++;
                    }
                }else{
                    if (ChangeLang(LangVer,Effect[ArrarySelected]).equals(Ingredient[i].Effect[j])){
                        tmp[a] = Ingredient[i].Name;
                        a++;
                    }
                }
            }
        }
        quickSort(tmp,0,a-1);
        String tmp2[] = new String[a];
        for(int i=0;i<a;i++){
            tmp2[i] = tmp[i];
        }

        return tmp2;
    }

    public int ReListindex(String precom,int flag){//flag=0,Item하기,flag=1,Effect하기
        int ind=0;
        int itmp=0;
        KOEN Ktmp[] = new KOEN[110];
        switch (flag){
            case 0:
                itmp = Item.length;
                Ktmp = Item;
                break;
            case 1:
                itmp = Effect.length;
                Ktmp = Effect;
                break;
        }
        for(int i=0;i<itmp;i++){
            if(precom.equals(ChangeLang(LangVer,Ktmp[i]))){
                ind = i;
                break;
            }
        }
        return ind;
    }

    public void SetDrawerSelected(int position){
        DrawerSelected = position;
        ArrarySelected = ReListindex(DrawerListList[position],IoE);
    }

    public void Init(Context context){
        LoadTextData(context,"EffectKO.txt",Effect);
        LoadTextData(context,"itemKO.txt",Item);
        LoadTextData(context,"ingredient.txt",Ingredient);
        BTnamelist = GetBTName();
    }

    private void LoadTextData(Context context,String str,KOEN ke[]){
        String tmp = ReadAssetTXT(context,str);
        String temp[] = DevideNextLine(tmp);

        OBInit(ke);
        DevideDataString(temp,ke);
    }

    public void TranslateLang(){
        int uu;
        if(LangVer==0){
            uu = 1;
        }else{
            uu = 0;
        }

        for(int i=0;i<Ingredient.length;i++){
            for(int j=0;j<Item.length;j++){
                if(Ingredient[i].Name.equals(ChangeLang(uu,Item[j]))){
                    if(Item[j].Kor!=""){
                        Ingredient[i].Name = ChangeLang(LangVer,Item[j]);
                    }
                    break;
                }
            }
        }
        for(int i=0;i<Ingredient.length;i++){
            for(int q=0;q<4;q++){
                for(int j=0;j<Effect.length;j++){
                    if(Ingredient[i].Effect[q].equals(ChangeLang(uu,Effect[j]))){
                        if(Effect[j].Kor!=""){
                            Ingredient[i].Effect[q] = ChangeLang(LangVer,Effect[j]);
                        }
                        break;
                    }
                }
            }
        }
    }


    private void quickSort(String [] a, int leftmost, int rightmost){
        int pe;
        int i, last;

        if(leftmost >= rightmost) return;
        pe = (leftmost+rightmost)/2;
        swap(a, leftmost, pe);
        last = leftmost;

        for (i=leftmost+1; i<=rightmost; i++)
            if (a[i].compareTo(a[leftmost]) < 0) swap(a, ++last, i);
        swap(a, leftmost, last);
        quickSort(a, leftmost, last-1);
        quickSort(a, leftmost+1, rightmost);
    }

    private void swap(String[] a, int i, int j)  // 치환
    {
        String temp;
        temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private String[][] GetBTName(){
        String str[][] = new String[2][2];
        str[0][0] = "ITEM";
        str[0][1] = "EFFECT";
        str[1][0] = "아이템";
        str[1][1] = "효과";
        return str;
    }

    private void OBInit(KOEN ke[]){//ob = 초기화 할 object, a = 배열의 개수,
        for(int i=0;i<ke.length;i++){
            ke[i] = new KOEN();
        }
    }

    private void DevideDataString(String str[],KOEN ke[]){
        for(int i=0;i<str.length;i++){
            SepareteWord(str[i],ke[i]);
        }
    }

    private void SepareteWord(String st, KOEN ke){
        String tmp[] = st.split("=");
        ke.Eng = tmp[0];
        ke.Kor = tmp[1];
    }

    private void LoadTextData(Context context,String str,Ingre ke[]){
        String tmp = ReadAssetTXT(context,str);
        String temp[] = DevideNextLine(tmp);

        OBInit(ke);
        DevideDataString(temp,ke);
    }

    private void OBInit(Ingre ke[]){//ob = 초기화 할 object, a = 배열의 개수,
        for(int i=0;i<ke.length;i++){
            ke[i] = new Ingre();
        }
    }

    private void DevideDataString(String str[],Ingre  ke[]){
        for(int i=0;i<str.length;i++){
            SepareteWord(str[i],ke[i]);
        }
    }

    private void SepareteWord(String st, Ingre ke){
        String tmp[] = st.split("=");
        ke.Name = tmp[0];
        ke.Effect[0] = tmp[1];
        ke.Effect[1] = tmp[2];
        ke.Effect[2] = tmp[3];
        ke.Effect[3] = tmp[4];
        ke.FileName = tmp[5];
    }

    private String[] DevideNextLine(String str){//\n을 기준으로 split한 String 배열을 반환
        String tmp[] = str.split("\n");//쓸모없을것 같네,
        return tmp;
    }

    private String ChangeLang(int cut,KOEN ke){
        String tmp=null;
        switch (cut){
            case 0:
                tmp = ke.Eng;
                break;
            case 1:
                tmp = ke.Kor;
                break;
        }
        return tmp;
    }

    public void stringprint(String bs,String str){
        Log.e("test1",bs +" = "+str);
    }

    private String ReadAssetTXT(Context context,String filename){//assets에서 txt 읽기 한글 읽기 가능함
        String txt = null;
        InputStream is = GetAssetsIS(context,"image/"+filename);
        try{
            txt = ISUTF8Convert(is).toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        CloseIS(is);
        return txt;
    }
    private StringBuilder ISUTF8Convert(InputStream is){
        StringBuilder builder = new StringBuilder();
        try{
            InputStreamReader tmp = new InputStreamReader(is, "UTF-8");
            BufferedReader reader = new BufferedReader(tmp);
            String str;
            reader.read();//왜 인지 모르겠는데, 첫글자에 뭔가 추가되어서, 없애기 위해 추가.
            while ((str = reader.readLine()) != null) {
                builder.append(str + "\n");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return builder;
    }
    private InputStream GetAssetsIS(Context context, String file_name){//assets에서 파일 읽어서 inputsteam으로 반환
        AssetManager am = context.getResources().getAssets();
        InputStream is = null;
        try{
            is = am.open(file_name);
        }catch(Exception e){
            e.printStackTrace();
        }
        return is;
    }

    private void CloseIS(InputStream is){//inputstream 닫기
        if(is != null){
            try{
                is.close();
                is = null;
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

}
class KOEN{
    String Kor;//한국어
    String Eng;//영어
}
class Ingre{
    String Name;//재료 이름
    String Effect[] = new String[4];//재료 효과
    String FileName;//재료 파일명
}