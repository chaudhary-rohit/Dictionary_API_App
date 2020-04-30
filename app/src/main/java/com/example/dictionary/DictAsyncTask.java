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
        L1DictData l1DictData = new L1DictData();
        try {
            String language = "en";
            String word = strings[0];
            String url = "https://www.google.com/async/dictw?hl="+language+"&async=term:"+word+",corpus:"+language+",hhdr:false,hwdgt:true,wfp:true,xpnd:true,ttl:,tsl:"+language+",_id:dictionary-modules,_pms:s,_jsfs:Ffpdje,_fmt:pc";
                       //https://www.google.com/async/dictw?hl=eng&async=term:web,corpus:eng,hhdr:false,hwdgt:true,wfp:true,xpnd:true,ttl:,tsl:eng,_id:dictionary-modules,_pms:s,_jsfs:Ffpdje,_fmt:pc
            Document document = Jsoup.connect(url).get();
            Elements content  = document.select(".VpH2eb.vmod.XpoqFe");

            String phonetic = content.select(".WI9k4c").select(".S23sjd").text();
            String audio = content.select(".gycwpf.D5gqpe").select("source").attr("src");

            for (Element c : content.select("div > .vmod").select(".vmod > .vmod"))
            {
                String partOfSpeech = c.select(".vpx4Fd").select(".pgRvse.vdBwhd i").text();

                L2DictData l2DictData = new L2DictData();
                l2DictData.partOfSpeech = partOfSpeech;

                for (Element x : c.select("div > ol").first().children().select("li"))
                {
                    List<String> synonyms = new ArrayList<>();
                    L3DictData l3DictData = new L3DictData();

                    String parent_selector = ".QIclbb.XpoqFe";

                    String definition = x.select(parent_selector + " div[data-dobid='dfn']").text();
                    String example = x.select(parent_selector + " .vk_gy").text();

                    for (Element y : x.select(parent_selector + " > div.qFRZdb div.CqMNyc").select("div[role='listitem']")) {
                        String synonym = y.select(".gWUzU.F5z5N").text();
                        synonyms.add(synonym);
                    }

                    l3DictData.definition = definition;
                    l3DictData.example = example;
                    l3DictData.synonyms = synonyms;

                    if (definition != "" && definition != " ")
                    {
                        Log.d("SEARCH_ERR", " non-empty check passed");
                        l2DictData.l3DictData.add(l3DictData);
                    }
                }
                l1DictData.l2DictData.add(l2DictData);
            }
            l1DictData.word = word;
            l1DictData.phonetic = phonetic;
            l1DictData.audio_link = audio;
        }
        catch (IOException e)
        {
            Log.d("IOExceptionLOG", "Failed at donInBackground -> First try");
            e.printStackTrace();
        }
        return l1DictData;
    }

    @Override
    protected void onPostExecute(L1DictData l1DictData)
    {
        Log.i("MEANING", "Obtained");

        delegate.dictProcessFinish(l1DictData);
    }
}