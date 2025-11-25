package spring.bean.def;

import java.util.LinkedHashMap;
import java.util.Map;

public class ConstructorArgumentValues {
    private final Map<Integer, ValueHolder> indexedArgumentValues = new LinkedHashMap<>();

    public void addArgumentValues(ValueHolder[] valueHolders) {
        int size = valueHolders.length;
        for(int i = 0; i < size; i++) {
            indexedArgumentValues.put(i, valueHolders[i]);
        }
    }

    public String[] getConstructorArgumentValueTypeNames() {
        int size = indexedArgumentValues.size();
        String[] constructorArgumentValueTypes = new String[size];

        int index = 0;
        for(Map.Entry<Integer, ValueHolder> entry : indexedArgumentValues.entrySet()) {
            constructorArgumentValueTypes[index] = entry.getValue().getType();
            index++;
        }
        return constructorArgumentValueTypes;
    }

    /**
     * 생성자 파라미터용 클래스
     */
    public static class ValueHolder {
        private final Object value;
        private final String type;
        private final String name;
        private final boolean isAutowirable;

        private ValueHolder(Object value, String type, String name, boolean isAutowirable) {
            this.value = value;
            this.type = type;
            this.name = name;
            this.isAutowirable = isAutowirable;
        }

        public Object getValue() {
            return value;
        }

        public String getType() {
            return type;
        }

        public String getName() {
            return name;
        }

        public boolean isAutowirable() {
            return isAutowirable;
        }

        public static ValueHolder of(Object value, String type, String name, boolean isAutowirable) {
            return new ValueHolder(value, type, name, isAutowirable);
        }
    }
}
