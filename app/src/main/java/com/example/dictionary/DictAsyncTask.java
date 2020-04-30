package com.example.dictionary;

import android.os.AsyncTask;
import android.util.Log;

import com.example.dictionary.recycler_views.L1DictData;
import com.example.dictionary.recycler_views.L2DictData;
import com.example.dictionary.recycler_views.L3DictData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DictAsyncTask extends AsyncTask<String, Void, L1DictData>
{
    DictAsyncResponse delegate = null;

    @Override
    protected L1DictData doInBackground(String... strings)
    {
        try
        {
            L1DictData l1DictData = new L1DictData();
            String language = "en";
            String word = strings[0];
            String url = "https://www.google.com/async/dictw?hl="+language+"&async=term:"+word+",corpus:"+language+",hhdr:false,hwdgt:true,wfp:true,xpnd:true,ttl:,tsl:"+language+",_id:dictionary-modules,_pms:s,_jsfs:Ffpdje,_fmt:pc";
                       //https://www.google.com/async/dictw?hl=eng&async=term:web,corpus:eng,hhdr:false,hwdgt:true,wfp:true,xpnd:true,ttl:,tsl:eng,_id:dictionary-modules,_pms:s,_jsfs:Ffpdje,_fmt:pc
            Document document = Jsoup.connect(url).get();
            Elements content  = document.select(".VpH2eb.vmod.XpoqFe");

            l1DictData.word       = word.substring(0, 1).toUpperCase() + word.substring(1);
            l1DictData.phonetic   = content.select(".WI9k4c").select(".S23sjd").text();
            l1DictData.audio_link = content.select(".gycwpf.D5gqpe").select("source").attr("src");

            if (!l1DictData.phonetic.equals("") && !l1DictData.phonetic.equals(" "))
            {
                l1DictData.l1_dictdata_available = true;
            }

            for (Element c : content.select("div > .vmod").select(".vmod > .vmod"))
            {
                L2DictData l2DictData = new L2DictData();
                l2DictData.partOfSpeech = c.select(".vpx4Fd").select(".pgRvse.vdBwhd i").text();

                for (Element x : c.select("div > ol").first().children().select("li"))
                {
                    L3DictData l3DictData = new L3DictData();

                    String parent_selector = ".QIclbb.XpoqFe";

                    l3DictData.definition = x.select(parent_selector + " div[data-dobid='dfn']").text();
                    l3DictData.example    = x.select(parent_selector + " .vk_gy").text();

                    List<String> synonyms = new ArrayList<>();
                    for (Element y : x.select(parent_selector + " > div.qFRZdb div.CqMNyc").select("div[role='listitem']"))
                    {
                        String synonym = y.select(".gWUzU.F5z5N").text();
                        synonyms.add(synonym);
                    }
                    if(synonyms.size()>0)
                    {
                        l3DictData.synonyms = synonyms;
                        l3DictData.synonyms_available = true;
                    }
                    else
                    {
                        l3DictData.synonyms.add("Synonym not available");
                        l3DictData.synonyms_available = false;
                    }
                    if (!l3DictData.definition.equals("") && !l3DictData.definition.equals(" "))
                    {
                        Log.d("MYTAG", " Definition is not null");
                        l2DictData.l3DictData.add(l3DictData);
                    }
                }
                l1DictData.l2DictData.add(l2DictData);
            }
            if(l1DictData.l2DictData.size()>0 && l1DictData.l1_dictdata_available)
            {
                l1DictData.l2_dictdata_available = true;
                l1DictData.l1_dictdata_available = true;
                Log.d("MYTAG", " CP1");
                return l1DictData;
            }
            else
            {
                l1DictData.l2_dictdata_available = false;
                l1DictData.l1_dictdata_available = false;
                Log.d("MYTAG", " CP2");
                return null;
            }
        }
        catch (IOException e)
        {
            Log.d("IOExceptionLOG", "Failed at donInBackground -> First try");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(L1DictData l1DictData)
    {
        Log.i("MEANING", "Obtained");
        delegate.dictProcessFinish(l1DictData);
    }
}