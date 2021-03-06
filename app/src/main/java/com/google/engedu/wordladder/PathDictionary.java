/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.wordladder;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;

public class PathDictionary {
    private static final int MAX_WORD_LENGTH = 4;
    private static HashSet<String> words = new HashSet<>();
    private static HashMap<String,ArrayList<String>> graph;

    public PathDictionary(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return;
        }
        Log.i("Word ladder", "Loading dict");
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        Log.i("Word ladder", "Loading dict");
        graph=new HashMap<>();
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() > MAX_WORD_LENGTH) {
                continue;

            }
            words.add(word);
          /*  if(!graph.containsKey(word))
            {
                graph.put(word,neighbours(word));
            }
    Log.d("tag",graph.get("pain").toString());*/

        }


    }

    public boolean isWord(String word) {
        return words.contains(word);
    }

    private ArrayList<String> neighbours(String word) {
        char alpha[]={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        ArrayList<String> neighbourslist = new ArrayList<>();
        for(int i=0;i<word.length();i++) {
            for (char ch : alpha) {
                StringBuilder newWord = new StringBuilder(word);
                newWord.setCharAt(i, ch);
                if (isWord(newWord.toString()) /*&& (!word.equals(newWord.toString()))*/) {
                    neighbourslist.add(newWord.toString());
                }

            }
        }
        return neighbourslist;
    }

    public String[] findPath(String start, String end) {
        ArrayDeque<ArrayList<String>> queue=new ArrayDeque<>();
        ArrayList<String> pathList = new ArrayList<>();

        HashSet<String> visited=new HashSet<>();
        pathList.add(start);
        visited.add(start);
        queue.addLast(pathList);
        while(!queue.isEmpty())
        {
            ArrayList<String> getPath = queue.poll();
            if(getPath.size()>5)
            {
                continue;
            }
            String lastWord = getPath.get(getPath.size()-1);
            for(String s : neighbours(lastWord))
            {
                if(s.equals(end))
                {
                    getPath.add(end);
                    return  getPath.toArray(new String[getPath.size()]);
                }
                else
                {
                    if(!visited.contains(s))
                    {
                        visited.add(s);
                        ArrayList<String> currentPath = new ArrayList<>(getPath);
                        currentPath.add(s);
                        queue.addLast(currentPath);
                    }
                }

            }
        }

        return null;
    }
}
