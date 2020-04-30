package com.example.dictionary.recycler_views;

import java.util.ArrayList;
import java.util.List;

public class L1DictData
{
    public String word, phonetic, audio_link;
    public boolean l1_dictdata_available;
    public List<L2DictData> l2DictData = new ArrayList<>();
    public boolean l2_dictdata_available;
}

