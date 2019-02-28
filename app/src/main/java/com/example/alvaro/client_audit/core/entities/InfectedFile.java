package com.example.alvaro.client_audit.core.entities;

import org.json.JSONArray;
import java.util.List;
import static com.example.alvaro.client_audit.core.utils.JsonParsers.parse_JSON_string_array;

public class InfectedFile {

    private String filename;
    private String file_route;
    private String size;
    private List<String> rules;

    public InfectedFile(String filename, String file_route, String size, JSONArray rules){
        this.filename = filename;
        this.file_route = file_route;
        this.size = size;
        this.rules = parse_JSON_string_array(rules);
    }

    public String getFilename() {
        return filename;
    }

    public String getFile_route() {
        return file_route;
    }

    public String getSize() {
        return size;
    }

    public List<String> getRules() {
        return rules;
    }
}
