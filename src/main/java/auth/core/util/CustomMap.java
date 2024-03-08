package auth.core.util;

import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;

@NoArgsConstructor
public class CustomMap extends LinkedHashMap<String, Object> {

    public Object put(String key, Object val) {
        return super.put(key, val);
    }

    public String getStr(String key) {
        Object obj = super.get(key);
        return obj == null ? "" : String.valueOf(obj);
    }

    public Long getLong(String key) {
        String val = getStr(key);
        return "".equals(val) ? 0L : Long.parseLong(val);
    }

    public int getInt(String key) {
        String val = getStr(key);
        return "".equals(val) ? 0 : Integer.parseInt(val);
    }
}
