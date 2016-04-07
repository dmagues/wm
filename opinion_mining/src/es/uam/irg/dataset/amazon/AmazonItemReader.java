/**
 * Copyright 2016 
 * Ivan Cantador
 * Information Retrieval Group at Universidad Autonoma de Madrid
 *
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with 
 * the current software. If not, see <http://www.gnu.org/licenses/>.
 */
package es.uam.irg.dataset.amazon;

import es.uam.irg.dataset.amazon.item.AmazonItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * AmazonItemReader
 *
 * Reads item metadata from a JSON file of the Julian McAuley's Amazon product dataset, http://jmcauley.ucsd.edu/data/amazon 
 *
 * @author Ivan Cantador, ivan.cantador@uam.es
 * @version 1.0 - 16/03/2016
 */
public class AmazonItemReader {

    public static final String DATA_PATH = "./data/amazon/amazon_metadata_music.json";
    private String file;

    public AmazonItemReader(String file) throws Exception {
        if (file == null) {
            throw new IllegalArgumentException("Null file");
        }
        this.file = file;
    }

    public Map<String, String> loadItemIds() throws Exception {
        Map<String, String> ids = new HashMap<String, String>();

        Gson gson = new GsonBuilder().create();

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.file), "UTF-8"));
        String line = null;
        while ((line = reader.readLine()) != null) {
            AmazonItem item = gson.fromJson(line, AmazonItem.class);
            ids.put(item.getId(), null);
        }
        reader.close();

        return ids;
    }

    public Map<String, AmazonItem> loadItems() throws Exception {
        Map<String, AmazonItem> items = new HashMap<String, AmazonItem>();

        Gson gson = new GsonBuilder().create();

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.file), "UTF-8"));
        String line = null;
        while ((line = reader.readLine()) != null) {
            AmazonItem item = gson.fromJson(line, AmazonItem.class);
            items.put(item.getId(), item);
        }
        reader.close();

        return items;
    }

    // firstIndex = inclusive
    // lastIndex = exclusive
    public Map<String, AmazonItem> loadItems(int firstIndex, int lastIndex) throws Exception {
        if (firstIndex < 0 || lastIndex < 0 || firstIndex > lastIndex) {
            throw new IllegalArgumentException("Invalid index: " + firstIndex + " " + lastIndex);
        }

        Map<String, AmazonItem> items = new HashMap<String, AmazonItem>();

        Gson gson = new GsonBuilder().create();

        int i = -1;
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.file), "UTF-8"));
        String line = null;
        while ((line = reader.readLine()) != null) {
            AmazonItem item = gson.fromJson(line, AmazonItem.class);
            i++;

            if (firstIndex <= i && i < lastIndex) {
                items.put(item.getId(), item);
            }
            if (i == lastIndex - 1) {
                break;
            }
        }
        reader.close();

        return items;
    }

    public AmazonItem loadItem(String itemId) throws Exception {
        Gson gson = new GsonBuilder().create();

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.file), "UTF-8"));
        String line = null;
        while ((line = reader.readLine()) != null) {
            AmazonItem item = gson.fromJson(line, AmazonItem.class);
            String id = item.getId();
            if (itemId.equals(id)) {
                reader.close();
                return item;
            }
        }
        reader.close();
        return null;
    }
}
