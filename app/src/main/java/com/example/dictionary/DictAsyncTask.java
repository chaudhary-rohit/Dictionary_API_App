package com.example.dictionary;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DictAsyncTask extends AsyncTask<String, Void, DictData>
{
    DictAsyncResponse delegate = null;

    @Override
    protected DictData doInBackground(String... strings)
    {
        DictData dictData = new DictData();
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

                SubData subData = new SubData();
                subData.partOfSpeech = partOfSpeech;

                for (Element x : c.select("div > ol").first().children().select("li"))
                {
                    List<String> synonyms = new ArrayList<>();
                    SubSubData subSubData = new SubSubData();

                    String parent_selector = ".QIclbb.XpoqFe";

                    String definition = x.select(parent_selector + " div[data-dobid='dfn']").text();
                    String example = x.select(parent_selector + " .vk_gy").text();

                    for (Element y : x.select(parent_selector + " > div.qFRZdb div.CqMNyc").select("div[role='listitem']")) {
                        String synonym = y.select(".gWUzU.F5z5N").text();
                        synonyms.add(synonym);
                    }

                    subSubData.definition = definition;
                    subSubData.example = example;
                    subSubData.synonyms = synonyms;

                    if (definition != "" && definition != " ")
                    {
                        Log.d("SEARCH_ERR", " non-empty check passed");
                        subData.subSubData.add(subSubData);
                    }
                }
                dictData.subData.add(subData);
            }
            dictData.word = word;
            dictData.phonetic = phonetic;
            dictData.audio_link = audio;
        }
        catch (IOException e)
        {
            Log.d("IOExceptionLOG", "Failed at donInBackground -> First try");
            e.printStackTrace();
        }
        return dictData;
    }

    @Override
    protected void onPostExecute(DictData dictData)
    {
        Log.i("MEANING", "Obtained");

        delegate.dictProcessFinish(dictData);
    }
}