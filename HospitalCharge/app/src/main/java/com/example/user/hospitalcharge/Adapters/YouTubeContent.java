package com.example.user.hospitalcharge.Adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 23-12-2015.
 */
public class YouTubeContent {

    public static List<YouTubeVideo> ITEMS = new ArrayList<>();

    /**
     * A map of YouTube videos, by ID.
     */
    public static Map<String, YouTubeVideo> ITEM_MAP = new HashMap<>();

    static {
        /*addItem(new YouTubeVideo("cRgI3YVYncc", "Basic Shoulder Arthroscopy in Orthopaedics"));
        addItem(new YouTubeVideo("38DZgGjaPOA", "Arthroscopic SLAP repair by Dr. Patrick Jost"));
        addItem(new YouTubeVideo("uObzRhWq7tk", "Arthroscopic Bankart Repair"));
        addItem(new YouTubeVideo("or_JR3dNpUs", "Hair Testing by Quest Diagnostics"));
        addItem(new YouTubeVideo("18075969", "The Wonder Hospital"));
        addItem(new YouTubeVideo("26186155", "Cervical Spine Medial Branch Nerve Neurolysis neurosurgical animations"));
        addItem(new YouTubeVideo("16144037", "What is endoscopic brain surgery"));
        addItem(new YouTubeVideo("26186167", "Cervical Spine Medial Branch Nerve Block Injection pain management"));
        addItem(new YouTubeVideo("54275902", "Seguiremos - Hospital Sant Joan de Déu y Macaco"));
        addItem(new YouTubeVideo("16144037", "What is endoscopic brain surgery"));*/

        addItem(new YouTubeVideo("_i7sNa0if9s", "Health issues associated with diabetes are on the decline."));
        addItem(new YouTubeVideo("o9Lz5ZVyKGE", "Genetic Analysis of Breast Cancer Could Change Treatment"));
        addItem(new YouTubeVideo("4ZRgVQALFUA", "Documentary: Why Does U.S. Health Care Cost So Much?"));
        addItem(new YouTubeVideo("LImml5sLZLg", "\"Former health care CEO argues America's medical system rewards bad outcomes"));
        addItem(new YouTubeVideo("iYOf6hXGx6M", "Health Care: U.S. vs. Canada"));
        addItem(new YouTubeVideo("sfnwgo3ErQY", "America's Bitter Pill\": Behind Obamacare, healthcare costs"));
    }
    public static void addItem(final YouTubeVideo item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A POJO representing a YouTube video
     */
    public static class YouTubeVideo {
        public String id;
        public String title;

        public YouTubeVideo(String id, String content) {
            this.id = id;
            this.title = content;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}
