package com.example.dictionary;

import java.util.ArrayList;
import java.util.List;

public class DictData
{
    public String word, phonetic, audio_link;
    public List<SubData> subData = new ArrayList<>();
}

class SubData
{
    public String partOfSpeech;
    public List<SubSubData> subSubData = new ArrayList<>();
}

class SubSubData
{
    public String definition;
    public String example;
    public List<String> synonyms= new ArrayList<>();
}
